package pt.up.fe.luisfonseca.cp.api.json

case class News(val news: List[NewItem], val error: String = null, val error_msg:String = null)
	extends Error
{
	
}

case class NewItem(val date: String, val img: String, val title: String, val desc: String)
{
  
}