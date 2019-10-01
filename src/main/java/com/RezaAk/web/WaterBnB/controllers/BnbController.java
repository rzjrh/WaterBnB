package com.RezaAk.web.WaterBnB.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.RezaAk.web.WaterBnB.models.Pool;
import com.RezaAk.web.WaterBnB.models.Review;
import com.RezaAk.web.WaterBnB.models.Role;
import com.RezaAk.web.WaterBnB.models.User;
import com.RezaAk.web.WaterBnB.services.PoolService;
import com.RezaAk.web.WaterBnB.services.UserService;
import com.RezaAk.web.WaterBnB.validators.UserValidator;

@Controller
public class BnbController {
    private UserService userService;
    private PoolService poolService;
    private UserValidator userValidator;
    private static ArrayList<String> sizes;
    private static ArrayList<String> ratings; 
    
    public BnbController(UserService userService, PoolService poolService, UserValidator userValidator) {
        this.userService = userService;
        this.poolService = poolService;
        this.userValidator = userValidator;
        sizes = new ArrayList<>();
        sizes.add("Small");
        sizes.add("Medium");
        sizes.add("Large");
        ratings = new ArrayList<>();
        ratings.add("1");
        ratings.add("2");
        ratings.add("3");
        ratings.add("4");
        ratings.add("5");
    }
    
    @RequestMapping("/")
    public String home() {
    	return "index";
    }
    
    @RequestMapping(value= {"/login", "/registration"})
    public String home(HttpSession session, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
            session.setAttribute("successReg", false);
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
            session.setAttribute("successReg", false);
        }
    	model.addAttribute("user", new User());
        return "signin";
    }
    
    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session) {
    	userValidator.validate(user, result);
    	if(result.hasErrors()) {
    		return "signin";
    	}
    	else{
    		if(this.userService.adminExists()) {
    			this.userService.saveWithUserRole(user);	
    		}
    		else {
    			this.userService.saveWithSuperAdminRole(user);	
    		}
    		session.setAttribute("successReg", true);
    		return "redirect:/login";
        }
    }
    
    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String email = principal.getName();
        model.addAttribute("currentUser", userService.findByEmail(email).get(0));
        List<User> users = this.userService.findAll();
        model.addAttribute("users", users);
        return "adminPage";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
    	this.userService.deleteUserById(id);
    	return "redirect:/admin";
    }
    
    @PostMapping("/make_admin/{id}")
    public String makeAdmin(@PathVariable("id") long id) {
    	this.userService.makeAdminById(id);
    	return "redirect:/admin";
    }
    
    @RequestMapping("/host/dashboard")
    public String dashboard(@Valid @ModelAttribute("newPool") Pool newPool, Principal principal, Model model, HttpSession session) {
    	session.setAttribute("successReg", false);
        String email = principal.getName();
        User user = userService.findByEmail(email).get(0);
        model.addAttribute("currentUser", user);
        model.addAttribute("pools", poolService.getAllPools());
        model.addAttribute("sizes", sizes);
        return "dashboard";
    }
 
    @RequestMapping(value="/host/dashboard", method=RequestMethod.POST)
    public String addPool(@Valid @ModelAttribute("newPool") Pool newPool, Principal principal, BindingResult result, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email).get(0);
    	if(result.hasErrors()) {
            model.addAttribute("currentUser", user);
            model.addAttribute("pools", this.poolService.getAllPools());
            model.addAttribute("sizes", sizes);
    		return "dashboard";
    	}
    	else {
    		this.poolService.newPool(newPool, user);
    		return "redirect:/host/dashboard";
    	}
    }
    
    @RequestMapping("/redirectHub")
    public String redirectAfterLogin(Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email).get(0);
        Role hostRole = new Role();
        hostRole.setName("ROLE_SUPERADMIN");
        List<Role> roles = user.getRoles();
        for(int i = 0; i < roles.size(); i++) {
        	System.out.println(roles.get(i).getName());
        	if(roles.get(i).getName().equals(hostRole.getName())) {
        		return "redirect:/host/dashboard";
        	}
        }
        return "redirect:/search";
    }
    
    @RequestMapping(value="/search", method=RequestMethod.GET)
    public String searchResults(@RequestParam(value="location", required=false) String searchQuery, Model model, Principal principal) {
    	if(searchQuery == null) {
    		searchQuery = "";
    	}
    	model.addAttribute("searchQuery", searchQuery);
    	model.addAttribute("loggedIn", isLoggedIn(principal));
    	model.addAttribute("pools", this.poolService.getAllPools());
    	return "results";
    }
    
    @RequestMapping(value="/search", method=RequestMethod.POST)
    public String search(@RequestParam(value="location", required=false) String searchQuery) {
    	return "redirect:/search";
    }
    
    @RequestMapping(value={"/pools/{id}", "/host/pools/{id}"})
    public String pool(@PathVariable("id") long id, Model model, Principal principal) {
    	String email = principal.getName();
    	model.addAttribute("pool", this.poolService.getById(id));
    	model.addAttribute("loggedIn", isLoggedIn(principal));
    	model.addAttribute("currentUser", this.userService.findByEmail(email).get(0));
    	model.addAttribute("sizes", sizes);
    	return "pool";
    }
    
    
    @RequestMapping(value="/host/pools/{id}", method=RequestMethod.POST)
    public String editPool(@PathVariable("id") long id, @Valid @ModelAttribute("pool") Pool pool, Principal principal, BindingResult result, Model model) {
    	if(result.hasErrors()) {
    		return "pool";
    	}  	
    	else {
    		this.poolService.editPool(pool, id);
    		return "redirect:/pools/" + id;
    	}
    }
    
    @RequestMapping("/pools/{id}/review")
    public String newReview(@PathVariable("id") long id, Principal principal, Model model, HttpSession session) {
        String email = principal.getName();
        User user = userService.findByEmail(email).get(0);
        Role hostRole = new Role();
        hostRole.setName("ROLE_SUPERADMIN");
        List<Role> roles = user.getRoles();
        for(int i = 0; i < roles.size(); i++) {
        	System.out.println(roles.get(i).getName());
        	if(roles.get(i).getName().equals(hostRole.getName())) {
        		return "redirect:/host/dashboard";
        	}
        }
    	model.addAttribute("pool", this.poolService.getById(id));
    	if(!isLoggedIn(principal)) {
    		return "redirect:/login";
    	}
    	model.addAttribute("ratings", ratings);
    	return "review";
    }
    
    @RequestMapping(value="/pools/{id}/review", method=RequestMethod.POST)
    public String submitNewReview(@PathVariable("id") long id,
    		@RequestParam(value="review", required=true) String review, @RequestParam(value="rating", required=true) String rating,
    		Model model, Principal principal) {
    		if(review.length() < 1 || rating.length() < 1) {
    			model.addAttribute("error", "Please fill out both fields!");
    			return "review";
    		}

        	String email = principal.getName();
        	User user = this.userService.findByEmail(email).get(0);
        	Pool pool = this.poolService.getById(id);
        	Review newReview = new Review();
        	newReview.setReview(review);
        	newReview.setRating(rating);
        	newReview.setAuthor(user);
        	newReview.setPool(pool);
    		this.poolService.addReview(newReview);
    		return "redirect:/pools/" + id;
    }
    
    public boolean isLoggedIn(Principal principal) {
    	String email = principal.getName();
    	return userService.findByEmail(email).size() > 0;
    }   
    
}
