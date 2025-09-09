package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Update user/password to match the DB user you created above
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "studentuser"; // or "root" if you prefer
    private static final String PASS = "studentpass"; // change to your password

    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // ensures driver is loaded
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
        return conn;
    }
}
