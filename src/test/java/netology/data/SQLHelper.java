package netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASSWORD = "pass";
    private static final QueryRunner runner = new QueryRunner();

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static DataHelper.VerificationCode getVerificationCode() throws SQLException {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(code);
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        try (var connection = getConn()) {
            runner.execute(connection, "DELETE FROM auth_codes");
            runner.execute(connection, "DELETE FROM card_transactions");
            runner.execute(connection, "DELETE FROM cards");
            runner.execute(connection, "DELETE FROM users");
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
}