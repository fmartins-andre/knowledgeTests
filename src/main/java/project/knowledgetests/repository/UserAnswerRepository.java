package project.knowledgetests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.knowledgetests.entity.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
}
