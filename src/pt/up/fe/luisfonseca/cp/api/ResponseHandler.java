package pt.up.fe.luisfonseca.cp.api;

public interface ResponseHandler<T> {

	enum ERROR_TYPE{
		CANCELLED,
		AUTHENTICATION,
		NETWORK,
		GENERAL
	};
	public void onError( ERROR_TYPE error );
	
	public void onResultReceived( T results );
}