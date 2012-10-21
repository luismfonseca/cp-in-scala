package pt.up.fe.luisfonseca.cp.api

import pt.up.fe.luisfonseca.util.ScalaAsyncTask
import org.apache.http.auth.AuthenticationException
import java.io.IOException

class RetrieveTaskResult[T>: Null <: AnyRef]
	(val result: T, val error : ResponseHandler.Error.Type = null)
{
	def this(error : ResponseHandler.Error.Type = null) = this(null, error)
}

class RetrieveTask[T>: Null <: AnyRef]
	(val command: ResponseHandler[T], val parser : JsonParser[T])
	extends ScalaAsyncTask[String, Void, RetrieveTaskResult[T]]
{

	override protected def onPostExecute(result: RetrieveTaskResult[T]) = {
		if (!isCancelled())
		{
			if (result == null)
				command.onError(null)
			else if (result.error == null)
				command.onResultReceived(result.result)
			else
				command.onError(result.error)
		}
	}
	
	override protected def doInBackground(page: String) : RetrieveTaskResult[T] = {
		try {
			if (isCancelled())
				return null
			
			if (page == null)
				return new RetrieveTaskResult[T](ResponseHandler.Error.General)

			new RetrieveTaskResult[T](parser.parse(CP.getReply(page, "")))
		} catch {
			case e : IOException => {
				e.printStackTrace()
				return new RetrieveTaskResult[T](ResponseHandler.Error.Network)
			}
			case e : AuthenticationException => {
				e.printStackTrace()
				return new RetrieveTaskResult[T](ResponseHandler.Error.Authentication)
			}
		}
	}
}