package pt.up.fe.luisfonseca.cp.api.json

case class Warnings(
    val today: List[WarningItem],
	val tomorrow: List[WarningItem],
	val after: List[WarningItem],
	val error: String = null, val error_msg:String = null)
	extends Error
{

}

case class WarningItem(val title: String, val desc: String)
{
  
}