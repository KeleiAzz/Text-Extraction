package DownloadContentInsertor;

import java.sql.*;

public class DBInsertor 
{

    public static void InsertRecord(RecordContent record)
    {

           // 驱动程序名
           String driver = "com.mysql.jdbc.Driver";

           // URL指向要访问的数据库名scutcs
           String url = "jdbc:mysql://127.0.0.1:3306/ml";
//	        String url = "jdbc:mysql://152.1.153.12:3306/ml";

			String table = "manual_link_content_2014";
           // MySQL配置时的用户名
           String user = "root"; 

           // MySQL配置时的密码
           String password = "1423";

           try 
           { 
                // 加载驱动程序
                Class.forName(driver);

                // 连续数据库
                Connection conn = DriverManager.getConnection(url, user, password);

                if(conn.isClosed()) 
                 System.out.println("Failed in connecting to the Database!");

                // statement用来执行SQL语句
                Statement statement = conn.createStatement();

                // 要执行的SQL语句
                String sql = "insert into " + table + " values ('"+record.getCompanyName().replace("'","")+"','"+record.getLink().replace("'","")+"','"+record.getContent().replace("'","")+"')";
    
                statement.executeUpdate(sql);
            
                conn.close();

           } 
           catch(ClassNotFoundException e) 
           {


            System.out.println("insert into " + table + " values ('"+record.getCompanyName()+"','"+record.getLink()+"','"+record.getContent()+"')");
            e.printStackTrace();


           } 
           catch(SQLException e) 
           {

            //System.out.println("insert into link_content values ('"+record.getCompanyName().replace("'","")+"','"+record.getLink().replace("'","")+"','"+record.getContent().replace("'","")+"')");
            
            e.printStackTrace();


           } 
           catch(Exception e) 
           {

            //System.out.println("insert into link_content values ('"+record.getCompanyName()+"','"+record.getLink()+"','"+record.getContent()+"')");
            
            e.printStackTrace();


           } 
    } 
}