package com.lunarstore.controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.lunarstore.model.Review;
import com.lunarstore.repository.ProductRepository;
import com.lunarstore.model.Product;
import com.lunarstore.model.Account;
import com.lunarstore.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/products")
public class ReviewController {
	private final ReviewService reviewService;
	private final ProductRepository productRepository;

	public ReviewController(ReviewService reviewService, ProductRepository productRepository, HttpSession session) {
		this.reviewService = reviewService;
		this.productRepository = productRepository;
	}

	@GetMapping("/{id}")
	public String productDetail(@PathVariable Integer id, Model model, @PathVariable String slug) {
		Product product = productRepository.findByActiveAndSlug(true, slug);
		model.addAttribute("product", product);
		model.addAttribute("reviews", reviewService.getReviewsByProductId(id));
		model.addAttribute("review", new Review()); // for form binding
		return "product-detail";
	}

	@PostMapping("/shopping/{slug}/reviews")
	public String addReview(@PathVariable String slug, @ModelAttribute Review review, HttpSession session) {
		try {
			Product product = productRepository.findByActiveAndSlug(true, slug);
			Account currentUser = (Account) session.getAttribute("account");

			if (currentUser == null) {
				return "redirect:/login";
			}

			review.setProduct(product);
			review.setAccount(currentUser);
			reviewService.saveReview(review);

			return "redirect:/shopping/" + slug;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return "product-detail";
	}
}