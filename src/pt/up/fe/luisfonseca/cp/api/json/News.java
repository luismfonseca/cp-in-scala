package pt.up.fe.luisfonseca.cp.api.json;

public class News extends Error {

	public NewItem[] news;
	
	public static class NewItem
	{
		public String date,
					  img,
					  title,
					  desc;
	}
}