//package com.UpTrend.ClothesShopping.controller;
//
//import java.security.Principal;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.UpTrend.ClothesShopping.model.Cart;
//import com.UpTrend.ClothesShopping.model.Category;
//import com.UpTrend.ClothesShopping.model.OrderRequest;
//import com.UpTrend.ClothesShopping.model.ProductOrder;
//import com.UpTrend.ClothesShopping.model.UserDtls;
//import com.UpTrend.ClothesShopping.service.CartService;
//import com.UpTrend.ClothesShopping.service.CategoryService;
//import com.UpTrend.ClothesShopping.service.OrderService;
//import com.UpTrend.ClothesShopping.service.UserService;
//import com.UpTrend.ClothesShopping.util.CommonUtil;
//import com.UpTrend.ClothesShopping.util.OrderStatus;
//
//import jakarta.servlet.http.HttpSession;
////
////@Controller
////@RequestMapping("/user")
////public class UserController {
////	@Autowired
////	private UserService userService;
////	@Autowired
////	private CategoryService categoryService;
////
////	@Autowired
////	private CartService cartService;
////
////	@Autowired
////	private OrderService orderService;
////
////	@Autowired
////	private CommonUtil commonUtil;
////
////	@Autowired
////	private PasswordEncoder passwordEncoder;
////
////
////	@GetMapping("/")
////	public String home() {
////		return "user/home";
////	}
////
////	@ModelAttribute
////	public void getUserDetails(Principal p, Model m) {
////		if (p != null) {
////			String email = p.getName();
////			UserDtls userDtls = userService.getUserByEmail(email);
////			m.addAttribute("user", userDtls);
////			Integer countCart = cartService.getCountCart(userDtls.getId());
////			m.addAttribute("countCart", countCart);
////		}
////
////		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
////		m.addAttribute("categorys", allActiveCategory);
////	}
////
////	@GetMapping("/addCart")
////	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
////		Cart saveCart = cartService.saveCart(pid, uid);
////
////		if (ObjectUtils.isEmpty(saveCart)) {
////			session.setAttribute("errorMsg", "Product add to cart failed");
////		} else {
////			session.setAttribute("succMsg", "Product added to cart");
////		}
////		return "redirect:/product/" + pid;
////	}
////
////	@GetMapping("/cart")
////	public String loadCartPage(Principal p, Model m) {
////
////		UserDtls user = getLoggedInUserDetails(p);
////		List<Cart> carts = cartService.getCartsByUser(user.getId());
////		m.addAttribute("carts", carts);
////		if (carts.size() > 0) {
////			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
////			m.addAttribute("totalOrderPrice", totalOrderPrice);
////		}
////		return "/user/cart";
////
////	}
////
////	@GetMapping("/cartQuantityUpdate")
////	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
////		cartService.updateQuantity(sy, cid);
////		return "redirect:/user/cart";
////	}
////
////	private UserDtls getLoggedInUserDetails(Principal p) {
////		String email = p.getName();
////		UserDtls userDtls = userService.getUserByEmail(email);
////		return userDtls;
////	}
////
////	@GetMapping("/orders")
////	public String orderPage(Principal p, Model m) {
////	    UserDtls user = getLoggedInUserDetails(p);
////	    List<Cart> carts = cartService.getCartsByUser(user.getId());
////	    m.addAttribute("carts", carts);
////
////	    if (!carts.isEmpty()) {
////	        Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
////	        double gstRate = 0.18;
////	        double deliveryFee = 30.0;
////
////	        double gstAmount = orderPrice * gstRate;
////	        double totalOrderPrice = orderPrice + gstAmount + deliveryFee;
////
////	     // Round totalOrderPrice to nearest integer
////	        long roundedTotal = Math.round(totalOrderPrice);
////
////	        m.addAttribute("orderPrice", orderPrice);
////	        m.addAttribute("gstAmount", gstAmount);
////	        m.addAttribute("deliveryFee", deliveryFee);
////	        m.addAttribute("totalOrderPrice", roundedTotal);
////	    }
////
////	    return "/user/order";
////	}
////
//////	@PostMapping("/save-order")
//////	public String saveOrder(@ModelAttribute OrderRequest request, Principal p) throws Exception {
//////		// System.out.println(request);
//////		UserDtls user = getLoggedInUserDetails(p);
//////		orderService.saveOrder(user.getId(), request);
//////
//////		return "redirect:/user/success";
//////	}
////
////
////	@PostMapping("/save-order")
////	public String saveOrder(
////			@ModelAttribute OrderRequest request,
////			@RequestParam(value = "razorpayPaymentId", required = false) String razorpayPaymentId,
////			@RequestParam(value = "razorpayOrderId", required = false) String razorpayOrderId,
////			Principal p,
////			Model model,
////			HttpSession session) throws Exception {
////
////		UserDtls user = getLoggedInUserDetails(p);
////
////		// Log Razorpay payment details if payment type is online
////		if ("online".equalsIgnoreCase(request.getPaymentType())) {
////			if (razorpayPaymentId == null || razorpayOrderId == null) {
////				session.setAttribute("errorMsg", "Payment failed or incomplete.");
////				return "redirect:/user/orders";
////			}
////
////			System.out.println("Online payment successful");
////			System.out.println("Razorpay Payment ID: " + razorpayPaymentId);
////			System.out.println("Razorpay Order ID: " + razorpayOrderId);
////
////
////			// Optional: Call Razorpay signature validation here for security
////		}
////
////		// Save the order
////		orderService.saveOrder(user.getId(), request);
////
////		return "redirect:/user/success";
////	}
////
////
////	@GetMapping("/success")
////	public String loadSuccess() {
////		return "/user/success";
////	}
////
////	@GetMapping("/user-orders")
////	public String myOrder(Model m, Principal p) {
////		UserDtls loginUser = getLoggedInUserDetails(p);
////		List<ProductOrder> orders = orderService.getOrdersByUser(loginUser.getId());
////		m.addAttribute("orders", orders);
////		return "/user/my_orders";
////	}
////
////	@GetMapping("/update-status")
////	public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {
////
////		OrderStatus[] values = OrderStatus.values();
////		String status = null;
////
////		for (OrderStatus orderSt : values) {
////			if (orderSt.getId().equals(st)) {
////				status = orderSt.getName();
////			}
////		}
////
////		ProductOrder updateOrder = orderService.updateOrderStatus(id, status);
////
////		try {
////			commonUtil.sendMailForProductOrder(updateOrder, status);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////
////		if (!ObjectUtils.isEmpty(updateOrder)) {
////			session.setAttribute("succMsg", "Status Updated");
////		} else {
////			session.setAttribute("errorMsg", "status not updated");
////		}
////		return "redirect:/user/user-orders";
////	}
////
////	@GetMapping("/profile")
////	public String profile() {
////		return "/user/profile";
////	}
////
////	@PostMapping("/update-profile")
////	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session) {
////		UserDtls updateUserProfile = userService.updateUserProfile(user, img);
////		if (ObjectUtils.isEmpty(updateUserProfile)) {
////			session.setAttribute("errorMsg", "Profile not updated");
////		} else {
////			session.setAttribute("succMsg", "Profile Updated");
////		}
////		return "redirect:/user/profile";
////	}
////
////	@PostMapping("/change-password")
////	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
////			HttpSession session) {
////		UserDtls loggedInUserDetails = getLoggedInUserDetails(p);
////
////		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());
////
////		if (matches) {
////			String encodePassword = passwordEncoder.encode(newPassword);
////			loggedInUserDetails.setPassword(encodePassword);
////			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
////			if (ObjectUtils.isEmpty(updateUser)) {
////				session.setAttribute("errorMsg", "Password not updated !! Error in server");
////			} else {
////				session.setAttribute("succMsg", "Password Updated sucessfully");
////			}
////		} else {
////			session.setAttribute("errorMsg", "Current Password incorrect");
////		}
////
////		return "redirect:/user/profile";
////	}
////
////}
//
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private CategoryService categoryService;
//
//	@Autowired
//	private CartService cartService;
//
//	@Autowired
//	private OrderService orderService;
//
//	@Autowired
//	private CommonUtil commonUtil;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//
//	@GetMapping("/")
//	public String home() {
//		return "user/home";
//	}
//
//	@ModelAttribute
//	public void getUserDetails(Principal p, Model m) {
//		if (p != null) {
//			String email = p.getName();
//			UserDtls userDtls = userService.getUserByEmail(email);
//			m.addAttribute("user", userDtls);
//			Integer countCart = cartService.getCountCart(userDtls.getId());
//			m.addAttribute("countCart", countCart);
//		}
//
//		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
//		m.addAttribute("categorys", allActiveCategory);
//	}
//
//	@GetMapping("/addCart")
//	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
//		Cart saveCart = cartService.saveCart(pid, uid);
//
//		if (ObjectUtils.isEmpty(saveCart)) {
//			session.setAttribute("errorMsg", "Product add to cart failed");
//		} else {
//			session.setAttribute("succMsg", "Product added to cart");
//		}
//		return "redirect:/product/" + pid;
//	}
//
//	@GetMapping("/cart")
//	public String loadCartPage(Principal p, Model m) {
//
//		UserDtls user = getLoggedInUserDetails(p);
//		List<Cart> carts = cartService.getCartsByUser(user.getId());
//		m.addAttribute("carts", carts);
//		if (carts.size() > 0) {
//			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
//			m.addAttribute("totalOrderPrice", totalOrderPrice);
//		}
//		return "/user/cart";
//	}
//
//	@GetMapping("/cartQuantityUpdate")
//	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
//		cartService.updateQuantity(sy, cid);
//		return "redirect:/user/cart";
//	}
//
//	private UserDtls getLoggedInUserDetails(Principal p) {
//		String email = p.getName();
//		UserDtls userDtls = userService.getUserByEmail(email);
//		return userDtls;
//	}
//
//	@GetMapping("/orders")
//	public String orderPage(Principal p, Model m) {
//		UserDtls user = getLoggedInUserDetails(p);
//		List<Cart> carts = cartService.getCartsByUser(user.getId());
//		m.addAttribute("carts", carts);
//		if (carts.size() > 0) {
//			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
//			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
//			m.addAttribute("orderPrice", orderPrice);
//			m.addAttribute("totalOrderPrice", totalOrderPrice);
//		}
//		return "/user/order";
//	}
//
//	@PostMapping("/save-order")
//	public String saveOrder(
//			@ModelAttribute OrderRequest request,
//			@RequestParam(value = "razorpayPaymentId", required = false) String razorpayPaymentId,
//			@RequestParam(value = "razorpayOrderId", required = false) String razorpayOrderId,
//			Principal p,
//			Model model,
//			HttpSession session) throws Exception {
//
//		UserDtls user = getLoggedInUserDetails(p);
//
//		// Log Razorpay payment details if payment type is online
//		if ("online".equalsIgnoreCase(request.getPaymentType())) {
//			if (razorpayPaymentId == null || razorpayOrderId == null) {
//				session.setAttribute("errorMsg", "Payment failed or incomplete.");
//				return "redirect:/user/orders";
//			}
//
//			System.out.println("Online payment successful");
//			System.out.println("Razorpay Payment ID: " + razorpayPaymentId);
//			System.out.println("Razorpay Order ID: " + razorpayOrderId);
//
//
//			// Optional: Call Razorpay signature validation here for security
//		}
//
//		// Save the order
//		orderService.saveOrder(user.getId(), request);
//
//		return "redirect:/user/success";
//	}
//
//
//	@GetMapping("/success")
//	public String loadSuccess() {
//		return "/user/success";
//	}
//
//	@GetMapping("/user-orders")
//	public String myOrder(Model m, Principal p) {
//		UserDtls loginUser = getLoggedInUserDetails(p);
//		List<ProductOrder> orders = orderService.getOrdersByUser(loginUser.getId());
//		m.addAttribute("orders", orders);
//		return "/user/my_orders";
//	}
//
//	@GetMapping("/update-status")
//	public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {
//
//		OrderStatus[] values = OrderStatus.values();
//		String status = null;
//
//		for (OrderStatus orderSt : values) {
//			if (orderSt.getId().equals(st)) {
//				status = orderSt.getName();
//			}
//		}
//
//		ProductOrder updateOrder = orderService.updateOrderStatus(id, status);
//
//		try {
//			commonUtil.sendMailForProductOrder(updateOrder, status);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (!ObjectUtils.isEmpty(updateOrder)) {
//			session.setAttribute("succMsg", "Status Updated");
//		} else {
//			session.setAttribute("errorMsg", "status not updated");
//		}
//		return "redirect:/user/user-orders";
//	}
//
//	@GetMapping("/profile")
//	public String profile() {
//		return "/user/profile";
//	}
//
//	@PostMapping("/update-profile")
//	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session) {
//		UserDtls updateUserProfile = userService.updateUserProfile(user, img);
//		if (ObjectUtils.isEmpty(updateUserProfile)) {
//			session.setAttribute("errorMsg", "Profile not updated");
//		} else {
//			session.setAttribute("succMsg", "Profile Updated");
//		}
//		return "redirect:/user/profile";
//	}
//
//	@PostMapping("/change-password")
//	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
//								 HttpSession session) {
//		UserDtls loggedInUserDetails = getLoggedInUserDetails(p);
//
//		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());
//
//		if (matches) {
//			String encodePassword = passwordEncoder.encode(newPassword);
//			loggedInUserDetails.setPassword(encodePassword);
//			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
//			if (ObjectUtils.isEmpty(updateUser)) {
//				session.setAttribute("errorMsg", "Password not updated !! Error in server");
//			} else {
//				session.setAttribute("succMsg", "Password Updated sucessfully");
//			}
//		} else {
//			session.setAttribute("errorMsg", "Current Password incorrect");
//		}
//
//		return "redirect:/user/profile";
//	}
//
//}
package com.UpTrend.ClothesShopping.controller;

