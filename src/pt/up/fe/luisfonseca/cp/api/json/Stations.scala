package pt.up.fe.luisfonseca.cp.api.json

case class Stations(val stations: List[String], val error: String = null, val error_msg:String = null)
	extends Error
{
  
}