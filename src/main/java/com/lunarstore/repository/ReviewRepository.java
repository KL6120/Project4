package com.lunarstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lunarstore.model.Account;
import com.lunarstore.model.Product;
import com.lunarstore.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByProduct(Product product);

	List<Review> findByAccount(Account account);
}
