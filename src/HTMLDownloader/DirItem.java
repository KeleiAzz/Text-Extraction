package HTMLDownloader;

/**
 * Created by keleigong on 1/26/15.
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
