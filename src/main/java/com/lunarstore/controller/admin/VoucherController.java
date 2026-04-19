package com.lunarstore.controller.admin;

import java.util.Date;
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

import com.lunarstore.model.Voucher;
import com.lunarstore.repository.VoucherRepository;

import jakarta.validation.Valid;

@Controller
public class VoucherController {
	@Autowired
	VoucherRepository voucherRepository;

	@GetMapping("/admin/vouchers")
	public String index(Model model) {

		List<Voucher> vouchers = voucherRepository.findAll(Sort.by(Direction.DESC, "id"));
		model.addAttribute("vouchers", vouchers);
		model.addAttribute("active", "vouchers");
		return "admin/vouchers/list";
	}

	// Gọi form thêm mới
	@GetMapping("/admin/vouchers/add")
	public String add(Model model) {
		model.addAttribute("voucher", new Voucher());
		model.addAttribute("active", "vouchers");
		return "admin/vouchers/add";
	}

	// Insert dữ liệu
	@PostMapping("/admin/vouchers/add")
	public String add(Model model, @Valid @ModelAttribute Voucher voucher, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng kiểm tra thông tin nhập");
		}
		Voucher voucher2 = voucherRepository.findByCode(voucher.getCode());

		try {
			if (voucher2 == null) {
				voucher.setCreatedAt(new Date());
				voucher.setActived(true);
				voucherRepository.save(voucher);
				return "redirect:/admin/vouchers";
			} else {
				model.addAttribute("codeError", "Mã giảm giá đã tồn tại");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("active", "voucher");
		return "admin/vouchers/add";
	}

	// Gọi form edit
	@GetMapping("/admin/vouchers/edit/{id}")
	public String edit(Model model, @PathVariable("id") Integer id) {
		Voucher voucher = voucherRepository.findById(id).orElse(null);
		model.addAttribute("voucher", voucher);
		model.addAttribute("active", "voucher");
		return "admin/vouchers/edit";
	}

	// update dữ liệu
	@PostMapping("/admin/vouchers/update")
	public String update(Model model, @Valid @ModelAttribute Voucher voucher, Errors errors) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng kiểm tra thông tin nhập");
		}
		try {
			Voucher oldVoucher = voucherRepository.findById(voucher.getId()).orElseThrow();

			oldVoucher.setDiscountPercent(voucher.getDiscountPercent());
			oldVoucher.setQuantity(voucher.getQuantity());
			oldVoucher.setStartedAt(voucher.getStartedAt());
			oldVoucher.setEndAt(voucher.getEndAt());
			voucherRepository.save(oldVoucher);
			return "redirect:/admin/vouchers";

		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("active", "voucher");
		return "admin/vouchers/edit";
	}

	// Gọi form active or deactive
	@GetMapping("/admin/vouchers/active/{id}")
	public String active(Model model, @PathVariable("id") Integer id) {
		Voucher voucher = voucherRepository.findById(id).orElse(null);
		voucher.setActived(!voucher.getActived());
		voucherRepository.save(voucher);
		model.addAttribute("active", "voucher");
		return "redirect:/admin/vouchers";
	}

	// Gọi form delete
	@GetMapping("/admin/vouchers/delete/{id}")
	public String delete(Model model, @PathVariable("id") Integer id) {
		Voucher voucher = voucherRepository.findById(id).orElse(null);
		if (!voucher.getOrders().isEmpty()) {
			voucher.setActived(false);
			voucherRepository.save(voucher);
		} else {
			voucherRepository.delete(voucher);
		}
		return "redirect:/admin/vouchers";
	}
}
