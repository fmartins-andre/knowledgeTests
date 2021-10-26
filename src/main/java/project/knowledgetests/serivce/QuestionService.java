package project.knowledgetests.serivce;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import project.knowledgetests.contract.QuestionRequestDTO;
import project.knowledgetests.contract.QuestionResponseDTO;
import project.knowledgetests.entity.Question;
import project.knowledgetests.exception.ResourceAlreadyExistsException;
import project.knowledgetests.exception.ResourceNotFoundException;
import project.knowledgetests.mapper.QuestionResponseMapper;
import project.knowledgetests.repository.QuestionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionResponseMapper questionResponseMapper = QuestionResponseMapper.INSTANCE;

    public List<QuestionResponseDTO> listAll() {
        final List<Question> questions = questionRepository.findAll();

        return questions
                .stream()
                .map(questionResponseMapper::toDTO)
                .collect(Collectors.toList());

    }

    public QuestionResponseDTO findById(Long id) {
        Question question = exists(id);
        return questionResponseMapper.toDTO(question);
    }

    public QuestionResponseDTO create(QuestionRequestDTO question) {
        final Question questionToSave = questionResponseMapper.toModel(question);

        try {
            final Question savedQuestion = questionRepository.save(questionToSave);
            return questionResponseMapper.toDTO(savedQuestion);

        } catch (DataIntegrityViolationException e) {
            throw new ResourceAlreadyExistsException("This question already exists. You can't add same question twice.");
        }
    }

    public QuestionResponseDTO updateById(Long id, QuestionRequestDTO question) {
        exists(id);

        Question questionToUpdate = questionResponseMapper.toModel(question);
        Question updatedQuestion = questionRepository.save(questionToUpdate);

        return questionResponseMapper.toDTO(updatedQuestion);

    }

    public void delete(Long id) {
        Question question = exists(id);
        questionRepository.delete(question);
    }

    private Question exists(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with ID: " + id));
    }


}
