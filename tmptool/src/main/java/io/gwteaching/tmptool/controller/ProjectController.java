package io.gwteaching.tmptool.controller;

import io.gwteaching.tmptool.dto.Project;
import io.gwteaching.tmptool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping(value = "")
    public ResponseEntity<Project> createNewProject(@RequestBody Project project) {
        Project newProject = projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(newProject, HttpStatus.CREATED);
    }

}
