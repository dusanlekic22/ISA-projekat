package isaproject.service.impl.boat;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.CustomerDTO;
import isaproject.dto.boat.BoatReservationDTO;
import isaproject.mapper.CustomerMapper;
import isaproject.mapper.boat.BoatReservationMapper;
import isaproject.model.AdditionalService;
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
import isaproject.service.boat.BoatReservationService;

@Service
public class BoatReservationServiceImpl implements BoatReservationService {

	BoatReservationRepository boatReservationRepository;
	BoatQuickReservationRepository boatQuickReservationRepository;
	BoatRepository boatRepository;
	CustomerService customerService;
	CustomerRepository customerRepository;
	BoatOwnerRepository boatOwnerRepository;

	@Autowired
	public BoatReservationServiceImpl(BoatReservationRepository boatReservationRepository,
			BoatQuickReservationRepository boatQuickReservationRepository, BoatRepository boatRepository,
			CustomerService customerService, CustomerRepository customerRepository,
			BoatOwnerRepository boatOwnerRepository) {
		super();
		this.boatReservationRepository = boatReservationRepository;
		this.boatQuickReservationRepository = boatQuickReservationRepository;
		this.boatRepository = boatRepository;
		this.customerService = customerService;
		this.customerRepository = customerRepository;
		this.boatOwnerRepository = boatOwnerRepository;
	}

	@Override
	public BoatReservationDTO findById(Long id) {
		BoatReservation boatReservation = boatReservationRepository.findById(id).orElse(null);
		return BoatReservationMapper.BoatReservationToBoatReservationDTO(boatReservation);
	}

