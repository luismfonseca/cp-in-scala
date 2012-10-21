package pt.up.fe.luisfonseca.cp.api

import scala.util.parsing.json.JSON
import pt.up.fe.luisfonseca.cp.api.json.Stations
import org.json.JSONObject

class StationsParser extends JsonParser[List[String]]
{
	def parse(json: String) : List[String] = {
	  
	  val jObject = new JSONObject(json);
	  if (jObject.has("stations"))
	  {
		  val jStations = jObject.getJSONArray("stations")
		  
		  for(i<- (0 to jStations.length() - 1).toList)
		    yield jStations.getString(i)
	  }
	  else
	    null
	}
}

class StationsLoader(val handler: ResponseHandler[List[String]])
	extends RetrieveTask[List[String]](handler, new StationsParser())
{
	def execute = super.execute(CP.URLstations);
}