package SynonymGrouping;

/**
 * Created by keleigong on 3/1/15.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter
{

	public static void Write(String filePath, String newContent) {
		try
		{
			File csv = new File(filePath); // CSV文件
			// 追记模式
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
			// 新增一行数据
			bw.newLine();
			bw.write(newContent);
			bw.close();
			System.out.println("The file is created!");
		}
		catch (FileNotFoundException e)
		{
			// 捕获File对象生成时的异常
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// 捕获BufferedWriter对象关闭时的异常
			e.printStackTrace();
		}
	}
}