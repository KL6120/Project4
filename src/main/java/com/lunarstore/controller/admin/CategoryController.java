package com.lunarstore.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lunarstore.model.Category;
import com.lunarstore.model.Product;
import com.lunarstore.repository.CategoryRepository;
import com.lunarstore.repository.ProductRepository;

import jakarta.validation.Valid;

@Controller
public class CategoryController {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductRepository productRepository;

	@GetMapping("admin/categories")
	public String index(Model model) {
		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.DESC, "id"));
		model.addAttribute("active", "category");
		model.addAttribute("categories", categories);
		return "admin/category/list";
	}

	// Gọi form thêm mới
	@GetMapping("admin/categories/add")
	public String add(Model model) {
		model.addAttribute("active", "category");
		model.addAttribute("category", new Category());
		return "admin/category/add";
	}

	// Insert dữ liệu
	@PostMapping("admin/categories/add")
	public String insert(Model model, @Valid @ModelAttribute Category category, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("active", "category");
			model.addAttribute("message", "Vui lòng nhập đầy đủ thông tin");
			return "admin/category/add";
		}

		Category cate = categoryRepository.findByActiveAndSlug(true, category.getSlug());
		if (cate != null) {
			model.addAttribute("active", "category");
			model.addAttribute("message", "Tên loại đã tồn tại");
			model.addAttribute("slugError", "Tên slug đã tồn tại");
			return "admin/category/add";
		}
		category.setActive(true);
		category.setParentId(0);
		categoryRepository.save(category);
		return "redirect:/admin/categories";
	}

	// Gọi form cập nhật
	@GetMapping("admin/categories/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) {
			return "redirect:/admin/categories";
		}
		model.addAttribute("active", "category");
		model.addAttribute("category", category);
		return "admin/category/edit";
	}

	// Update dữ liệu
	@PostMapping("admin/categories/update")
	public String update(Model model, @Valid @ModelAttribute Category category, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("active", "category");
			model.addAttribute("message", "Vui lòng nhập đầy đủ thông tin");
			return "admin/category/edit";
		}

		Category cate = categoryRepository.findByActiveAndSlug(true, category.getSlug());
		if (cate != null && cate.getId() != category.getId()) {
			model.addAttribute("active", "category");
			model.addAttribute("message", "Tên loại đã tồn tại");
			model.addAttribute("slugError", "Tên slug đã tồn tại");
			return "admin/category/edit";
		}
		category.setActive(true);
		category.setParentId(0);
		categoryRepository.save(category);
		return "redirect:/admin/categories";
	}

	// Delete
	@GetMapping("admin/categories/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) {
		Category category = categoryRepository.findById(id).orElse(null);
		if (category == null) {
			return "redirect:/admin/categories";
		}
		if (category.getProducts() != null && !category.getProducts().isEmpty()) {
			for (Product product : category.getProducts()) {
				product.setActive(false);
				productRepository.save(product);
			}
			category.setActive(false);
			categoryRepository.save(category);
		} else {
			categoryRepository.delete(category);
		}

		return "redirect:/admin/categories";
	}
}
