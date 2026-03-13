package com.lunarstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lunarstore.model.Account;
import com.lunarstore.model.CartDetail;
import com.lunarstore.model.Product;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer>{
	public CartDetail findByAccountAndProduct(Account account, Product product);
	List<CartDetail> findByAccount(Account account);
}
