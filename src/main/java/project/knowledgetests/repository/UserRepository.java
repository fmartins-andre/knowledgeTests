package project.knowledgetests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.knowledgetests.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
