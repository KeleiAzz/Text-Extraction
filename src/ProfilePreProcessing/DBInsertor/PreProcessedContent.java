/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProfilePreProcessing.DBInsertor;

/**
 *
 * @author SCRC
 */
public class PreProcessedContent 
{
    private String companyName;
    private String content;

    public PreProcessedContent(String companyName, String content) 
    {
        this.companyName = companyName;
        this.content = content;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
