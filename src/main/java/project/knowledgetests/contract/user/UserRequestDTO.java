package project.knowledgetests.contract.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    private Long id;

    @Pattern(regexp = "^[\\w][\\w\\\\.-]{2,49}$",
            message = "The username field must have between 3 and 50 alphanumeric characters long.")
    private String username;

    @Pattern(regexp = "(^$|^[\\p{L}\\p{N}\\s_.-]{5,150}$)",
            message = "The fullName field must have between 5 and 150 characters long.")
    private String fullName;
}
