package project.knowledgetests.serivce;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import project.knowledgetests.contract.question.QuestionRequestDTO;
import project.knowledgetests.contract.question.QuestionResponseDTO;
import project.knowledgetests.entity.Question;
import project.knowledgetests.entity.User;
import project.knowledgetests.exception.ConstraintViolationException;
import project.knowledgetests.exception.ResourceNotFoundException;
import project.knowledgetests.mapper.QuestionRequestMapper;
import project.knowledgetests.mapper.QuestionResponseMapper;
import project.knowledgetests.repository.QuestionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionResponseMapper questionResponseMapper = QuestionResponseMapper.INSTANCE;
    private final QuestionRequestMapper questionRequestMapper = QuestionRequestMapper.INSTANCE;
    private final UserService userService;

    @PersistenceContext
    private EntityManager em;

    public List<QuestionResponseDTO> listAll() {
        final List<Question> questions = questionRepository.findAll();

        return questions
                .stream()
                .map(questionResponseMapper::toDTO)
                .collect(Collectors.toList());

    }

    public Question getEntityById(Long id) {
        Optional<Question> question = questionRepository.findById(id);

        if (question.isEmpty()) throw new ResourceNotFoundException("Could not find a question with ID: " + id);

        return question.get();
    }

    public QuestionResponseDTO findById(Long id) {
        Question question = exists(id);
        return questionResponseMapper.toDTO(question);
    }

    public QuestionResponseDTO create(QuestionRequestDTO question) {
        final Question questionToSave = questionRequestMapper.toModel(question);

        try {
            final Question savedQuestion = questionRepository.save(questionToSave);
            return questionResponseMapper.toDTO(savedQuestion);

        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "A conflict occurred while creating a question. "
                            + "Make sure all given data is respecting data constraints.");
        }
    }

    public QuestionResponseDTO updateById(Long id, QuestionRequestDTO question) {
        exists(id);

        Question questionToUpdate = questionRequestMapper.toModel(question);
        questionToUpdate.setId(id);

        try {
            Question updatedQuestion = questionRepository.save(questionToUpdate);
            return questionResponseMapper.toDTO(updatedQuestion);

        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "A conflict occurred while updating question with ID " + id
                            + "'. Make sure all given data is respecting data constraints.");
        }
    }

    public void delete(Long id) {
        Question question = exists(id);
        try {
            questionRepository.delete(question);

        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "This question has been referenced by another resource and cannot be deleted.");
        }
    }


    public List<QuestionResponseDTO> getDailyQuestions(Jwt principal) {

        User user = userService.fromJwt(principal);

        TypedQuery<Question> query = em.createQuery(
                        "select q from Question q " +
                                "left join UserAnswer a on a.questionReference = q.id " +
                                "where (datediff('day', cast(a.updatedAt as date), current_date()) >= :interval " +
                                "and a.user = :user) or a.updatedAt is null or a.user is null " +
                                "order by random()", Question.class)
                .setParameter("user", user)
                .setParameter("interval", 1)
                .setMaxResults(5);

        List<Question> results = query.getResultList();

        return results
                .stream()
                .map(questionResponseMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Question exists(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));
    }


}
