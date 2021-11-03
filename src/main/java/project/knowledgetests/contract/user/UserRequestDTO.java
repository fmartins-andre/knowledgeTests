package project.knowledgetests.contract.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private Long id;

    @Pattern(regexp = "^[\\p{Alnum}]{3,50}$", message = "The username field must have between 3 and 50 characters long.")
    private String username;

    @Pattern(regexp = "(^$|^[\\p{Alnum}]{5,150}$)", message = "The fullName field must have between 5 and 150 characters long.")
    private String fullName;
}
