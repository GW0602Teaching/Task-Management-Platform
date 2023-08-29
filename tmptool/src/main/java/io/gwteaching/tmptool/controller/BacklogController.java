package io.gwteaching.tmptool.controller;

import io.gwteaching.tmptool.dto.Task;
import io.gwteaching.tmptool.services.MapValidationErrorService;
import io.gwteaching.tmptool.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping(value = "/{projectId}")
    public ResponseEntity<?> addTaskToBacklog(@Valid @RequestBody Task task, BindingResult result, @PathVariable String projectId) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidation(result);

        if (errorMap != null) {
            return errorMap;
        }

        Task newTask = taskService.addProjectTask(projectId, task);

        return new ResponseEntity<Task>(newTask, HttpStatus.CREATED);
    }
}
