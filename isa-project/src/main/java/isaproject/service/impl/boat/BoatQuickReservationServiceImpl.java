package isaproject.service.impl.boat;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.boat.BoatQuickReservationDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.mapper.boat.BoatQuickReservationMapper;
import isaproject.mapper.boat.BoatReservationMapper;
import isaproject.model.Customer;
import isaproject.model.DateTimeSpan;
import isaproject.model.boat.Boat;
import isaproject.model.boat.BoatQuickReservation;
import isaproject.model.boat.BoatReservation;
import isaproject.repository.CustomerRepository;
import isaproject.repository.boat.BoatOwnerRepository;
import isaproject.repository.boat.BoatQuickReservationRepository;
import isaproject.repository.boat.BoatRepository;
import isaproject.repository.boat.BoatReservationRepository;
import isaproject.service.CustomerService;
import isaproject.service.boat.BoatQuickReservationService;

@Service
public class BoatQuickReservationServiceImpl implements BoatQuickReservationService {

	BoatQuickReservationRepository boatQuickReservationRepository;
	BoatReservationRepository boatReservationRepository;
	BoatRepository boatRepository;
	CustomerRepository customerRepository;
	CustomerService customerService;
	BoatOwnerRepository boatOwnerRepository;

	@Autowired
	public BoatQuickReservationServiceImpl(BoatQuickReservationRepository boatQuickReservationRepository,
			BoatReservationRepository boatReservationRepository, BoatRepository boatRepository,
			CustomerRepository customerRepository, CustomerService customerService,
			BoatOwnerRepository boatOwnerRepository) {
		super();
		this.boatQuickReservationRepository = boatQuickReservationRepository;
		this.boatReservationRepository = boatReservationRepository;
		this.boatRepository = boatRepository;
		this.customerRepository = customerRepository;
		this.customerService = customerService;
		this.boatOwnerRepository = boatOwnerRepository;
	}

	@Override
	public BoatQuickReservationDTO findById(Long id) {
		BoatQuickReservation boatQuickReservation = boatQuickReservationRepository.findById(id).orElse(null);
		return BoatQuickReservationMapper.BoatQuickReservationToBoatQuickReservationDTO(boatQuickReservation);
	}

	@Override
	public Set<BoatQuickReservationDTO> findByBoatName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BoatQuickReservationDTO> findAll() {
		Set<BoatQuickReservation> boats = new HashSet<>(boatQuickReservationRepository.findAll());
		Set<BoatQuickReservationDTO> dtos = new HashSet<>();
		if (boats.size() != 0) {
			BoatQuickReservationDTO dto;
			for (BoatQuickReservation p : boats) {
				dto = BoatQuickReservationMapper.BoatQuickReservationToBoatQuickReservationDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	private void reserveAvailableDateSpan(BoatQuickReservation boatQuickReservation, DateTimeSpan availableDateSpan) {
		Boat boat = boatQuickReservation.getBoat();
		DateTimeSpan duration = boatQuickReservation.getDuration();
		boat.getAvailableReservationDateSpan().remove(availableDateSpan);
		if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) <= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			boat.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) >= 0) {
			DateTimeSpan newDateSpan = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			boat.getAvailableReservationDateSpan().add(newDateSpan);
		} else if (duration.getStartDate().compareTo(availableDateSpan.getStartDate()) >= 0
				&& duration.getEndDate().compareTo(availableDateSpan.getEndDate()) <= 0) {
			DateTimeSpan newDateSpan1 = new DateTimeSpan(availableDateSpan.getStartDate(), duration.getStartDate());
			DateTimeSpan newDateSpan2 = new DateTimeSpan(duration.getEndDate(), availableDateSpan.getEndDate());
			boat.getAvailableReservationDateSpan().add(newDateSpan1);
			boat.getAvailableReservationDateSpan().add(newDateSpan2);
		}
		boatRepository.save(boat);
	}

	@Transactional
	@Override
	public BoatQuickReservationDTO save(BoatQuickReservationDTO boatQuickReservationDTO, String siteUrl)
			throws UnsupportedEncodingException, MessagingException {
		BoatQuickReservation boatQuickReservation = BoatQuickReservationMapper
				.BoatQuickReservationDTOToBoatQuickReservation(boatQuickReservationDTO);
		boatQuickReservation.setReserved(false);

		if (!boatQuickReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}

		for (BoatQuickReservation q : boatQuickReservationRepository
				.findByBoatId(boatQuickReservation.getBoat().getId())) {
			if (q.getDuration().overlapsWith(boatQuickReservation.getDuration())) {
				return null;
			}
		}

		for (DateTimeSpan dateTimeSpan : boatOwnerRepository
				.findById(boatQuickReservation.getBoat().getBoatOwner().getId()).get()
				.getUnavailableReservationDateSpan()) {
			if (dateTimeSpan.overlapsWith(boatQuickReservation.getDuration())) {
				return null;
			}
		}

		for (BoatReservation q : boatReservationRepository.findByBoatId(boatQuickReservation.getBoat().getId())) {
			if (q.getDuration().overlapsWith(boatQuickReservation.getDuration())) {
				return null;
			}
		}

		for (DateTimeSpan dateTimeSpan : boatQuickReservation.getBoat().getAvailableReservationDateSpan()) {
			if (boatQuickReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpan(boatQuickReservation, dateTimeSpan);
				break;
			}
		}
		BoatQuickReservation boatQuickReservationReturn = boatQuickReservationRepository.save(boatQuickReservation);
		for (Customer customer : boatQuickReservation.getBoat().getSubscribers()) {
			System.out.println(customer.getFirstName());
			customerService.sendNewQuickReservationEmail(customer, siteUrl, boatQuickReservationReturn);
		}

		return BoatQuickReservationMapper.BoatQuickReservationToBoatQuickReservationDTO(boatQuickReservationReturn);
	}

