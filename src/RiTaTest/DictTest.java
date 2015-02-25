package RiTaTest;

/**
 * Created by keleigong on 2/19/15.
 */

import net.didion.jwnl.*;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.*;
//import org.tartarus.snowball.EnglishSnowballStemmerFactory;
import org.tartarus.snowball.EnglishSnowballStemmerFactory;
import org.tartarus.snowball.util.StemmerException;

import java.io.*;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
//import rita.wordnet.RiWordnet;
//import java.util.logging.Level;
//import java.util.logging.Logger;



public class DictTest {
	private int MaxWordLength = 50;
	private Dictionary dic;
	private MorphologicalProcessor morph;
	private boolean IsInitialized = false;
	public HashMap AllWords = null;

	public static void main(String[] args) throws StemmerException {
//		DictTest();
		DictTest test = new DictTest();
//		test.Unload();
		String t = test.StemWordWithWordNet("acquires");
		System.out.println(t);
		t = test.StemWordWithWordNet("packages");
		System.out.println(t);
		t = test.StemWordWithWordNet("based");
		System.out.println(t);
//		EnglishSnowballStemmerFactory s = new EnglishSnowballStemmerFactory();
//		s.getInstance().process("entry");
//		System.out.println(s);
//		test.Unload();
	}
	public DictTest()
	{
		AllWords = new HashMap ();

		try
		{
			JWNL.initialize(new FileInputStream("/Users/keleigong/Dropbox/Java/SCRC_Text_Extraction/src/RiTaTest/file_properties.xml"));
			dic = Dictionary.getInstance();
			morph = dic.getMorphologicalProcessor();
			// ((AbstractCachingDictionary)dic).
			//	setCacheCapacity (10000);
			IsInitialized = true;
		}
		catch ( FileNotFoundException e )
		{
			System.out.println ( "Error initializing Stemmer: JWNLproperties.xml not found" );
		}
		catch ( JWNLException e )
		{
			System.out.println ( "Error initializing Stemmer: "
					+ e.toString() );
		}

	}
	public void Unload ()
	{
		dic.close();
		Dictionary.uninstall();
		JWNL.shutdown();
	}
	public String StemWordWithWordNet ( String word )
	{
		if ( !IsInitialized )
			return word;
		if ( word == null ) return null;
		if ( morph == null ) morph = dic.getMorphologicalProcessor();

		IndexWord w;
		try
		{
			w = morph.lookupBaseForm( POS.VERB, word );
			if ( w != null )
				return w.getLemma().toString ();
			w = morph.lookupBaseForm( POS.NOUN, word );
			if ( w != null )
				return w.getLemma().toString();
			w = morph.lookupBaseForm( POS.ADJECTIVE, word );
			if ( w != null )
				return w.getLemma().toString();
			w = morph.lookupBaseForm( POS.ADVERB, word );
			if ( w != null )
				return w.getLemma().toString();
		}
		catch ( JWNLException e )
		{
		}
		return null;
	}
	public String Stem(String word )
	{
		// check if we already know the word
		String stemmedword = (String) AllWords.get( word );
		if ( stemmedword != null )
			return stemmedword; // return it if we already know it

		// don't check words with digits in them
//		if ( containsNumbers (word) == true )
//			stemmedword = null;
//		else	// unknown word: try to stem it
//			stemmedword = StemWordWithWordNet (word);

		if ( stemmedword != null )
		{
			// word was recognized and stemmed with wordnet:
			// add it to hashmap and return the stemmed word
			AllWords.put( word, stemmedword );
			return stemmedword;
		}
		// word could not be stemmed by wordnet,
		// thus it is no correct english word
		// just add it to the list of known words so
		// we won't have to look it up again
		AllWords.put( word, word );
		return word;
	}


	public String StemCompanyContent(String companyContent)
	{
		String stemmedCompanyContent = new String(",");
		String[] splittedWords=companyContent.split(",");
		for(int i =0;i<splittedWords.length;i++)
		{
			stemmedCompanyContent=stemmedCompanyContent+ StemWordWithWordNet(splittedWords[i])+",";
//			try
//			{
//
//			}
//			catch (JWNLException ex)
//			{
////				Logger.getLogger(Stemmer.class.getName()).log(Level.SEVERE, null, ex);
//			}
//			catch (IOException ex)
//			{
////				Logger.getLogger(Stemmer.class.getName()).log(Level.SEVERE, null, ex);
//			}
//			catch (ClassNotFoundException ex)
//			{
//				Logger.getLogger(DictTest.class.getName()).log(Level.SEVERE, null, ex);
//			}
		}
		return stemmedCompanyContent;
	}
}
