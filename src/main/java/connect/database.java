package connect;

import java.sql.Connection;
import java.sql.DriverManager;
public class database {
    public static Connection connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/cafe", "root", "");
            System.out.println("Ket noi thanh cong");
            return connect;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Ket noi that bai ");
            return null;
        }
    }
}
