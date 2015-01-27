package ProfilePreProcessing;

/**
 * Created by keleigong on 1/26/15.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class CSVReader
{

	public static String Read(String filePath)
	{
		String result="";
		try
		{
			//File csv = new File("C://writers.csv"); // CSV文件

			File csv = new File(filePath); // CSV文件

			BufferedReader br = new BufferedReader(new FileReader(csv));

			// 读取直到最后一行
			String line = "";
			while ((line = br.readLine()) != null)
			{
				// 把一行数据分割成多个字段
				StringTokenizer st = new StringTokenizer(line, ",");

				while (st.hasMoreTokens())
				{
					// 每一行的多个字段用TAB隔开表示
					//System.out.print(st.nextToken() + "\t");
					result = result + st.nextToken() + "\t";
				}
				//System.out.println();
			}
			br.close();

		}
		catch (FileNotFoundException e)
		{
			// 捕获File对象生成时的异常
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// 捕获BufferedReader对象关闭时的异常
			e.printStackTrace();
		}
		return result;
	}
}