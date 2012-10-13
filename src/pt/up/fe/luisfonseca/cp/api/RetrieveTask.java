package pt.up.fe.luisfonseca.cp.api;

import java.io.IOException;

import org.apache.http.auth.AuthenticationException;

import pt.up.fe.luisfonseca.cp.api.ResponseHandler.ERROR_TYPE;
import android.os.AsyncTask;

public class RetrieveTask<T> extends AsyncTask<String, Void, ResponseHandler.ERROR_TYPE> {

	private final ResponseHandler<T> command;
	private final JsonParser<T> parser;
	private T result;
    private boolean running = true;

	public RetrieveTask(ResponseHandler<T> com, JsonParser<T> parser) {
		this.command = com;
		this.parser = parser;
	}

    @Override
    protected void onCancelled() {
        running = false;
    }

	protected void onPostExecute(ERROR_TYPE error) {
		if(!running)
			return;
		
		if (error == null) {
			command.onResultReceived(this.result);
			return;
		}
		command.onError(error);
	}

	protected ERROR_TYPE doInBackground(String... pages) {
		String page = "";
		try {
			if (pages.length < 1)
				return ERROR_TYPE.GENERAL;
			
			if (isCancelled())
				return null;
			
			page = CP.getReply(pages[0], "");
			result = parser.parse(page);
			if (isCancelled())
				return null;
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR_TYPE.NETWORK;
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return ERROR_TYPE.AUTHENTICATION;
		}
		return null;
	}
}
