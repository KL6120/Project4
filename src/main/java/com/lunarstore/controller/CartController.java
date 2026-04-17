package com.lunarstore.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lunarstore.model.Account;
import com.lunarstore.model.CartDetail;
import com.lunarstore.model.Order;
import com.lunarstore.model.OrderDetail;
import com.lunarstore.model.Product;
import com.lunarstore.model.Voucher;
import com.lunarstore.repository.CartDetailRepository;
import com.lunarstore.repository.OrderDetailRepository;
import com.lunarstore.repository.OrderRepository;
import com.lunarstore.repository.ProductRepository;
import com.lunarstore.repository.VoucherRepository;
import com.lunarstore.service.GHNService;
import com.lunarstore.service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {
	@Autowired
	CartDetailRepository cartDetailRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	HttpSession session;
	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@Autowired
	GHNService ghnService;
	@Autowired
	VNPayService vnpayService;
	@Autowired
	HttpServletRequest request;

	@RequestMapping("/carts/add")
	public String add(RedirectAttributes redirectAttributes, @RequestParam("quantity") Integer quantity,
			@RequestParam("productSlug") String productSlug) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		Product product = productRepository.findByActiveAndSlug(true, productSlug);
		if (product == null) {
			return "redirect:/carts";
		}

		CartDetail cartDetail = cartDetailRepository.findByAccountAndProduct(account, product);
		if (cartDetail != null) {
			cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
		} else {
			cartDetail = new CartDetail();
			cartDetail.setAccount(account);
			cartDetail.setProduct(product);
			cartDetail.setQuantity(quantity);
		}
		if (cartDetail.getQuantity() > product.getQuantity()) {
			redirectAttributes.addFlashAttribute("errorQuantity", "Số lượng không đủ");
			return "redirect:/shopping/" + productSlug;
		}
		cartDetailRepository.save(cartDetail);
		return "redirect:/carts";
	}

	@RequestMapping("/carts")
	public String carts(Model model) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		List<CartDetail> cartDetails = cartDetailRepository.findByAccount(account);
		List<Integer> ids = new ArrayList<Integer>();
		List<Order> orders = orderRepository.findByAccount(account);

		for (Order order : orders) {
			if (order.getVoucher() != null) {
				ids.add(order.getVoucher().getId());
			}
		}

		List<Voucher> vouchers = voucherRepository.findVoucherValidList(ids,
				Sort.by(Direction.DESC, "discountPercent"));
		model.addAttribute("cartDetails", cartDetails);
		model.addAttribute("vouchers", vouchers);

		return "cart";
	}

	@RequestMapping("/carts/remove/{id}")
	public String remove(Model model, @PathVariable("id") Integer id) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		CartDetail cartDetail = cartDetailRepository.findById(id).orElse(null);
		if (account.getId() != cartDetail.getAccount().getId()) {
			return "redirect:/carts";
		}
		cartDetailRepository.delete(cartDetail);

		return "redirect:/carts";
	}

	@RequestMapping("/carts/update")
	public String update(RedirectAttributes redirectAttributes, @RequestParam("id") Integer id,
			@RequestParam("quantity") Integer quantity) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		CartDetail cartDetail = cartDetailRepository.findById(id).orElse(null);
		if (account.getId() != cartDetail.getAccount().getId()) {
			return "redirect:/carts";
		}
		if (cartDetail != null) {
			if (quantity > cartDetail.getProduct().getQuantity()) {
				redirectAttributes.addFlashAttribute("errorQuantity", cartDetail.getId());
				cartDetail.setQuantity(cartDetail.getProduct().getQuantity());
			} else {
				cartDetail.setQuantity(quantity);
			}
			cartDetailRepository.save(cartDetail);
		}

		return "redirect:/carts";
	}

	@RequestMapping("/carts/checkout")
	public String checkout(@RequestParam("ids") String[] ids, @RequestParam("provinceSelect") int provinceSelect,
			@RequestParam("districtSelect") int districtSelect, @RequestParam("wardSelect") String wardSelect,
			@RequestParam("fullAddress") String fullAddress, @RequestParam("voucher") Integer voucherId,
			@RequestParam("paymentMethod") String paymentMethod) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		int total = 0;
		int discount = 0;
		for (String id : ids) {
			CartDetail cartDetail = cartDetailRepository.findById(Integer.parseInt(id)).orElse(null);
			total += cartDetail.getQuantity() * cartDetail.getProduct().getPrice();
		}
		Voucher voucher = voucherRepository.findById(voucherId).orElse(null);
		if (voucher != null) {
			discount = total * voucher.getDiscountPercent() / 100;
		}
		int feeShip = ghnService.getShippingFee(districtSelect, wardSelect);
		Order order = new Order();
		order.setCreatedAt(new Date());
		order.setAccount(account);
		order.setVoucher(voucher);
		order.setDiscount(discount);
		order.setFeeShip(feeShip);
		order.setShipAddress(fullAddress);
		order.setTotal(total);
		order.setStatus(1);

		if (paymentMethod.equals("COD")) {
			order.setPaymentMethod(0);
			order.setPaymentStatus(0);

			orderRepository.save(order);
			for (String id : ids) {
				CartDetail cartDetail = cartDetailRepository.findById(Integer.parseInt(id)).orElse(null);
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setProduct(cartDetail.getProduct());
				orderDetail.setPrice(cartDetail.getProduct().getPrice());
				orderDetail.setQuantity(cartDetail.getQuantity());
				orderDetailRepository.save(orderDetail);
				cartDetailRepository.delete(cartDetail);
			}
			return "redirect:/orders/detail/" + order.getId();
		} else {
			order.setPaymentMethod(1);
			order.setPaymentMethod(1);
			orderRepository.save(order);
			for (String id : ids) {
				CartDetail cartDetail = cartDetailRepository.findById(Integer.parseInt(id)).orElse(null);
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setOrder(order);
				orderDetail.setProduct(cartDetail.getProduct());
				orderDetail.setPrice(cartDetail.getProduct().getPrice());
				orderDetail.setQuantity(cartDetail.getQuantity());
				orderDetailRepository.save(orderDetail);
				cartDetailRepository.delete(cartDetail);
			}
			String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			String vnpayUrl = vnpayService.createOrder((total + feeShip - discount), order.getId().toString(), baseUrl);
			return "redirect:" + vnpayUrl;
		}
	}

	@GetMapping("/vnpay-payment")
	public String vnpayPayment(Model model, HttpServletRequest request) {
		Account account = (Account) session.getAttribute("account");
		if (account == null) {
			return "redirect:/login";
		}
		int paymentStatus = vnpayService.orderReturn(request);
		String orderInfo = request.getParameter("vnp_OrderInfo");
		if (paymentStatus == 1) {
			return "redirect:/orders/detail/" + orderInfo;
		} else {
			Order order = orderRepository.findById(Integer.parseInt(orderInfo)).orElse(null);
			if (order != null) {
				for (OrderDetail orderDetail : order.getOrderDetails()) {
					CartDetail cartDetail = new CartDetail();
					cartDetail.setAccount(account);
					cartDetail.setProduct(orderDetail.getProduct());
					cartDetail.setQuantity(orderDetail.getQuantity());
					cartDetailRepository.save(cartDetail);
					orderDetailRepository.delete(orderDetail);
				}
			}
			orderRepository.delete(order);
			return "redirect:/orders/error/";
		}
	}
}
