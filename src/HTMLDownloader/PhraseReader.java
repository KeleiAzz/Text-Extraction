package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
 */
import java.io.FileInputStream;
import java.util.ArrayList;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author momo
 */
public class PhraseReader
{
	//public static String fileToBeRead = "f://test//phrase.xlsx";
	public static String fileToBeRead = "";

	public PhraseReader(String filePath)
	{
		fileToBeRead = filePath;
	}

	public ArrayList<DirItem> ReadPhraseExcel()
	{
		ArrayList<DirItem> ls = new  ArrayList();
		try{

			// 构造 XSSFWorkbook 对象，strPath 传入文件路径
			XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(fileToBeRead));
			// 读取第一章表格内容
			XSSFSheet sheet = xwb.getSheetAt(0);
			// 定义 row、cell
			XSSFRow row;

			// 循环输出表格中的内容
			//System.out.println("RowNum = "+sheet.getPhysicalNumberOfRows());
			int RowNum = sheet.getPhysicalNumberOfRows();
			for (int i= 0; i < RowNum; i++)
			{
				String cell = new String("");
				String cell2 = new String("");
				//System.out.println("This is row = "+i);
				row = sheet.getRow(i);
				//System.out.println("This is company row cell number " + row.getLastCellNum());
				//System.out.println("CellNum = "+row.getPhysicalNumberOfCells());
				//获得第一行第一个cell的short name，并写入DirItem中
				cell = row.getCell(0).toString();
				//System.out.println("This is the company name" + cell);
				// System.out.println("This is the number of cell " + sheet.getRow(i).getLastCellNum());
				// 通过 row.getCell(j).toString() 获取单元格内容，
				//获得第二列的元素
				cell2 = row.getCell(1).toString();
				//create dictionary item
				DirItem item = new DirItem(cell,cell2);
				ls.add(item);
			}
			//System.out.println("===============here is some code at tail==============");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		//System.out.println("Arraylist size= " +ls.size());
		return ls;
	}
}
