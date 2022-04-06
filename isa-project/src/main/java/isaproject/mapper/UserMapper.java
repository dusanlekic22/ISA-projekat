package isaproject.mapper;

import java.sql.Timestamp;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.dto.UserDTO;
import isaproject.model.FishingTrainer;
import isaproject.model.User;
import net.bytebuddy.utility.RandomString;

public class UserMapper {

	public static FishingTrainer DTOToFishingTrainer(BusinessOwnerDTO businessOwnerDTO) {
		FishingTrainer user = new FishingTrainer();
		user.setUsername(businessOwnerDTO.getUsername());
		user.setPassword(businessOwnerDTO.getPassword());
		user.setFirstName(businessOwnerDTO.getFirstName());
		user.setLastName(businessOwnerDTO.getLastName());
		user.setEmail(businessOwnerDTO.getEmail());
		user.setEnabled(false);
		user.setVerificationCode(RandomString.make(64));
		user.setPhoneNumber(businessOwnerDTO.getPhoneNumber());
		user.setAddress(businessOwnerDTO.getAddress());
		user.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		return user;
	}
	
	public static User UsertoUserDTO(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setVerificationCode(userDTO.getVerificationCode());
		user.setRoles(userDTO.getRoles());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setAddress(userDTO.getAddress());
		user.setLastPasswordResetDate(userDTO.getLastPasswordResetDate());
		return user;
	}
	
}
