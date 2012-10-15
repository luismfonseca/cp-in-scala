package pt.up.fe.luisfonseca.cp.api

import android.os.AsyncTask
import java.io.IOException
import org.apache.http.auth.AuthenticationException

class RetrieveTaskResult[T>: Null <: AnyRef]
		(val result: T = null, val error : ResponseHandler.Error.Type = null)
{
	def this(error : ResponseHandler.Error.Type = null) = this(null, error)
}

class RetrieveTask[T>: Null <: AnyRef]
	(val command: ResponseHandler[T], val parser : JsonParser[T])
	extends AsyncTask[String, Void, RetrieveTaskResult[T]]
{

	override protected def onPostExecute(result: RetrieveTaskResult[T]) = {
		if (!isCancelled())
		{
			if (result.error == null)
				command.onResultReceived(result.result)
			else
				command.onError(result.error)
		}
	}
	
	override protected def doInBackground(pages: String*) : RetrieveTaskResult[T] = {
		try {
			if (isCancelled())
				return null
			
			if (pages.length < 1)
				return new RetrieveTaskResult[T](ResponseHandler.Error.General)

			new RetrieveTaskResult[T](parser.parse(CP.getReply(pages.head, "")))
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
		return null;
	}
}