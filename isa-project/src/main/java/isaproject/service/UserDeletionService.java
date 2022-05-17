package isaproject.service;

import java.util.List;

import isaproject.dto.CreateUserDeletionRequestDTO;
import isaproject.dto.UserDeletionRequestDTO;
import isaproject.model.UserDeletionRequest;

public interface UserDeletionService {

	UserDeletionRequest approve(UserDeletionRequestDTO userDeletionRequestDTO, String answer);
	UserDeletionRequest decline(UserDeletionRequestDTO userDeletionRequestDTO, String answer);
	List<UserDeletionRequestDTO> getAllUserDeletionRequests();
	UserDeletionRequest create(CreateUserDeletionRequestDTO requestDTO);

}
