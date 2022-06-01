package isaproject.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isaproject.dto.FishingReservationReportDTO;
import isaproject.mapper.ReservationReportMapper;
import isaproject.model.Customer;
import isaproject.model.FishingReservationReport;
import isaproject.model.ReservationReportStatus;
import isaproject.repository.CustomerRepository;
import isaproject.repository.FishingReservationReportRepository;
import isaproject.service.FishingReservationReportService;

@Service
public class FishingReservationReportServiceImpl implements FishingReservationReportService {

	FishingReservationReportRepository fishingReservationReportRepository;
	CustomerRepository customerRepository;

	@Autowired
	public FishingReservationReportServiceImpl(FishingReservationReportRepository fishingReservationReportRepository,
			CustomerRepository customerRepository) {
		super();
		this.fishingReservationReportRepository = fishingReservationReportRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public FishingReservationReportDTO create(FishingReservationReportDTO fishingReservationReportDTO) {
		FishingReservationReport fishingReservationReport = ReservationReportMapper
				.DTOToFishingReservationReport(fishingReservationReportDTO);
		if (fishingReservationReport.getReservationReportStatus() == ReservationReportStatus.Positive) {
			fishingReservationReport.setUserPenalized(false);
		} else if (fishingReservationReport.getReservationReportStatus() == ReservationReportStatus.NoCustomer) {
			fishingReservationReport.setUserPenalized(true);
			penalizedCustomer(fishingReservationReport);
		} else if (fishingReservationReport.getReservationReportStatus() == ReservationReportStatus.Negative) {
			fishingReservationReport.setUserPenalized(null);
		}
		return ReservationReportMapper
				.FishingReservationReportToDTO(fishingReservationReportRepository.save(fishingReservationReport));
	}

	private void penalizedCustomer(FishingReservationReport fishingReservationReport) {
		Customer customer = customerRepository.findById(fishingReservationReport.getFishingReservation().getCustomer().getId()).get();
		// TODO: KAZNI KORISNIKA 3:) :ROFL:
		customerRepository.save(customer);
	}

	@Override
	public Set<FishingReservationReportDTO> getAllFishingReservationReports() {
		Set<FishingReservationReportDTO> dtos = new HashSet<FishingReservationReportDTO>();
		for (FishingReservationReport fishingReservationReport : fishingReservationReportRepository.findAll()) {
			dtos.add(ReservationReportMapper.FishingReservationReportToDTO(fishingReservationReport));
		}
		return dtos;
	}

	@Transactional
	@Override
	public FishingReservationReportDTO approve(FishingReservationReportDTO fishingReservationReportDTO,
			String answerCutomer, String answerOwner) {
		FishingReservationReport report = fishingReservationReportRepository
				.findById(fishingReservationReportDTO.getId()).get();
		if (report.getUserPenalized() != null)
			return ReservationReportMapper.FishingReservationReportToDTO(report);
		report.setUserPenalized(true);
		penalizedCustomer(report);
		sendApproveEmailCustomer(report.getFishingReservation().getCustomer().getEmail(), answerCutomer);
		sendApproveEmailOwner(report.getFishingReservation().getCustomer().getEmail(), answerOwner);
		return ReservationReportMapper.FishingReservationReportToDTO(report);
	}

	private void sendApproveEmailOwner(String email, String answerOwner) {
		// TODO Auto-generated method stub

	}

	private void sendApproveEmailCustomer(String email, String answerCutomer) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public FishingReservationReportDTO decline(FishingReservationReportDTO fishingReservationReportDTO,
			String answerCutomer, String answerOwner) {
		FishingReservationReport report = fishingReservationReportRepository
				.findById(fishingReservationReportDTO.getId()).get();
		if (report.getUserPenalized() != null)
			return ReservationReportMapper.FishingReservationReportToDTO(report);
		report.setUserPenalized(false);
		sendDeclineEmailCustomer(report.getFishingReservation().getCustomer().getEmail(), answerCutomer);
		sendDeclineEmailOwner(report.getFishingReservation().getCustomer().getEmail(), answerOwner);
		return ReservationReportMapper.FishingReservationReportToDTO(report);
	}

	private void sendDeclineEmailOwner(String email, String answerOwner) {
		// TODO Auto-generated method stub

	}

	private void sendDeclineEmailCustomer(String email, String answerCutomer) {
		// TODO Auto-generated method stub

	}

}
