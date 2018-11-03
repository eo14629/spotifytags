package actions;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectDb {
    private Connection c;

    private ConnectDb () {}
    public ConnectDb (String databaseName) {
        connect(databaseName);
    }

    private void connect(String databaseName) {
        String dbURL = "jdbc:mysql://localhost:3307/" + databaseName;
        String user = "root";
        String password = "root";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(dbURL, user, password);
        } catch (ClassNotFoundException ce) {
            System.err.println(ce.getCause().getMessage());
        } catch (SQLException se) {
            System.err.println(se.getCause().getMessage());
        }
    }

    public Connection getConn() {
        return c;
    }

    public static void main(String[] args) {
        ConnectDb program = new ConnectDb();
        program.test();
    }

    private void claim(boolean b) { if (!b) throw new Error("Test failure"); }

    private void test() {
        ConnectDb db = new ConnectDb("spotify");
    }
}
