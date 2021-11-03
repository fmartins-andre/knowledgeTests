package project.knowledgetests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import project.knowledgetests.contract.user.UserByUsernameRequestDTO;
import project.knowledgetests.contract.user.UserResponseDTO;
import project.knowledgetests.exception.ConstraintViolationException;
import project.knowledgetests.serivce.UserService;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt principal) throws JsonProcessingException {

        HashMap allRoles = new ObjectMapper()
                .readValue(principal.getClaimAsString("resource_access"), HashMap.class);

        Map<String, Object> user = new HashMap<>();
        user.put("username", principal.getClaimAsString("preferred_username"));
        user.put("fullName", principal.getClaimAsString("name"));
        user.put("email", principal.getClaimAsString("email"));
        user.put("scope", principal.getClaimAsString("scope"));
        user.put("roles", ((Map) allRoles.get("rest-api")).get("roles"));

        return user;
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/username/{username}")
    public UserResponseDTO findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll();
    }
}
