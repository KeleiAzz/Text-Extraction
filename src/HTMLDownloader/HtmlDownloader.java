package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
 */

//package htmldownloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author lance
 */
public class HtmlDownloader
{
	public static String getHTML(String pageURL, String encoding)
	{
		StringBuilder pageHTML = new StringBuilder();
		try
		{
			URL url = new URL(pageURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 7.0; Windows NT 5.1)");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
			String line = null;
			while ((line = br.readLine()) != null)
			{
				pageHTML.append(line);
				pageHTML.append("\r\n");
			}
			connection.disconnect();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return pageHTML.toString();
	}


}