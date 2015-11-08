/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DownloadContentInsertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author SCRC
 */
public class RecordFileLoader 
{
    public static String Read(String filePath,String companyName) 
    { 
        String result="";
        try 
        {            
            File recordFile = new File(filePath); // CSV文件

            BufferedReader br = new BufferedReader(new FileReader(recordFile));

            // 读取直到最后一行 
        String resultLine = ""; 
            String line="";
            int start_flag = 0;
            int link_flag = 0;
            int category_flag = 0;
            String content = "";
            String link = "";
            while ((line = br.readLine()) != null)
            {
//                resultLine+=line+"\n";
                if(line.startsWith("==========================")) {
                    if (start_flag == 1) {
                        RecordContent newRecore = new RecordContent(companyName, link, content);
                        DBInsertor.InsertRecord(newRecore);
                        link_flag = 1;
                        content = "";
                    } else {
                        start_flag = 1;
                        link_flag = 1;
                    }
                }
                else if(link_flag == 1)
                {
                    link = line;
                    link_flag = 0;
                    category_flag = 1;
                }
                else if(category_flag == 1)
                {
                    category_flag = 0;
                }
                else
                {
                    content += line;
                }
            }
            RecordContent newRecore = new RecordContent(companyName, link, content);
            DBInsertor.InsertRecord(newRecore);
            br.close();
//            String[] splitedString = resultLine.split("======================================================");
//            for(int i =0;i<splitedString.length;i++)
//            {
//                //System.out.println("splitted:"+splitedString[i]);
//                if(splitedString[i].startsWith("\nhttp"))
//                {
//                    splitedString[i] = splitedString[i].substring(1,splitedString[i].length());
//                    int indexOfEnter = splitedString[i].indexOf("\n");
//                    String http = splitedString[i].substring(0, indexOfEnter);
//                    String restOfContent = splitedString[i].substring(indexOfEnter+1,splitedString[i].length());
//                    //System.out.println(http+restOfContent);
//                    RecordContent newRecore = new RecordContent(companyName,http,restOfContent);
//                    //System.out.println("New object created for company:"+companyName+"\t"+http);
//                    DBInsertor.InsertRecord(newRecore);
//                }
//                else//some garbage, just ignore
//                {
//                    continue;
//                }
//            }
            
        }
        catch (FileNotFoundException e) 
        { 
            // 捕获File对象生成时的异常 
            e.printStackTrace(); 
        }
        catch (IOException e) 
        { 
            // 捕获BufferedReader对象关闭时的异常 
            e.printStackTrace(); 
        } 
        return result;
    } 
    
}
