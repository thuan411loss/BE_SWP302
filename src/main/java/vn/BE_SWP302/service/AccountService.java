package vn.BE_SWP302.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.BE_SWP302.domain.Account;
import vn.BE_SWP302.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	AccountRepository repository;

	public List<Account> findAll() {
		return repository.findAll();
	}

	public Account findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Account save(Account account) {
		return repository.save(account);
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}
}
