package SynonymGrouping;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
//		SynonymCompare sc = new SynonymCompare();
		HashMap<String,String> sc = getSynonymList();
		System.out.println(sc);
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
//						res = res + sc.SynonymCompare(splitted[i]) + ",";
						if (sc.containsKey(splitted[i])){
							res = res + sc.get(splitted[i]) + ",";
						}else {
							res = res + splitted[i] + ",";
						}
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

	public HashMap<String,String> getSynonymList(){
		String fileToBeRead = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/second run/synonyms.xlsx";
		HashMap<String,String> res = new HashMap<String, String>();
		try
		{
			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileToBeRead));
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			XSSFRow row;
			String cell;
			// 循环输出表格中的内容
			int Rownum = sheet.getLastRowNum();

			for (int i=0; i < Rownum; i++)
			{
				row = sheet.getRow(i);
				for (int j = 1; j < row.getPhysicalNumberOfCells(); j++)
				{
					//  row.getCell(j).toString() get the content of cell
					// compare the content with each word in csv file

					cell = row.getCell(j).toString();
					if (cell.length()>1){
						res.put(cell,row.getCell(0).toString());
					}
//					if(str[k].contentEquals(cell))
//					{
//						str[k] = str[k].replace(str[k], row.getCell(0).toString());
//					}

				}
			}

//			for(int i =0; i < str.length; i++)
//			{
//				if(str[i].contentEquals("CompanyName"))
//				{
//					str[i] = str[i].replace(str[i],str[i]+",");
//				}
//				if(str[i].contains(".txt"))
//				{
//					str[i] = str[i].replace(str[i],"\r\n"+str[i]+",");
//				}
//				ls = ls +" "+ str[i];
//			}
            /*for(int i = 0; i < str.length; i++)
            {
                if(str[i].contentEquals("LEVEL"))
                {
                    str[i] = str[i].replace(str[i],str[i]+",");
                }
                if(str[i].matches("1")||str[i].matches("5")||str[i].matches("2")||str[i].matches("3")||str[i].matches("4"))
                {
                    str[i] = str[i].replace(str[i],"\r\n"+str[i]+",");
                }
                ls = ls +" "+ str[i];
            }*/
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return res;
	}

}
