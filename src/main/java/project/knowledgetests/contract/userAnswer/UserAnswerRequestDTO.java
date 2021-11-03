package project.knowledgetests.contract.userAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerRequestDTO {

    private Long id;

    @Min(value = 1, message = "The questionReference field is required and must not be empty.")
    private Long questionReference;

    @NotEmpty(message = "The answer field is required and must not be empty.")
    @Size(min = 1, max = 2048, message = "The answer field must have between 1 and 2048 characters long")
    private String answer;

    @Range(min = 0, max = 100, message = "The score field must be a number between 0 and 100")
    private Byte scoreByOwnUser;
}
