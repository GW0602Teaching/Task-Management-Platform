package io.gwteaching.tmptool.controller;

import io.gwteaching.tmptool.dto.Project;
import io.gwteaching.tmptool.services.MapValidationErrorService;
import io.gwteaching.tmptool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping(value = "")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidation(result);

        if (errorMap != null) {
            return errorMap;
        }


        Project newProject = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{projectId}")
    public ResponseEntity<?> getProjectByProjectId(@PathVariable String projectId) {
        Project project = projectService.findProjectById(projectId);
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

//    @GetMapping(value = "/all")
//    public Iterable<Project> getAll() {
//        return projectService.findAllProjects();
//    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll(Principal principal) {
        Map<String, Iterable<Project>> map = new HashMap<>();
        map.put("data", projectService.findAllProjects(principal.getName()));
        return new ResponseEntity<Map<String, Iterable<Project>>>(map, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{projectId}")
    public ResponseEntity<?> deleteProjectByProjectId(@PathVariable String projectId) {
        projectService.deleteProjectByProjectId(projectId);
        return new ResponseEntity<String>("Project ID '" + projectId.toUpperCase() + "' was deleted", HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> updateNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidation(result);

        if (errorMap != null) {
            return errorMap;
        }


        Project newProject = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }
}
