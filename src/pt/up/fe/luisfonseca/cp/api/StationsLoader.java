package pt.up.fe.luisfonseca.cp.api;

import pt.up.fe.luisfonseca.cp.api.json.Stations;

import com.google.gson.Gson;

public class StationsLoader extends RetrieveTask<String[]> {
	
	private static class StationsParser implements JsonParser<String[]>
	{
		public String[] parse(String json) {
	        Gson gson = new Gson();
	        Stations response = gson.fromJson(json, Stations.class);
	        
			return response.stations;
		}
	}

	public StationsLoader(ResponseHandler<String[]> handler) {
		super(handler, new StationsParser());
		this.execute(CP.URLstations);
	}
}