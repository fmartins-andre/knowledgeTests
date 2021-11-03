package project.knowledgetests.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.knowledgetests.contract.userAnswer.UserAnswerRequestDTO;
import project.knowledgetests.entity.Question;
import project.knowledgetests.entity.UserAnswer;
import project.knowledgetests.serivce.QuestionService;


@Component
public class UserAnswerRequestMapperImpl {

    @Autowired
    QuestionService questionService;
    @Autowired
    private QuestionRequestMapper questionRequestMapper;

    public UserAnswer toModel(UserAnswerRequestDTO answer) {
        if (answer == null) return null;

        Question question = questionService.getEntityById(answer.getQuestionReference());

        UserAnswer userAnswer = new UserAnswer();

        userAnswer.setId(answer.getId());
        userAnswer.setQuestionReference(question);
        userAnswer.setQuestion(question.getQuestion());
        userAnswer.setAnswer(answer.getAnswer());
        userAnswer.setScoreByOwnUser(answer.getScoreByOwnUser());

        return userAnswer;
    }

    public UserAnswerRequestDTO toDTO(UserAnswer answer) {
        if (answer == null) return null;

        UserAnswerRequestDTO userAnswerRequestDTO = new UserAnswerRequestDTO();

        userAnswerRequestDTO.setId(answer.getId());
        userAnswerRequestDTO.setQuestionReference(answer.getQuestionReference().getId());
        userAnswerRequestDTO.setAnswer(answer.getAnswer());
        userAnswerRequestDTO.setScoreByOwnUser(answer.getScoreByOwnUser());

        return userAnswerRequestDTO;
    }
}
