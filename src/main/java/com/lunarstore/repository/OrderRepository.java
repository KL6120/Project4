package com.lunarstore.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lunarstore.model.Account;
import com.lunarstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	List<Order> findByAccount(Account account);
	Page<Order> findByAccount(Account account, Pageable pageable);
}
