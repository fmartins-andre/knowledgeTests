package project.knowledgetests.serivce;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import project.knowledgetests.contract.userAnswer.UserAnswerRequestDTO;
import project.knowledgetests.contract.userAnswer.UserAnswerResponseDTO;
import project.knowledgetests.entity.User;
import project.knowledgetests.entity.UserAnswer;
import project.knowledgetests.exception.ConstraintViolationException;
import project.knowledgetests.exception.ResourceNotFoundException;
import project.knowledgetests.mapper.UserAnswerRequestMapperImpl;
import project.knowledgetests.mapper.UserAnswerResponseMapper;
import project.knowledgetests.repository.UserAnswerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserAnswerService {

    private final UserAnswerRepository userAnswerRepository;
    private final UserService userService;
    private final UserAnswerResponseMapper userAnswerResponseMapper = UserAnswerResponseMapper.INSTANCE;
    private final UserAnswerRequestMapperImpl userAnswerRequestMapper;

    public List<UserAnswerResponseDTO> listAll() {
        final List<UserAnswer> answers = userAnswerRepository.findAll();

        return answers
                .stream()
                .map(userAnswerResponseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserAnswerResponseDTO findById(Long id) {
        UserAnswer answer = exists(id);
        return userAnswerResponseMapper.toDTO(answer);
    }

    public UserAnswerResponseDTO create(UserAnswerRequestDTO answer, Jwt principal) {
        User user = userService.fromJwt(principal);

        try {
            final UserAnswer answerToSave = userAnswerRequestMapper.toModel(answer);
            if (answerToSave == null) throw new ConstraintViolationException("Question reference not found");
            answerToSave.setUser(user);

            final UserAnswer savedAnswer = userAnswerRepository.save(answerToSave);
            return userAnswerResponseMapper.toDTO(savedAnswer);

        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "A conflict occurred while creating an answer. You cannot answer the same question twice!");
        }
    }

    public UserAnswerResponseDTO updateById(Long id, UserAnswerRequestDTO answer) {
        exists(id);

        UserAnswer answerToUpdate = userAnswerRequestMapper.toModel(answer);
        answerToUpdate.setId(id);

        try {
            UserAnswer updatedAnswer = userAnswerRepository.save(answerToUpdate);
            return userAnswerResponseMapper.toDTO(updatedAnswer);

        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "A conflict occurred while updating answer with ID " + answer.getId()
                            + "'. Make sure all given data is respecting data constraints.");
        }
    }

    public void delete(Long id) {
        UserAnswer answer = exists(id);
        userAnswerRepository.delete(answer);
    }

    private UserAnswer exists(Long id) {
        return userAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User answer not found with ID: " + id));
    }


}
