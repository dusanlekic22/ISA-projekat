package isaproject.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.FishingTrainerDTO;
import isaproject.dto.RoleDTO;
import isaproject.dto.UserDTO;
import isaproject.dto.boat.BoatOwnerDTO;
import isaproject.dto.cottage.CottageOwnerDTO;
import isaproject.model.Admin;
import isaproject.model.FishingTrainer;
import isaproject.model.Role;
import isaproject.model.User;
import isaproject.model.boat.BoatOwner;
import isaproject.model.cottage.CottageOwner;

public class UserMapper {
	
	public static CottageOwner DTOToCottageOwner(BusinessOwnerDTO businessOwnerDTO) {
		CottageOwner user = new CottageOwner();
		user.setId(businessOwnerDTO.getId());
		user.setUsername(businessOwnerDTO.getUsername());
		user.setFirstName(businessOwnerDTO.getFirstName());
		user.setLastName(businessOwnerDTO.getLastName());
		user.setEmail(businessOwnerDTO.getEmail());
		user.setPhoneNumber(businessOwnerDTO.getPhoneNumber());
		user.setAddress(businessOwnerDTO.getAddress());
		return user;
	}
	
	public static BoatOwner DTOToBoatOwner(BusinessOwnerDTO businessOwnerDTO) {
		BoatOwner user = new BoatOwner();
		user.setId(businessOwnerDTO.getId());
		user.setUsername(businessOwnerDTO.getUsername());
		user.setFirstName(businessOwnerDTO.getFirstName());
		user.setLastName(businessOwnerDTO.getLastName());
		user.setEmail(businessOwnerDTO.getEmail());
		user.setPhoneNumber(businessOwnerDTO.getPhoneNumber());
		user.setAddress(businessOwnerDTO.getAddress());
		return user;
	}

	public static FishingTrainer DTOToFishingTrainer(BusinessOwnerDTO businessOwnerDTO) {
		FishingTrainer user = new FishingTrainer();
		user.setUsername(businessOwnerDTO.getUsername());
		user.setFirstName(businessOwnerDTO.getFirstName());
		user.setLastName(businessOwnerDTO.getLastName());
		user.setEmail(businessOwnerDTO.getEmail());
		user.setPhoneNumber(businessOwnerDTO.getPhoneNumber());
		user.setAddress(businessOwnerDTO.getAddress());
		return user;
	}
	
	public static User DTOToUser(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setAddress(userDTO.getAddress());
		return user;
	}
	
	public static Set<RoleDTO> SetRoleToDTO(Set<Role> roles) {
		Set<RoleDTO> rolesDTO = new HashSet<RoleDTO>();
		roles.forEach(role -> rolesDTO.add(RoleToDTO(role)));
		return rolesDTO;
	}
	
	public static RoleDTO RoleToDTO(Role role) {
		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(role.getId());
		roleDTO.setName(role.getName());
		return roleDTO;
	}
	
	public static UserDTO UserToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setPassword(user.getPassword());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setRoles(SetRoleToDTO(user.getRoles()));
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setAddress(user.getAddress());
		return userDTO;
	}

	public static Admin DTOToAdmin(UserDTO userDTO) {
		Admin user = new Admin();
		user.setUsername(userDTO.getUsername());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setAddress(userDTO.getAddress());
		return user;
	}

	public static FishingTrainerDTO FishingTrainerToDTO(FishingTrainer fishingTrainer) {
		FishingTrainerDTO fishingTrainerDTO = new FishingTrainerDTO();
		fishingTrainerDTO.setId(fishingTrainer.getId());
		fishingTrainerDTO.setUsername(fishingTrainer.getUsername());
		fishingTrainerDTO.setPassword(fishingTrainer.getPassword());
		fishingTrainerDTO.setFirstName(fishingTrainer.getFirstName());
		fishingTrainerDTO.setLastName(fishingTrainer.getLastName());
		fishingTrainerDTO.setEmail(fishingTrainer.getEmail());
		fishingTrainerDTO.setRoles(SetRoleToDTO(fishingTrainer.getRoles()));
		fishingTrainerDTO.setPhoneNumber(fishingTrainer.getPhoneNumber());
		fishingTrainerDTO.setAddress(fishingTrainer.getAddress());
		fishingTrainerDTO.setBiography(fishingTrainer.getBiography());
		fishingTrainerDTO.setAverageGrade(fishingTrainer.getAverageGrade());
		fishingTrainerDTO.setFishingCourse(fishingTrainer.getFishingCourse());
		fishingTrainerDTO.setAvailableReservationDateSpan(fishingTrainer.getAvailableReservationDateSpan());
		fishingTrainerDTO.setUnavailableReservationDateSpan(fishingTrainer.getUnavailableReservationDateSpan());
		return fishingTrainerDTO;
	}
	
	public static CottageOwnerDTO CottageOwnerToDTO(CottageOwner cottageOwner) {
		CottageOwnerDTO cottageOwnerDTO = new CottageOwnerDTO();
		cottageOwnerDTO.setId(cottageOwner.getId());
		cottageOwnerDTO.setUsername(cottageOwner.getUsername());
		cottageOwnerDTO.setPassword(cottageOwner.getPassword());
		cottageOwnerDTO.setFirstName(cottageOwner.getFirstName());
		cottageOwnerDTO.setLastName(cottageOwner.getLastName());
		cottageOwnerDTO.setEmail(cottageOwner.getEmail());
		cottageOwnerDTO.setRoles(SetRoleToDTO(cottageOwner.getRoles()));
		cottageOwnerDTO.setPhoneNumber(cottageOwner.getPhoneNumber());
		cottageOwnerDTO.setAddress(cottageOwner.getAddress());
		cottageOwnerDTO.setCottage(cottageOwner.getCottage());
		cottageOwnerDTO.setUnavailableReservationDateSpan(cottageOwner.getUnavailableReservationDateSpan());
		return cottageOwnerDTO;
	}
	
	public static BoatOwnerDTO BoatOwnerToDTO(BoatOwner boatOwner) {
		BoatOwnerDTO boatOwnerDTO = new BoatOwnerDTO();
		boatOwnerDTO.setId(boatOwner.getId());
		boatOwnerDTO.setUsername(boatOwner.getUsername());
		boatOwnerDTO.setPassword(boatOwner.getPassword());
		boatOwnerDTO.setFirstName(boatOwner.getFirstName());
		boatOwnerDTO.setLastName(boatOwner.getLastName());
		boatOwnerDTO.setEmail(boatOwner.getEmail());
		boatOwnerDTO.setRoles(SetRoleToDTO(boatOwner.getRoles()));
		boatOwnerDTO.setPhoneNumber(boatOwner.getPhoneNumber());
		boatOwnerDTO.setAddress(boatOwner.getAddress());
		boatOwnerDTO.setBoat(boatOwner.getBoat());
		boatOwnerDTO.setUnavailableReservationDateSpan(boatOwner.getUnavailableReservationDateSpan());
		return boatOwnerDTO;
	}

	
	
	public static Page<FishingTrainerDTO> pageFishingTrainerToPageDTO(Page<FishingTrainer> fishingTrainerPages) {
		Page<FishingTrainerDTO> fishingTrainerDTOPages = fishingTrainerPages.map(new Function<FishingTrainer, FishingTrainerDTO>() {
			@Override
			public FishingTrainerDTO apply(FishingTrainer entity) {
				FishingTrainerDTO dto = UserMapper.FishingTrainerToDTO(entity);
				return dto;
			}
		});
		return fishingTrainerDTOPages;
	}
}
