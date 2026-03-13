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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lunarstore.model.Category;
import com.lunarstore.model.Product;
import com.lunarstore.repository.CategoryRepository;
import com.lunarstore.repository.ProductRepository;

@Controller
public class HomeController {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductRepository productRepository;
	
	@RequestMapping("/")
	public String home(Model model, @RequestParam("page") Optional<Integer> page) {
		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.ASC, "id"));
		Pageable pageable = PageRequest.of(page.orElse(0), 8, Sort.by(Direction.DESC, "id"));
		Page<Product> products = productRepository.findByActive(true, pageable);
		
		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
		model.addAttribute("page", page.orElse(0) + 1);
		model.addAttribute("totalPages", products.getTotalPages());
		model.addAttribute("active", "home");
		return "home";
	}
	
	//Search
	@RequestMapping("/search")
	public String search(Model model, @RequestParam("keyword") String keyword, @RequestParam("page") Optional<Integer> page) {
		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.ASC, "id"));
		Pageable pageable = PageRequest.of(page.orElse(0), 8, Sort.by(Direction.DESC, "id"));
		Page<Product> products = productRepository.findByKeyword("%" + keyword + "%", pageable);
		
		model.addAttribute("categories", categories);
		model.addAttribute("products", products);
		model.addAttribute("page", page.orElse(0) + 1);
		model.addAttribute("totalPages", products.getTotalPages());
		return "search";
	}
	
}
