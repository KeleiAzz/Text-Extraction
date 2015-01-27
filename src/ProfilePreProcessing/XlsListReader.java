package ProfilePreProcessing;

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
public class XlsListReader
{
	//private String fileToBeRead = "d://URL.xlsx";
	private String fileToBeRead = "";

	public XlsListReader(String filePath)
	{
		this.fileToBeRead = filePath;
	}


	public ArrayList<String> ReadExcel()
	{
		ArrayList<String> ls = new  ArrayList<String>();
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
			int real_i = sheet.getFirstRowNum();
			int Rownum = sheet.getLastRowNum();
			for (int i=real_i; real_i <= Rownum; i++)
			{
				row = sheet.getRow(i);
				if(row == null)
				{
					real_i++;
					continue;
				}

				for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++)
				{
					// 通过 row.getCell(j).toString() 获取单元格内容，
					cell = row.getCell(j).toString();
					//System.out.println(cell + "\t");
					ls.add(cell);
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return ls;
	}
}
