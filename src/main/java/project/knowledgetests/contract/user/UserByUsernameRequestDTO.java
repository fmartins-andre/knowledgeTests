package project.knowledgetests.contract.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserByUsernameRequestDTO {

    @Pattern(regexp = "^[\\p{Alnum}]{3,50}$", message = "The username field must have between 3 and 50 characters long.")
    private String username;
}
