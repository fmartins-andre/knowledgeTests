package project.knowledgetests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.knowledgetests.contract.question.QuestionResponseDTO;
import project.knowledgetests.entity.Question;

@Mapper
public interface QuestionResponseMapper {
    QuestionResponseMapper INSTANCE = Mappers.getMapper(QuestionResponseMapper.class);

    Question toModel(QuestionResponseDTO questionDTO);

    QuestionResponseDTO toDTO(Question question);
}
