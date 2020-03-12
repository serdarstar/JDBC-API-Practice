package jdbctests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import utilities.DBUtils;
import utilities.DBUtils_Spartans;
import utilities.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class study {
    //connections strings
    String dbUrl = "jdbc:oracle:thin:@3.95.190.225:1521:xe";
    String dbUsername="hr";
    String dbPassword="hr";
//    WebDriver driver= Driver.get();


    @Test
    public void test1() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select department_id from departments");


       // Tabloyu yazdirmanin 1. yolu

//        List<String> dept=new ArrayList<>();
//        resultSet.next();
//        Burada row numberi ogreniyoruz
//        resultSet.last();
//        int row=resultSet.getRow();
//        System.out.println(row);
//
//        for (int i = 0; i < 26; i++) {
//            dept.add(resultSet.getString("DEPARTMENT_ID"));
//            resultSet.next();
//        }
//        System.out.println(dept);

//      Tabloyu yazdirmanin 2. yolu

        while (resultSet.next()){
            System.out.println("resultSet = " + resultSet.getObject("DEPARTMENT_ID"));
        }
        resultSet.last();
        System.out.println("resultSet.getString(\"DEPARTMENT_ID\") = " + resultSet.getString("DEPARTMENT_ID"));

        DatabaseMetaData databaseMetaData=connection.getMetaData();
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();




        System.out.println(resultSetMetaData.getColumnCount());
        System.out.println(databaseMetaData.getUserName());
        System.out.println(databaseMetaData.getDatabaseProductVersion());


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

    @Test
    public void dynamic() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from regions");
//        resultSet.absolute(2);
//        System.out.println(resultSet.getString(2));
//
        System.out.println(resultSet.getRow());
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
        resultSetMetaData.getColumnCount();
        resultSet.next();

        DBUtils.createConnection();
        String query="select * from regions";
        List<Map<String, Object>> data= DBUtils.getQueryResultMap(query);

        System.out.println(data);
//        DBUtils.getQueryResultList(query);


        Map<String, Object> row=new HashMap<>();
        row.put(resultSetMetaData.getColumnName(1),resultSet.getString(1));
        row.put(resultSetMetaData.getColumnName(2),resultSet.getString(2));
        for (Object s : row.values()) {
            System.out.println(s);

        }

//        DBUtils.getQueryResultList();

//        resultSet.next();
//        Map<String, Object> row2=new HashMap<>();
//        row2.put(resultSetMetaData.getColumnName(1),resultSet.getString(1));
//        row2.put(resultSetMetaData.getColumnName(2),resultSet.getString(2));
//        data.add(row);
//        data.add(row2);
//        System.out.println(data.toString());
//
//        while(resultSet.next()){
//
//            Map<String, Object> row3=new HashMap<>();
//
//            for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
//                 row3.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
//
//            }
//            data.add(row3);
//            System.out.println(row3.toString());
//
//        }



    }

    @Test
    public void SQL() throws SQLException {

        System.out.println(".................. DATABASE TEST WITH JDBC..............................................");
        String query = "select * from spartans order by spartan_id asc";
        String dbUrl = "jdbc:oracle:thin:@3.95.190.225:1521/xe";
        String dbUsername = "SP";
        String dbPassword = "SP";
        Connection connection=DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet=statement.executeQuery(query);

//        System.out.println("resultSet.getString(\"PHONE\") = " + resultSet.getString("PHONE"));

        resultSet.last();
        int rowNumber= resultSet.getRow();
        resultSet.absolute(rowNumber);
        String lastPhone=resultSet.getString("PHONE");


//        //save the query result as a list of maps(just like we did together)
//        Map<String, Object> rowMap = DBUtils_Spartans.getRowMap(query);
//        System.out.println(rowMap.get("SPARTAN_ID"));
//        //close connection
        DBUtils.destroy();






    }
    @Test
    public void test2(){
        //create the connection to db
        DBUtils_Spartans.createConnection();
        String query = "select spartan_id from spartans where phone=3144144953";
        //save the query result as a list of maps(just like we did together)
        Map<String, Object> rowMap = DBUtils_Spartans.getRowMap(query);
        System.out.println(rowMap);
        //close connection
        DBUtils.destroy();
    }


}
