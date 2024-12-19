package kanban.service.impl;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;

import kanban.customexception.CreateFileException;
import kanban.customexception.ManagerSaveException;
import kanban.customexception.NotFileFindExceptions;
import kanban.model.TaskInterface;
import kanban.util.Graph;

public class FileBackedTaskManager extends InMemoryTaskManager {

	private String mainFile;
	private String historyFile;
	private static ObjectMapper mapper = new ObjectMapper();
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
	}

	public static FileBackedTaskManager loadFromFile(String mainFile, String historyFile) {
		FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(mainFile, historyFile);
		fileBackedTaskManager.graph = loadFileData(mainFile);
		return fileBackedTaskManager;
	}

	private static Graph<TaskInterface> loadFileData(String mainFile) {
		try(FileReader reader =new FileReader(new File(mainFile))){
		JsonNode current = mapper.readTree(reader);
		return current;
	}

	@Override
	public boolean addTask(TaskInterface task) {
		boolean ans = super.addTask(task);
		if (ans)
			save();
		return ans;
	}

	@Override
	public boolean addTask(TaskInterface topTask, TaskInterface task) {
		boolean ans = super.addTask(topTask, task);
		if (ans)
			save();
		return ans;
	}

	@Override
	public boolean removeTaskById(String id) {
		boolean ans = super.removeTaskById(id);
		if (ans)
			save();
		return ans;
	}

	@Override
	public void removeTasks() {
		super.removeTasks();
		try {
			Files.createFile(mainPath);
		} catch (IOException e) {
			throw new CreateFileException(
					"Возникла ошибка при создании-перезаписи пустого файла во время удаления данных! "
							+ e.getMessage());
		}
	}

	@Override
	public boolean updateTask(String id, String newName, String newDescription) {
		boolean ans = super.updateTask(id, newName, newDescription);
		if (ans)
			save();
		return ans;
	}

	@Override
	public boolean updateTask(TaskInterface task) {
		boolean ans = super.updateTask(task);
		if (ans)
			save();
		return ans;
	}

	private void save() {
		try (FileWriter writer = new FileWriter(mainPath.toFile());
				BufferedWriter buffer = new BufferedWriter(writer)) {
			String json = mapper.writeValueAsString(graph);
			buffer.write(json);
		} catch (JsonProcessingException e) {
			throw new ManagerSaveException("Ошибка преобразования в JSON. " + e.getMessage());
		} catch (IOException e) {
			throw new ManagerSaveException("Ошибка при сохранении в файл. " + e.getMessage());
		}
	}
}
