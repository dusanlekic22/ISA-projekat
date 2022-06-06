package isaproject.service;

import java.util.Set;

import isaproject.dto.ComplaintDTO;

public interface ComplaintService {

	ComplaintDTO create(ComplaintDTO complaintDTO);

	ComplaintDTO answer(ComplaintDTO complaintDTO, String answerCustomer, String answerOwner);

	Set<ComplaintDTO> getAllComplaints();

}
