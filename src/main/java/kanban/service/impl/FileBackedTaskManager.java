package kanban.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import kanban.customexception.CreateFileException;
import kanban.customexception.ManagerSaveException;
import kanban.customexception.NotFileFindExceptions;
import kanban.model.TaskInterface;
import kanban.parsing.GraphDeserializer;
import kanban.parsing.TaskInterfaceDeserializer;
import kanban.parsing.TaskInterfaceKeyDeserializer;
import kanban.util.Graph;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileBackedTaskManager extends InMemoryTaskManager {

	private static ObjectMapper mapper = new ObjectMapper();
	private Path mainPath;

	public FileBackedTaskManager(final String pathToFile) {
		this.mainPath = Paths.get(pathToFile);
		if (!Files.exists(mainPath))
			throw new NotFileFindExceptions(
					"При создании объекта FileBackedTaskManager был не верно указан путь к файлу! ");
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

}
