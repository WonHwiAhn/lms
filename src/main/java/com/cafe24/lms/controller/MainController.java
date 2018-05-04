package com.cafe24.lms.controller;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe24.lms.domain.Item;
import com.cafe24.lms.domain.Rent;
import com.cafe24.lms.domain.Reserve;
import com.cafe24.lms.domain.User;
import com.cafe24.lms.security.AuthUser;
import com.cafe24.lms.service.ItemService;
import com.cafe24.lms.service.RentService;
import com.cafe24.lms.service.ReserveService;
import com.cafe24.lms.service.UserService;
import com.cafe24.lms.util.SearchCriteria;

@Controller
public class MainController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RentService rentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReserveService reserveService;
	
	@RequestMapping( { "", "/main", "{pno}", "/main/{pno}" } )
	public String index(@PathVariable(value="pno") Optional<String> pno,
						//@ModelAttribute("cri") SearchCriteria cri,
						@RequestParam(value="searchString", required=false, defaultValue="") String searchString,
						Model model ) {
		System.out.println("/main searchString ==> " + searchString);
		/*System.out.println("/main cri.getPage ==> " + cri.getPage());
		System.out.println("/main cri.getPage ==> " + cri.getPageStart());*/
		// model.addAttribute("list", itemService.listSearchCriteria(cri));
		//System.out.println("/main criList ==> " + itemService.listSearchCriteria(cri).get(0).getNo());
		//System.out.println("/main criList ==> " + itemService.listSearchCriteria(cri).get(0).getRents());
		
		//System.out.println("+++++++++++++++   "  + itemService.testPage(0).getContent());
		System.out.println("/main pno ==> " + pno);
		System.out.println("/main searchString ==> " + searchString);
		
		// 경로에 pno가 존재할 경우
		if(pno.isPresent()) {
			/////// pathVariable 숫자로 안들어 올 때 해결해야됨...
			if(pno.get().matches("-?\\d+(\\.\\d+)?")) {
				//model.addAttribute("list", itemService.testPage(0).getContent());
				int nowPage = Integer.parseInt(pno.get());
				// page수가 1보다 작으면 에러남
				if(nowPage > 0) {
					// 키워드 없을 때
					// HashMap<String, Object> map = itemService.testPage(nowPage);
					System.out.println("/main searchString ==> " + searchString);
					HashMap<String, Object> map = itemService.pageListSearch(nowPage, searchString);
					model.addAttribute("list", map);
					
					if(nowPage > Integer.parseInt(map.get("totalPages").toString())) {
						model.addAttribute("msg", "noData");
					}
				}else { // 페이지가 1보다 작을 경우에
					model.addAttribute("msg", "failToGetData");
				}
			}else { //경로에 숫자가 아닌 다른 문자가 들어왔을 경우에
				model.addAttribute("msg", "failToGetData");
			}
		}else {
			// 페이지가 없을 때는 리다이렉트로 첫 번째 페이지 ㄱㄱ
			if("".equals(searchString))
				return "redirect:/1";
			else
				return "redirect:/1?searchString="+searchString;
		}
		
		return "main/index";
	}
	
	@Transactional
	@RequestMapping( value= {"/rent", "/rent/{pno}"} )
	public String rent(@PathVariable(value="pno") Optional<String> pno,
					   @AuthUser User user,
					   @RequestParam(value="itemNo", required=false) Long no,
					   @ModelAttribute Rent rent,
					   RedirectAttributes rttr,
					   Model model ) {
		
		if(user == null) {
			System.out.println("오긴오나?????");
			rttr.addFlashAttribute("msg", "fail");
			return "redirect:/1";
		}
		
		// 경로에 pno가 존재할 경우
		if(pno.isPresent()) {
			int nowPage = 1;
			/////// pathVariable 숫자로 안들어 올 때 해결해야됨...
			if(pno.get().matches("-?\\d+(\\.\\d+)?")) {
				//model.addAttribute("list", itemService.testPage(0).getContent());
				nowPage = Integer.parseInt(pno.get());
				// page수가 1보다 작으면 에러남
				if(nowPage > 0) {
					// 번호로 상품 정보 긁어오기
					Item item = itemService.findOne(no);
					User user1 = userService.getUser(user.getNo());
					System.out.println("/rent UserInfo ==> " + user1);
					
					System.out.println("/rent itemInfo ==> " + item);
					
					// 상품 대여
					rentService.insert(rent, item, user1);
					model.addAttribute("nowPage", nowPage);
				}else { // 페이지가 1보다 작을 경우에
					model.addAttribute("msg", "failToGetData");
				}
			}else { //경로에 숫자가 아닌 다른 문자가 들어왔을 경우에
				model.addAttribute("msg", "failToGetData");
			}
			return "main/rent";
		}else {
			// 페이지가 없을 때는 리다이렉트로 첫 번째 페이지 ㄱㄱ
			return "redirect:/1";
		}
	}

	@Transactional
	@RequestMapping( value= {"/reserve", "/reserve/{pno}"} )
	public String reserve(@PathVariable(value="pno") Optional<String> pno,
						  @AuthUser User user,
						  @RequestParam(value="itemNo", required=false) Long no,
						  @ModelAttribute Reserve reserve,
						  RedirectAttributes rttr,
						  Model model ) {
		
		if(user == null) {
			rttr.addFlashAttribute("msg", "fail");
			return "redirect:/1";
		}
		
		// 경로에 pno가 존재할 경우
		if(pno.isPresent()) {
			int nowPage = 1;
			/////// pathVariable 숫자로 안들어 올 때 해결해야됨...
			if(pno.get().matches("-?\\d+(\\.\\d+)?")) {
				//model.addAttribute("list", itemService.testPage(0).getContent());
				nowPage = Integer.parseInt(pno.get());
				// page수가 1보다 작으면 에러남
				if(nowPage > 0) {
					// 번호로 상품 정보 긁어오기
					Item item = itemService.findOne(no);
					Long rentNo = item.getRents().get(item.getRents().size()-1).getNo();
					System.out.println("item rentNO ==> " + item.getRents().get(0).getNo());
					Rent rent = rentService.findOne(rentNo);
					
					System.out.println("RENTVO ==> " + rent);
					User user1 = userService.getUser(user.getNo());
					System.out.println("/reserve UserInfo ==> " + user1);
					
					System.out.println("/reserve itemInfo ==> " + item);
					
					//////////////////// 이 놈 문제 -> solved
					System.out.println("/reserve maxorderNum ==> " + reserveService.findMaxOrderNum(rent));
					
					
					
					int maxOrderNum = reserveService.findMaxOrderNum(rent);
					// 상품 예약
					if(reserveService.insert(reserve, rent, user1, maxOrderNum) == false) {
						rttr.addFlashAttribute("msg", "exist");
						return "redirect:/"+nowPage;
					}
					model.addAttribute("nowPage", nowPage);
				}else { // 페이지가 1보다 작을 경우에
					model.addAttribute("msg", "failToGetData");
				}
			}else { //경로에 숫자가 아닌 다른 문자가 들어왔을 경우에
				model.addAttribute("msg", "failToGetData");
			}
			return "main/rent";
		}else {
			// 페이지가 없을 때는 리다이렉트로 첫 번째 페이지 ㄱㄱ
			return "redirect:/1";
		}
		
		//return "main/rent";
	}
}
