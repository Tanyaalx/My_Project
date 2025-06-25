package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Status;
import kanban.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void init() {
        taskManager = Managers.getDefault();
    }


    @Test
    void addTask() {
        String name = "Сделать ТЗ";
        String description = "Написать тест";
       Task task = new Task(name, description, Status.NEW);

       taskManager.addTask(task);
       Task actualTask = taskManager.getTaskById(task.getId());

        Assertions.assertNotNull(actualTask.getId());
        Assertions.assertEquals(actualTask.getStatus(), Status.NEW);
        Assertions.assertEquals(actualTask.getName(), name);
        Assertions.assertEquals(actualTask.getDescription(), description);
    }


    @Test
    void getTaskById() {
        String name = "task";
        String description = "description";
        Task task = new Task(name, description, Status.NEW);

        taskManager.addTask(task);
        Integer id = task.getId();
        Task actualTask = taskManager.getTaskById(id);

        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(actualTask.getStatus(), Status.NEW);
        Assertions.assertEquals(actualTask.getName(), name);
        Assertions.assertEquals(actualTask.getDescription(), description);
        Assertions.assertEquals(actualTask.getId(), id);
    }

    @Test
    void getTaskByNonexistenId() {
        Integer id = 100;
        String name = "task";
        String description = "description";
        Task task = new Task(id, name, description);

        Task actualTask = taskManager.getEpicById(task.getId());

        Assertions.assertNull(actualTask);
    }

    @Test
    void updateTask() {
        String name = "task";
        String description = "description";
        Task task = new Task(name, description, Status.DONE);

        taskManager.addTask(task);
        Integer id = task.getId();
        Task actualTask = taskManager.updateTask(task);

        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(actualTask.getName(), name);
        Assertions.assertEquals(actualTask.getDescription(), description);
        Assertions.assertEquals(actualTask.getStatus(), Status.DONE);
        Assertions.assertEquals(actualTask.getId(),id);
    }
    @Test
    void updateNonexistenTask() {
        String name = "task";
        String description = "description";
        Task task = new Task(name, description, Status.NEW);

        Task actualTask = taskManager.updateTask(task);

        Assertions.assertNull(actualTask);
    }

    @Test
    void deleteTaskById() {
        String name = "task";
        String description = "description";
        Task task = new Task(name, description, Status.NEW);

        taskManager.addTask(task);
        Integer id = task.getId();

        Task actualTask = taskManager.deleteTaskById(id);

        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(actualTask.getName(), name);
        Assertions.assertEquals(actualTask.getDescription(), description);
        Assertions.assertEquals(actualTask.getStatus(), Status.NEW);
        Assertions.assertEquals(actualTask.getId(), id);
    }


    @Test
    void addEpic() {
        String name = "epic1";
        String description = "description1";
        Epic epic = new Epic(name, description);

        taskManager.addEpic(epic);
        Integer id = epic.getId();
        Epic actualEpic = taskManager.getEpicById(id);

        Assertions.assertNotNull(actualEpic.getId());
        Assertions.assertEquals(actualEpic.getName(), name);
        Assertions.assertEquals(actualEpic.getDescription(), description);

    }

    @Test
    void getEpicById() {
        String name = "epic1";
        String description = "description1";
        Epic epic = new Epic(name, description);

        taskManager.addEpic(epic);
        Integer id = epic.getId();
        Epic actualEpic = taskManager.getEpicById(id);

        Assertions.assertNotNull(actualEpic);
        Assertions.assertEquals(actualEpic.getId(),id);
        Assertions.assertEquals(actualEpic.getName(),name);
        Assertions.assertEquals(actualEpic.getDescription(), description);
    }
    @Test
    void getEpicByNonexistenId() {
        String name = "epic1";
        String description = "description1";
        Epic epic = new Epic(name, description);
        epic.setId(100);

        Epic actualEpic = taskManager.getEpicById(epic.getId());

        Assertions.assertNull(actualEpic);
    }

    @Test
    void updateEpic() {
    }

    @Test
    void deleteEpicById() {
        String name = "epic1";
        String description = "description1";
        Epic epic = new Epic(name, description);

        taskManager.addEpic(epic);
        Integer id = epic.getId();
        Epic actualEpic = taskManager.deleteEpicById(id);

        Assertions.assertNotNull(actualEpic);
        Assertions.assertEquals(actualEpic.getName(), name);
        Assertions.assertEquals(actualEpic.getDescription(), description);
        Assertions.assertEquals(actualEpic.getId(), id);
    }

    @Test
    void addSubtask() {
    }

    @Test
    void getSubtaskById() {
    }

    @Test
    void updateSubtask() {
    }

    @Test
    void deleteSubtaskById() {
    }

    @Test
    void getSubtasksByEpicId() {
    }

    @Test
    void updateEpicStatus() {
    }

    @Test
    void getHistory() {
    }
}