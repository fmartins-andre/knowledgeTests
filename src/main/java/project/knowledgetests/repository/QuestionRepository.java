package project.knowledgetests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.knowledgetests.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
