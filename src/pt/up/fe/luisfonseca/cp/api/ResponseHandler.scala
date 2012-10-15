package pt.up.fe.luisfonseca.cp.api

object ResponseHandler {
  object Error extends Enumeration {
    type Type = Value
    val Cancelled,
		Authentication,
		Network,
		General = Value
  }
}

trait ResponseHandler[T] {
	
  def onError(error: ResponseHandler.Error.Type)
  
  def onResultReceived(result: T)
}