package kanban.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kanban.customexception.ManagerSaveException;
import kanban.customexception.NotFileFindExceptions;
import kanban.model.TaskInterface;

public class FileBackedTaskManager extends InMemoryTaskManager {

	private final String mainFile;
	private final String historyFile;
	private ObjectMapper mapper;
	private Path mainPath;
	private Path historyPath;

	public FileBackedTaskManager(final String mainFile, final String historyFile) {
		this.mainFile = mainFile;
		this.historyFile = historyFile;
		this.mainPath = Paths.get(mainFile);
		this.historyPath = Paths.get(historyFile);
		if (!Files.exists(mainPath) || !Files.exists(historyPath))
			throw new NotFileFindExceptions(
					"При создании объекта FileBackedTaskManager был не верно указан путь к файлу/файлам! ");
		mapper = new ObjectMapper();
	}

	@Override
	public boolean addTask(TaskInterface task) {
		boolean ans = super.addTask(task);
		if (ans)
			save();
		return ans;
	}

	private void save() {
		try {
			String json = mapper.writeValueAsString(graph);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			throw new ManagerSaveException(e.getMessage());
		}

	}
}
