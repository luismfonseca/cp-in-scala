package pt.up.fe.luisfonseca.cp.api;

public interface JsonParser<T> {

	public T parse(String json);
}
