package project.knowledgetests.contract;

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

    @NotEmpty
    @Size(min = 5, max = 1024)
    private String question;

    @NotEmpty
    @Size(min = 1, max = 1024)
    private String answer;
}
