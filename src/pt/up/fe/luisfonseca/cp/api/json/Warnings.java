package pt.up.fe.luisfonseca.cp.api.json;

public class Warnings extends Error {

	public WarningItem[] today;
	public WarningItem[] tomorrow;
	public WarningItem[] after;
	
	public static class WarningItem
	{
		public String title,
					  desc;
	}
}