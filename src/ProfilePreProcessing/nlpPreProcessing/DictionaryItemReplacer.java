/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProfilePreProcessing.nlpPreProcessing;

import java.util.ArrayList;


/**
 *
 * @author lance
 */
public class DictionaryItemReplacer 
{

    public static ArrayList<DirItem> phraseDictionary;
    
    public DictionaryItemReplacer() 
    {
	    String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/";
        PhraseReader phraseReader = new PhraseReader(filepath + "phrase.xlsx");
        phraseDictionary = phraseReader.ReadPhraseExcel();
        System.out.println("there r "+phraseDictionary.size()+" phrase dictionary items");
        
    }
    
    //假设main函数里面是有这个ArrayList的,如果这个列表时空就什么都不做.
    public static String replaceDictionaryItems(String line)
    {
        String tempLine = ","+line;
        for(int i = 0; i<=phraseDictionary.size()-1;i++)
        {
            tempLine =tempLine.replace(","+phraseDictionary.get(i).getItemS()+",", ","+phraseDictionary.get(i).getItemC()+",");
        }
        tempLine = tempLine.replace(",,",",");
        return tempLine;
    }
}
