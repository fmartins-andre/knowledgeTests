package project.knowledgetests.serivce;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import project.knowledgetests.contract.question.QuestionRequestDTO;
import project.knowledgetests.contract.question.QuestionResponseDTO;
import project.knowledgetests.entity.Question;
import project.knowledgetests.exception.ConstraintViolationException;
import project.knowledgetests.exception.ResourceNotFoundException;
import project.knowledgetests.mapper.QuestionRequestMapper;
import project.knowledgetests.mapper.QuestionResponseMapper;
import project.knowledgetests.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionResponseMapper questionResponseMapper = QuestionResponseMapper.INSTANCE;
    private final QuestionRequestMapper questionRequestMapper = QuestionRequestMapper.INSTANCE;

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
            throw new ConstraintViolationException("This question already exists. You can't add same question twice.");
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
            throw new ConstraintViolationException("This question already exists. You can't add same question twice.");
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

    private Question exists(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));
    }


}
