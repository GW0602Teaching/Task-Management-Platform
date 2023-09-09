package io.gwteaching.tmptool.services;

import io.gwteaching.tmptool.dto.Backlog;
import io.gwteaching.tmptool.dto.Project;
import io.gwteaching.tmptool.dto.User;
import io.gwteaching.tmptool.exceptions.ProjectIdException;
import io.gwteaching.tmptool.exceptions.ProjectNotFoundException;
import io.gwteaching.tmptool.repositories.BacklogRepository;
import io.gwteaching.tmptool.repositories.ProjectRepository;
import io.gwteaching.tmptool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        try {

            // Find the user
            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectOwner(user.getUsername());

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

    public Project findProjectById(String projectId, String username) {
        Project project = projectRepository.findByProjectId(projectId);

        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not existed");
        }

        // User access restriction
        if (!project.getProjectOwner().equals(username)) {
            throw new ProjectNotFoundException("Project ID '" + projectId.toUpperCase() + "' not found in your account");
        }

        return projectRepository.findByProjectId((projectId));
    }

    public Iterable<Project> findAllProjects(String username) {
//        return projectRepository.findAll();
        return projectRepository.findAllByProjectOwner(username);
    }

    public void deleteProjectByProjectId(String projectId) {
        Project project = projectRepository.findByProjectId(projectId);

        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not existed");
        }

        projectRepository.delete(project);
    }

}