	@Override
	public Set<BoatReservationDTO> findByBoatName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<BoatReservationDTO> findAll() {
		Set<BoatReservation> boats = new HashSet<>(boatReservationRepository.findAll());
		Set<BoatReservationDTO> dtos = new HashSet<>();
		if (boats.size() != 0) {
			BoatReservationDTO dto;
			for (BoatReservation p : boats) {
				dto = BoatReservationMapper.BoatReservationToBoatReservationDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Override
	public Set<CustomerDTO> findCustomersHasCurrentReservation(long boatId) {
		Set<Customer> customers = new HashSet<>(customerRepository.findAll());
		Set<CustomerDTO> dtos = new HashSet<>();
		if (customers.size() != 0) {
			CustomerDTO dto;
			for (Customer p : customers) {
				for (BoatReservation boatReservation : p.getBoatReservation()) {
					if (boatReservation.getDuration().isBetween(LocalDateTime.now()) && boatReservation.getBoat().getId() == boatId) {
						dto = CustomerMapper.customertoCustomerDTO(p);
						dtos.add(dto);
						break;
					}
				}
			}
		}
		return dtos;
	}

	@Transactional
	@Override
	public BoatReservationDTO reserveCustomer(BoatReservationDTO boatReservationDTO) {
		BoatReservation boatReservation = BoatReservationMapper.BoatReservationDTOToBoatReservation(boatReservationDTO);
		boatReservation.setConfirmed(true);
		boatReservation.setCustomer(customerRepository.findById(boatReservationDTO.getCustomer().getId()).get());
		long reservationPrice = boatReservation.getBoat().getPricePerHour() * boatReservation.getDuration().getHours();
		if(boatReservation.getAdditionalService()!= null) {
			for(AdditionalService additionalService: boatReservation.getAdditionalService()) {
				reservationPrice+= Double.parseDouble(additionalService.getPrice());
			}
			}
		boatReservation.setPrice((int) (long) reservationPrice);
		if (boatReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
			return null;
		}

		for (BoatReservation q : boatReservationRepository.findByConfirmedIsTrueAndBoatId(boatReservation.getBoat().getId())) {
			if (q.getDuration().overlapsWith(boatReservation.getDuration())) {
				return null;
			}
		}

		for (DateTimeSpan dateTimeSpan : boatOwnerRepository.findById(boatReservation.getBoat().getBoatOwner().getId())
				.get().getUnavailableReservationDateSpan()) {
			if (dateTimeSpan.overlapsWith(boatReservation.getDuration())) {
				return null;
			}
		}

		boolean overlaps = false;

		// Boat boat =
		// boatRepository.getById(boatReservation.getBoat().getId());

		for (DateTimeSpan dateTimeSpan : boatReservation.getBoat().getAvailableReservationDateSpan()) {
			if (boatReservation.getDuration().overlapsWith(dateTimeSpan)) {
				overlaps = true;
				reserveAvailableDateSpan(boatReservation, dateTimeSpan);
				break;
			}
		}

		if (!overlaps) {
			return null;}
		return BoatReservationMapper
				.BoatReservationToBoatReservationDTO(boatReservationRepository.save(boatReservation));
	}

	private boolean isCustomersReservationInAction(BoatReservation newReservation,
			BoatReservation existingReservation) {
		return (existingReservation.getCustomer().getId() == newReservation.getCustomer().getId())
				&& existingReservation.getDuration().isBetween(LocalDateTime.now());
	}

	private void reserveAvailableDateSpan(BoatReservation boatReservation, DateTimeSpan availableDateSpan) {
		Boat boat = boatReservation.getBoat();
		DateTimeSpan duration = boatReservation.getDuration();
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
	public BoatReservationDTO reserveBoatOwner(BoatReservationDTO boatReservationDTO, String siteUrl)
			throws UnsupportedEncodingException, MessagingException {
		BoatReservation boatReservation = BoatReservationMapper.BoatReservationDTOToBoatReservation(boatReservationDTO);
		boatReservation.setConfirmed(false);
		boatReservation.setCustomer(customerRepository.findById(boatReservationDTO.getCustomer().getId()).get());
		if (boatReservation.getDuration().isHoursBefore(LocalDateTime.now(), 1)) {
			return null;
		}

		boolean inAction = false;
		for (BoatReservation q : boatReservationRepository.findByConfirmedIsTrueAndBoatId(boatReservation.getBoat().getId())) {

			if (q.getDuration().overlapsWith(boatReservation.getDuration())) {
				return null;
			}

			if (isCustomersReservationInAction(boatReservation, q)) {
				inAction = true;
			}
		}

		for (DateTimeSpan dateTimeSpan : boatOwnerRepository.findById(boatReservation.getBoat().getBoatOwner().getId())
				.get().getUnavailableReservationDateSpan()) {
			if (dateTimeSpan.overlapsWith(boatReservation.getDuration())) {
				return null;
			}
		}

		for (BoatQuickReservation q : boatQuickReservationRepository.findByBoatId(boatReservation.getBoat().getId())) {

			if (q.getDuration().overlapsWith(boatReservation.getDuration())) {
				return null;
			}
		}

		if (!inAction) {
			return null;
		}

		for (DateTimeSpan dateTimeSpan : boatReservation.getBoat().getAvailableReservationDateSpan()) {
			if (boatReservation.getDuration().overlapsWith(dateTimeSpan)) {
				reserveAvailableDateSpan(boatReservation, dateTimeSpan);
				break;
			}
		}
		BoatReservation boatReservationReturn = boatReservationRepository.save(boatReservation);

		customerService.sendReservationConfirmationEmail(siteUrl, boatReservationReturn);

		return BoatReservationMapper.BoatReservationToBoatReservationDTO(boatReservationReturn);
	}

	@Transactional
	@Override
	public Set<BoatReservationDTO> findByBoatId(Long id) {
		Set<BoatReservation> boatReservations = new HashSet<>(boatReservationRepository.findByConfirmedIsTrueAndBoatId(id));
		Set<BoatReservationDTO> dtos = new HashSet<>();
		if (boatReservations.size() != 0) {

			BoatReservationDTO dto;
			for (BoatReservation p : boatReservations) {
				dto = BoatReservationMapper.BoatReservationToBoatReservationDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Transactional
	@Override
	public BoatReservationDTO deleteById(Long id) {
		BoatReservation boatReservation = boatReservationRepository.findById(id).get();
		freeReservedSpan(boatReservation);
		boatQuickReservationRepository.deleteById(id);
		return BoatReservationMapper.BoatReservationToBoatReservationDTO(boatReservation);
	}

	private void freeReservedSpan(BoatReservation boatReservation) {
		Boat boat = boatReservation.getBoat();
		DateTimeSpan duration = boatReservation.getDuration();
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

	@Override
	public Set<BoatReservationDTO> findAllPast() {
		Set<BoatReservationDTO> dtos = new HashSet<>();
		for (BoatReservationDTO boatReservationDTO : findAll()) {
			if (boatReservationDTO.getDuration().passed())
				dtos.add(boatReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<BoatReservationDTO> findAllActive() {
		Set<BoatReservationDTO> dtos = new HashSet<>();
		for (BoatReservationDTO boatReservationDTO : findAll()) {
			if (!boatReservationDTO.getDuration().passed())
				dtos.add(boatReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<BoatReservationDTO> findAllPastByBoatId(Long id) {
		Set<BoatReservationDTO> dtos = new HashSet<>();
		for (BoatReservationDTO boatReservationDTO : findByBoatId(id)) {
			if (boatReservationDTO.getDuration().passed())
				dtos.add(boatReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<BoatReservationDTO> findAllActiveByBoatId(Long id) {
		Set<BoatReservationDTO> dtos = new HashSet<>();
		for (BoatReservationDTO boatReservationDTO : findByBoatId(id)) {
			if (!boatReservationDTO.getDuration().passed())
				dtos.add(boatReservationDTO);
		}
		return dtos;
	}

	@Override
	public BoatReservationDTO confirmReservation(Long id) {
		BoatReservation boatReservation = boatReservationRepository.findById(id).get();
		boatReservation.setConfirmed(true);
		return BoatReservationMapper
				.BoatReservationToBoatReservationDTO(boatReservationRepository.save(boatReservation));
	}

	@Override
	public Set<BoatReservationDTO> findByBoatBoatOwnerId(Long id) {
		Set<BoatReservation> boatReservations = new HashSet<>(boatReservationRepository.findByConfirmedIsTrueAndBoat_BoatOwner_Id(id));
		Set<BoatReservationDTO> dtos = new HashSet<>();
		if (boatReservations.size() != 0) {

			BoatReservationDTO dto;
			for (BoatReservation p : boatReservations) {
				dto = BoatReservationMapper.BoatReservationToBoatReservationDTO(p);
				dtos.add(dto);
			}
		}
		return dtos;
	}

	@Override
	public Set<BoatReservationDTO> findAllActiveByBoatOwnerId(Long id) {
		Set<BoatReservationDTO> dtos = new HashSet<>();
		for (BoatReservationDTO boatReservationDTO : findByBoatBoatOwnerId(id)) {
			if (!boatReservationDTO.getDuration().passed())
				dtos.add(boatReservationDTO);
		}
		return dtos;
	}

	@Override
	public Set<BoatReservationDTO> findAllPastByBoatOwnerId(Long id) {
		Set<BoatReservationDTO> dtos = new HashSet<>();
		for (BoatReservationDTO boatReservationDTO : findByBoatBoatOwnerId(id)) {
			if (boatReservationDTO.getDuration().passed())
				dtos.add(boatReservationDTO);
		}
		return dtos;
	}

}
