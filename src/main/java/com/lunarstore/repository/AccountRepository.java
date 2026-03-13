package com.lunarstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunarstore.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	public Account findByEmail(String email);
}
