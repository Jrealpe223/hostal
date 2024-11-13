package co.com.usc.hostalusc.api.filter;

import co.com.usc.hostalusc.api.utils.CurrentUserPrincipal;
import co.com.usc.hostalusc.api.utils.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private String secret;

    public JWTAuthorizationFilter(String secret) {
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request.getRequestURI().contains("/v1/users/login") ||
            request.getRequestURI().contains("swagger-ui.html") ||
            request.getRequestURI().contains("swagger-ui/") ||
            request.getRequestURI().contains("/v3/api-docs") ||
            request.getRequestURI().contains("/favicon.ico")
        ) {
            chain.doFilter(request, response);
            return;
        }
        try {

            if (existeJWTToken(request, response)) {
                Claims claims = validateToken(request);

                if (claims.get("userId") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            System.out.println("erro jwauthoritation filter: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
            return;
        }
    }

    private Claims validateToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER);
        System.out.println("Token recibido: " + jwtToken);
        jwtToken = jwtToken.replace(PREFIX, "");
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwtToken).getBody();
    }

    /**
     * Metodo para autenticarnos dentro del flujo de Spring
     *
     * @param claims
     */
    private void setUpSpringAuthentication(Claims claims) {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        String userId = (String) claims.get("userId");
        Boolean isAdmin = (Boolean) claims.get("isAdmin");
        if(isAdmin){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(CurrentUserPrincipal.builder()
                .userId(Objects.nonNull(userId) ? Long.parseLong(userId) : null)
                .isAdmin(isAdmin)
                .build(), null,authorities
                );
        System.out.println(auth);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private boolean existeJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)){
            return false;
        }
        return true;
    }

}