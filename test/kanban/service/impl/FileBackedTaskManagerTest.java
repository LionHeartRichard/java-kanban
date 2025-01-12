package kanban.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kanban.model.TaskInterface;
import kanban.model.impl.Epic;
import kanban.model.impl.Subtask;
import kanban.model.impl.Task;
import kanban.util.Graph;

public class FileBackedTaskManagerTest {
	/*
	 * private FileBackedTaskManager manager;
	 * 
	 * private TaskInterface root;
	 * 
	 * private TaskInterface parentA; private TaskInterface parentB; private
	 * TaskInterface childrenA; private TaskInterface childrenA1;
	 * 
	 * private List<String> files; private String path =
	 * "/home/kerrigan_kein/yandex_praktikum/java-kanban/tmp/case"; private final
	 * int CASE_COUNTER = 11;
	 * 
	 * @BeforeEach public void setUp() throws IOException {
	 * 
	 * files = new ArrayList<>(); for (int i = 0; i < CASE_COUNTER; ++i) {
	 * files.add(path + i + ".json"); }
	 * 
	 * for (String file : files) { Path currentPath = Paths.get(file); if
	 * (!Files.exists(currentPath)) Files.createFile(currentPath); }
	 * 
	 * root = new Epic("ROOT", "description ROOT"); parentA = new Epic("Parent A",
	 * "description parent A"); childrenA = new Epic("Children A",
	 * "description children A"); childrenA1 = new Task("Children A1",
	 * "description children A1"); parentB = new Task("Parent B",
	 * "description parent B"); }
	 * 
	 * @Test public void case0AddTaskWhenAddNewTaskInTaskManagerThenReturnNewSize()
	 * { manager = new FileBackedTaskManager(files.get(0)); int expectedSize =
	 * manager.size();
	 * 
	 * assertTrue(manager.addTask(root)); expectedSize += 1;
	 * 
	 * assertEquals(expectedSize, manager.size()); }
	 * 
	 * @Test public void case1AddTaskWhenAddTopAndSubtaskThenReturnNewSize() {
	 * manager = new FileBackedTaskManager(files.get(1)); int expectedSize =
	 * manager.size();
	 * 
	 * manager.addTask(root, parentA); expectedSize += 2;
	 * 
	 * assertEquals(expectedSize, manager.size()); }
	 * 
	 * @Test public void
	 * case2RemoveTasksWhenAdd2TopTasksAnd2SubtasksThenReturnEmptyTaskManager() {
	 * manager = new FileBackedTaskManager(files.get(2)); manager.addTask(root,
	 * parentB); manager.addTask(root, parentA);
	 * 
	 * manager.removeTasks();
	 * 
	 * assertTrue(manager.isEmpty()); }
	 * 
	 * @Test public void
	 * case3UpdateTaskWhenUpdateNameAndDescriptionThenReturnUpdateTask() { manager =
	 * new FileBackedTaskManager(files.get(3)); manager.addTask(root); String id =
	 * root.getId(); String newName = "new name = ROOT or super task"; String
	 * newDescription = "new Description!!!";
	 * 
	 * manager.updateTask(id, newName, newDescription); TaskInterface actual =
	 * manager.getTaskById(id);
	 * 
	 * assertEquals(newName, actual.getName()); assertEquals(newDescription,
	 * actual.getDescription()); }
	 * 
	 * @Test public void case4UpdateTaskWhenUpdateTaskThenReturnUpdateTask() {
	 * manager = new FileBackedTaskManager(files.get(4)); manager.addTask(root);
	 * String id = root.getId(); String newName = "new name = ROOT or super task";
	 * String newDescription = "new Description!!!"; root.setName(newName);
	 * root.setDescription(newDescription);
	 * 
	 * manager.updateTask(root); TaskInterface actual = manager.getTaskById(id);
	 * 
	 * assertEquals(root, actual); }
	 * 
	 * @Test public void case5LoadFromFileWhenInputGraphReturnNewGraphTasks() {
	 * manager = new FileBackedTaskManager(files.get(5)); manager.addTask(root,
	 * parentA); manager.addTask(root, parentB); manager.addTask(parentA,
	 * childrenA); manager.addTask(parentA, childrenA1); Map<TaskInterface,
	 * Set<TaskInterface>> expected = manager.graph.getGraph();
	 * 
	 * FileBackedTaskManager fileManager =
	 * FileBackedTaskManager.loadFromFile(files.get(5)); Map<TaskInterface,
	 * Set<TaskInterface>> actual = fileManager.graph.getGraph();
	 * 
	 * expected.forEach((k, v) -> assertTrue(actual.containsKey(k))); }
	 */
}
