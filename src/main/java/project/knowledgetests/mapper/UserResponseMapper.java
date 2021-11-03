package project.knowledgetests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.knowledgetests.contract.user.UserResponseDTO;
import project.knowledgetests.entity.User;

@Mapper
public interface UserResponseMapper {
    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    User toModel(UserResponseDTO userResponseDTO);

    UserResponseDTO toDTO(User user);
}
