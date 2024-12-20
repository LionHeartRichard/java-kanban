package kanban.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;

import kanban.customexception.CreateFileException;
import kanban.customexception.ManagerSaveException;
import kanban.customexception.NotFileFindExceptions;
import kanban.deletethispackage.GraphNotGenerics;
import kanban.model.TaskInterface;
import kanban.model.impl.Task;
import kanban.util.Graph;
import kanban.util.Status;

public class FileBackedTaskManager extends InMemoryTaskManager {

	@JsonAnyGetter
	private static ObjectMapper mapper = new ObjectMapper();
	private Path mainPath;

	public FileBackedTaskManager(final String pathToFile) {
		this.mainPath = Paths.get(pathToFile);
		if (!Files.exists(mainPath))
			throw new NotFileFindExceptions(
					"При создании объекта FileBackedTaskManager был не верно указан путь к файлу! ");
	}

	public static FileBackedTaskManager loadFromFile(String pathToFile) {
		FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(pathToFile);
		fileBackedTaskManager.graph = loadData(pathToFile);
		return fileBackedTaskManager;
	}

	private static Graph<TaskInterface> loadData(String fileName) {
		try {
			File file = new File(fileName);
			Graph<TaskInterface> result = mapper.readValue(file, new TypeReference<Graph<TaskInterface>>() {
			});
			return result;
		} catch (IOException e) {
			System.err.println("Произошла ошибка во время чтения файла!!! ");
			e.printStackTrace();
		}
		return null;
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
			Files.delete(mainPath);
			Files.createFile(mainPath);
		} catch (IOException e) {
			throw new CreateFileException(
					"Возникла ошибка при перезаписи файла во время удаления данных! " + e.getMessage());
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

	@Override
	public boolean changeStatusTask(String id) {
		boolean ans = super.changeStatusTask(id);
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
