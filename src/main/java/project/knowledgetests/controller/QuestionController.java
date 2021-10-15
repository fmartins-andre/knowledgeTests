package project.knowledgetests.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.knowledgetests.contract.MessageResponseDTO;
import project.knowledgetests.contract.QuestionRequestDTO;
import project.knowledgetests.contract.QuestionResponseDTO;
import project.knowledgetests.exception.ResourceAlreadyExistsException;
import project.knowledgetests.serivce.QuestionService;

import javax.validation.Valid;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class QuestionController {

    private final QuestionService questionService;

    @PreAuthorize("hasAnyAuthority('ROLE_QUESTION_MAINTAINER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid QuestionRequestDTO question) {
        return questionService.create(question);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_QUESTION_VIEWER')")
    @GetMapping
    public List<QuestionResponseDTO> listAll() {
        return questionService.listAll();
    }

    @GetMapping("/{id}")
    public QuestionResponseDTO findById(@PathVariable Long id) {
        return questionService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid QuestionRequestDTO question) {
        return questionService.updateById(id, question);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        questionService.delete(id);
    }


}
