package kanban.main;

import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Status;
import kanban.tasks.SubTask;
import kanban.tasks.Task;

public class Main {
    public static void main(String[] args) {
        // Создайте две задачи, а также эпик с двумя подзадачами и эпик с одной подзадачей
        // Распечатайте списки эпиков, задач и подзадач

        TaskManager taskManager = new TaskManager();
       /* Task task1 = new Task("task1", "description 1");

        Task newTask = taskManager.addTask(task1);
        taskManager.updateTask(task1);
        System.out.println(newTask.getId());

        Epic epic1 = new Epic("Epic1", "description 1");

        Epic newEpic = taskManager.addEpic(epic1);
        System.out.println(newEpic.getId());

        SubTask subTask1 = new SubTask("SubTask1", "description 1", newEpic.getId());

        SubTask newSubTask = taskManager.addSubTask(subTask1);
        System.out.println(newSubTask); */

        Task task1 = new Task("task1", "description 1", Status.NEW);
        Task task2 = new Task("task2", "description 2", Status.IN_PROGRESS);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("epic1", "description 1");
        Epic epic2 = new Epic("epic2", "description 2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        SubTask subTask1 = new SubTask("subTask1", "description 1", epic1.getId(), Status.NEW);
        SubTask subTask2 = new SubTask("subTask2", "description 2", epic1.getId(), Status.IN_PROGRESS);
        SubTask subTask3 = new SubTask("subTask3", "description 3", epic2.getId(), Status.NEW);

        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);

        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
        System.out.println("Список подзадач 1 эпика: " + taskManager.getSubTasksByEpicId(epic1.getId()));
        System.out.println("Список подзадач 2 эпика: " + taskManager.getSubTasksByEpicId(epic2.getId()));

        taskManager.updateTask(task1);
        taskManager.updateSubTask(subTask2);
        taskManager.updateSubTask(subTask3);
        taskManager.updateEpic(epic1);
        taskManager.updateEpic(epic2);

        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
        System.out.println("Список подзадач 1 эпика: " + taskManager.getSubTasksByEpicId(epic1.getId()));
        System.out.println("Список подзадач 2 эпика: " + taskManager.getSubTasksByEpicId(epic2.getId()));

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic1.getId());

        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
    }
}
