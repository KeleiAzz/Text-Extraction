package ProfilePreProcessing;

/**
 * Created by keleigong on 1/26/15.
 */
import HTMLDownloader.CSVWriter;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;

import java.io.*;
import java.sql.*;
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
	public static void main(String[] args) throws FileNotFoundException {
		ProfilePreProcessing preprocessor = new ProfilePreProcessing();
		companyNames = getCompanyNames("link_content_2014_KWE");
		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/third run/";
		String srmConteng = new String("");
		//String spmConteng = new String("");
		String susConteng = new String("");
		String lhrConteng = new String("");

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ml", "root", "");
			for(int i=0;i<companyNames.size();i++)
			{
				srmConteng = getCompanyProfile(companyNames.get(i),"SRM",conn);
				//spmConteng = getCompanyProfile(companyNames.get(i),"Spend Management");
				susConteng = getCompanyProfile(companyNames.get(i),"SUS",conn);
				lhrConteng = getCompanyProfile(companyNames.get(i),"LHR",conn);
				String processedSRMProfile = preprocessor.preProcessProfile(srmConteng);
				//String processedSPMProfile = preprocessor.preProcessProfile(spmConteng);
				String processedSUSProfile = preprocessor.preProcessProfile(susConteng);
				String processedLHRProfile = preprocessor.preProcessProfile(lhrConteng);

				CSVWriter.Write(filepath + "e.keyword_category/"+companyNames.get(i)+"_SRM.txt", processedSRMProfile);
				//CSVWriter.Write("F://SCRC Research//2013 ML//Data_Extraction//e.keyword_category//"+companyNames.get(i)+"_SPM.txt", processedSPMProfile);
				CSVWriter.Write(filepath + "e.keyword_category/"+companyNames.get(i)+"_SUS.txt", processedSUSProfile);
				CSVWriter.Write(filepath + "e.keyword_category/"+companyNames.get(i)+"_LHR.txt", processedLHRProfile);


			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<String> getCompanyNames(String tableName)
	{
		ArrayList<String> companyNames = new ArrayList();
		String sql = "select DISTINCT company_name from "+tableName; //+" LIMIT 113,312";
//
		ResultSet rs=null;
		Connection  conn;
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ml","root","");
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				companyNames.add(rs.getString("company_name"));
			}
			conn.close();
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
	private static String getCompanyProfile(String companyName,String category,Connection conn)
	{
		String companyProfile = new String("");
		String sql = "select content from link_content_2014_KWE,link_category_2014 WHERE link_content_2014_KWE.company_name=link_category_2014.company_name and link_content_2014_KWE.link=link_category_2014.link and link_content_2014_KWE.company_name='"+companyName+"' and link_category_2014.category LIKE \"%"+category+"%\"";
		System.out.println(sql);
		ResultSet rs=null;
//		Connection  conn;
		try
		{
//			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/TextExtraction", "root", "");
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
//			conn.close();
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
