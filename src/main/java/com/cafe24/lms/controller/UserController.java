package com.cafe24.lms.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.lms.domain.User;
import com.cafe24.lms.service.UserService;
import com.cafe24.lms.security.Auth;
import com.cafe24.lms.security.AuthUser;

@Controller
@RequestMapping( "/user" )
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( value="/join", method=RequestMethod.GET )
	public String join( @ModelAttribute User user ){
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid User vo,
					   BindingResult result) {
		if(result.hasErrors()) {
			return "user/join";
		}
		
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping( value="/login", method=RequestMethod.GET )
	public String login() {
		return "user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpSession session, @ModelAttribute User vo, Model model) {
		User authUser = userService.getUser(vo.getEmail(), vo.getPassword());//userDAO.get(vo.getEmail(), vo.getPassword());
		
		if(authUser == null) {
			model.addAttribute("email", vo.getEmail());
			model.addAttribute("result", "fail");
			return "/user/login";
		}
				
		// 인증처리
		session.setAttribute("authUser", authUser);
		return "redirect:/main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/main";
	}

	@RequestMapping( "/joinsuccess" )
	public String joinsuccess(){
		return "user/joinsuccess";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(@AuthUser User authUser,
						 Model model, HttpSession session) {
		
		//System.out.println("usercont modify AuthUser ==> " + session.getAttribute("authUser"));
		System.out.println("usercont modify AuthUser ==> " + authUser);
		
		User vo = userService.getUser(authUser.getNo());
		model.addAttribute("vo", vo);
		
		/*접근제어
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/main";
		}*/
		
		return "user/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpSession session, @ModelAttribute User vo, Model model) {
		
		System.out.println("/modify vo ==> " + vo);
		
		boolean success = userService.modifyUser(vo);
		
		System.out.println("sucess ==> " + success);
		
		if(success) {
			session.setAttribute("authUser", userService.getUser(vo.getNo()));
		}
		/*접근제어
		UserVO authUser = (UserVO) session.getAttribute("authUser");
		
		// 로그인이 안되어있다면 login페이지
		if(authUser == null) {
			model.addAttribute("result", "fail");
			return "/user/login";
		}*/
		
		return "redirect:/user/modifysuccess";
	}
	
	@RequestMapping(value="/modifysuccess", method=RequestMethod.GET)
	public String modifysuccess(HttpSession session) {
		return "/user/modifysuccess";
	}
}
