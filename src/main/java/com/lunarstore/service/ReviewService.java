package com.lunarstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunarstore.model.Product;
import com.lunarstore.model.Review;
import com.lunarstore.repository.ProductRepository;
import com.lunarstore.repository.ReviewRepository;

@Service
public class ReviewService {
	@Autowired
	private ReviewRepository revRep;
	@Autowired
	private ProductRepository proRep;

	public ReviewService(ReviewRepository revRep, ProductRepository proRep) {
		this.revRep = revRep;
		this.proRep = proRep;
	}

	public List<Review> getReviewsByProductId(Integer productId) {
		Product product = proRep.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
		return revRep.findByProduct(product);
	}

	public List<Review> getReviewsByProduct(Product product) {
		return revRep.findByProduct(product);
	}

	public Review saveReview(Review review) {
		return revRep.save(review);
	}

}
