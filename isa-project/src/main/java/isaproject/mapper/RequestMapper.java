package isaproject.mapper;

import isaproject.dto.BusinessOwnerRegistrationRequestDTO;
import isaproject.dto.UserDeletionRequestDTO;
import isaproject.model.BusinessOwnerRegistrationRequest;
import isaproject.model.UserDeletionRequest;

public class RequestMapper {

	public static BusinessOwnerRegistrationRequest DTOToBusinessOwnerRegistrationReques(BusinessOwnerRegistrationRequestDTO registrationRequestDTO) {
		BusinessOwnerRegistrationRequest registrationRequest = new BusinessOwnerRegistrationRequest();
		registrationRequest.setId(registrationRequestDTO.getId());
		registrationRequest.setAccepted(registrationRequestDTO.getAccepted());
		registrationRequest.setDeclineReason(registrationRequestDTO.getDeclineReason());
		registrationRequest.setRegistrationExplanation(registrationRequestDTO.getRegistrationExplanation());
		registrationRequest.setUserEmail(registrationRequestDTO.getUserEmail());
		return registrationRequest;
	}
	
	public static BusinessOwnerRegistrationRequestDTO BusinessOwnerRegistrationRequesToDTO(BusinessOwnerRegistrationRequest registrationRequest) {
		BusinessOwnerRegistrationRequestDTO registrationRequestDTO = new BusinessOwnerRegistrationRequestDTO();
		registrationRequestDTO.setId(registrationRequest.getId());
		registrationRequestDTO.setAccepted(registrationRequest.getAccepted());
		registrationRequestDTO.setDeclineReason(registrationRequest.getDeclineReason());
		registrationRequestDTO.setRegistrationExplanation(registrationRequest.getRegistrationExplanation());
		registrationRequestDTO.setUserEmail(registrationRequest.getUserEmail());
		return registrationRequestDTO;
	}

	public static UserDeletionRequestDTO UserDeletionRequestToDTO(UserDeletionRequest userDeletionRequest) {
		UserDeletionRequestDTO deletionRequestDTO = new UserDeletionRequestDTO();
		deletionRequestDTO.setId(userDeletionRequest.getId());
		deletionRequestDTO.setAccepted(userDeletionRequest.getAccepted());
		deletionRequestDTO.setDeletionExplanation(userDeletionRequest.getDeletionExplanation());
		deletionRequestDTO.setUserEmail(userDeletionRequest.getUserEmail());
		return deletionRequestDTO;
	}

	public static UserDeletionRequest DTOtoUserDeletionRequest(UserDeletionRequestDTO deletionRequestDTO) {
		UserDeletionRequest deletionRequest = new UserDeletionRequest();
		deletionRequest.setAccepted(deletionRequestDTO.getAccepted());
		deletionRequest.setDeletionExplanation(deletionRequestDTO.getDeletionExplanation());
		deletionRequest.setId(deletionRequestDTO.getId());
		deletionRequest.setUserEmail(deletionRequestDTO.getUserEmail());
		return deletionRequest;
	}

}
