package kanban;

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

        Epic epic1 = new Epic("epic1", "description 1");
        taskManager.addEpic(epic1);
        taskManager.updateEpic(epic1);

        SubTask subTask1 = new SubTask("subTask1", "description 1", epic1.getId(), Status.NEW);
        SubTask subTask2 = new SubTask("subTask2", "description 2", epic1.getId(), Status.IN_PROGRESS);


    }
}
