package com.lunarstore.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lunarstore.model.Account;
import com.lunarstore.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	HttpSession session;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email);
		if (account != null) {
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			String role = "ROLE_USER";
			if (account.getAdmin()) {
				role = "ROLE_ADMIN";
			}
			authorities.add(new SimpleGrantedAuthority(role));
			session.setAttribute("account", account);
			return new User(email, account.getPassword(), authorities);
		}
		return null;
	}

}
