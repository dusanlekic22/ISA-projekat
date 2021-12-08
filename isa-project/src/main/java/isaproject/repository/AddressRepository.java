package isaproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import isaproject.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

}
