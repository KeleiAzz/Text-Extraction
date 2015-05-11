package ProfilePreProcessing.nlpPreProcessing;

//import java.io.FileInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import processing.core.PApplet;
import rita.wordnet.RiWordnet;
//import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;

public class Stemmer 
{

    public RiWordnet rwordnet;

    public Stemmer(){
//	    String propsFile = "/usr/local/Cellar/wordnet/3.1";
//	    String propsFile2 = "/Users/keleigong/Dropbox/Java/Stemmer/src/Stemmer/file_properties.xml";
//	    PApplet p = new PApplet();
//	    rwordnet = new RiWordnet(p,propsFile);
	    rwordnet = new RiWordnet();
    }
    
	
    public static void main(String[] args) throws JWNLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException
    {		
            //initalizing the part-of-speech-tagger 
            //change the path to the location of the stanford POS tagger on your computer
                //MaxentTagger posTagger = new MaxentTagger("F:/NetBeans Project/stanford-postagger-2013-04-04/models/english-bidirectional-distsim.tagger");
//	        String propsFile = "/Users/keleigong/Dropbox/Java/SCRC_Text_Extraction/src/ProfilePreProcessing/file_properties.xml";
//	        JWNL.initialize(new FileInputStream(propsFile));

            Stemmer sn = new Stemmer();
            //transfer running to the function to get stem
            ArrayList<String> stringsToBeStemmed = new ArrayList();
            /*
            stringsToBeStemmed.add("intellectual");
            stringsToBeStemmed.add("acquires");
            stringsToBeStemmed.add("business");
            stringsToBeStemmed.add("rendering");
            stringsToBeStemmed.add("pensions");
            stringsToBeStemmed.add("applied");
            stringsToBeStemmed.add("writing");
            stringsToBeStemmed.add("supplier");
            stringsToBeStemmed.add("suppliers");
            stringsToBeStemmed.add("fully");
            stringsToBeStemmed.add("customer");
            stringsToBeStemmed.add("financial1");
            stringsToBeStemmed.add("pandas");
            stringsToBeStemmed.add("murdered");
            stringsToBeStemmed.add("1079"); 
            stringsToBeStemmed.add("keep"); 
            stringsToBeStemmed.add("keeps"); 
            stringsToBeStemmed.add("keept"); 
            stringsToBeStemmed.add("kept"); 
            */
            stringsToBeStemmed.add("based");
            stringsToBeStemmed.add("than"); 
            stringsToBeStemmed.add("we"); 
            stringsToBeStemmed.add("packages");
            stringsToBeStemmed.add("of"); 
            stringsToBeStemmed.add("our"); 
            stringsToBeStemmed.add("without"); 
            stringsToBeStemmed.add("unreliable");
            stringsToBeStemmed.add("studied");
            stringsToBeStemmed.add("acquires");
            for(int i = 0; i< stringsToBeStemmed.size(); i++)
            {
                    String word = stringsToBeStemmed.get(i);
                    String result = sn.Stem(word);
                    System.out.println(result);
            }
    }
    
    public String StemCompanyContent(String companyContent)
    {
        String stemmedCompanyContent = new String(",");
        String[] splittedWords=companyContent.split(",");
        for(int i =0;i<splittedWords.length;i++)
        {
            try
            {
                stemmedCompanyContent=stemmedCompanyContent+ Stem(splittedWords[i])+",";
            }
            catch (JWNLException ex)
            {
                Logger.getLogger(Stemmer.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex)
            {
                Logger.getLogger(Stemmer.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (ClassNotFoundException ex)
            {
                Logger.getLogger(Stemmer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return stemmedCompanyContent;
    }
    

    //stem a single word
    public String Stem( String token1) throws JWNLException, IOException, ClassNotFoundException
    {	
            //initializing the wordnet dictionary
            //download the properties file. 
            //Change the path to the dictionary in the properties file to point to the Wordnet dictionary on your computer
        //String propsFile = "F:/NetBeans Project/Stemmer/src/file_properties.xml";

        //JWNL.initialize(new FileInputStream(propsFile));

//	    String propsFile = "/Users/keleigong/Dropbox/Java/SCRC_Text_Extraction/src/ProfilePreProcessing/file_properties.xml";

//	    JWNL.initialize(new FileInputStream(propsFile));

        // get stems for the word got from main function
        if(rwordnet.getBestPos(token1)==null)
        {
            String re = token1;
            return re;
            //System.out.println(token1+" POS: null");
            //return "";
        }
        else
        {
                String[] stems  = rwordnet.getStems(token1, rwordnet.getBestPos(token1));
                if(stems==null||stems.length==0)
                {
                    return token1;
                }
                else
                {
                    String returnStem = stems[0]; 

                    return returnStem;
                }
        }
    }

	
}