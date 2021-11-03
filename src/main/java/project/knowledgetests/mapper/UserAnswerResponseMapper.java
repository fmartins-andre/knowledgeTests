package project.knowledgetests.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import project.knowledgetests.contract.userAnswer.UserAnswerResponseDTO;
import project.knowledgetests.entity.UserAnswer;

@Mapper
public interface UserAnswerResponseMapper {
    UserAnswerResponseMapper INSTANCE = Mappers.getMapper(UserAnswerResponseMapper.class);

    UserAnswer toModel(UserAnswerResponseDTO userAnswerResponseDTO);

    UserAnswerResponseDTO toDTO(UserAnswer answer);
}
