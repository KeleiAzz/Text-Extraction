package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
 */

//package htmldownloader;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 *
 * @author lance
 */
public class HtmlDownloader
{
	public static void main(String args[]){
		String res = getHTML("http://www.apriso.com/library/white_papers/Delivering%20on%20the%20Promise%20of%20Global%20Growth%20-%20Cummins%20Inc.pdf","utf-8");
		System.out.println(res);
	}
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