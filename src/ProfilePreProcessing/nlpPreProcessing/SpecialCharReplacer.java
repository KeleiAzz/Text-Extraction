/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProfilePreProcessing.nlpPreProcessing;

/**
 *
 * @author lance
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
    
     public static String ReplaceEnter(String input)         
    {
        String tempInput = input;
        int length = tempInput.length();
        
        for(;;)
        {
            tempInput = tempInput.replace("\n", ",");
            tempInput = tempInput.replace("\r", ",");
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
    
    
    public static String LeaveEngLetter(String input)
    {
        char[] cha = input.toCharArray();
        StringBuffer buf = new StringBuffer();
        for(int i=0;i<cha.length;i++)
        {
            char a=cha[i];
            if(a>='a'&&a<='z')
            {
                  buf.append(a);
            }
            else if(a>='A'&&a<='Z')
            {
                  buf.append(a);
            }
            else if(a==' '||a==',')
            {
                  buf.append(a);
            }
	}
        return buf.toString();   
    }
}
