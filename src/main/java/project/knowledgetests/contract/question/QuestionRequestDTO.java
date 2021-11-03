package project.knowledgetests.contract.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDTO {

    private Long id;

    @NotEmpty(message = "The question field is required and must not be empty.")
    @Size(min = 5, max = 4096, message = "The question field must have between 5 and 4096 characters long.")
    private String question;

    @NotEmpty(message = "The answer field is required and must not be empty.")
    @Size(min = 1, max = 2048, message = "The answer field must have between 1 and 2048 characters long.")
    private String answer;
}
