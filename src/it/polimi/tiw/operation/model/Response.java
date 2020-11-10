package it.polimi.tiw.operation.model;

public class Response {

	private final boolean success;
	private final Object data;
	
	public Response(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}
	
	public static Response success(Object data) {
		return new Response(true, data);
	}
	
	public static Response success() {
		return new Response(true, null);
	}
	
	public static Response error(String message) {
		return new Response(false, message);
	}
}
