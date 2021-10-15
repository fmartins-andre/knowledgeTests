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
public class UserRequestDTO {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 50)
    private String username;

    @NotEmpty
    @Size(min = 5, max = 150)
    private String fullName;
}
