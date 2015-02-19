package ProfilePreProcessing;

/**
 * Created by keleigong on 1/26/15.
 */
import ProfilePreProcessing.DBInsertor.DBInsertor;
import ProfilePreProcessing.DBInsertor.PreProcessedContent;
import HTMLDownloader.CSVWriter;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ProfilePreProcessing.nlpPreProcessing.DictionaryItemReplacer;
import ProfilePreProcessing.nlpPreProcessing.NumberEliminator;
import ProfilePreProcessing.nlpPreProcessing.SpecialCharReplacer;
import ProfilePreProcessing.nlpPreProcessing.Stemmer;
import ProfilePreProcessing.nlpPreProcessing.StopWordEliminator;
import ProfilePreProcessing.nlpPreProcessing.UpperLetterFlipper;
import ProfilePreProcessing.nlpPreProcessing.WordListChecker;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import RiTaTest.DictTest;
/**
 *
 * @author SCRC
 */
public class ProfilePreProcessing
{
	//private WordListChecker checker;
	private DictionaryItemReplacer dicReplacer;
	private StopWordEliminator stopWordEliminator;
	private Stemmer stemmer;
	private DictTest test;
	public ProfilePreProcessing() throws FileNotFoundException {
		//checker = new WordListChecker("D:\\wordlist.txt");
		dicReplacer = new DictionaryItemReplacer();
		stopWordEliminator = new StopWordEliminator();
		String propsFile = "/Users/keleigong/Dropbox/Java/SCRC_Text_Extraction/src/ProfilePreProcessing/file_properties.xml";

		try {
//			String propsFile = "/Users/keleigong/Dropbox/Java/SCRC_Text_Extraction/src/ProfilePreProcessing/file_properties.xml";
			JWNL.initialize(new FileInputStream("/users/keleigong/Dropbox/Java/SCRC_Text_Extraction/src/RiTaTest/file_properties.xml"));
		} catch (JWNLException e) {
//			e.printStackTrace();
		}

//		stemmer = new Stemmer();
		test = new DictTest();


	}

	/**
	 * @param args the command line arguments
	 */
	public void main(String[] args)
	{
		ArrayList<String> companyNames = new ArrayList();
		companyNames = getCompanyNames();
		System.out.println("there r "+companyNames.size()+"companies");

		for(int i=0;i<companyNames.size();i++)
		{
			String companyProfile = getCompanyProfile(companyNames.get(i));
			String processedProfile = preProcessProfile(companyProfile);
			DBProfileInsert(companyNames.get(i),processedProfile);
			CSVWriter.Write("d:\\processed_profiles\\"+companyNames.get(i)+".txt", processedProfile);
		}

	}

	//get company names from DB
	private static ArrayList<String> getCompanyNames()
	{
		ArrayList<String> companyNames = new ArrayList();
		String sql = "select DISTINCT company_name from link_content_2014";

		ResultSet rs=null;
		Connection  conn;
		try
		{
			conn = java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/TextExtraction","root","");
			Statement stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while (rs.next())
			{
				companyNames.add(rs.getString("company_name"));
			}
		}
		catch (SQLException ex)
		{
			Logger.getLogger(BlobReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return companyNames;
	}

	//querry DB and get all the profiles for this company
	private static String getCompanyProfile(String companyName)
	{
		String companyProfile = new String("");
		String sql = "select content from link_content_2014 where company='"+companyName+"'";
		ResultSet rs=null;
		Connection  conn;
		try
		{
			conn = java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/TextExtraction","root","");
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
		} catch (IOException ex) {
			Logger.getLogger(ProfilePreProcessing.class.getName()).log(Level.SEVERE, null, ex);
		}
		return companyProfile;
	}

	//NLP preprocess company Profiles
	public  String preProcessProfile(String companyProfile)
	{
		//split the content first instead of

		String[] splittedProfile = companyProfile.split("\n");
		for(int i=0;i<splittedProfile.length;i++)
		{
			splittedProfile[i]=SpecialCharReplacer.ReplaceSpecialChar(splittedProfile[i]);
			splittedProfile[i]=UpperLetterFlipper.upperFlip(splittedProfile[i]);
			splittedProfile[i]=stopWordEliminator.deleteStopWords(splittedProfile[i]);
			splittedProfile[i]=dicReplacer.replaceDictionaryItems(splittedProfile[i]);
			splittedProfile[i]=NumberEliminator.EliminateNumber(splittedProfile[i]);
			splittedProfile[i]=SpecialCharReplacer.ReplaceComma(splittedProfile[i]);
			splittedProfile[i]=stopWordEliminator.deleteStopWords(splittedProfile[i]);
			splittedProfile[i]=SpecialCharReplacer.LeaveEngLetter(splittedProfile[i]);
			splittedProfile[i]=SpecialCharReplacer.ReplaceComma(splittedProfile[i]);
//			splittedProfile[i]=stemmer.StemCompanyContent(splittedProfile[i]);
			splittedProfile[i]=test.StemCompanyContent(splittedProfile[i]);
			//splittedProfile[i]=checker.checkCompanyContent(splittedProfile[i]);
		}

		String combinedProfile = new String("");
		for(int i=0;i<splittedProfile.length;i++)
		{
			if(splittedProfile[i].length()<=1)
			{
				continue;
			}
			combinedProfile=combinedProfile+splittedProfile[i];
		}

		combinedProfile=SpecialCharReplacer.ReplaceComma(combinedProfile);
		combinedProfile=SpecialCharReplacer.ReplaceEnter(combinedProfile);

		return combinedProfile;
	}

	//Insert preprocessed company profiles into DB
	private static void DBProfileInsert(String companyName, String processedProfile)
	{
		PreProcessedContent record = new PreProcessedContent(companyName,processedProfile);
		DBInsertor.InsertRecord(record);
	}
}