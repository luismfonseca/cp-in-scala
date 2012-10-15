package pt.up.fe.luisfonseca.cp.api

import scala.util.parsing.json.JSON
import org.json.JSONObject
import pt.up.fe.luisfonseca.cp.api.json.News
import pt.up.fe.luisfonseca.cp.api.json.NewItem

class NewsLoader(handler: ResponseHandler[News])
	extends RetrieveTask[News](handler, new NewsParser())
{
	def execute = super.execute(CP.URLnews);
}

class NewsParser extends JsonParser[News]
{
	def parse(json: String) : News = {
	  
	  val jObject = new JSONObject(json);
	  if(jObject.has("news"))
	  {
		  val jNews = jObject.getJSONArray("news")
		  
		  val newsItems = for(i<- (0 to jNews.length() - 1).toList)
			  yield new NewItem(jNews.getJSONObject(i).getString("date"),
		    					jNews.getJSONObject(i).getString("img"),
		    					jNews.getJSONObject(i).getString("title"),
		    					jNews.getJSONObject(i).getString("desc"))
		  
		  new News(newsItems)
	  }
	  else
	    null
	}
}