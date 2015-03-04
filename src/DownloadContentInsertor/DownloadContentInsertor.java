/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DownloadContentInsertor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author lance
 */
public class DownloadContentInsertor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //read folder for all the downloaded files for all companies
        ArrayList<String> companyNames = new ArrayList();
	    String filepath = "/Users/keleigong/Google Drive/SCRC 2015 work/2014_data/second run/";
        try 
        {
            companyNames = FileFolderIterator.readCompanyNames(filepath + "c.company_text/company_csv/");
            System.out.println(companyNames.size());
        } 
        catch (FileNotFoundException ex) 
        {
            ex.printStackTrace(); 
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
        
        for(int j=0;j<companyNames.size();j++)//每次循环读一个company的所有url
        {	
        	System.out.println(companyNames.get(j));
            String path = filepath + "c.company_text/company_csv/"+companyNames.get(j)+".txt";
            
            RecordFileLoader.Read(path,companyNames.get(j));
        }
       
    }
}
