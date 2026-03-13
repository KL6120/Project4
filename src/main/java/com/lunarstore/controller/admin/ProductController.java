package com.lunarstore.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lunarstore.model.Category;
import com.lunarstore.model.Product;
import com.lunarstore.repository.CategoryRepository;
import com.lunarstore.repository.ProductRepository;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@Controller
public class ProductController {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ServletContext servletContext;

	@RequestMapping("admin/products")
	public String index(Model model) {
		List<Product> products = productRepository.findByActive(true);
		model.addAttribute("products", products);
		model.addAttribute("active", "product");

		return "admin/products/list";
	}

	// Gọi form thêm mới
	@RequestMapping("admin/products/add")
	public String add(Model model) {
		model.addAttribute("active", "product");
		model.addAttribute("product", new Product());
		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.DESC, "id"));
		model.addAttribute("categories", categories);
		return "admin/products/add";
	}

	// Insert dữ liệu
	@PostMapping("admin/products/add")
	public String add(Model model, @Valid @ModelAttribute Product product, Errors errors,
			@RequestParam("imageProduct") MultipartFile image) {
		model.addAttribute("active", "product");
		if (errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng nhập đầy đủ thông tin");
		}
		try {
			if (!image.isEmpty()) {
				String fileName = image.getOriginalFilename();
				String filePath = servletContext.getRealPath("/images/" + fileName);
				if (!Files.exists(Path.of(filePath))) {
					Files.createDirectories(Path.of(filePath));
				}
				File file = new File(filePath);
				image.transferTo(file);
				product.setImage(fileName);
			}
			Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
			product.setCategory(category);
			product.setActive(true);
			productRepository.save(product);
			return "redirect:/admin/products";
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.DESC, "id"));
		model.addAttribute("categories", categories);
		return "admin/products/add";
	}
	
	//Gọi form cập nhật
	@RequestMapping("admin/products/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("active", "product");
		Product product = productRepository.findById(id).orElse(null);
		model.addAttribute("product", product);
		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.DESC, "id"));
		model.addAttribute("categories", categories);
		return "admin/products/edit";
	}
	
	//Update dữ liệu
	@PostMapping("admin/products/update")
	public String update(Model model, @Valid @ModelAttribute Product product, Errors errors,
			@RequestParam("imageProduct") MultipartFile image) {
		model.addAttribute("active", "product");
		if (errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng nhập đầy đủ thông tin");
		}
		try {
			if (!image.isEmpty()) {
				String fileName = image.getOriginalFilename();
				String filePath = servletContext.getRealPath("/images/" + fileName);
				if (!Files.exists(Path.of(filePath))) {
					Files.createDirectories(Path.of(filePath));
				}
				File file = new File(filePath);
				image.transferTo(file);
				product.setImage(fileName);
			}
			Category category = categoryRepository.findById(product.getCategory().getId()).orElse(null);
			product.setCategory(category);
			product.setActive(true);
			productRepository.save(product);
			return "redirect:/admin/products";
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.DESC, "id"));
		model.addAttribute("categories", categories);
		return "admin/products/add";
	}
	
	//Delete
		@RequestMapping("admin/products/delete/{id}")
		public String delete(Model model, @PathVariable("id") Integer id) {
			model.addAttribute("active", "product");
			Product product = productRepository.findById(id).orElse(null);
			product.setActive(false);
			productRepository.save(product);
			return "redirect:/admin/products";
		}
		
}
