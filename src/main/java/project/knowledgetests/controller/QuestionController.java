package project.knowledgetests.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.knowledgetests.contract.question.QuestionRequestDTO;
import project.knowledgetests.contract.question.QuestionResponseDTO;
import project.knowledgetests.serivce.QuestionService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionController {

    private final QuestionService questionService;

    @PreAuthorize("hasAnyAuthority('ROLE_QUESTION_MAINTAINER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponseDTO create(@RequestBody @Valid QuestionRequestDTO question, HttpServletResponse response) {
        QuestionResponseDTO createdQuestion = questionService.create(question);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdQuestion.getId())
                .toUri();

        response.setHeader("Location", String.valueOf(location));
        return createdQuestion;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_QUESTION_VIEWER')")
    @GetMapping
    public List<QuestionResponseDTO> listAll() {
        return questionService.listAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_QUESTION_VIEWER')")
    @GetMapping("/{id}")
    public QuestionResponseDTO findById(@PathVariable Long id) {
        return questionService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_QUESTION_MAINTAINER')")
    @PutMapping("/{id}")
    public QuestionResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid QuestionRequestDTO question) {
        return questionService.updateById(id, question);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_QUESTION_MAINTAINER')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        questionService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_QUESTION_VIEWER')")
    @GetMapping("/daily")
    public List<QuestionResponseDTO> getDailyQuestions(@AuthenticationPrincipal Jwt principal) {
        return questionService.getDailyQuestions(principal);
    }

}
