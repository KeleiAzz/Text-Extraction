package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
 */
public class SpecialCharReplacer
{

	public SpecialCharReplacer()
	{
	}

	public static String ReplacePartialSpecialChar(String input)
	{
		String tempInput = input;
		tempInput = tempInput.replace("\r", "");
		tempInput = tempInput.replace("\t", "");
		tempInput = tempInput.replace("\n", "");
		int length = tempInput.length();
		for(;;)
		{
			tempInput = tempInput.replace("  ", " ");
			if(tempInput.length()==length)
			{
				break;
			}
			else
			{
				length = tempInput.length();
			}
		}
		return tempInput;
	}

	public static String ReplaceSpecialChar(String input)
	{
		String tempInput = input;
		tempInput = tempInput.replaceAll("\\s+", ",");
		tempInput = tempInput.replace(" ", ",");
		tempInput = tempInput.replace("	", "");
		tempInput = tempInput.replace("\r", ",");
		tempInput = tempInput.replace("�", ",");
		tempInput = tempInput.replace("/", ",");
		tempInput = tempInput.replace("@", ",");
		tempInput = tempInput.replace("(", ",");
		tempInput = tempInput.replace(")", ",");
		tempInput = tempInput.replace("[", ",");
		tempInput = tempInput.replace("]", ",");
		tempInput = tempInput.replace(".", ",");
		tempInput = tempInput.replace(";", ",");
		tempInput = tempInput.replace("\"", ",");
		tempInput = tempInput.replace("\'", ",");
		tempInput = tempInput.replace("\\?", ",");
		tempInput = tempInput.replace("\\!", ",");
		tempInput = tempInput.replace("\\;", ",");
		tempInput = tempInput.replace("\\:", ",");
		tempInput = tempInput.replace("•", "");
		tempInput = tempInput.replace("·", "");
		tempInput = tempInput.replace("‘", "");
		tempInput = tempInput.replace("’", "");
		tempInput = tempInput.replace("*", "");
		tempInput = tempInput.replace("#", "");
		tempInput = tempInput.replace(":", "");
		tempInput = tempInput.replace("?", "");
		tempInput = tempInput.replace("@", "");
		tempInput = tempInput.replace("_", "");
		tempInput = tempInput.replace("–", "");
		tempInput = tempInput.replace("`", "");
		tempInput = tempInput.replace("&", "");
		tempInput = tempInput.replace("+", "");
		tempInput = tempInput.replace("-", "");
		tempInput = tempInput.replace("$", "");
		tempInput = tempInput.replace("…", "");
		tempInput = tempInput.replace("\"", "");
		tempInput = tempInput.replace("\\.", ",");
		//tempInput = tempInput.replace("\\s+", ",");
		int length = tempInput.length();
		for(;;)
		{
			tempInput = tempInput.replace(",,", ",");
			if(tempInput.length()==length)
			{
				break;
			}
			else
			{
				length = tempInput.length();
			}
		}


		tempInput = tempInput.replaceAll("(?is)<.*?>", "");
		return tempInput;
	}

	public static String ReplaceComma(String input)
	{
		String tempInput = input;
		int length = tempInput.length();

		for(;;)
		{
			tempInput = tempInput.replace(",,", ",");
			if(tempInput.length()==length)
			{
				break;
			}
			else
			{
				length = tempInput.length();
			}
		}


		tempInput = tempInput.replaceAll("(?is)<.*?>", "");
		return tempInput;
	}
}
