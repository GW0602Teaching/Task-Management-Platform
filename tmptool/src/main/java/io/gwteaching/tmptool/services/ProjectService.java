package io.gwteaching.tmptool.services;

import io.gwteaching.tmptool.dto.Backlog;
import io.gwteaching.tmptool.dto.Project;
import io.gwteaching.tmptool.exceptions.ProjectIdException;
import io.gwteaching.tmptool.repositories.BacklogRepository;
import io.gwteaching.tmptool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectId(project.getProjectId().toUpperCase());

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectId(project.getProjectId().toUpperCase());
            } else {
                project.setBacklog(backlogRepository.findByProjectId(project.getProjectId()));
            }

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

    public void deleteProjectByProjectId(String projectId) {
        Project project = projectRepository.findByProjectId(projectId);

        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not existed");
        }

        projectRepository.delete(project);
    }

}
