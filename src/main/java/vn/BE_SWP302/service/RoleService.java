package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.Role;
import vn.BE_SWP302.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository repository;

	public List<Role> findAll() {
		return repository.findAll();
	}

	public Role findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Role save(Role role) {
		return repository.save(role);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
