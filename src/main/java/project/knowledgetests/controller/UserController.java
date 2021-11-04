package project.knowledgetests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.knowledgetests.contract.user.UserRequestDTO;
import project.knowledgetests.contract.user.UserResponseDTO;
import project.knowledgetests.mapper.UserRequestMapper;
import project.knowledgetests.mapper.UserResponseMapper;
import project.knowledgetests.serivce.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final UserRequestMapper userRequestMapper = UserRequestMapper.INSTANCE;
    private final UserResponseMapper userResponseMapper = UserResponseMapper.INSTANCE;

    @PreAuthorize("hasAnyAuthority('ROLE_USER_MAINTAINER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@Valid @RequestBody UserRequestDTO user, HttpServletResponse response) {
        UserResponseDTO createdUser = userService.create(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        response.setHeader("Location", String.valueOf(location));

        return createdUser;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_VIEWER')")
    @GetMapping
    public List<UserResponseDTO> listAll() {
        return userService.listAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_VIEWER')")
    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_MAINTAINER')")
    @PutMapping("/{id}")
    public UserResponseDTO updateById(@PathVariable Long id, @Valid @RequestBody UserRequestDTO user) {
        return userService.updateById(id, user);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_MAINTAINER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER_VIEWER')")
    @GetMapping("/username/{username}")
    public UserResponseDTO findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

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
}
