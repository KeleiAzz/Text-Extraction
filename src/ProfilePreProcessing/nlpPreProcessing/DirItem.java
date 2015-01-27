/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ProfilePreProcessing.nlpPreProcessing;
/**
 *
 * @author momo
 */
public class DirItem {
    private String ItemS;
    private String ItemC;
    public DirItem(String Item1, String Item2)
    {
        this.ItemS = Item1;
        this.ItemC = Item2;
    }
    public String getItemS()
    {
        return ItemS;
    }
    public String getItemC()
    {
        return ItemC;
    }
    public void setItemS(String Item1)
    {
        this.ItemS = Item1;
    }
    public void setItemC(String Item2)
    {
        this.ItemC = Item2;
    }
}
