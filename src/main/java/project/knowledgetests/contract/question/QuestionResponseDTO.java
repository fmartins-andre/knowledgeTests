package project.knowledgetests.contract.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponseDTO {
    private Long id;
    private String question;
    private String answer;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
