package pt.up.fe.luisfonseca.cp.api;

import pt.up.fe.luisfonseca.cp.api.json.Warnings;

import com.google.gson.Gson;

public class WarningsLoader extends RetrieveTask<Warnings> {
	
	private static class WarningsParser implements JsonParser<Warnings>
	{
		public Warnings parse(String json) {
	        Gson gson = new Gson();
	        
			return gson.fromJson(json, Warnings.class);
		}
	}

	public WarningsLoader(ResponseHandler<Warnings> handler) {
		super(handler, new WarningsParser());
		this.execute(CP.URLwarnings);
	}
}