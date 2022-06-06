package isaproject.mapper;

import isaproject.dto.ComplaintDTO;
import isaproject.model.Complaint;

public class ComplaintMapper {

	public static Complaint DTOToComplaint(ComplaintDTO complaintDTO) {
		Complaint complaint = new Complaint();
		complaint.setId(complaintDTO.getId());
		complaint.setText(complaintDTO.getText());
		complaint.setBoatReservation(complaintDTO.getBoatReservation());
		complaint.setCottageReservation(complaintDTO.getCottageReservation());
		complaint.setFishingReservation(complaintDTO.getFishingReservation());
		return complaint;
	}

	public static ComplaintDTO ComplaintToDTO(Complaint complaint) {
		ComplaintDTO complaintDTO = new ComplaintDTO();
		complaintDTO.setId(complaint.getId());
		complaintDTO.setText(complaint.getText());
		complaintDTO.setBoatReservation(complaint.getBoatReservation());
		complaintDTO.setCottageReservation(complaint.getCottageReservation());
		complaintDTO.setFishingReservation(complaint.getFishingReservation());
		return complaintDTO;
	}
}
