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
	public static String table_name = "link_content_2015_sentences";
//	public static
	private static ArrayList<String> companyNames = new ArrayList();
	public static void main(String[] args) throws FileNotFoundException {
		ProfilePreProcessing preprocessor = new ProfilePreProcessing();
		companyNames = getCompanyNames(table_name);
		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/auto-rating/7th_sentence/";
		String ssContent = new String("");
		String smContent = new String("");
		String cmContent = new String("");
		String srmContent = new String("");
		//String spmConteng = new String("");
		String susContent = new String("");
		String lhrContent = new String("");

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ml_2015", "root", "1423");
			for(int i=0;i<companyNames.size();i++)
			{
				ssContent = getCompanyProfile(companyNames.get(i),"SS",conn);
				smContent = getCompanyProfile(companyNames.get(i),"SM",conn);
				cmContent = getCompanyProfile(companyNames.get(i),"CM",conn);
				srmContent = getCompanyProfile(companyNames.get(i),"SRM",conn);
				susContent = getCompanyProfile(companyNames.get(i),"ES",conn);
				lhrContent = getCompanyProfile(companyNames.get(i),"LHR",conn);

				String processedSSProfile = preprocessor.preProcessProfile(ssContent);
				String processedSMProfile = preprocessor.preProcessProfile(smContent);
				String processedCMProfile = preprocessor.preProcessProfile(cmContent);
				String processedSRMProfile = preprocessor.preProcessProfile(srmContent);
				String processedSUSProfile = preprocessor.preProcessProfile(susContent);
				String processedLHRProfile = preprocessor.preProcessProfile(lhrContent);

				CSVWriter.Write(filepath + "e.keyword_category/"+companyNames.get(i)+"_SS.txt", processedSSProfile);
				CSVWriter.Write(filepath + "e.keyword_category/"+companyNames.get(i)+"_SM.txt", processedSMProfile);
				CSVWriter.Write(filepath + "e.keyword_category/"+companyNames.get(i)+"_CM.txt", processedCMProfile);
				CSVWriter.Write(filepath + "e.keyword_category/"+companyNames.get(i)+"_SRM.txt", processedSRMProfile);
				//CSVWriter.Write("F://SCRC Research//2013 ML//Data_Extraction//e.keyword_category//"+companyNames.get(i)+"_SPM.txt", processedSPMProfile);
				CSVWriter.Write(filepath + "e.keyword_category/"+companyNames.get(i)+"_ES.txt", processedSUSProfile);
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
		String sql = "select DISTINCT company from "+tableName; //+" LIMIT 113,312";
//
		ResultSet rs=null;
		Connection  conn;
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ml_2015","root","1423");
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				companyNames.add(rs.getString("company"));
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
		String sql;
		if(category.equals("ALL"))
		{
//			 sql = "select content from manual_link_content_sentences WHERE " +
//					"manual_link_content_sentences.company_name='"+companyName+"'";
			sql = String.format("select content from %s where company = \"%s\"", table_name, companyName);
		}
		else
		{
//			 sql = "select content from manual_link_content_2014,link_category_2014 WHERE manual_link_content_2014.company_name=link_category_2014.company_name and manual_link_content_2014.link=link_category_2014.link and manual_link_content_2014.company_name='"+companyName+"' and link_category_2014.category LIKE \"%"+category+"%\"";
			sql = String.format("select content from %s where company = \"%s\" and categories LIKE \"%%%s%%\"", table_name, companyName, category);
		}

//		String sql = "select content from manual_link_content_2014 WHERE " +
//				"manual_link_content_2014.company_name='"+companyName+"'";
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
