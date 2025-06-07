package mode;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    public SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static DbUtils.VerificationCode getVerificationCode() {
        var requestSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            return QUERY_RUNNER.query(conn, codeSQL, new BeanHandler<>(DbUtils.VerificationCode.class));
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var conn = getConn();
        QUERY_RUNNER.execute(conn, "DELETE FROM auth_codes");
        QUERY_RUNNER.execute(conn, "DELETE FROM card_transactions");
        QUERY_RUNNER.execute(conn, "DELETE FROM cards");
        QUERY_RUNNER.execute(conn, "DELETE FROM users");
    }
}

@SneakyThrows
public static void cleanAuthCodes() {
    try (var conn = SQLHelper.getConn()) {
        SQLHelper.QUERY_RUNNER.execute(conn, "DELETE FROM auth_codes");
    }
 }

}