package FormatKeywords;

/**
 * Created by keleigong on 1/26/15.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import java.util.regex.*;

class FormatKeywords{

	public static void main(String[] args) throws IOException
	{
		FormatKeywords fk = new FormatKeywords();
		//output file
//		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/";
//		PrintWriter csvfile = new PrintWriter(new FileWriter(filepath + "f.profiles/profiles_2013_SRM_no_spell_check.csv"));
//		csvfile.append("Company Name"); csvfile.append(',');
//		csvfile.append("Profile keywords"); csvfile.append(',');
//		csvfile.append('\n'); csvfile.close();


		//extract keywrods
		fk.extractKeywords();

		//testing spell checker
//		System.out.println(fk.spellChecking("cornerstonesupplier"));

//		fk.spellCheckDocument();


	}

	public void writeFiles(String companyName, String text, int category) throws IOException{
		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/auto-rating/7th_sentence/";
		PrintWriter csvfile;
//		if (cagtegory.equals("SRM")){
//			csvfile = new PrintWriter(new FileWriter(filepath + "f.profiles/profiles_2013_SRM_no_spell_check.csv", true));
//			csvfile.append(companyName); csvfile.append(',');
//			csvfile.append(text); csvfile.append(',');
//			csvfile.append('\n'); csvfile.close();
//		}
		switch (category)
		{
			case 1:
				System.out.println("For SRM");
				csvfile = new PrintWriter(new FileWriter(filepath + "f.profiles/profiles_2014_SRM_spell_check.csv", true));
				csvfile.append(companyName); csvfile.append(',');
				csvfile.append(text); csvfile.append(',');
				csvfile.append('\n'); csvfile.close();
				break;
			case 2:
				System.out.println("For LHR");
				csvfile = new PrintWriter(new FileWriter(filepath + "f.profiles/profiles_2014_LHR_spell_check.csv", true));
				csvfile.append(companyName); csvfile.append(',');
				csvfile.append(text); csvfile.append(',');
				csvfile.append('\n'); csvfile.close();
				break;
			case 3:
				System.out.println("For SUS");
				csvfile = new PrintWriter(new FileWriter(filepath + "f.profiles/profiles_2014_SUS_spell_check.csv", true));
				csvfile.append(companyName); csvfile.append(',');
				csvfile.append(text); csvfile.append(',');
				csvfile.append('\n'); csvfile.close();
				break;
		}
//		csvfile.append(companyName); csvfile.append(',');
//		csvfile.append(text); csvfile.append(',');
//		csvfile.append('\n'); csvfile.close();
	}


	public void extractKeywords() throws IOException
	{
		FormatKeywords fk = new FormatKeywords();
		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/sixth run/";
		String folder = filepath + "e.keyword_category";
		File[] files = new File(folder).listFiles();
		Pattern p = Pattern.compile("^[A-Za-z]+$", Pattern.CASE_INSENSITIVE);
		int category = 0;
		Matcher m;
		for(File f: files)
		{
			if(f.isFile())
			{
//				System.out.println(f.getName());
				if (f.getName().contains("_SRM"))
				{
					category = 1;
				}
				if (f.getName().contains("_LHR"))
				{
					category = 2;
				}
				if (f.getName().contains("_SUS"))
				{
					category = 3;
				}
				System.out.println(f.getName() + " " + category);
				BufferedReader bf = new BufferedReader(new FileReader(f));
				String text = "";//holds one company's profile
				String temp;
				while((temp = bf.readLine()) != null)
				{
					temp = temp.replaceAll(",", " ");
					temp = temp.replaceAll("\n", " ");
					temp = temp.replaceAll("\r", " ");
//					text = text+" "+(temp.toString()).toLowerCase();
					StringTokenizer st = new StringTokenizer(temp);
					while(st.hasMoreTokens())
					{
						String tok = st.nextToken();
						m = p.matcher(tok);
						if(m.matches() == true)
						{
							//tok = fk.spellChecking(tok);
							if(tok==null)
							{
								System.out.println(tok.toString()+"  may not be typo");
							}
							else if(tok.length() < 30)
							{
								//System.out.println(tok.toString());
								text = text+" "+tok.toString();
							}
						}
					}
				}
				String name = f.getName().replaceAll(",", "");
				//if you use profiles by 3 category please uncomment next line
				//name = name.substring(0, name.lastIndexOf("_"));
				fk.writeFiles(name, text, category);
			}
		}
	}
}