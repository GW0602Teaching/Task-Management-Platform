package io.gwteaching.tmptool.services;

import io.gwteaching.tmptool.dto.Project;
import io.gwteaching.tmptool.exceptions.ProjectIdException;
import io.gwteaching.tmptool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectId(project.getProjectId().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectId().toUpperCase() + "' is already existed");
        }

    }

    public Project findProjectById(String projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        
        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not existed");

        }

        return projectRepository.findByProjectId((projectId));
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

}
