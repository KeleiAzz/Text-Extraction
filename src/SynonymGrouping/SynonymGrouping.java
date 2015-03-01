package SynonymGrouping;

/**
 * Created by keleigong on 3/1/15.
 */
public class SynonymGrouping {
	public static void main(String[] args)
	{
		// TODO code application logic here

		String wr = "";
		String re = "";
		wr = CSVReader.Read("C:\\Users\\SCRC\\Dropbox\\sharing\\company3section\\profiles_2012_SUS.csv");
		//wr = CSVReader.Read("C:\\Users\\SCRC\\Dropbox\\Sharing\\Basic Unsupervised Model\\Stage 1\\LSA-R-code-in-out\\keyword-company-profiles\\profiles10.csv");
		SynonymCompare sc = new SynonymCompare();
		re = sc.SynonymCompare(wr);
		//System.out.println(re);
		//System.out.println(wr);
		//CSVWriter.Write("f://profiles10_nosyn.csv",re);
		CSVWriter.Write("C:\\Users\\SCRC\\Dropbox\\sharing\\company3section\\profiles_2012_SUS_nonsym.csv",re);
	}
}
