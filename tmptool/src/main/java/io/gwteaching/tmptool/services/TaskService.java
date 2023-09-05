package io.gwteaching.tmptool.services;

import io.gwteaching.tmptool.dto.Backlog;
import io.gwteaching.tmptool.dto.Task;
import io.gwteaching.tmptool.exceptions.ProjectNotFoundException;
import io.gwteaching.tmptool.repositories.BacklogRepository;
import io.gwteaching.tmptool.repositories.TaskRepository;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Task addProjectTask(String projectId, Task task) {
        try {
            Backlog backlog = backlogRepository.findByProjectId(projectId);
            task.setBacklog(backlog);

            Integer backlogSequence = backlog.getTaskSequence();
            backlogSequence++;
            backlog.setTaskSequence(backlogSequence);

            task.setProjectSequence(projectId + "-" + backlogSequence);
            task.setProjectId(projectId);

            if (task.getPriority() == null) {
                task.setPriority(3);
            }

            if (StringHelper.isEmpty(task.getStatus())) {
                task.setStatus("TO_DO");
            }

            return taskRepository.save(task);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project " + projectId + " does not existed");
        }

    }

    public List<Task> findTaskByProjectId(String productId) {
        return taskRepository.findByProjectIdOrderByPriority(productId);
    }
}
