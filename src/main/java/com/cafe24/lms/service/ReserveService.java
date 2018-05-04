package com.cafe24.lms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.cafe24.lms.domain.Item;
import com.cafe24.lms.domain.Rent;
import com.cafe24.lms.domain.Reserve;
import com.cafe24.lms.domain.User;
import com.cafe24.lms.repository.ReserveRepository;

@Service
public class ReserveService {
	@Autowired
	private ReserveRepository reserveRepository;
	
	public boolean insert(Reserve reserve, Rent rent, User user, int maxOrderNum) {
		
		Reserve checkUser = reserveRepository.boolAlreadyExist(rent, user);
		
		System.out.println("checkUser ==> " + checkUser);
		
		// 현재 유저로 예약이 안되있는 상태
		if(checkUser == null) {
			reserve.setRent(rent);
			reserve.setUser(user);
			
			reserve.setOrderNum(maxOrderNum + 1);
			System.out.println("maxOrderNum -------> " + maxOrderNum);
			// 반납일 생성
			/*
			 * reserve.getOrderNum()*7 == 순번 * 7 예약자의 최종 반납예정일
			 * reserve.getOrderNum()*7 - 6 == 예약자의 최초 수령예정일
			 * 
			 * !!!!!!!!!!MAX값 이상... controller에서 get(0)으로 가져와서 문제됨.
			 */
			int returnDateCount = (maxOrderNum + 1)*7;
			Calendar cal = new GregorianCalendar(Locale.KOREA);
			cal.setTime(rent.getReturnDate());
			cal.add(Calendar.DAY_OF_YEAR, returnDateCount - 6);
			
			// 반납일 다음날이 다른 사람의 대여일
			reserve.setPlanRentDate(cal.getTime());
			
			// 대여일 +7일 반납일
			cal.add(Calendar.DAY_OF_YEAR, 6);
			reserve.setPlanReturnDate(cal.getTime());
			
			reserveRepository.save(reserve);
			
			return true;
		}else {
			return false;
		}
	}
	
	public int findMaxOrderNum(Rent rent) {
		return reserveRepository.findMaxOrderNum(rent);
	}
	
	public /*Page<Item>*/ HashMap<String, Object> testPage(int nowPage){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Page<Reserve> list = reserveRepository.findAllByOrderByNoDesc(new PageRequest(nowPage-1, 10));
		
		map.put("list", list.getContent());
		map.put("totalCounts", list.getTotalElements());
		map.put("totalPages", list.getTotalPages());
		map.put("nextPage", list.hasNext());
		map.put("previous", list.hasPrevious());
		map.put("nowPage", nowPage);
		
		// 페이지 수는 최대 5개만 보이게 설정.
		int divPage = (int)(nowPage / 5.1);
		int startPage = (int)(5*divPage + 1);
		
		map.put("startPage", startPage);
		
		return map;
	}
}
