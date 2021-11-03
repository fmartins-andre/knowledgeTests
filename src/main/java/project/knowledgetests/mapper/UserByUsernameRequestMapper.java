package project.knowledgetests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import project.knowledgetests.contract.user.UserByUsernameRequestDTO;
import project.knowledgetests.entity.User;

@Mapper(componentModel = "spring")
public interface UserByUsernameRequestMapper {

    UserByUsernameRequestMapper INSTANCE = Mappers.getMapper(UserByUsernameRequestMapper.class);

    @Mapping(target = "fullName", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toModel(UserByUsernameRequestDTO usernameRequestDTO);

    UserByUsernameRequestDTO toDTO(User user);
}
