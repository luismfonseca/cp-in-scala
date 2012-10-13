package pt.up.fe.luisfonseca.cp.api;

import pt.up.fe.luisfonseca.cp.api.json.News;

import com.google.gson.Gson;

public class NewsLoader extends RetrieveTask<News> {
	
	private static class NewsParser implements JsonParser<News>
	{
		public News parse(String json) {
	        Gson gson = new Gson();
	        
			return gson.fromJson(json, News.class);
		}
	}

	public NewsLoader(ResponseHandler<News> handler) {
		super(handler, new NewsParser());
		this.execute(CP.URLnews);
	}
}