	@Override
	public Set<BoatQuickReservationDTO> findByBoatId(Long id) {
		Set<BoatQuickReservation> boatQuickReservations = new HashSet<>(
				boatQuickReservationRepository.findByBoatId(id));
		Set<BoatQuickReservationDTO> dtos = new HashSet<>();
		if (boatQuickReservations.size() != 0) {

			BoatQuickReservationDTO dto;
			for (BoatQuickReservation p : boatQuickReservations) {
				dto = BoatQuickReservationMapper.BoatQuickReservationToBoatQuickReservationDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Transactional
	@Override
	public BoatQuickReservationDTO deleteById(Long id) {
		BoatQuickReservation boatQuickReservation = boatQuickReservationRepository.findById(id).get();
		freeReservedSpan(boatQuickReservation);
		boatQuickReservationRepository.deleteById(id);
		return BoatQuickReservationMapper.BoatQuickReservationToBoatQuickReservationDTO(boatQuickReservation);
	}

	private void freeReservedSpan(BoatQuickReservation boatQuickReservation) {
		Boat boat = boatQuickReservation.getBoat();
		DateTimeSpan duration = boatQuickReservation.getDuration();
		DateTimeSpan newAvailableDateSpan = new DateTimeSpan(duration);
		Set<DateTimeSpan> availableDateSpans = new HashSet<>(boat.getAvailableReservationDateSpan());
		boolean startChanged = false;
		boolean endChanged = false;
		for (DateTimeSpan dateTimeSpan : availableDateSpans) {
			if (newAvailableDateSpan.getStartDate().compareTo(dateTimeSpan.getEndDate()) == 0) {
				boat.getAvailableReservationDateSpan().remove(dateTimeSpan);
				if (endChanged) {
					boat.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(),
							newAvailableDateSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(dateTimeSpan.getStartDate(), duration.getEndDate());
					startChanged = true;
				}
				boat.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
			if (newAvailableDateSpan.getEndDate().compareTo(dateTimeSpan.getStartDate()) == 0) {
				boat.getAvailableReservationDateSpan().remove(dateTimeSpan);
				if (startChanged) {
					boat.getAvailableReservationDateSpan().remove(newAvailableDateSpan);
					newAvailableDateSpan = new DateTimeSpan(newAvailableDateSpan.getStartDate(),
							dateTimeSpan.getEndDate());
				} else {
					newAvailableDateSpan = new DateTimeSpan(duration.getStartDate(), dateTimeSpan.getEndDate());
					endChanged = true;
				}
				boat.getAvailableReservationDateSpan().add(newAvailableDateSpan);
			}
		}

		boatRepository.save(boat);
	}

	@Transactional
	@Override
	public BoatReservationDTO appointQuickReservation(Long reservationId, Long userId) {
		BoatQuickReservation boatQuickReservation = boatQuickReservationRepository.findById(reservationId).get();
		if (!boatQuickReservation.getDuration().isDaysAfter(LocalDateTime.now(), 1)) {
			return null;
		}
		boatQuickReservation.setReserved(true);
		boatQuickReservationRepository.save(boatQuickReservation);
		BoatReservation boatReservation = BoatQuickReservationMapper
				.BoatQuickReservationToBoatReservation(boatQuickReservation);
		boatReservation.setCustomer(customerRepository.findById(userId).get());
		BoatReservation boatReservationReturn = boatReservationRepository.save(boatReservation);
		return BoatReservationMapper.BoatReservationToBoatReservationDTO(boatReservationReturn);
	}

	@Override
	public Set<BoatQuickReservationDTO> findByIsReservedFalseAndBoatId(Long id) {
		Set<BoatQuickReservation> boatQuickReservations = new HashSet<>(
				boatQuickReservationRepository.findByIsReservedFalseAndBoatId(id));
		Set<BoatQuickReservationDTO> dtos = new HashSet<>();
		if (boatQuickReservations.size() != 0) {

			BoatQuickReservationDTO dto;
			for (BoatQuickReservation p : boatQuickReservations) {
				dto = BoatQuickReservationMapper.BoatQuickReservationToBoatQuickReservationDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<BoatQuickReservationDTO> findByIsReservedFalse() {
		Set<BoatQuickReservation> boatQuickReservations = new HashSet<>(
				boatQuickReservationRepository.findByIsReservedFalse());
		Set<BoatQuickReservationDTO> dtos = new HashSet<>();
		if (boatQuickReservations.size() != 0) {

			BoatQuickReservationDTO dto;
			for (BoatQuickReservation p : boatQuickReservations) {
				dto = BoatQuickReservationMapper.BoatQuickReservationToBoatQuickReservationDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

	@Override
	public Set<BoatQuickReservationDTO> findByBoatOwnerId(Long id) {
		Set<BoatQuickReservation> boatQuickReservations = new HashSet<>(
				boatQuickReservationRepository.findByBoat_BoatOwner_Id(id));
		Set<BoatQuickReservationDTO> dtos = new HashSet<>();
		if (boatQuickReservations.size() != 0) {

			BoatQuickReservationDTO dto;
			for (BoatQuickReservation p : boatQuickReservations) {
				dto = BoatQuickReservationMapper.BoatQuickReservationToBoatQuickReservationDTO(p);
				dtos.add(dto);
			}
		}

		return dtos;
	}

}
