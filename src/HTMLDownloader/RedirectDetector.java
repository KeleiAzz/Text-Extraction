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
//		System.out.println( "orignal url: " + con.getURL() );
		String orignalULR = url;
		try {
//			System.out.println("1");
			con.connect();
			orignalULR = con.getURL().toString();
//			System.out.println("2");
		} catch (IOException e) {
			e.printStackTrace();
		}

//		System.out.println( "connected url: " + con.getURL() );
		InputStream is = null;
		String TrueURL = orignalULR;
		try {
//			System.out.println("3");
			is = con.getInputStream();
			TrueURL = con.getURL().toString();
//			System.out.println("4");
		} catch (IOException e) {
			e.printStackTrace();
			return url;
		}

		if (!orignalULR.equals(TrueURL)){
			System.out.println("Redirected");
		}
//		System.out.println( "redirected url: " + TrueURL );
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return TrueURL;
	}

}
