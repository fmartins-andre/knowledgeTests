package project.knowledgetests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import project.knowledgetests.contract.QuestionRequestDTO;
import project.knowledgetests.entity.Question;

@Mapper
public interface QuestionRequestMapper {

    QuestionRequestMapper INSTANCE = Mappers.getMapper(QuestionRequestMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Question toModel(QuestionRequestDTO questionDTO);

    QuestionRequestDTO toDTO(Question question);
}
