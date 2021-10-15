package project.knowledgetests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import project.knowledgetests.contract.QuestionRequestDTO;
import project.knowledgetests.contract.QuestionResponseDTO;
import project.knowledgetests.entity.Question;

@Mapper
public interface QuestionResponseMapper {

    QuestionResponseMapper INSTANCE = Mappers.getMapper(QuestionResponseMapper.class);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Question toModel(QuestionRequestDTO questionDTO);

    QuestionResponseDTO toDTO(Question question);
}
