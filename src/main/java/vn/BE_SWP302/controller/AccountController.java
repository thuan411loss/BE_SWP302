package vn.BE_SWP302.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.BE_SWP302.domain.Account;
import vn.BE_SWP302.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	AccountService service;

	@GetMapping
	public List<Account> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Account get(@PathVariable Long id) {
		return service.findById(id);
	}

	@PostMapping
	public Account create(@RequestBody Account account) {
		return service.save(account);
	}

	@PutMapping("/{id}")
	public Account update(@PathVariable Long id, @RequestBody Account account) {
		account.setAccountId(id);
		return service.save(account);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}

