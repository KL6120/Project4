package com.lunarstore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lunarstore.model.Account;
import com.lunarstore.model.Favourite;
import com.lunarstore.model.Product;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer>{
	public Page<Favourite> findByAccount(Account account, Pageable pageable);
	public boolean existsByAccountAndProduct(Account account, Product product);
}
