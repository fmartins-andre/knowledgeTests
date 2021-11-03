package project.knowledgetests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.knowledgetests.contract.user.UserRequestDTO;
import project.knowledgetests.entity.User;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

    User toModel(UserRequestDTO questionDTO);

    UserRequestDTO toDTO(User question);
}
