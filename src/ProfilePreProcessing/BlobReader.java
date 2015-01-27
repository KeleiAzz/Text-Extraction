package ProfilePreProcessing;

/**
 * Created by keleigong on 1/26/15.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCRC
 */
public class BlobReader
{
	public static void main(String args[])
	{

		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名scutcs
		String url = "jdbc:mysql://127.0.0.1:3306/test3";

		// MySQL配置时的用户名
		String user = "root";

		// MySQL配置时的密码
		String password = "";

		ResultSet rs=null;
		Connection  conn;
		try
		{
			conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/test3","root","");
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery("select * from link_content_233 where link ='http://www.abbott.com/static/cms_workspace/content/document/Citizenship/Abbott_Green_Purchasing_Policy.pdf'");
			if (rs.next())
			{
				Blob b = rs.getBlob("content");

				int size = (int) b.length();
				System.out.println("size:"+size);
				InputStream in = b.getBinaryStream();

				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null)
				{
					buffer.append(line);
				}
				System.out.println(buffer.toString());

				in.close();
				//sos.flush();

			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(BlobReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (IOException ex)
		{
			Logger.getLogger(BlobReader.class.getName()).log(Level.SEVERE, null, ex);
		}



	}

}
