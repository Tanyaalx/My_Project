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
        Epic epic = new Epic(name, description);

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
        Epic epic = new Epic(name, description);

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
        Epic origialEpic = new Epic(name, description);

        taskManager.addEpic(origialEpic);
        Integer id = origialEpic.getId();

        Epic updatedEpic = new Epic("update name", "update description");
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
        Epic epic = new Epic(name, description);

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
        Epic epic = new Epic(name, description);

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
        Epic epic = new Epic(name, description);

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
        Epic epic = new Epic(name, description);

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
        Epic epic = new Epic(epicName, epicDescription);

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
        Epic epic = new Epic(epicName, epicDescription);

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
        Epic epic = new Epic(epicName, epicDescription);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();
        Epic actualEpic = taskManager.getEpicById(epicId);

        Subtask subtask1 = new Subtask("name1", "description1", epicId, Status.NEW);
        Subtask subtask2 = new Subtask("name2", "description2", epicId, Status.NEW);
        Subtask subtask3 = new Subtask("name3", "description3", epicId, Status.NEW);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        taskManager.updateEpicStatus(epicId);

        Assertions.assertEquals(Status.NEW, actualEpic.getStatus());
    }

    @Test
    void updateEpicStatus_epicStatusShouldBeNewIfEpicDoNotHaveSubtask() {
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();
        Epic actualEpic = taskManager.getEpicById(epicId);

        taskManager.updateEpicStatus(epicId);
        Assertions.assertEquals(Status.NEW, actualEpic.getStatus());
    }

    @Test
    void updateEpicStatus_epicStatusShouldBeDoneIfAllSubtasksHaveStatusDone() {
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();

        Subtask subtask1 = new Subtask("name1", "description1", epicId, Status.DONE);
        Subtask subtask2 = new Subtask("name2", "description2", epicId, Status.DONE);
        Subtask subtask3 = new Subtask("name3", "description3", epicId, Status.DONE);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        taskManager.updateEpicStatus(epicId);
        Epic epicFromManager = taskManager.getEpicById(epicId);

        System.out.println("Subtask IDs in Epic:" + epicFromManager.getSubtasksId());
        for (Integer id: epicFromManager.getSubtasksId()) {
            System.out.println("Subtask " + id + " status: " + taskManager.getSubtaskById(id).getStatus());
        }
        System.out.println("Epic status: " + epicFromManager.getStatus());
        Assertions.assertEquals(Status.DONE, epicFromManager.getStatus());
    }

    @Test
    void updateEpicStatus_epicStatusShouldBeInProgress() {
        String epicName = "Epic";
        String epicDescription = "description";
        Epic epic = new Epic(epicName, epicDescription);

        taskManager.addEpic(epic);
        Integer epicId = epic.getId();
        Epic actualEpic = taskManager.getEpicById(epicId);

        Subtask subtask1 = new Subtask("name1", "description1", epicId, Status.NEW);
        Subtask subtask2 = new Subtask("name2", "description2", epicId, Status.IN_PROGRESS);
        Subtask subtask3 = new Subtask("name3", "description3", epicId, Status.DONE);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        taskManager.updateEpicStatus(epicId);
        Assertions.assertEquals(Status.IN_PROGRESS, actualEpic.getStatus());
    }

    @Test
    void getHistory() {
    }
}