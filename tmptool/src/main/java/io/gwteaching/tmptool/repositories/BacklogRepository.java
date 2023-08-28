package io.gwteaching.tmptool.repositories;

import io.gwteaching.tmptool.dto.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    Backlog findByProjectId(String projectId);
}
