package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
 */
//import test.*;
import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class PdfDownloader
{
	public static boolean downloadFromUrl(String url,String dir,String fileName)
	{
		try {
			URL httpurl = new URL(url);
			System.out.println(fileName);
			File f = new File(dir + fileName);
			FileUtils.copyURLToFile(httpurl, f);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String getFileNameFromUrl(String url){
		String name = new Long(System.currentTimeMillis()).toString() + ".X";
		int index = url.lastIndexOf("/");
		if(index > 0){
			name = url.substring(index + 1);
			if(name.trim().length()>0){
				return name;
			}
		}
		return name;
	}
}