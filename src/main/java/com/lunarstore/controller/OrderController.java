package com.lunarstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lunarstore.model.Account;
import com.lunarstore.model.Order;
import com.lunarstore.repository.OrderDetailRepository;
import com.lunarstore.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository; 
	@Autowired
	HttpSession session;
	
	@RequestMapping("/orders")
	public String orders(Model model) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		
		return "order";
	}
	
	@RequestMapping("/orders/success/{id}")
	public String orderSuccess(Model model, @PathVariable("id") Integer id) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		Order order = orderRepository.findById(id).orElse(null);
		if(order == null || order.getAccount().getId() != account.getId()) {
			return "redirect:/404";
		}
		model.addAttribute("order", order);
		return "order-success";
	}
}
