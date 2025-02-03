package kanban.customexception;

public class LoadException extends RuntimeException {
	public LoadException(String message) {
		super(message);
	}
}
