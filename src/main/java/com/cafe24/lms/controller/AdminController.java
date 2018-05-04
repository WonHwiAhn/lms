package com.cafe24.lms.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.lms.security.Auth;
import com.cafe24.lms.service.RentService;
import com.cafe24.lms.service.ReserveService;

@Auth(role=Auth.Role.ADMIN)
@Controller
@RequestMapping( "/admin" )
public class AdminController {
	
	@Autowired
	private RentService rentService;
	
	@Autowired
	private ReserveService reserveService;
	
	//@Auth(role=Auth.Role.ADMIN)
	@RequestMapping( { "", "/rent", "/main", "{pno}", "/rent/{pno}", "/main/{pno}" } )
	public String main(@PathVariable(value="pno") Optional<String> pno,
					   Model model ) {
		
		// 경로에 pno가 존재할 경우
		if(pno.isPresent()) {
			/////// pathVariable 숫자로 안들어 올 때 해결해야됨...
			if(pno.get().matches("-?\\d+(\\.\\d+)?")) {
				int nowPage = Integer.parseInt(pno.get());
				// page수가 1보다 작으면 에러남
				if(nowPage > 0) {
					// 키워드 없을 때
					HashMap<String, Object> map = rentService.testPage(nowPage);
					
					System.out.println("/admin list =====> " + map.get("list"));
					
					model.addAttribute("list", map);
					
					if(nowPage > Integer.parseInt(map.get("totalPages").toString())) {
						model.addAttribute("msg", "failToGetData");
					}
				}else { // 페이지가 1보다 작을 경우에
					model.addAttribute("msg", "failToGetData");
				}
			}else { //경로에 숫자가 아닌 다른 문자가 들어왔을 경우에
				model.addAttribute("msg", "failToGetData");
			}
		}else {
			// 페이지가 없을 때는 리다이렉트로 첫 번째 페이지 ㄱㄱ
			return "redirect:admin/1";
		}
		
		return "admin/rent";
	}
	
	@RequestMapping( value= {"/reserve", "/reserve/{pno}"} )
	public String board(@PathVariable(value="pno") Optional<String> pno,
			   			Model model) {
		// 경로에 pno가 존재할 경우
		if(pno.isPresent()) {
			/////// pathVariable 숫자로 안들어 올 때 해결해야됨...
			if(pno.get().matches("-?\\d+(\\.\\d+)?")) {
				int nowPage = Integer.parseInt(pno.get());
				// page수가 1보다 작으면 에러남
				if(nowPage > 0) {
					// 키워드 없을 때
					HashMap<String, Object> map = reserveService.testPage(nowPage);
					
					System.out.println("/admin list =====> " + map.get("list"));
					
					model.addAttribute("list", map);
					
					if(nowPage > Integer.parseInt(map.get("totalPages").toString())) {
						model.addAttribute("msg", "failToGetData");
					}
				}else { // 페이지가 1보다 작을 경우에
					model.addAttribute("msg", "failToGetData");
				}
			}else { //경로에 숫자가 아닌 다른 문자가 들어왔을 경우에
				model.addAttribute("msg", "failToGetData");
			}
		}else {
			// 페이지가 없을 때는 리다이렉트로 첫 번째 페이지 ㄱㄱ
			return "redirect:reserve/1";
		}
				
		return "admin/reserve";
	}
	
}
