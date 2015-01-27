/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProfilePreProcessing.nlpPreProcessing;

/**
 *
 * @author lance
 */
public class NumberEliminator 
{
    public static String EliminateNumber(String input)
    {
        //String temp = input.replaceAll(",^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$,", ",");
        String temp = input;
        String tempBackUp=input;
        for(;;)
        {   
            for(int i=0;i<10;i++)
            {
                temp=temp.replaceAll(",[0-9]{1,20},", ",");
            }
            if(temp.length()==tempBackUp.length())
            {
                break;
            }
            else
            {
                tempBackUp=temp;
            }
        }
        temp=temp.replace(",,", ",");
        return temp;
    }
    
}
