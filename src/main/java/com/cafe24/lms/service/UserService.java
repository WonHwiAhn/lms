package com.cafe24.lms.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.lms.domain.User;
import com.cafe24.lms.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	// 유저 아이디 중복체크
	public boolean existEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user != null;
	}
	
	// 회원가입
	public void join(User user) {
		user.setRegDate(new Date());
		userRepository.save(user);
	}
	
	// 유저 정보 얻기 (이메일과 패스워드)
	public User getUser(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}
	
	// 유저 정보 얻기 (회원 번호)
	public User getUser(Long no) {
		return userRepository.findOne(no);
	}
	
	public boolean modifyUser(User user) {
		
		System.out.println("USERSERVICE modify user ==> " + user);
		
		/// 패스워드 입력없을 때 나중에 처리!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		if(user.getPassword() != null || !user.getPassword().equals("")) {
			System.out.println("예 아님?");
			return userRepository.update(user) == 1;
		}
		else{// if(user.getPassword() == null)
			System.out.println("else!!");
			return userRepository.updateWithoutPass(user) == 1;
		}
	}
}
