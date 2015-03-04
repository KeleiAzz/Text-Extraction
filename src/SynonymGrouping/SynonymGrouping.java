package SynonymGrouping;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by keleigong on 3/1/15.
 */
public class SynonymGrouping {
	public static void main(String[] args) throws IOException {
		// TODO code application logic here
		SynonymGrouping sg = new SynonymGrouping();
		sg.extractKeywords();
//		String[] wr = new String[3];
//		String re = "";
//		String path = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/second run/f.profiles/";
//		wr[0] = "profiles_2014_LHR_spell_check.csv";
//		wr[1] = "profiles_2014_SRM_spell_check.csv";
//		wr[2] = "profiles_2014_SUS_spell_check.csv";


		//wr = CSVReader.Read("C:\\Users\\SCRC\\Dropbox\\Sharing\\Basic Unsupervised Model\\Stage 1\\LSA-R-code-in-out\\keyword-company-profiles\\profiles10.csv");

//		for (int i=0; i<1;i++){
//			re = sc.SynonymCompare(CSVReader.Read("/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/second run/unsupervised learning/first run/1_SM_original.csv"));
//			re = sc.SynonymCompare("firm");
//			System.out.println(re);
//			System.out.println(re);
//			System.out.println(wr);
//			CSVWriter.Write("f://profiles10_nosyn.csv",re);
//			CSVWriter.Write(path + "Synonym_" + wr[i],re);
//		}
//		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/second run/";
//		String folder = filepath + "e.keyword_category2";
//		File[] files = new File(folder).listFiles();
//		Pattern p = Pattern.compile("^[A-Za-z]+$", Pattern.CASE_INSENSITIVE);
//		int category = 0;
//		Matcher m;
//		for(File f: files)
//		{
//			if(f.isFile())
//			{
//				System.out.println(f.getName() + " " + category);
//				BufferedReader bf = new BufferedReader(new FileReader(f));
//				String text = "";//holds one company's profile
//				String temp;
//				while((temp = bf.readLine()) != null)
//				{
////					temp = temp.replaceAll(",", " ");
//					temp = temp.replaceAll("\n", " ");
//					temp = temp.replaceAll("\r", " ");
//					String[] splitted = temp.split(",");
//					String res = "";
////					ArrayList<String> res = new ArrayList<String>();
////					text = text+" "+(temp.toString()).toLowerCase();
//					for (int i = 0; i < splitted.length; i++){
////						res.add(sc.SynonymCompare(splitted[i]));
//						res = res + sc.SynonymCompare(splitted[i]) + ",";
//					}
////					temp = ",";
//					StringTokenizer st = new StringTokenizer(temp);
//					while(st.hasMoreTokens())
//					{
//						String tok = st.nextToken();
//						m = p.matcher(tok);
//						if(m.matches() == true)
//						{
//							//tok = fk.spellChecking(tok);
//							if(tok==null)
//							{
//								System.out.println(tok.toString()+"  may not be typo");
//							}
//							else if(tok.length() < 30)
//							{
//								//System.out.println(tok.toString());
//								text = text+" "+tok.toString();
//							}
//						}
//					}
//				}
//				String name = f.getName().replaceAll(",", "");
//				//if you use profiles by 3 category please uncomment next line
//				//name = name.substring(0, name.lastIndexOf("_"));
////				fk.writeFiles(name, text, category);
//			}
//		}

	}
	public void writeFiles(String companyName, String text, int category) throws IOException{
		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/second run/";
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
				csvfile = new PrintWriter(new FileWriter(filepath + "f.profiles/profiles_2014_SRM_spell_check_synom.csv", true));
				csvfile.append(companyName); csvfile.append(',');
				csvfile.append(text); csvfile.append(',');
				csvfile.append('\n'); csvfile.close();
				break;
			case 2:
				System.out.println("For LHR");
				csvfile = new PrintWriter(new FileWriter(filepath + "f.profiles/profiles_2014_LHR_spell_check_synom.csv", true));
				csvfile.append(companyName); csvfile.append(',');
				csvfile.append(text); csvfile.append(',');
				csvfile.append('\n'); csvfile.close();
				break;
			case 3:
				System.out.println("For SUS");
				csvfile = new PrintWriter(new FileWriter(filepath + "f.profiles/profiles_2014_SUS_spell_check_synom.csv", true));
				csvfile.append(companyName); csvfile.append(',');
				csvfile.append(text); csvfile.append(',');
				csvfile.append('\n'); csvfile.close();
				break;
		}
//		csvfile.append(companyName); csvfile.append(',');
//		csvfile.append(text); csvfile.append(',');
//		csvfile.append('\n'); csvfile.close();
	}


	public void extractKeywords() throws IOException {
		SynonymGrouping fk = new SynonymGrouping();
		SynonymCompare sc = new SynonymCompare();
		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/second run/";
		String folder = filepath + "e.keyword_category2";
		File[] files = new File(folder).listFiles();
		Pattern p = Pattern.compile("^[A-Za-z]+$", Pattern.CASE_INSENSITIVE);
		int category = 0;
		Matcher m;
		for (File f : files) {
			if (f.isFile()) {
//				System.out.println(f.getName());
				if (f.getName().contains("_SRM")) {
					category = 1;
				}
				if (f.getName().contains("_LHR")) {
					category = 2;
				}
				if (f.getName().contains("_SUS")) {
					category = 3;
				}
				System.out.println(f.getName() + " " + category);
				BufferedReader bf = new BufferedReader(new FileReader(f));
				String text = "";//holds one company's profile
				String temp;
				while ((temp = bf.readLine()) != null) {
//					temp = temp.replaceAll(",", " ");
					temp = temp.replaceAll("\n", " ");
					temp = temp.replaceAll("\r", " ");
					String[] splitted = temp.split(",");
					String res = "";
//
					for (int i = 0; i < splitted.length; i++){
//						res.add(sc.SynonymCompare(splitted[i]));
						res = res + sc.SynonymCompare(splitted[i]) + ",";
					}
//					text = text+" "+(temp.toString()).toLowerCase();
					StringTokenizer st = new StringTokenizer(res.replaceAll(","," "));
					while (st.hasMoreTokens()) {
						String tok = st.nextToken();
						m = p.matcher(tok);
						if (m.matches() == true) {
							//tok = fk.spellChecking(tok);
							if (tok == null) {
								System.out.println(tok.toString() + "  may not be typo");
							} else if (tok.length() < 30) {
								//System.out.println(tok.toString());
								text = text + " " + tok.toString();
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
