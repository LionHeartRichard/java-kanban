package kanban.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import kanban.customexception.CreateFileException;
import kanban.customexception.LoadException;
import kanban.customexception.ManagerSaveException;
import kanban.customexception.NotFileFindExceptions;
import kanban.model.TaskInterface;
import kanban.parsing.GraphDeserializer;
import kanban.parsing.TaskInterfaceDeserializer;
import kanban.parsing.TaskInterfaceKeyDeserializer;
import kanban.util.Graph;
import kanban.util.Status;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileBackedTaskManager extends InMemoryTaskManager {

	private static ObjectMapper mapper = new ObjectMapper();
	private Path mainPath;

	public FileBackedTaskManager(final String pathToFile) {
		this.mainPath = Paths.get(pathToFile);
		if (!Files.exists(mainPath))
			throw new NotFileFindExceptions("При запуске приложения был не верно указан путь к файлу! ");
	}

	public static FileBackedTaskManager loadFromFile(String pathToFile) throws IOException {
		FileBackedTaskManager fileTaskManager = new FileBackedTaskManager(pathToFile);
		InMemoryTaskManager memoryTaskManger = loadData(pathToFile);

		// получаем данные из файла (граф хранит взаимоотношения между задачами)
		fileTaskManager.graph = memoryTaskManger.graph;

		// настраиваем factory, регистрируем все такски в factory
		// для того чтобы на выходе получить настроеный объект fileTaskManager
		for (TaskInterface task : fileTaskManager.graph.getAdjacencyList().keySet()) {
			task.registerMyself(fileTaskManager.factory);
		}
		return fileTaskManager;
	}

	private static InMemoryTaskManager loadData(String pathToFile) throws IOException {
		try (FileReader reader = new FileReader(pathToFile); BufferedReader buffer = new BufferedReader(reader)) {

			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = buffer.readLine()) != null) {
				builder.append(line);
			}
			String json = builder.toString();

			SimpleModule module = new SimpleModule();
			module.addDeserializer(Graph.class, new GraphDeserializer());

			module.addKeyDeserializer(TaskInterface.class, new TaskInterfaceKeyDeserializer());

			module.addDeserializer(TaskInterface.class, new TaskInterfaceDeserializer());

			mapper.registerModule(module);

			InMemoryTaskManager memoryTaskManager = mapper.readValue(json, InMemoryTaskManager.class);
			return memoryTaskManager;
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
	}

	private void save() {
		try (FileWriter writer = new FileWriter(mainPath.toFile());
				BufferedWriter buffer = new BufferedWriter(writer)) {
			InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager(graph);
			String json = mapper.writeValueAsString(inMemoryTaskManager);
			buffer.write(json);
		} catch (JsonProcessingException e) {
			throw new ManagerSaveException("Ошибка преобразования в JSON. " + e.getMessage());
		} catch (IOException e) {
			throw new ManagerSaveException("Ошибка при сохранении в файл. " + e.getMessage());
		}
	}

	private void load() throws IOException {
		try (FileReader reader = new FileReader(this.mainPath.toString());
				BufferedReader buffer = new BufferedReader(reader)) {

			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = buffer.readLine()) != null) {
				builder.append(line);
			}
			String json = builder.toString();

			SimpleModule module = new SimpleModule();
			module.addDeserializer(Graph.class, new GraphDeserializer());

			module.addKeyDeserializer(TaskInterface.class, new TaskInterfaceKeyDeserializer());

			module.addDeserializer(TaskInterface.class, new TaskInterfaceDeserializer());

			mapper.registerModule(module);

			InMemoryTaskManager memoryTaskManager = mapper.readValue(json, InMemoryTaskManager.class);
			this.graph = memoryTaskManager.graph;
			for (TaskInterface task : this.graph.getAdjacencyList().keySet()) {
				task.registerMyself(this.factory);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		}
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

	@Override
	public Set<TaskInterface> getAllSetTasks() {
		try {
			load();
			return super.getAllSetTasks();
		} catch (IOException e) {
			throw new LoadException("Возникла ошибка при загрузке набора задач (getAllSetTasks) " + e.getMessage());
		}
	}

	@Override
	public List<TaskInterface> getAllTasks() {
		try {
			load();
			return super.getAllTasks();
		} catch (IOException e) {
			throw new LoadException("Возникла ошибка при загрузке листа задач (getAllTasks) " + e.getMessage());
		}
	}

	@Override
	public boolean isEmpty() {
		try {
			load();
			return super.isEmpty();
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (isEmpty) " + e.getMessage());
		}
	}

	@Override
	public int size() {
		try {
			load();
			return super.size();
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (size) " + e.getMessage());
		}
	}

	@Override
	public boolean containsTaskById(String id) {
		try {
			load();
			return super.containsTaskById(id);
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (containsTaskById) " + e.getMessage());
		}
	}

	@Override
	public TaskInterface getTaskById(String id) {
		try {
			load();
			return super.getTaskById(id);
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (getTaskById) " + e.getMessage());
		}
	}

	@Override
	public Set<TaskInterface> getSetSubtasks(String id) {
		try {
			load();
			return super.getSetSubtasks(id);
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (getSetSubtasks) " + e.getMessage());
		}
	}

	@Override
	public List<TaskInterface> getSubtasks(String id) {
		try {
			load();
			return super.getSubtasks(id);
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (getSubtasks) " + e.getMessage());
		}
	}

	@Override
	public List<TaskInterface> getListTasksByStatus(Status status) {
		try {
			load();
			return super.getListTasksByStatus(status);
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (getListTasksByStatus) " + e.getMessage());
		}
	}

	@Override
	public List<TaskInterface> getHistory() {
		try {
			load();
			return super.getHistory();
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (getHistory) " + e.getMessage());
		}
	}

	@Override
	public Set<TaskInterface> getPrioritizedTasks() {
		try {
			load();
			return super.getPrioritizedTasks();
		} catch (Exception e) {
			throw new LoadException("Ошибка загрузки (getPrioritizedTasks) " + e.getMessage());
		}
	}

}
