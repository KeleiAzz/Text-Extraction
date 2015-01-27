/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DownloadContentInsertor;

/**
 *
 * @author SCRC
 */
public class RecordContent 
{
    private String companyName;
    private String link;
    private String content;

    public RecordContent(String companyName, String link, String content) 
    {
        this.companyName = companyName;
        this.link = link;
        this.content = content;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
