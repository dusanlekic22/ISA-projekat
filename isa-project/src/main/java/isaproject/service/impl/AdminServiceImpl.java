package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isaproject.dto.UserChangePasswordDTO;
import isaproject.dto.UserDTO;
import isaproject.mapper.UserMapper;
import isaproject.model.Admin;
import isaproject.repository.AddressRepository;
import isaproject.repository.AdminRepository;
import isaproject.service.AdminService;
import isaproject.service.RoleService;

@Service
public class AdminServiceImpl implements AdminService {

	private AdminRepository adminRepository;
	private RoleService roleService;
	private PasswordEncoder passwordEncoder;
	private AddressRepository addressRepository;

	@Autowired
	public AdminServiceImpl(AdminRepository adminRepository, RoleService roleService, PasswordEncoder passwordEncoder,
			AddressRepository addressRepository) {
		super();
		this.adminRepository = adminRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.addressRepository = addressRepository;
	}

	@Override
	public Admin registerAdmin(UserDTO userDTO) {
//		addressRepository.save(userDTO.getAddress());
		Admin admin = UserMapper.DTOToAdmin(userDTO);
		admin.setRoles(roleService.findByName("ROLE_ADMIN"));
		admin.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		admin.setEnabled(true);
		admin.setFirstTimeLoggedIn(true);
		return this.adminRepository.save(admin);
	}

	@Transactional
	@Override
	public void changePassword(UserChangePasswordDTO adminDTO) {
		Admin admin = adminRepository.getById(adminDTO.getId());
        admin.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        admin.setFirstTimeLoggedIn(false);
        adminRepository.save(admin);
	}
	
	 @Override
	 public Admin findByUsername(String username) {
		 return adminRepository.findByUsername(username);
	 }
	 
}
