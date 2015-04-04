package HTMLDownloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by keleigong on 4/3/15.
 */
public class RedirectDetector {
	public static void main(String[] args) throws IOException {
		String url = "http://www.apriso.com/library/white_papers/Delivering%20on%20the%20Promise%20of%20Global%20Growth%20-%20Cummins%20Inc.pdf";
		String TrueURL = getTrueURL(url);
		System.out.println(TrueURL);
	}
	public static String getTrueURL(String url)  {
		URLConnection con = null;
		try {
			con = new URL(url).openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println( "orignal url: " + con.getURL() );
		try {
			con.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println( "connected url: " + con.getURL() );
		InputStream is = null;
		try {
			is = con.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return url;
		}
		String TrueURL = con.getURL().toString();
		System.out.println( "redirected url: " + TrueURL );
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TrueURL;
	}

}
