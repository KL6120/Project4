package com.lunarstore.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.lunarstore.model.Category;
import com.lunarstore.model.Product;
import com.lunarstore.model.Review;
import com.lunarstore.repository.CategoryRepository;
import com.lunarstore.repository.ProductRepository;
import com.lunarstore.service.ReviewService;

@Controller
public class ProductClientController {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	private ReviewService revSer;

	@GetMapping("/shopping")
	public String index(Model model, @RequestParam("page") Optional<Integer> page) {
		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.ASC, "id"));
		Pageable pageable = PageRequest.of(page.orElse(0), 8, Sort.by(Direction.DESC, "id"));
		Page<Product> products = productRepository.findByActive(true, pageable);

		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
		model.addAttribute("page", page.orElse(0) + 1);
		model.addAttribute("totalPages", products.getTotalPages());
		model.addAttribute("active", "product");
		return "product";
	}

	@GetMapping("/shopping/{slug}")
	public String productDetail(Model model, @PathVariable String slug,
			@RequestParam(required = false) Long id) {
		Product product = productRepository.findByActiveAndSlug(true, slug);

		if (product == null) {
			return "redirect:/shopping"; // fallback if product not found
		}

		// Use the product entity directly for reviews
		model.addAttribute("reviews", revSer.getReviewsByProduct(product));
		model.addAttribute("review", new Review()); // for form binding
		model.addAttribute("active", "product");
		model.addAttribute("product", product);

		List<Product> relateProducts = productRepository.findByActiveAndCategory(true, product.getCategory(),
				Sort.by(Direction.DESC, "id"));
		model.addAttribute("relateProducts", relateProducts);

		return "product-detail";
	}

}
