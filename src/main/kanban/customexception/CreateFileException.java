package main.kanban.customexception;

public class CreateFileException extends RuntimeException {
	public CreateFileException(String message) {
		super(message);
	}
}