import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.UpTrend.ClothesShopping.model.Cart;
import com.UpTrend.ClothesShopping.model.Category;
import com.UpTrend.ClothesShopping.model.OrderRequest;
import com.UpTrend.ClothesShopping.model.ProductOrder;
import com.UpTrend.ClothesShopping.model.UserDtls;
import com.UpTrend.ClothesShopping.service.CartService;
import com.UpTrend.ClothesShopping.service.CategoryService;
import com.UpTrend.ClothesShopping.service.OrderService;
import com.UpTrend.ClothesShopping.service.UserService;
import com.UpTrend.ClothesShopping.util.CommonUtil;
import com.UpTrend.ClothesShopping.util.OrderStatus;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	@ModelAttribute
	public void getUserDetails(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			UserDtls userDtls = userService.getUserByEmail(email);
			m.addAttribute("user", userDtls);
			Integer countCart = cartService.getCountCart(userDtls.getId());
			m.addAttribute("countCart", countCart);
		}

		List<Category> allActiveCategory = categoryService.getAllActiveCategory();
		m.addAttribute("categorys", allActiveCategory);
	}

	@GetMapping("/addCart")
	public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
		Cart saveCart = cartService.saveCart(pid, uid);

		if (ObjectUtils.isEmpty(saveCart)) {
			session.setAttribute("errorMsg", "Product add to cart failed");
		} else {
			session.setAttribute("succMsg", "Product added to cart");
		}
		return "redirect:/product/" + pid;
	}

	@GetMapping("/cart")
	public String loadCartPage(Principal p, Model m) {

		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/cart";
	}

	@GetMapping("/cartQuantityUpdate")
	public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
		cartService.updateQuantity(sy, cid);
		return "redirect:/user/cart";
	}

	private UserDtls getLoggedInUserDetails(Principal p) {
		String email = p.getName();
		UserDtls userDtls = userService.getUserByEmail(email);
		return userDtls;
	}

	@GetMapping("/orders")
	public String orderPage(Principal p, Model m) {
		UserDtls user = getLoggedInUserDetails(p);
		List<Cart> carts = cartService.getCartsByUser(user.getId());
		m.addAttribute("carts", carts);
		if (carts.size() > 0) {
			Double orderPrice = carts.get(carts.size() - 1).getTotalOrderPrice();
			Double totalOrderPrice = carts.get(carts.size() - 1).getTotalOrderPrice() + 250 + 100;
			m.addAttribute("orderPrice", orderPrice);
			m.addAttribute("totalOrderPrice", totalOrderPrice);
		}
		return "/user/order";
	}

	@PostMapping("/save-order")
	public String saveOrder(
			@ModelAttribute OrderRequest request,
			@RequestParam(value = "razorpayPaymentId", required = false) String razorpayPaymentId,
			@RequestParam(value = "razorpayOrderId", required = false) String razorpayOrderId,
			Principal p,
			Model model,
			HttpSession session) throws Exception {

		UserDtls user = getLoggedInUserDetails(p);

		// Log Razorpay payment details if payment type is online
		if ("online".equalsIgnoreCase(request.getPaymentType())) {
			if (razorpayPaymentId == null || razorpayOrderId == null) {
				session.setAttribute("errorMsg", "Payment failed or incomplete.");
				return "redirect:/user/orders";
			}

			System.out.println("Online payment successful");
			System.out.println("Razorpay Payment ID: " + razorpayPaymentId);
			System.out.println("Razorpay Order ID: " + razorpayOrderId);


			// Optional: Call Razorpay signature validation here for security
		}

		// Save the order
		orderService.saveOrder(user.getId(), request);

		return "redirect:/user/success";
	}


	@GetMapping("/success")
	public String loadSuccess() {
		return "/user/success";
	}

	@GetMapping("/user-orders")
	public String myOrder(Model m, Principal p) {
		UserDtls loginUser = getLoggedInUserDetails(p);
		List<ProductOrder> orders = orderService.getOrdersByUser(loginUser.getId());
		m.addAttribute("orders", orders);
		return "/user/my_orders";
	}

	@GetMapping("/update-status")
	public String updateOrderStatus(@RequestParam Integer id, @RequestParam Integer st, HttpSession session) {

		OrderStatus[] values = OrderStatus.values();
		String status = null;

		for (OrderStatus orderSt : values) {
			if (orderSt.getId().equals(st)) {
				status = orderSt.getName();
			}
		}

		ProductOrder updateOrder = orderService.updateOrderStatus(id, status);

		try {
			commonUtil.sendMailForProductOrder(updateOrder, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!ObjectUtils.isEmpty(updateOrder)) {
			session.setAttribute("succMsg", "Status Updated");
		} else {
			session.setAttribute("errorMsg", "status not updated");
		}
		return "redirect:/user/user-orders";
	}

	@GetMapping("/profile")
	public String profile() {
		return "/user/profile";
	}

	@PostMapping("/update-profile")
	public String updateProfile(@ModelAttribute UserDtls user, @RequestParam MultipartFile img, HttpSession session) {
		UserDtls updateUserProfile = userService.updateUserProfile(user, img);
		if (ObjectUtils.isEmpty(updateUserProfile)) {
			session.setAttribute("errorMsg", "Profile not updated");
		} else {
			session.setAttribute("succMsg", "Profile Updated");
		}
		return "redirect:/user/profile";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String newPassword, @RequestParam String currentPassword, Principal p,
								 HttpSession session) {
		UserDtls loggedInUserDetails = getLoggedInUserDetails(p);

		boolean matches = passwordEncoder.matches(currentPassword, loggedInUserDetails.getPassword());

		if (matches) {
			String encodePassword = passwordEncoder.encode(newPassword);
			loggedInUserDetails.setPassword(encodePassword);
			UserDtls updateUser = userService.updateUser(loggedInUserDetails);
			if (ObjectUtils.isEmpty(updateUser)) {
				session.setAttribute("errorMsg", "Password not updated !! Error in server");
			} else {
				session.setAttribute("succMsg", "Password Updated sucessfully");
			}
		} else {
			session.setAttribute("errorMsg", "Current Password incorrect");
		}

		return "redirect:/user/profile";
	}

}
