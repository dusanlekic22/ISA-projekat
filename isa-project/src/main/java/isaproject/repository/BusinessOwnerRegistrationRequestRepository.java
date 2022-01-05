package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.BusinessOwnerRegistrationRequest;

public interface BusinessOwnerRegistrationRequestRepository extends JpaRepository<BusinessOwnerRegistrationRequest, Long> {

}
