package ProfilePreProcessing.DBInsertor;

import java.sql.*;

public class DBInsertor 
{

    public static void InsertRecord(PreProcessedContent record)
    {

           // 驱动程序名
           String driver = "com.mysql.jdbc.Driver";

           // URL指向要访问的数据库名scutcs
           String url = "jdbc:mysql://127.0.0.1:3306/TextExtraction";

           // MySQL配置时的用户名
           String user = "root"; 

           // MySQL配置时的密码
           String password = "";

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
                String sql = "insert into pre_processed_content_543 values ('"+record.getCompanyName().replace("'","")+"','"+record.getContent().replace("'","")+"')";
    
                statement.executeUpdate(sql);
            
                conn.close();

           } 
           catch(ClassNotFoundException e) 
           {


            //System.out.println("insert into link_content values ('"+record.getCompanyName()+"','"+record.getLink()+"','"+record.getContent()+"')");
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