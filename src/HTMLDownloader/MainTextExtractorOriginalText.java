package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * 在线性时间内抽取主题类（新闻、博客等）网页的正文。
 * 采用了<b>基于行块分布函数</b>的方法，为保持通用性没有针对特定网站编写规则。
 * </p>
 * @author  Chen Xin
 * @version 1.0, 2009-11-11
 */
public class MainTextExtractorOriginalText {

	private static List<String> lines;
	private final static int blocksWidth;
	private static int threshold;
	private static String html;
	private static boolean flag;
	private static int start;
	private static int end;
	private static StringBuilder text;
	private static ArrayList<Integer> indexDistribution;

	public static void main(String args[]){
		String res = HtmlDownloader.getHTML("http://tinyurl.com/KindleWireless", "utf-8");
		String result = parse(res);
		System.out.println(result);
	}

	static {
		lines = new ArrayList<String>();
		indexDistribution = new ArrayList<Integer>();
		text = new StringBuilder();
		blocksWidth = 3;
		flag = false;
		/* 当待抽取的网页正文中遇到成块的新闻标题未剔除时，只要增大此阈值即可。*/
		/* 阈值增大，准确率提升，召回率下降；值变小，噪声会大，但可以保证抽到只有一句话的正文 */
		threshold	= 50;
	}

	public static void setthreshold(int value) {
		threshold = value;
	}

	/**
	 * 抽取网页正文，不判断该网页是否是目录型。即已知传入的肯定是可以抽取正文的主题类网页。
	 *
	 * @param _html 网页HTML字符串
	 *
	 * @return 网页正文string
	 */
	public static String parse(String _html) {
		return parse(_html, false);
	}

	/**
	 * 判断传入HTML，若是主题类网页，则抽取正文；否则输出<b>"unkown"</b>。
	 *
	 * @param _html 网页HTML字符串
	 * @param _flag true进行主题类判断, 省略此参数则默认为false
	 *
	 * @return 网页正文string
	 */
	public static String parse(String _html, boolean _flag)
	{
		flag = _flag;
		html = _html;
		preProcess();
//		System.out.println(html);
		return getText();
	}

	private static void preProcess()
	{
		html = html.replaceAll("(?is)<!DOCTYPE.*?>", "");
		html = html.replaceAll("(?is)<!--.*?-->", "");				// remove html comment
		html = html.replaceAll("(?is)<script.*?>.*?</script>", ""); // remove javascript
		html = html.replaceAll("(?is)<style.*?>.*?</style>", "");   // remove css
		html = html.replaceAll("&.{2,5};|&#.{2,5};", " ");			// remove special char
		html = html.replaceAll("(?is)<.*?>", "");
	}

	private static String getText()
	{
		SpecialCharReplacer scr = new SpecialCharReplacer();
		lines = Arrays.asList(html.split("\n"));
		indexDistribution.clear();

		for (int i = 0; i < lines.size() - blocksWidth; i++)
		{
			int wordsNum = 0;
			for (int j = i; j < i + blocksWidth; j++)
			{
				// comment these because we need original text
				//replace all special chars from the line
				//lines.set(j, scr.ReplaceSpecialChar(lines.get(j)));
				//flip all upper letters
				//lines.set(j, UpperLetterFlipper.upperFlip(lines.get(j)));

				wordsNum += lines.get(j).length();
			}
			indexDistribution.add(wordsNum);
			//System.out.println(wordsNum);
		}

		start = -1; end = -1;
		boolean boolstart = false, boolend = false;
		text.setLength(0);
		if(lines.size()<100)
		{
			boolean supplierPortalFlag = testSupplierPortal();
			if(supplierPortalFlag==true) // do not need to extract main text anymore, it's a supplier login portal
			{
				text.append(getSupplierPortal());
			}
		}
		else//not a supplier login page. take it as ordinary page.
		{
			extractLongWebPage(boolstart, boolend);
		}
		return text.toString().replace(",,", ",");
	}

	private static void extractLongWebPage(boolean boolstart, boolean boolend)
	{
		for (int i = 0; i < indexDistribution.size() - 1; i++)
		{
			if (indexDistribution.get(i) > threshold && ! boolstart)
			{
				if (indexDistribution.get(i+1).intValue() != 0
						|| indexDistribution.get(i+2).intValue() != 0
						|| indexDistribution.get(i+3).intValue() != 0)
				{
					boolstart = true;
					start = i;
					//System.out.println("started at:"+i);
					continue;
				}
			}
			if (boolstart)
			{
				if (indexDistribution.get(i).intValue() <=5
						|| indexDistribution.get(i+1).intValue() <=5)
				{
					end = i;
					//System.out.println("ended at:"+i);
					boolend = true;
				}
			}
			StringBuilder tmp = new StringBuilder();
			if (boolend)
			{
				//System.out.println(start + "\t\t" + end);
				append(start, end,tmp);
				String str = tmp.toString();
				//System.out.println(str);


				//str = deleteStopWords(str);
				text.append(str);
				boolend = false;
				boolstart =false;
			}
		}
		//System.out.println("total String length = "+text.length());
		if(text.length()<=200)
		{
			OriginalTextDownloader.deadCounter++;
		}
	}

	private static boolean testSupplierPortal()
	{
		for(int i=0;i<lines.size();i++)
		{
			if(lines.get(i).contains("Login")||lines.get(i).contains("Log In"))
			{
				return true;
			}
			if(lines.get(i).contains("Username")||lines.get(i).contains("UwerName"))
			{
				return true;
			}
			if(lines.get(i).contains("Password")||lines.get(i).contains("password"))
			{
				return true;
			}
			if(lines.get(i).contains("Portal")||lines.get(i).contains("portal"))
			{
				return true;
			}
			if(lines.get(i).contains("Supplier")||lines.get(i).contains("supplier"))
			{
				return true;
			}
			if(lines.get(i).contains("Register")||lines.get(i).contains("register"))
			{
				return true;
			}
		}
		return false;
	}

	private static Object getSupplierPortal()
	{
		StringBuilder tmp = new StringBuilder();
		append(0, lines.size()-1,tmp);
		String str = tmp.toString();
		return str;
		//return "this is a supplier portal";
	}

	private static void append(int start, int end,StringBuilder tmp)
	{
		for (int ii = start; ii <= end; ii++)
		{
			if (lines.get(ii).length() < 5) continue;
			//comment these because we need the original text
			//special char eliminator
			//lines.set(ii, SpecialCharReplacer.ReplaceSpecialChar(","+lines.get(ii)+","));
			//eliminate stop words
			//lines.set(ii, StopWordEliminator.deleteStopWords(","+lines.get(ii)+","));
			//replace dictionary items
			//lines.set(ii, DictionaryItemReplacer.replaceDictionaryItems(","+lines.get(ii)+","));
			//eliminate pure numbers
			//lines.set(ii, NumberEliminator.EliminateNumber(lines.get(ii)));
			//why still ",,,,,"?
			//lines.set(ii, SpecialCharReplacer.ReplaceSpecialChar(lines.get(ii)));

			//copy right issue
			if ((lines.get(ii).contains("Copyright")  || lines.get(ii).contains("copyright"))&&lines.get(ii).length()<30)
			{
				continue;
			}
			//remove "\t\r\n"
			lines.set(ii,SpecialCharReplacer.ReplacePartialSpecialChar(lines.get(ii).replace("\t", "")));

			if(lines.get(ii).length()>2)
			{
				tmp.append(lines.get(ii) + "\n");
			}
		}
	}

}


