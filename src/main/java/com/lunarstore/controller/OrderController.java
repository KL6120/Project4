package com.lunarstore.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lunarstore.model.Account;
import com.lunarstore.model.Order;
import com.lunarstore.repository.OrderDetailRepository;
import com.lunarstore.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class OrderController {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/orders")
	public String orders(Model model, @RequestParam("page") Optional<Integer> page) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		Pageable pageable = PageRequest.of(page.orElse(0), 8, Sort.by(Direction.DESC, "id"));
		Page<Order> orders = orderRepository.findByAccount(account, pageable);

		model.addAttribute("orders", orders);
		model.addAttribute("page", page.orElse(0) + 1);
		model.addAttribute("totalPages", orders.getTotalPages());
		model.addAttribute("active", "home");
		return "order";
	}

	@GetMapping("/orders/detail/{id}")
	public String orderSuccess(Model model, @PathVariable("id") Integer id) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		Order order = orderRepository.findById(id).orElse(null);
		if (order == null || order.getAccount().getId() != account.getId()) {
			return "redirect:/404";
		}
		model.addAttribute("order", order);
		return "order-detail";
	}

	@PostMapping("/orders/cancel/{id}")
	public String orderCancel(Model model, @PathVariable("id") Integer id) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		Order order = orderRepository.findById(id).orElse(null);
		if (order == null || order.getAccount().getId() != account.getId()) {
			return "redirect:/404";
		}
		order.setStatus(6);
		orderRepository.save(order);
		return "redirect:/orders";
	}

	@PostMapping("/orders/receive/{id}")
	public String orderUpdate(Model model, @PathVariable("id") Integer id) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		Order order = orderRepository.findById(id).orElse(null);
		if (order == null || order.getAccount().getId() != account.getId()) {
			return "redirect:/404";
		}
		if (order.getStatus() != 4) {
			return "redirect:/orders";
		}
		order.setStatus(5);
		orderRepository.save(order);
		return "redirect:/orders";
	}
}