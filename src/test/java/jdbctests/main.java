package jdbctests;

import java.sql.*;

public class main {
    public static void main(String[] args) throws SQLException {

        //connections strings
        String dbUrl = "jdbc:oracle:thin:@3.95.190.225:1521:xe";
        String dbUsername="hr";
        String dbPassword="hr";

        //create connection to database
        Connection connection= DriverManager.getConnection(dbUrl,dbUsername,dbPassword);

        //create statement object
        Statement statement=connection.createStatement();

        ResultSet resultSet=statement.executeQuery("Select * from employees");

        //move pointer to next row
//        resultSet.next();
//        System.out.println(resultSet.getString("region_name"));

        while(resultSet.next()){
            System.out.println(resultSet.getString(2)+"-"+
                    resultSet.getString(3)+"-"+
                    resultSet.getInt("salary")) ;
            System.out.println(resultSet.getObject("salary"));
        }



        //close connection
        resultSet.close();
        statement.close();
        connection.close();




    }
}
