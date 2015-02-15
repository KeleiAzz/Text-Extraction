package HTMLDownloader;


import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import com.google.gson.Gson;

/**
 * Created by keleigong on 2/10/15.
 */

public class URLextract {
	public static void main(String[] args) throws Exception {
		String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
		String search = "apple";
		String charset = "UTF-8";
//		String page = String.format("&rsz=8&start=%d", 10);
//		System.out.println(URLEncoder.encode(search, charset)+page);
		for (int i = 0;i < 5; i++){
			String page = String.format("&rsz=8&start=%d", i*8);
			URL url = new URL(google + URLEncoder.encode(search, charset) + page);
			Reader reader = new InputStreamReader(url.openStream(), charset);
			GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
			System.out.println("Page: " + ((Integer)(i+1)).toString());
			for (int j = 0;j < 8; j++){
//				System.out.println(results.getResponseData().getResults().get(j).getTitle());
				System.out.println(results.getResponseData().getResults().get(j).getUrl());
			}

		}


		// Show title and URL of 1st result.
//		System.out.println(results.getResponseData().getResults().size());


	}
}

class GoogleResults {

	private ResponseData responseData;
	public ResponseData getResponseData() { return responseData; }
	public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
	public String toString() { return "ResponseData[" + responseData + "]"; }

	static class ResponseData {
		private List<Result> results;
		public List<Result> getResults() { return results; }
		public void setResults(List<Result> results) { this.results = results; }
		public String toString() { return "Results[" + results + "]"; }
	}

	static class Result {
		private String url;
		private String title;
		public String getUrl() { return url; }
		public String getTitle() { return title; }
		public void setUrl(String url) { this.url = url; }
		public void setTitle(String title) { this.title = title; }
		public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
	}

}


