package kanban.manager;

import kanban.tasks.Epic;
import kanban.tasks.Status;
import kanban.tasks.Subtask;
import kanban.tasks.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void init() {
        taskManager = Managers.getDefault();
    }


    @Test
    void addTask_shouldCreateTask() {
        String name = "Сделать ТЗ";
        String description = "Написать тест";
       Task task = new Task(name, description, Status.NEW);

       taskManager.addTask(task);
       Integer id = task.getId();
       Task actualTask = taskManager.getTaskById(id);

        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(Status.NEW, actualTask.getStatus());
        Assertions.assertEquals(name, actualTask.getName());
        Assertions.assertEquals(description, actualTask.getDescription());
        Assertions.assertEquals(id, actualTask.getId());
    }


    @Test
    void getTaskById_shouldReturnTaskIfTaskHaveInMap() {
        String name = "task";
        String description = "description";
        Task task = new Task(name, description, Status.NEW);

        taskManager.addTask(task);
        Integer id = task.getId();
        Task actualTask = taskManager.getTaskById(id);

        Assertions.assertNotNull(actualTask);
        Assertions.assertEquals(Status.NEW, actualTask.getStatus());
        Assertions.assertEquals(name, actualTask.getName());
        Assertions.assertEquals(description, actualTask.getDescription());
        Assertions.assertEquals(id, actualTask.getId());
    }

    @Test
    void getTaskById_shouldReturnNullIfTaskIdDoesNotExist() {
        Task actualTask = taskManager.getTaskById(-100);

        Assertions.assertNull(actualTask);
    }

    @Test
    void updateTask_shouldUpdateTaskFieldsInMap() {
        String name = "original name";
        String description = "original description";
        Task originalTask = new Task(name, description, Status.NEW);

        taskManager.addTask(originalTask);
        Integer id = originalTask.getId();

        Task updatedTask = new Task("update name", "update description", Status.IN_PROGRESS);
        updatedTask.setId(id);
        taskManager.updateTask(updatedTask);

        Task storedTask = taskManager.getTaskById(id);

        Assertions.assertNotNull(storedTask);
        Assertions.assertEquals("update name", storedTask.getName());
        Assertions.assertEquals("update description", storedTask.getDescription());
        Assertions.assertEquals(Status.IN_PROGRESS, storedTask.getStatus());
        Assertions.assertEquals(id, storedTask.getId());
    }
    @Test
    void updateTask_shouldReturnNullIfTaskDoesNotExist() {
        String name = "task";
        String description = "description";
        Task nonExistingTask = new Task(name, description, Status.NEW);
        nonExistingTask.setId(-100);

        Task updateTask = taskManager.updateTask(nonExistingTask);

        Assertions.assertNull(updateTask);
    }

    @Test
    void deleteTaskById_shouldDeleteTask() {
        String name = "task";
        String description = "description";
        Task task = new Task(name, description, Status.NEW);

        taskManager.addTask(task);
        Integer id = task.getId();

        taskManager.deleteTaskById(id);
        Task actualTask = taskManager.getTaskById(id);

        Assertions.assertNull(actualTask);
    }


    @Test
    void addEpic_shouldCreateEpic() {
        String name = "epic1";
        String description = "description1";
        Epic epic = new Epic(name, description, Status.NEW);

        taskManager.addEpic(epic);
        Integer id = epic.getId();
        Epic actualEpic = taskManager.getEpicById(id);

        Assertions.assertNotNull(actualEpic);
        Assertions.assertEquals(name, actualEpic.getName());
        Assertions.assertEquals(description, actualEpic.getDescription());
        Assertions.assertEquals(id, actualEpic.getId());
    }

    @Test
    void getEpicById_shouldReturnEpic() {
        String name = "epic1";
        String description = "description1";
        Epic epic = new Epic(name, description, Status.NEW);

        taskManager.addEpic(epic);
        Integer id = epic.getId();
        Epic actualEpic = taskManager.getEpicById(id);

        Assertions.assertNotNull(actualEpic);
        Assertions.assertEquals(id, actualEpic.getId());
        Assertions.assertEquals(name, actualEpic.getName());
        Assertions.assertEquals(description, actualEpic.getDescription());
    }
    @Test
    void getEpicById_shouldReturnNullIfEpicDoesNotExist() {
        Epic actualEpic = taskManager.getEpicById(-100);

        Assertions.assertNull(actualEpic);
    }

    @Test
    void updateEpic_shouldUpdateEpicFieldsInMap() {
        String name = "original name";
        String description = "original description";
        Epic origialEpic = new Epic(name, description, Status.NEW);

        taskManager.addEpic(origialEpic);
        Integer id = origialEpic.getId();

        Epic updatedEpic = new Epic("update name", "update description", Status.NEW);
        updatedEpic.setId(id);
        taskManager.updateEpic(updatedEpic);

        Epic storedEpic = taskManager.getEpicById(id);

        Assertions.assertNotNull(storedEpic);
        Assertions.assertEquals("update name", storedEpic.getName());
        Assertions.assertEquals("update description", storedEpic.getDescription());
        Assertions.assertEquals(id, storedEpic.getId());
    }

    @Test
    void deleteEpicById_shouldDeleteEpic() {
        String name = "epic1";
        String description = "description1";
        Epic epic = new Epic(name, description, Status.NEW);

        taskManager.addEpic(epic);
        Integer id = epic.getId();
        taskManager.deleteEpicById(id);
        Epic actualEpic = taskManager.getEpicById(id);

        Assertions.assertNull(actualEpic);
    }

    @Test
    void addSubtask_shouldCreateSubtask() {
        String name = "Epic";
        String description = "description";
        Epic epic = new Epic(name, description, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        String originalName = "Subtask";
        String originalDescription = "description";
        Subtask subtask = new Subtask(originalName, originalDescription, epicId, Status.NEW);

        taskManager.addSubtask(subtask);
        Integer id = subtask.getId();
        Subtask actualSubtask = taskManager.getSubtaskById(id);

        Assertions.assertNotNull(actualSubtask);
        Assertions.assertEquals(originalName, actualSubtask.getName());
        Assertions.assertEquals(originalDescription, actualSubtask.getDescription());
        Assertions.assertEquals(id, actualSubtask.getId());
        Assertions.assertEquals(epicId, actualSubtask.getEpicId());
        Assertions.assertEquals(Status.NEW, actualSubtask.getStatus());
    }

    @Test
    void getSubtaskById_shouldReturnSubtaskIfSubtaskHaveInMap() {
        String name = "Epic";
        String description = "description";
        Epic epic = new Epic(name, description, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        String originalName = "Subtask";
        String originalDescription = "description";
        Subtask subtask = new Subtask(originalName, originalDescription, epicId, Status.NEW);

        taskManager.addSubtask(subtask);
        Integer id = subtask.getId();
        Subtask actualSubtask = taskManager.getSubtaskById(id);

        Assertions.assertNotNull(actualSubtask);
        Assertions.assertEquals(originalName, actualSubtask.getName());
        Assertions.assertEquals(originalDescription, actualSubtask.getDescription());
        Assertions.assertEquals(id, actualSubtask.getId());
        Assertions.assertEquals(Status.NEW, actualSubtask.getStatus());
        Assertions.assertEquals(epicId, actualSubtask.getEpicId());
    }

    @Test
    void updateSubtask_shouldUpdateSubtaskFieldsInMap() {
        String name = "Epic";
        String description = "description";
        Epic epic = new Epic(name, description, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        String originalName = "original name";
        String originalDescription = "original description";
        Subtask subtask = new Subtask(originalName, originalDescription, epicId, Status.NEW);

        taskManager.addSubtask(subtask);
        Integer id = subtask.getId();

        Subtask updatedSubtask = new Subtask("update name", "update description", epicId, Status.IN_PROGRESS);
        updatedSubtask.setId(id);
        taskManager.updateSubtask(updatedSubtask);

        Subtask storedSubtask = taskManager.getSubtaskById(id);

        Assertions.assertNotNull(storedSubtask);
        Assertions.assertEquals("update name", storedSubtask.getName());
        Assertions.assertEquals("update description", storedSubtask.getDescription());
        Assertions.assertEquals(id, storedSubtask.getId());
        Assertions.assertEquals(Status.IN_PROGRESS, storedSubtask.getStatus());
        Assertions.assertEquals(epicId, storedSubtask.getEpicId());
    }
    @Test
    void updateSubtask_shouldReturnNullIfSubtaskDoesNotExist() {
        String name = "subtask";
        String description = "description";
        Integer id = -100;
        Subtask nonExistingSubtask = new Subtask(id, name, description,1);
        Subtask updateSubtask = taskManager.updateSubtask(nonExistingSubtask);

        Assertions.assertNull(updateSubtask);
    }

    @Test
    void deleteSubtaskById_shouldDeleteSubtask() {
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        String name = "name";
        String description = "description";
        Subtask subtask = new Subtask(name, description, epicId, Status.NEW);

        taskManager.addSubtask(subtask);
        Integer id = subtask.getId();
        taskManager.deleteSubtaskById(id);
        Subtask deletedSubtask = taskManager.getSubtaskById(id);

        Assertions.assertNull(deletedSubtask);
    }

    @Test
    void getSubtasksByEpicId_shouldReturnSubtask() {
        List<Subtask> allEpicSubtasks;
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        String name = "name";
        String description = "description";
        Subtask subtask = new Subtask(name, description, epicId, Status.NEW);

        taskManager.addSubtask(subtask);

        allEpicSubtasks = taskManager.getSubtasksByEpicId(epicId);

        Assertions.assertNotNull(allEpicSubtasks);
        Assertions.assertEquals(1, allEpicSubtasks.size());
    }

    @Test
    void updateEpicStatus_epicStatusShouldBeNewIfAllSubtasksHaveStatusNew() {
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        Subtask subtask = new Subtask("name", "description", epicId, Status.NEW);
        taskManager.addSubtask(subtask);

        Epic actualEpic = taskManager.getEpicById(epicId);

        Assertions.assertEquals(Status.NEW, actualEpic.getStatus());
    }

    @Test
    void updateEpicStatus_epicStatusShouldBeNewIfEpicDoNotHaveSubtask() {
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();
        Epic actualEpic = taskManager.getEpicById(epicId);

        Assertions.assertEquals(Status.NEW, actualEpic.getStatus());
    }

    @Test
    void updateEpicStatus_epicStatusShouldBeDoneIfAllSubtasksHaveStatusDone() {
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        Subtask subtask = new Subtask("name", "description", epicId, Status.DONE);
        taskManager.addSubtask(subtask);

        Epic actualEpic = taskManager.getEpicById(epicId);

        Assertions.assertEquals(Status.DONE, actualEpic.getStatus());
    }

    @Test
    void updateEpicStatus_epicStatusShouldBeInProgress() {
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription, Status.NEW);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        Subtask subtask = new Subtask("name", "description", epicId, Status.IN_PROGRESS);
        taskManager.addSubtask(subtask);
        Epic actualEpic = taskManager.getEpicById(epicId);

        Assertions.assertEquals(Status.IN_PROGRESS, actualEpic.getStatus());
    }

    @Test
    void getHistory_shouldNotBeEmptyAfterGetTaskById() {
        Task task1 = new Task("task1", "description1", Status.NEW);
        Task task2 = new Task("task2", "description2", Status.NEW);
        Task task3 = new Task("task3", "description3", Status.IN_PROGRESS);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        Integer task1Id = task1.getId();
        Integer task2Id = task2.getId();
        Integer task3Id = task3.getId();
        taskManager.getTaskById(task1Id);
        taskManager.getTaskById(task2Id);
        taskManager.getTaskById(task3Id);
        List<Task> history = taskManager.getHistory();

        Assertions.assertNotNull(history);
        Assertions.assertEquals(3, history.size(), "После добавления задачи, история не должна быть пустой.");

        Epic epic1 = new Epic("epic1", "description1", Status.NEW);
        Epic epic2 = new Epic("epic2", "description2", Status.NEW);
        Epic epic3 = new Epic("epic3", "description3", Status.NEW);

        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addEpic(epic3);
        Integer epic1Id = epic1.getId();
        Integer epic2Id = epic2.getId();
        Integer epic3Id = epic3.getId();
        taskManager.getEpicById(epic1Id);
        taskManager.getEpicById(epic2Id);
        taskManager.getEpicById(epic3Id);

        history = taskManager.getHistory();
        Assertions.assertEquals(6, history.size());

        Subtask subtask1 = new Subtask("name1", "description1", epic1Id, Status.NEW);
        Subtask subtask2 = new Subtask("name2", "description2", epic1Id, Status.IN_PROGRESS);
        Subtask subtask3 = new Subtask("name3", "description3", epic2Id, Status.DONE);
        Subtask subtask4 = new Subtask("name4", "description4", epic2Id, Status.IN_PROGRESS);
        Subtask subtask5 = new Subtask("name5", "description5", epic3Id, Status.NEW);

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        taskManager.addSubtask(subtask4);
        taskManager.addSubtask(subtask5);
        Integer subtask1Id = subtask1.getId();
        Integer subtask2Id = subtask2.getId();
        Integer subtask3Id = subtask3.getId();
        Integer subtask4Id = subtask4.getId();
        Integer subtask5Id = subtask5.getId();
        taskManager.getSubtaskById(subtask1Id);
        taskManager.getSubtaskById(subtask2Id);
        taskManager.getSubtaskById(subtask3Id);
        taskManager.getSubtaskById(subtask4Id);

        history = taskManager.getHistory();

        Assertions.assertEquals(10, history.size());

        taskManager.getSubtaskById(subtask5Id);
        history = taskManager.getHistory();

        Assertions.assertEquals(10, history.size());
    }

    @Test
    void compareTwoTasksWithTheSameId_mustBeEqual() {
        Task task1 = new Task(1, "name1", "description1", Status.NEW);
        Task task2 = new Task(1, "name2", "description2", Status.IN_PROGRESS);

        Assertions.assertEquals(task1, task2);
    }

    @Test
    void compareTwoEpicsWithTheSameId_mustBeEqual() {
        Epic epic1 = new Epic(1, "name1", "description1", Status.NEW);
        Epic epic2 = new Epic(1, "name2", "description2", Status.IN_PROGRESS);

        Assertions.assertEquals(epic1, epic2);
    }

    @Test
    void compareTwoSubtaskWithTheSameId_mustBeEqual() {
        Subtask subtask1 = new Subtask(1, "name1", "description1", 1);
        Subtask subtask2 = new Subtask(1, "name2", "description2", 2);

        Assertions.assertEquals(subtask1, subtask2);
    }

    @Test
    void checkManagerInstance() {
        TaskManager manager = Managers.getDefault();

        Task task = new Task("name", "description", Status.NEW);
        manager.addTask(task);
        Integer id = task.getId();
        Task actualTask = manager.getTaskById(id);

        Assertions.assertNotNull(actualTask);

        List<Task> history = manager.getHistory();
        Assertions.assertNotNull(history);
        Assertions.assertEquals(1, history.size());
    }

    @Test
    void TaskWithGeneratedId_shouldOverwrittenTaskId() {
        Task task = new Task(25, "name", "description", Status.NEW);
        taskManager.addTask(task);
        Integer id = task.getId();

        Assertions.assertNotEquals(25, id);
    }

    @Test
    void immutabilityTask() {
        Task task = new Task("name", "description", Status.NEW);
        taskManager.addTask(task);
        Integer id = task.getId();

        Task actualTask = taskManager.getTaskById(id);
        actualTask.setName("updatedName");
        Task updatedTask = taskManager.getTaskById(id);

        Assertions.assertNotEquals(updatedTask.getName(), actualTask.getName());
    }
}