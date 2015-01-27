package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfReaderOriginalText
{
	private static String pdfPath;

	public PdfReaderOriginalText(String pdfPath)
	{
		this.pdfPath = pdfPath;
	}


	/**
	 * @param 读取PDF文件
	 */
//	public static void main(String[] args) {
//		PdfReaderOriginalText pdf = new PdfReaderOriginalText();
//		String pdfPath = "D:\\temp\\myPDF.pdf";
//		pdf.readFileOfPDF(pdfPath);
//
//	}

	// 读取指定的PDF文件的内容，其中：pdfPath表示要读取的PDF文件的路径
	public static String readFileOfPDF(String pdfPath)
	{
		File file = new File(pdfPath);// 创建一个文件对象
		FileInputStream infile = null;
		String context="";
		try
		{
			infile = new FileInputStream(pdfPath);// 创建一个文件输入流
			// 新建一个PDF解析器对象
			PDFParser parser = new PDFParser(infile);
			// 对PDF文件进行解析
			parser.parse();
			// 获取解析后得到的PDF文档对象
			PDDocument pdfdocument = parser.getPDDocument();
			// 新建一个PDF文本剥离器
			PDFTextStripper stripper = new PDFTextStripper();
			// 从PDF文档对象中剥离文本
			context = stripper.getText(pdfdocument);
			//System.out.println("PDF file" + file.getAbsolutePath() + "has following content");
			//System.out.println(context);
			//comment these line because we need original text
			//context = SpecialCharReplacer.ReplaceSpecialChar(","+context+",");
			//context = UpperLetterFlipper.upperFlip(context);
			// context = StopWordEliminator.deleteStopWords(context);
			//context = DictionaryItemReplacer.replaceDictionaryItems(context);
			//why still ",,,,,"?
			//context = SpecialCharReplacer.ReplaceSpecialChar(context);
			//eliminate pure numbers
			//context = NumberEliminator.EliminateNumber(context);
		}
		catch (Exception e)
		{
			System.out.println(" reading the pdf file" + file.getAbsolutePath() + "failed"
					+ e.getMessage());
		}
		finally
		{
			if (infile != null)
			{
				try
				{
					infile.close();
				}
				catch (IOException e1)
				{
				}
			}
			return context;
		}
	}
}
