package project.knowledgetests.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.knowledgetests.entity.Question;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerRequestDTO {

    private Long id;

    @NotEmpty
    private Question questionReference;

    @NotEmpty
    @Size(min = 1, max = 2048)
    private String answer;
}
