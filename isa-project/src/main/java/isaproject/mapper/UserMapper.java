package isaproject.mapper;

import java.sql.Timestamp;

import isaproject.dto.BusinessOwnerDTO;
import isaproject.model.FishingTrainer;
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
		user.setRoles(businessOwnerDTO.getRoles());
		user.setPhoneNumber(businessOwnerDTO.getPhoneNumber());
		user.setAddress(businessOwnerDTO.getAddress());
		user.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		return user;
	}
	
}
