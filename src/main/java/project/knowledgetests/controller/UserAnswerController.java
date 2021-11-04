package project.knowledgetests.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.knowledgetests.contract.userAnswer.UserAnswerRequestDTO;
import project.knowledgetests.contract.userAnswer.UserAnswerResponseDTO;
import project.knowledgetests.serivce.UserAnswerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/answer")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserAnswerController {

    private final UserAnswerService userAnswerService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER_ANSWER_MAINTAINER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserAnswerResponseDTO create(@RequestBody @Valid UserAnswerRequestDTO answer,
                                        HttpServletResponse response, @AuthenticationPrincipal Jwt principal) {

        UserAnswerResponseDTO createdAnswer = userAnswerService.create(answer, principal);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdAnswer.getId())
                .toUri();

        response.setHeader("Location", String.valueOf(location));
        return createdAnswer;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_ANSWER_VIEWER')")
    @GetMapping
    public List<UserAnswerResponseDTO> listAll() {
        return userAnswerService.listAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_ANSWER_VIEWER')")
    @GetMapping("/{id}")
    public UserAnswerResponseDTO findById(@PathVariable Long id) {
        return userAnswerService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_ANSWER_MAINTAINER')")
    @PutMapping("/{id}")
    public UserAnswerResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid UserAnswerRequestDTO answer) {
        return userAnswerService.updateById(id, answer);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_ANSWER_MAINTAINER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userAnswerService.delete(id);
    }


}
