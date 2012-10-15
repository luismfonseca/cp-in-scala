package pt.up.fe.luisfonseca.cp.api

import org.json.JSONObject
import scala.util.parsing.json.JSON
import pt.up.fe.luisfonseca.cp.api.json.{Warnings, WarningItem}
import org.json.JSONArray

class WarningsLoader(val handler: ResponseHandler[Warnings])
	extends RetrieveTask[Warnings](handler, new WarningsParser())
{
	def execute = super.execute(CP.URLwarnings);
}

class WarningsParser extends JsonParser[Warnings]
{
	def parse(json: String) : Warnings = {
	  
	  val jObject = new JSONObject(json);
	  if(jObject.has("today") && jObject.has("tomorrow") && jObject.has("after"))
	  {  
		  def extractNewsItem(ja: JSONArray): List[WarningItem] = {
		    for(i<- (0 to ja.length() - 1).toList)
			  yield new WarningItem(ja.getJSONObject(i).getString("title"),
		    						ja.getJSONObject(i).getString("desc"))
		  }
		  
		  val today = extractNewsItem(jObject.getJSONArray("today"))
		  val tomorrow = extractNewsItem(jObject.getJSONArray("tomorrow"))
		  val after = extractNewsItem(jObject.getJSONArray("after"))

		  new Warnings(today, tomorrow, after)
	  }
	  else
	    null
	}
}