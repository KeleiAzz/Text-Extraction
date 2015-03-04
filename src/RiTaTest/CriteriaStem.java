package RiTaTest;

import ProfilePreProcessing.nlpPreProcessing.Stemmer;
import net.didion.jwnl.JWNLException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by keleigong on 3/1/15.
 */
class CriteriaStem {
//	private Stemmer stemmer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("/Users/keleigong/Dropbox/Java/SCRC_Text_Extraction/src/RiTaTest/original_criteria.txt"));
		try {
			Stemmer stemmer = new Stemmer();
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			String[] splitted_line;
			while (line != null) {
//				System.out.println(line);
				splitted_line = line.split(" ");
				String stemmedline = "";
				for (int i = 0; i<splitted_line.length;i++)
				{
					splitted_line[i]= stemmer.Stem(splitted_line[i]);
					stemmedline = stemmedline + " " + splitted_line[i];
				}
//				String stemmedline;
//				sb.append(" ".join(splitted_line));
				System.out.println(stemmedline);
				sb.append(stemmedline);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (JWNLException e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
	}
}
