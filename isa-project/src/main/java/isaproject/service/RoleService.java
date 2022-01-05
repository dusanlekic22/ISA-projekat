package isaproject.service;

import java.util.Set;

import isaproject.model.Role;

public interface RoleService {

	Set<Role> findByName(String name);
}