package ProfilePreProcessing;

/**
 * Created by keleigong on 1/26/15.
 */
import HTMLDownloader.CSVWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import profilepreprocessing.BlobReader;
//import profilepreprocessing.ProfilePreProcessing;

/**
 *
 * @author SCRC
 */
public class ProfilePreProcessingForCategory
{

	private static ArrayList<String> companyNames = new ArrayList();
	public static void main(String[] args)
	{
		ProfilePreProcessing preprocessor = new ProfilePreProcessing();
		companyNames = getCompanyNames("link_content");

		String srmConteng = new String("");
		//String spmConteng = new String("");
		String susConteng = new String("");
		String lhrConteng = new String("");
		for(int i=0;i<companyNames.size();i++)
		{
			srmConteng="";
			//spmConteng="";
			susConteng="";
			lhrConteng="";

			srmConteng = getCompanyProfile(companyNames.get(i),"SRM");
			//spmConteng = getCompanyProfile(companyNames.get(i),"Spend Management");
			susConteng = getCompanyProfile(companyNames.get(i),"SUS");
			lhrConteng = getCompanyProfile(companyNames.get(i),"LHR");
			String processedSRMProfile = preprocessor.preProcessProfile(srmConteng);
			//String processedSPMProfile = preprocessor.preProcessProfile(spmConteng);
			String processedSUSProfile = preprocessor.preProcessProfile(susConteng);
			String processedLHRProfile = preprocessor.preProcessProfile(lhrConteng);

			CSVWriter.Write("F://SCRC Research//2013 ML//Data_Extraction//e.keyword_category//"+companyNames.get(i)+"_SRM.txt", processedSRMProfile);
			//CSVWriter.Write("F://SCRC Research//2013 ML//Data_Extraction//e.keyword_category//"+companyNames.get(i)+"_SPM.txt", processedSPMProfile);
			CSVWriter.Write("F://SCRC Research//2013 ML//Data_Extraction//e.keyword_category//"+companyNames.get(i)+"_SUS.txt", processedSUSProfile);
			CSVWriter.Write("F://SCRC Research//2013 ML//Data_Extraction//e.keyword_category//"+companyNames.get(i)+"_LHR.txt", processedLHRProfile);


		}
	}

	public static ArrayList<String> getCompanyNames(String tableName)
	{
		ArrayList<String> companyNames = new ArrayList();
		String sql = "select DISTINCT company from "+tableName;

		ResultSet rs=null;
		Connection  conn;
		try
		{
			conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/test3","root","");
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				companyNames.add(rs.getString("company"));
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(BlobReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return companyNames;
	}

	//select content from link_content_233,link_category_2013 WHERE
	//link_content_233.company=link_category_2013.company
	//and link_content_233.link=link_category_2013.link
	//and link_content_233.company='V.F. CORP'
	//and link_category_2013.category LIKE "%SUS%"
	private static String getCompanyProfile(String companyName,String category)
	{
		String companyProfile = new String("");
		String sql = "select content from link_content_2013,link_category WHERE link_content_2013.company=link_category_2013.company and link_content_2013.link=link_category_2013.link and link_content_2013.company='"+companyName+"' and link_category_2013.category LIKE \"%"+category+"%\"";
		System.out.println(sql);
		ResultSet rs=null;
		Connection  conn;
		try
		{
			conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/ml","root","root");
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				Blob b = rs.getBlob("content");

				//int size = (int) b.length();
				//System.out.println("size:"+size);
				InputStream in = b.getBinaryStream();

				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null)
				{
					buffer.append(line+"\n");
				}
				companyProfile = companyProfile+buffer.toString();

				in.close();
			}
			conn.close();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(BlobReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (IOException ex)
		{
			Logger.getLogger(ProfilePreProcessing.class.getName()).log(Level.SEVERE, null, ex);
		}
		return companyProfile;
	}



}
