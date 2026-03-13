package com.lunarstore.controller.admin;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lunarstore.model.Account;
import com.lunarstore.model.Category;
import com.lunarstore.model.Favourite;
import com.lunarstore.model.Product;
import com.lunarstore.repository.CategoryRepository;
import com.lunarstore.repository.FavouriteRepository;
import com.lunarstore.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class FavouriteController {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	FavouriteRepository favouriteRepository;
	@Autowired
	HttpSession session;

	@RequestMapping("/favourite")
	public String index(Model model, @RequestParam("page") Optional<Integer> page) {
		List<Category> categories = categoryRepository.findByActive(true, Sort.by(Direction.ASC, "id"));
		Account account = (Account) session.getAttribute("account");
		Pageable pageable = PageRequest.of(page.orElse(0), 8, Sort.by(Direction.DESC, "id"));
		Page<Favourite> favourites = favouriteRepository.findByAccount(account, pageable);
		model.addAttribute("categories", categories);
		model.addAttribute("favourites", favourites.getContent());
		model.addAttribute("page", page.orElse(0) + 1);
		model.addAttribute("totalPages", favourites.getTotalPages());
		model.addAttribute("active", "favourite");
		return "favourite";
	}

	@RequestMapping("/favourite/add/{slug}")
	public String add(Model model, @PathVariable("slug") String slug) {
		Account account = (Account) session.getAttribute("account");
		Product product = productRepository.findByActiveAndSlug(true, slug);
		boolean exists = favouriteRepository.existsByAccountAndProduct(account, product);

		if (!exists) {
			Favourite favourite = new Favourite();
			favourite.setAccount(account);
			favourite.setProduct(product);
			favouriteRepository.save(favourite);
		}
		return "redirect:/favourite";
	}
}
