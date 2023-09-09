package io.gwteaching.tmptool.repositories;

import io.gwteaching.tmptool.dto.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

//    @Override
//    Iterable<Project> findAllById(Iterable<Long> longs);

    Project findByProjectId(String projectId);

    @Override
    Iterable<Project> findAll();

    Iterable<Project> findAllByProjectOwner(String username);
}
