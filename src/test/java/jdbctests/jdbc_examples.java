package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;

public class jdbc_examples {
    //connections strings
    String dbUrl = "jdbc:oracle:thin:@3.95.190.225:1521:xe";
    String dbUsername="hr";
    String dbPassword="hr";


    @Test
    public void test1() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select region_name from regions");

        while (resultSet.next()){
             String regionName=resultSet.getString("region_name");
            System.out.println(regionName);
        }

        resultSet = statement.executeQuery("Select * from countries");
        while(resultSet.next()){
            System.out.println(resultSet.getString(2));
        }

        //close connection
        resultSet.close();
        statement.close();
        connection.close();

    }

    @Test
    public void CountandNavigate() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from departments");
        //how to find how many record(rows) i have for the query
        //go to last row
        resultSet.last();
        //get the row count
        int rowCount = resultSet.getRow();
        System.out.println("rowCount = " + rowCount);
        System.out.println(resultSet.getString("department_name"));
        //we need to move before the first row to get full list since we are at the last row right now
        resultSet.beforeFirst();
        System.out.println("---------------While loop starts-------------");
        while (resultSet.next()){
            System.out.println(resultSet.getString("department_name"));
        }
        //close connection
        resultSet.close();
        statement.close();
        connection.close();

    }

    @Test
    public void metada() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from departments");

        DatabaseMetaData  dbMetada=connection.getMetaData();

        resultSet.close();
        statement.close();
        connection.close();

    }



}
