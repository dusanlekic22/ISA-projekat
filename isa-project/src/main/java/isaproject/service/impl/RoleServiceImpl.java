package isaproject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import isaproject.repository.RoleRepository;
import isaproject.service.RoleService;

public class RoleServiceImpl implements RoleService {

	private RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}
	
	
}
