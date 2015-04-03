package HTMLDownloader;

/**
 * Created by keleigong on 4/3/15.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
//import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.awt.Desktop;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

public class pdfconverter{
	public static void main(String[] args){
//		selectPDFFiles();
		convertPDFToText("/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/fourth run/pdfdownloader/adt corp/adt corp1.pdf","output2.txt");
	}


	//allow pdf files selection for converting
	public static void selectPDFFiles(){

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF","pdf");
		chooser.setFileFilter(filter);
		chooser.setMultiSelectionEnabled(true);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File[] Files=chooser.getSelectedFiles();
			System.out.println("Please wait...");
			for( int i=0;i<Files.length;i++){
				convertPDFToText(Files[i].toString(),"textfrompdf"+i+".txt");
			}
			System.out.println("Conversion complete");
		}


	}

	public static String convertPDFToText(String src,String desc){
		String text = "";
		try{
			//create file writer
//			FileWriter fw=new FileWriter(desc);
			//create buffered writer
//			BufferedWriter bw=new BufferedWriter(fw);
			//create pdf reader
			PdfReader pr=new PdfReader(src);
			//get the number of pages in the document
			int pNum=pr.getNumberOfPages();
			//extract text from each page and write it to the output text file

			for(int page=1;page<=pNum;page++){
				text += PdfTextExtractor.getTextFromPage(pr, page) + " ";
			}

//			bw.write(text);
//			bw.newLine();
//			bw.flush();
//			bw.close();



		}catch(Exception e){e.printStackTrace();}
		return text;
	}

}
