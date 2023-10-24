package bankmanagementsystem;

import java.sql.*;
import javax.swing.JOptionPane;

public class JDBC_Connect {
    
    
    public static Boolean valExists(String uid) {
        Boolean result = false; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "vedant", "vedant@99");
            con.setAutoCommit(true);

            PreparedStatement st = con.prepareStatement("SELECT * FROM ACCOUNTS WHERE UID=?");
            st.setString(1, uid); 

            ResultSet rs = st.executeQuery();


            if (rs.next()) {
                result = true; 
            }

            // Close resources
            rs.close();
            st.close();
            con.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return result;
    }

    
    public static ResultSet connectJDBC(String query, String val1, String val2) {
        ResultSet set = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "vedant", "vedant@99");
            con.setAutoCommit(true);

            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, val1);
            st.setString(2, val2);

            ResultSet rs = st.executeQuery();

            set = rs;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return set;
    }
    
    
    public static int executeUpdateJDBC(String query, String val1, String val2) {
        int rowsAffected = 0;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "vedant", "vedant@99")) {
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, val1);
            st.setString(2, val2);

            rowsAffected = st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return rowsAffected;
    }

    
    
    
    public static String getBalance(String uid){
        String balance = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "vedant", "vedant@99");
            con.setAutoCommit(true);

            PreparedStatement st = con.prepareStatement("SELECT DEPOSITE FROM ACCOUNTS WHERE UID=?;");
            st.setString(1, uid);

            ResultSet rs = st.executeQuery();
            while(rs.next()){
                balance = rs.getString("DEPOSITE");
            }
            
            con.close();
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return balance;
    
    }
    
    
    
    
    public static void transaction(String fromUID, String toUID, String amt){
      try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "vedant", "vedant@99");
            con.setAutoCommit(true);

            PreparedStatement st_from = con.prepareStatement("SELECT DEPOSITE FROM ACCOUNTS WHERE UID=?;");
            st_from.setString(1, fromUID);
            
            PreparedStatement st_to = con.prepareStatement("SELECT DEPOSITE FROM ACCOUNTS WHERE UID=?;");
            st_to.setString(1, toUID);
            
            ResultSet fromRS = st_from.executeQuery();
            ResultSet toRS = st_to.executeQuery();
            float fromBal, toBal, Amt;
            
            Amt = Float.parseFloat(amt);
            
            while(fromRS.next() && toRS.next()){
                fromBal = Float.parseFloat(fromRS.getString("DEPOSITE"));
                toBal = Float.parseFloat(toRS.getString("DEPOSITE"));
                
                if(fromBal >= Amt){
                    fromBal -= Amt;
                    toBal += Amt;
                    

                    PreparedStatement st1 = con.prepareStatement("UPDATE ACCOUNTS SET DEPOSITE=? WHERE UID=?");
                    st1.setString(1, String.valueOf(toBal));
                    st1.setString(2, toUID);

                    PreparedStatement st2 = con.prepareStatement("UPDATE ACCOUNTS SET DEPOSITE=? WHERE UID=?");
                    st2.setString(1, String.valueOf(fromBal));
                    st2.setString(2, fromUID);

                    
                    st1.executeUpdate();
                    st2.executeUpdate();
                }
                
                else{
                    JOptionPane.showMessageDialog(null, "Insufficient Funds, transaction failed!");
                }
            }
            
            con.close();
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }  
    }
    
    
    
    
    public static ResultSet checkBalance(String uid){
        ResultSet set = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "vedant", "vedant@99");
            con.setAutoCommit(true);

            PreparedStatement st = con.prepareStatement("SELECT * FROM ACCOUNTS WHERE UID=?;");
            st.setString(1, uid);
  

            ResultSet rs = st.executeQuery();

            set = rs;
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }

        return set;
        
    
    }   
    
    public static void insert(String aadhar, String panno, String uid, String upin, String name, String email, String phone, String dob, String gender, String account_type, String deposit) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankDB", "vedant", "vedant@99");
            con.setAutoCommit(true); // Check if this line is not explicitly setting auto-commit to false
            
            // Insert data into the database
            PreparedStatement insertStatement = con.prepareStatement("INSERT INTO ACCOUNTS(aadhar_no, pan_no, uid, name, email, phone, dob, gender, account_type, deposite, upin) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            insertStatement.setString(1, aadhar);
            insertStatement.setString(2, panno);
            insertStatement.setString(3, uid);
            insertStatement.setString(4, name);
            insertStatement.setString(5, email);
            insertStatement.setString(6, phone);
            insertStatement.setString(7, dob);
            insertStatement.setString(8, gender);
            insertStatement.setString(9, account_type);
            insertStatement.setString(10, deposit);
            insertStatement.setString(11, upin);
            int i = insertStatement.executeUpdate();
            System.out.println(i + " record(s) inserted.");

            // Retrieve data from the database
            
            con.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
    
}
