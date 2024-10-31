package co.com.usc.hostalusc.repository.datasource;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Jorge Villada
 * @since 05/03/2023
 */
public class TenantAwareHikariDataSource extends HikariDataSource {

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();

      //  try (Statement sql = connection.createStatement()) {
       //     sql.execute("SET app.current_company = '" + TenantContext.getCurrentTenant() + "'");
        //}

        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = super.getConnection(username, password);

      //  try (Statement sql = connection.createStatement()) {
        //    sql.execute("SET app.current_company = '" + TenantContext.getCurrentTenant() + "'");
        //}

        return connection;
    }

    /*private void setTenant(Connection connection) throws SQLException {
        String tenantId = TenantContext.getCurrentTenant();
        if (tenantId != null) {
            try (Statement sql = connection.createStatement()) {
                sql.execute("SET app.current_company = '" + tenantId + "'");
            }
        }
    }*/

}