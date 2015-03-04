package SynonymGrouping;

/**
 * Created by keleigong on 3/1/15.
 */
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author SCRC
 */
public class SynonymCompare {

	private String fileToBeRead = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/second run/synonyms.xlsx";
	public String SynonymCompare(String result)
	{
		String ls = "";
		result = result.replace("\t", " ");
		String[] str = result.split(" ");
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
			for(int k = 0; k < str.length; k++)
			{
				for (int i=0; i < Rownum; i++)
				{
					row = sheet.getRow(i);
					for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++)
					{
						//  row.getCell(j).toString() get the content of cell
						// compare the content with each word in csv file

						cell = row.getCell(j).toString();
						if(str[k].contentEquals(cell))
						{
							str[k] = str[k].replace(str[k], row.getCell(0).toString());
						}

					}
				}
			}
			for(int i =0; i < str.length; i++)
			{
				if(str[i].contentEquals("CompanyName"))
				{
					str[i] = str[i].replace(str[i],str[i]+",");
				}
				if(str[i].contains(".txt"))
				{
					str[i] = str[i].replace(str[i],"\r\n"+str[i]+",");
				}
				ls = ls +" "+ str[i];
			}
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
		return ls;
	}

}
