package project.knowledgetests.serivce;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.knowledgetests.contract.MessageResponseDTO;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessageResponseService {


    public MessageResponseDTO createMessageResponse(String message) {
        return MessageResponseDTO
                .builder()
                .message(message)
                .build();
    }

}
