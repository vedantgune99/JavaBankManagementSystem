package bankmanagementsystem;

import java.sql.*;

public class JDBC_Connect {
    public static void insert(String aadhar, String panno, String uid,String upin, String name, String email, String phone, String dob, String gender, String account_type, String deposit) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "vedant", "vedant@99");
            con.setAutoCommit(true); // Check if this line is not explicitly setting auto-commit to false
            
            // Insert data into the database
            PreparedStatement insertStatement = con.prepareStatement("INSERT INTO ACCOUNTS(aadhar_no, pan_no, uid, upin, name, email, phone, dob, gender, account_type, deposite) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            insertStatement.setString(1, aadhar);
            insertStatement.setString(2, panno);
            insertStatement.setString(3, uid);
            insertStatement.setString(4, upin);
            insertStatement.setString(5, name);
            insertStatement.setString(6, email);
            insertStatement.setString(7, phone);
            insertStatement.setString(8, dob);
            insertStatement.setString(9, gender);
            insertStatement.setString(10, account_type);
            insertStatement.setString(11, deposit);
            int i = insertStatement.executeUpdate();
            System.out.println(i + " record(s) inserted.");

            // Retrieve data from the database
            
            con.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
    
}
