package project.knowledgetests.contract.userAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.knowledgetests.contract.question.QuestionReferenceResponseDTO;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerWithoutQuestionAnswerResponseDTO {
    private Long id;
    private QuestionReferenceResponseDTO questionReference;
    private String question;
    private String answer;
    private Byte scoreByOwnUser;
    private Byte score;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
