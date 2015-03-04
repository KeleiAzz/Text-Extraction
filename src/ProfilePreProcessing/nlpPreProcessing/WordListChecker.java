/*
 * In stemmer, if it does not delete the words failed in stemming,
 * the typos will not be eliminated.
 * Therefore I used this word list to further check the stemmed words.
 * the word will be deleted if it is not in this list.
 */
package ProfilePreProcessing.nlpPreProcessing;

import ProfilePreProcessing.CSVReader;
import java.util.ArrayList;

/**
 *
 * @author SCRC
 */
public class WordListChecker 
{
    private ArrayList<String> words;

    public WordListChecker(String filePath) 
    {
        String content=CSVReader.Read(filePath);
        String[] splittedWords=content.split("\t");
        words = new ArrayList();
        for(int i =0;i<splittedWords.length;i++)
        {
            words.add(splittedWords[i]);
        }
        System.out.println(words.size());
    }
    
    public static void main(String[] args)
    {
        WordListChecker checker = new WordListChecker("/Users/keleigong/Dropbox/Java/SCRC_Text_Extraction/src/ProfilePreProcessing/wordlist.txt");
        System.out.println(checker.check("hello"));
        System.out.println(checker.check("world"));
    }
 
    public String checkCompanyContent(String companyContent)
    {
        String checkedCompanyContent = new String(",");
        String[] splittedWords=companyContent.split(",");
        for(int i =0;i<splittedWords.length;i++)
        {
            if(check(splittedWords[i])==true)
            {
                checkedCompanyContent+=splittedWords[i]+",";
            }
        }
        return checkedCompanyContent;
    }
    
    public boolean check(String token)
    {
        if(words.contains(token))
            return true;
        else
            return false;
    }
    
}
