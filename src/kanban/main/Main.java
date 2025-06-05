package kanban.main;

import kanban.manager.TaskManager;
import kanban.tasks.Epic;
import kanban.tasks.Status;
import kanban.tasks.Subtask;
import kanban.tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();


        Task task1 = new Task("task1", "description 1", Status.NEW);
        Task task2 = new Task("task2", "description 2", Status.IN_PROGRESS);

        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Epic epic1 = new Epic("epic1", "description 1");
        Epic epic2 = new Epic("epic2", "description 2");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask("subtask1", "description 1", epic1.getId(), Status.NEW);
        Subtask subtask2 = new Subtask("subtask2", "description 2", epic1.getId(), Status.IN_PROGRESS);
        Subtask subtask3 = new Subtask("subtask3", "description 3", epic2.getId(), Status.NEW);

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
        System.out.println("Список подзадач 1 эпика: " + taskManager.getSubtasksByEpicId(epic1.getId()));
        System.out.println("Список подзадач 2 эпика: " + taskManager.getSubtasksByEpicId(epic2.getId()));

        taskManager.updateTask(task1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);
        taskManager.updateEpic(epic1);
        taskManager.updateEpic(epic2);

        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
        System.out.println("Список подзадач 1 эпика: " + taskManager.getSubtasksByEpicId(epic1.getId()));
        System.out.println("Список подзадач 2 эпика: " + taskManager.getSubtasksByEpicId(epic2.getId()));

        taskManager.deleteTaskById(task1.getId());
        taskManager.deleteEpicById(epic1.getId());

        System.out.println("Список задач: " + taskManager.getAllTasks());
        System.out.println("Список эпиков: " + taskManager.getAllEpics());
    }
}
