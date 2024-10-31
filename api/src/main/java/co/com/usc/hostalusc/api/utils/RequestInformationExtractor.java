package co.com.usc.hostalusc.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestInformationExtractor {

  private static final String PATH = "path";
  private static final String METHOD = "method";
  private static final String HEADERS = "headers";
  private static final String BODY = "body";
  private static final String REQUEST_PARAMS = "request_params";

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String extractInformation(HttpServletRequest request, Object requestBody) {
    if (Objects.isNull(request)) {
      String message = "Request is null";
      return convertObjectToJson(message).toString();
    }

    Map<String, Object> info = new HashMap<>();

    info.put(PATH, request.getRequestURI());
    info.put(METHOD, request.getMethod());
    info.put(HEADERS, getHeadersFromRequest(request));
    info.put(BODY, requestBody);
    info.put(REQUEST_PARAMS, request.getQueryString());

    return convertObjectToJson(info).toString();
  }

  private static Map<String, String> getHeadersFromRequest(HttpServletRequest request) {
    if (Objects.isNull(request.getHeaderNames())) {
      return Collections.emptyMap();
    }

    return Collections.list(request.getHeaderNames())
        .stream()
        .collect(Collectors.toMap(headerName -> headerName, request::getHeader));
  }

  private static Object convertObjectToJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      return object;
    }
  }
}
