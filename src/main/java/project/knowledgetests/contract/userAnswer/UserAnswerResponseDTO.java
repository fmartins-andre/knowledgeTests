package project.knowledgetests.contract.userAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.knowledgetests.contract.question.QuestionResponseDTO;
import project.knowledgetests.contract.user.UserResponseDTO;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerResponseDTO {
    private Long id;
    private QuestionResponseDTO questionReference;
    private String question;
    private String answer;
    private UserResponseDTO user;
    private Byte scoreByOwnUser;
    private Byte score;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
