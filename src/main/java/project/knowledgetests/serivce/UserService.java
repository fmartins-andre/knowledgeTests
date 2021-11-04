package project.knowledgetests.serivce;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import project.knowledgetests.contract.user.UserRequestDTO;
import project.knowledgetests.contract.user.UserResponseDTO;
import project.knowledgetests.entity.User;
import project.knowledgetests.exception.ConstraintViolationException;
import project.knowledgetests.exception.ResourceNotFoundException;
import project.knowledgetests.mapper.UserRequestMapper;
import project.knowledgetests.mapper.UserResponseMapper;
import project.knowledgetests.repository.UserRepository;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final UserResponseMapper userResponseMapper = UserResponseMapper.INSTANCE;
    private final UserRequestMapper userRequestMapper = UserRequestMapper.INSTANCE;

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream().map(userResponseMapper::toDTO).collect(Collectors.toList());
    }

    public UserResponseDTO findById(Long id) {
        User user = exists(id);
        return userResponseMapper.toDTO(user);
    }

    public UserResponseDTO findByUsername(String username) {
        User user = exists(username);

        if (user == null) throw new ResourceNotFoundException("User not found with username: " + username);

        return userResponseMapper.toDTO(user);
    }

    public UserResponseDTO create(UserRequestDTO user) {
        try {
            User userToSave = userRequestMapper.toModel(user);
            User savedUser = userRepository.save(userToSave);
            return userResponseMapper.toDTO(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "A conflict occurred while creating user '"
                            + user.getUsername()
                            + "'. Make sure all given data is respecting data constraints.");
        }
    }

    public UserResponseDTO updateById(Long id, UserRequestDTO user) {
        exists(id);

        User userToUpdate = userRequestMapper.toModel(user);
        user.setId(id);

        try {
            User updatedUser = userRepository.save(userToUpdate);
            return userResponseMapper.toDTO(updatedUser);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "A conflict occurred while updating user '"
                            + user.getUsername()
                            + "'. Make sure all given data is respecting data constraints.");
        }
    }

    public void delete(Long id) {
        User user = exists(id);
        userRepository.delete(user);
    }

    public User fromJwt(Jwt principal) {
        if (principal == null) throw new ValidationException("Could not find username in authentication credentials");

        User contextUser = exists(principal.getClaimAsString("preferred_username"));
        if (contextUser != null) return contextUser;

        User userToSave = new User();
        userToSave.setUsername(principal.getClaimAsString("preferred_username"));
        userToSave.setFullName(principal.getClaimAsString("name"));

        try {
            return userRepository.save(userToSave);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException(
                    "A conflict occurred while persisting user '"
                            + principal.getClaimAsString("preferred_username")
                            + "' to database.");
        }

    }

    private User exists(String username) {
        return userRepository.findByUsername(username);
    }

    private User exists(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with ID: " + id));
    }

}
