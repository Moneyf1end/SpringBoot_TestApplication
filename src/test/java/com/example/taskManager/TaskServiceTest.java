package com.example.taskManager;

import com.example.taskManager.dto.TaskRequestDto;
import com.example.taskManager.dto.TaskResponseDto;
import com.example.taskManager.mapper.TaskMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    /**
     * Testing a successful method getTaskById
//     * @param id
     * @return TaskResponseDto
     */
    @Test
    public void shouldReturnTaskById_WhenExists_ToDto() {
        Long id = 1l;
        List<Task> listOfTasks = listOfTasks();
        Task task = listOfTasks.get(0);
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(id);
        taskResponseDto.setDescription("Task 1");
        taskResponseDto.setDone(false);

        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));
        Mockito.when(taskMapper.toDto(task)).thenReturn(taskResponseDto);

        TaskResponseDto taskDto = taskService.getTaskById(id);

        Assertions.assertNotNull(taskDto);
        Assertions.assertEquals(id, taskDto.getId());
        Assertions.assertEquals("Task 1", taskDto.getDescription());
        Assertions.assertFalse(taskDto.isDone());
    }

    private List<Task> listOfTasks() {
        Task task1 = new Task();
        Task task2 = new Task();

        task1.setId(1L);
        task2.setId(2L);

        task1.setDescription("Task 1");
        task2.setDescription("Task 2");

        task1.setDone(false);
        task2.setDone(true);

        return List.of(task1, task2);
    }

    /**
     * Testing a failed method getTaskById
//     * @param id
     * @return ResponseStatusException
     */
    @Test
    public void getTaskNyID_WhenNotExists_ShouldThrowResponseStatusException() {
        List<Task> listOfTasks = listOfTasks();
        Long idRange = listOfTasks.size() + 1L;

        Mockito.when(taskRepository.findById(idRange)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () ->
                taskService.getTaskById(idRange));

        verifyNoInteractions(taskMapper);
    }

    /**
     * Testing a successful method of updateTask
     * @return TaskResponseDto
     */
    @Test
    public void updateTask_WhenTaskExists_ShouldReturnUpdatedTask() {
        Long id = 1L;
        Task taskBeforeUpdate = listOfTasks().get(0);

        TaskRequestDto taskRequestDto = new TaskRequestDto();
        taskRequestDto.setDescription("Updated task");
        taskRequestDto.setDone(true);

        Task taskAfterUpdate = listOfTasks().get(0);
        taskAfterUpdate.setDone(taskRequestDto.isDone());


        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(taskBeforeUpdate));
        Mockito.doAnswer(invocation -> {
            TaskRequestDto source = invocation.getArgument(0); // DTO
            Task target = invocation.getArgument(1);       // Entity
            target.setDone(source.isDone());

            return target;
        }).when(taskMapper).copyToEntity(Mockito.any(TaskRequestDto.class), Mockito.any(Task.class));


        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenAnswer(i -> i.getArgument(0));

        TaskResponseDto taskResponseDtoMock = new TaskResponseDto();
        taskResponseDtoMock.setId(id);
        taskResponseDtoMock.setDescription(taskAfterUpdate.getDescription());
        taskResponseDtoMock.setDone(taskAfterUpdate.isDone());

        Mockito.when(taskMapper.toDto(Mockito.any(Task.class))).thenReturn(taskResponseDtoMock);

        TaskResponseDto taskResponseDto = taskService.updateTask(id, taskRequestDto);

        Assertions.assertNotNull(taskResponseDto);
        Assertions.assertEquals("Task 1", taskResponseDto.getDescription());
        Assertions.assertEquals(taskAfterUpdate.isDone(), taskResponseDto.isDone());
    }

    @Test
    public void shouldReturnAllTasks() {
        List<Task> listOfTasks = listOfTasks();
        Task task1 = listOfTasks.get(0);
        Task task2 = listOfTasks.get(1);

        TaskResponseDto dto1 = new TaskResponseDto();
        dto1.setId(1L);
        dto1.setDescription("Task 1");

        TaskResponseDto dto2 = new TaskResponseDto();
        dto2.setId(2L);
        dto2.setDescription("Task 2");

        Mockito.when(taskRepository.findAll()).thenReturn(listOfTasks);

        Mockito.when(taskMapper.toDto(task1)).thenReturn(dto1);
        Mockito.when(taskMapper.toDto(task2)).thenReturn(dto2);


        List<TaskResponseDto> resultList = taskService.getAllTasks();

        Assertions.assertNotNull(resultList);
        Assertions.assertEquals(2, resultList.size());


        Assertions.assertEquals(1L, resultList.get(0).getId());
        Assertions.assertEquals("Task 1", resultList.get(0).getDescription());


        Assertions.assertEquals(2L, resultList.get(1).getId());

        Mockito.verify(taskMapper, Mockito.times(2)).toDto(Mockito.any(Task.class));
    }

    @Test
    public void shouldCreateTask() {
        List<Task> listOfTasks = listOfTasks();
        TaskRequestDto task = new TaskRequestDto();
        task.setDescription("Task 1 test createTask");
        task.setDone(true);

        Task taskCheck = new Task();
        taskCheck.setDescription("Task 1 test createTask");
        taskCheck.setDone(true);


        Mockito.when(taskMapper.toEntity(task)).thenReturn(taskCheck);

        taskCheck.setId(15L);

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setDescription("Task 1 test createTask");
        taskResponseDto.setDone(true);
        taskResponseDto.setId(15L);

        Mockito.when(taskRepository.save(taskCheck)).thenReturn(taskCheck);
        Mockito.when(taskMapper.toDto(taskCheck)).thenReturn(taskResponseDto);

        TaskResponseDto result = taskService.createTask(task);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(true, result.isDone());
        Assertions.assertEquals(15L, result.getId());
    }

    @Test
    public void shouldDelete() {
        Long id = 1L;
        List<Task> list = listOfTasks();
        Task task = list.get(0);

        Mockito.when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));
        taskService.deleteTask(id);

        Mockito.verify(taskRepository).findById(id);
        Mockito.verify(taskRepository).deleteById(id);
    }

    @Test
    public void shouldFindTasksByCertainDone() {
        boolean doneType = true;
        List<Task> list = listOfTasks();
        List<Task> afterSort = new ArrayList<>();
        afterSort.addAll(list);
        afterSort = afterSort.stream()
                .filter(elem -> elem.isDone() == doneType)
                .collect(Collectors.toList());

        Task task1 = list.get(0);
        Task task2 = list.get(1);

        TaskResponseDto taskResponseDto1 = new TaskResponseDto();
        taskResponseDto1.setId(list.get(0).getId());
        taskResponseDto1.setDescription(list.get(0).getDescription());
        taskResponseDto1.setDone(list.get(0).isDone());

        TaskResponseDto taskResponseDto2 = new TaskResponseDto();
        taskResponseDto2.setId(list.get(1).getId());
        taskResponseDto2.setDescription(list.get(1).getDescription());
        taskResponseDto2.setDone(list.get(1).isDone());

        Mockito.when(taskRepository.findByDone(doneType)).thenReturn(afterSort);

        Mockito.when(taskMapper.toDto(task2)).thenReturn(taskResponseDto2);

        List<TaskResponseDto> listFinal = taskService.findByDone(doneType);

        Assertions.assertNotNull(listFinal);
        Assertions.assertEquals(doneType, listFinal.stream()
                        .map(elem -> elem.isDone())
                .findFirst()
                .orElseThrow(AssertionError::new));
    }

    @Test
    public void shouldFindTasksByCertainDescription() {
        String pattern = "Task 1";
        List<Task> list = listOfTasks();
        List<Task> listAfterMatching = new ArrayList<>(list);
        listAfterMatching.removeIf(elem -> !elem.getDescription().equals(pattern));
        Mockito.when(taskRepository.findByDescriptionIgnoreCaseContaining(pattern)).thenReturn(listAfterMatching);

        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setId(list.get(0).getId());
        taskResponseDto.setDescription(list.get(0).getDescription());
        taskResponseDto.setDone(list.get(0).isDone());

        Mockito.when(taskMapper.toDto(list.get(0))).thenReturn(taskResponseDto);

        List<TaskResponseDto> listFinal = taskService.findByDescription(pattern);

        Assertions.assertNotNull(listFinal);
        Assertions.assertEquals("Task 1", listFinal.stream()
                .map(item -> item.getDescription())
                .findFirst()
                .orElseThrow(AssertionError::new));
    }

    @Test
    public void shouldFindAllTasksInCertainOrder() {
        String orderBy = "asc";
        String orderByValue = "id";
        List<Task> list = listOfTasks();
        Task task1 = list.get(0);
        Task task2 = list.get(1);

        TaskResponseDto taskResponseDto1 = new TaskResponseDto();
        taskResponseDto1.setId(list.get(0).getId());
        taskResponseDto1.setDescription(list.get(0).getDescription());
        taskResponseDto1.setDone(list.get(0).isDone());

        TaskResponseDto taskResponseDto2 = new TaskResponseDto();
        taskResponseDto2.setId(list.get(1).getId());
        taskResponseDto2.setDescription(list.get(1).getDescription());
        taskResponseDto2.setDone(list.get(1).isDone());
        List<TaskResponseDto> listOfResponses = new ArrayList<>();
        listOfResponses.add(taskResponseDto1);
        listOfResponses.add(taskResponseDto2);

        Sort.Direction orderOfSorting = Sort.Direction.fromString(orderBy);
        Sort sort = Sort.by(orderOfSorting, orderByValue);

        Mockito.when(taskRepository.findAll(Sort.by(orderOfSorting, orderByValue))).thenReturn(list);
        Mockito.when(taskMapper.toDto(list.get(0))).thenReturn(taskResponseDto1);

        List<TaskResponseDto> listFinal = taskService.findAll(orderBy);

        Assertions.assertNotNull(listFinal);
        Assertions.assertEquals(1L, listFinal.get(0).getId());

    }
}
