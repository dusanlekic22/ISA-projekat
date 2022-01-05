package isaproject.mapper;

import isaproject.dto.BusinessOwnerRegistrationRequestDTO;
import isaproject.model.BusinessOwnerRegistrationRequest;

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

}
