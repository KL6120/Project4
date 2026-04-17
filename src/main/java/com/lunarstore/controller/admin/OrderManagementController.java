package com.lunarstore.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lunarstore.model.Account;
import com.lunarstore.model.CartDetail;
import com.lunarstore.model.Order;
import com.lunarstore.repository.OrderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderManagementController {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	HttpSession session;

	@RequestMapping("/admin/orders")
	public String index(Model model) {
		List<Order> orders = orderRepository.findAll(Sort.by(Direction.DESC, "id"));
		model.addAttribute("orders", orders);
		model.addAttribute("active", "order");
		return "/admin/orders/list";
	}

	@RequestMapping("/admin/orders/updateStatus/{id}/{status}")
	public String updateStatus(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id,
			@PathVariable("status") Optional<Integer> status) {
		Order order = orderRepository.findById(id).orElse(null);

		if (order != null && !status.isEmpty()) {
			order.setStatus(status.get());
			orderRepository.save(order);
			redirectAttributes.addFlashAttribute("message", "Cập nhật trạng thái thành công");
		}
		return "redirect:/admin/orders";
	}

	@RequestMapping("/admin/orders/detail/{id}")
	public String orderDetail(Model model, @PathVariable("id") Integer id) {
		Order order = orderRepository.findById(id).orElse(null);
		model.addAttribute("order", order);
		model.addAttribute("active", "order");
		return "/admin/orders/detail";
	}

	@RequestMapping("/admin/orders/cancel/{id}")
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
}
