package ch.uzh.ifi.seal.soprafs19.repository;

import ch.uzh.ifi.seal.soprafs19.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// handles db access <type of object, type of ID column>
// save, findById, findAll, count (how many stored)
// are inherited by CrudRepository
// @Repository indicates that the decorated class is a repo
@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
	User findByName(String name);

	User findByUsername(String username);

	User findByToken(String token);
}