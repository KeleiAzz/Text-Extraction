package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

//import CSVWriter;
//import filefolderiterator.FileFolderIterator;
//import fileoperation.FileOperator;
//import static HtmlDownloader.getHTML;
//import xlsurllistreader.UrlListReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
//import nlpprocessor.NumberEliminator;
//import static pdfdownloader.PdfDownloader.downloadFromUrl;
//import pdfparser.PdfReader;
//import pdfparser.PdfReaderOriginalText;
//import phrasereader.DirItem;
//import phrasereader.PhraseReader;



public class OriginalTextDownloader
{
	public static ArrayList<String> stopWordList;
	public static int deadCounter = 0;
	public static int htmlCounter = 0;
	public static int deadPdfCounter = 0;
	public static int pdfCounter = 0;
	public static ArrayList<DirItem> phraseDictionary;

	public static void main(String args[]) throws IOException {
		//Phrase Dictionary List
		String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/sixth run/";
		PhraseReader phraseReader = new PhraseReader(filepath + "phrase.xlsx");
		phraseDictionary = phraseReader.ReadPhraseExcel();
		System.out.println("there r "+phraseDictionary.size()+" phrase dictionary items");

		//load stopwords list
		UrlListReader stopWordReader = new UrlListReader(filepath + "stopwords.xlsx");
		stopWordList = stopWordReader.ReadExcel();
		System.out.println("there r "+stopWordList.size()+" stop words");

		//read folder for all the url lists for all companies
		ArrayList<String> companyNames = new ArrayList();
		try
		{
			companyNames = FileFolderIterator.readCompanyNames(filepath + "b.company_url");
			System.out.println(companyNames.size());
		}
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

		ArrayList<String> deadLink = new ArrayList();
		for(int j=0;j<companyNames.size();j++)//每次循环读一个company的所有url
		{
			//clear the dead link list
			deadLink.clear();
			//all the CSV string from both pdf and webpage
			String AllCompanyContent = new String("==========\r\n");
			//set counters to 0;
			htmlCounter=0;
			pdfCounter = 0;
			deadPdfCounter=0;
			deadCounter=0;

			//create folders for each company names
			FileOperator.newFolder(filepath + "pdfdownloader/"+companyNames.get(j));

			//Read URL list
			UrlListReader urlReader = new UrlListReader(filepath + "b.company_url/"+companyNames.get(j)+".xlsx");
			ArrayList<String> urlArrayList = urlReader.ReadExcel();
			System.out.println("there r "+urlArrayList.size()+" urls for company:"+companyNames.get(j));

			for(int i = 0; i<=urlArrayList.size()-1;i++)
			{
				String trueURL = RedirectDetector.getTrueURL(urlArrayList.get(i));
				if((!trueURL.contains("File")||!trueURL.contains(".aspx"))&&!trueURL.contains(".ashx")&&!trueURL.contains(".pdf")&&!trueURL.contains(".File?item"))//webpage url
				{
					htmlCounter++;
					System.out.println(trueURL);
					//System.out.println(MainTextExtractor.parse(getHTML(urlArrayList.get(i), "utf-8")));
//					String result = MainTextExtractorOriginalText.parse(HtmlDownloader.getHTML(urlArrayList.get(i).replace("http//", "http://"), "utf-8"));
					String result = MainTextExtractorOriginalText.parse(HtmlDownloader.getHTML(trueURL, "utf-8"));
					if(result.length()==0)
					{
						deadLink.add(urlArrayList.get(i));
						deadCounter++;
					}
					else
					{
						AllCompanyContent+=urlArrayList.get(i)+"\r\n"+result+"\r\n==========\r\n";
					}

				}
				else//pdf URL
				{
					pdfCounter++;
					System.out.println(trueURL);
					boolean res = PdfDownloader.downloadFromUrl(trueURL,filepath + "pdfdownloader/"+companyNames.get(j)+"/",companyNames.get(j)+pdfCounter+".pdf");
					if(res==false)//pdf文件有问题或不能下载
					{
						deadPdfCounter++;
						deadLink.add(urlArrayList.get(i));
					}
					else//如果能下载,读取文件内容并打印
					{
						//System.out.println(PdfReader.readFileOfPDF("d://pdfdownload//"+companyNames.get(j)+"//"+companyNames.get(j)+pdfCounter+".pdf"));
						AllCompanyContent+=urlArrayList.get(i)+"\r\n"+PdfReaderOriginalText.readFileOfPDF(filepath + "pdfdownloader/"+companyNames.get(j)+"/"+companyNames.get(j)+pdfCounter+".pdf")+"\r\n==========\r\n";
					}
				}
				System.out.println("dead  link # "+deadCounter+" over total: "+htmlCounter);
				System.out.println("dead pdf link # "+deadPdfCounter+" over total: "+pdfCounter);
				System.out.println("===========================");
			}
			//stem AllCompanyContent
			//AllCompanyContent=stemmer.StemCompanyContent(AllCompanyContent);

			//out put deadlink for this company
			String deadLinkString="";
			for(int i =0;i<deadLink.size();i++)
			{
				deadLinkString += companyNames.get(j)+","+deadLink.get(i)+"\r\n";
			}
			CSVWriter.Write(filepath + "deadlink.csv",deadLinkString);


			//write csv for current company
			CSVWriter.Write(filepath + "c.company_text/company_csv/"+companyNames.get(j)+".txt",AllCompanyContent);

		}

	}
}
