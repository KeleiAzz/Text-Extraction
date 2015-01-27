/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProfilePreProcessing.nlpPreProcessing;

/**
 *
 * @author lance
 */
public class UpperLetterFlipper 
{
    public static String upperFlip(String s)
        {
            String res =new String("");
            for(int i=0;i<s.length();i++)
            {
                char c = s.charAt(i);
                if(c>='A'&&c<='Z')c = (char)(c-'A'+'a');
                res+=""+c;
            }
             return res;
        }
    
}
