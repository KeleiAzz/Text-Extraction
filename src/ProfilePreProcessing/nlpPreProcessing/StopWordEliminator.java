/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProfilePreProcessing.nlpPreProcessing;

import ProfilePreProcessing.XlsListReader;
import java.util.ArrayList;

/**
 *
 * @author lance
 */
public class StopWordEliminator 
{
    public static ArrayList<String> stopWordList;

    public StopWordEliminator() 
    {
	    String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/";
        XlsListReader stopWordReader = new XlsListReader(filepath + "stopwords.xlsx");
        stopWordList = stopWordReader.ReadExcel();
        System.out.println("there r "+stopWordList.size()+" stop words");
    }
    
    public static String deleteStopWords(String line) 
    {
        String tempLine = ","+line;
        for(int i = 0;i<=stopWordList.size()-1;i++)
        {
            tempLine = tempLine.replace(","+stopWordList.get(i)+",", ",");
        } 
        //System.out.println("After: "+tempLine);
        tempLine = tempLine.replace(",,",",");
        return tempLine;
    } 
    
}
