package com.cafe24.lms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.lms.domain.Item;
import com.cafe24.lms.repository.ItemRepository;
import com.cafe24.lms.util.SearchCriteria;

@Service
@Transactional
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> listSearchCriteria(SearchCriteria cri){
		//return itemRepository.findAllByOrderByNoDesc(new PageRequest(0, 10));
		return itemRepository.findAllByOrderByNoDesc();
	}
	
	public Item findOne(Long no) {
		return itemRepository.findOne(no);
	}
	
	// 키워드 없을 때
	public /*Page<Item>*/ HashMap<String, Object> testPage(int nowPage){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Page<Item> list = itemRepository.findAllByOrderByNoDesc(new PageRequest(nowPage-1, 10));
		
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
		
		//return itemRepository.findAllByOrderByNoDesc(new PageRequest(nowPage, 10));
	}
	
	public HashMap<String, Object> pageListSearch(int nowPage, String searchString){
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println("pageListSearch search ==> " + searchString);
		Page<Item> list = itemRepository.findAllSearch(searchString, new PageRequest(nowPage-1, 10, new Sort(Direction.DESC, "no")));
		System.out.println("list ======================> " + list.getContent());
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
