package io.gwteaching.tmptool.repositories;

import io.gwteaching.tmptool.dto.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
