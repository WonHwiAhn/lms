package com.cafe24.lms.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.cafe24.lms.domain.Album;
import com.cafe24.lms.domain.Book;
import com.cafe24.lms.domain.Category;
import com.cafe24.lms.domain.DVD;
import com.cafe24.lms.domain.User;

public class App {
	public static void main(String[] args) {
		// 1. Entity Manager Factory 생성
    	EntityManagerFactory emf = 
    			Persistence.createEntityManagerFactory("lms");
    	
    	// 2. Entity Manager 생성
    	EntityManager em = emf.createEntityManager();
    	
    	// 3. GetTx 트랜잭션
    	EntityTransaction tx = em.getTransaction(); 
    	
    	// 4. TX Begins
    	tx.begin();
    	
    	// 5. Business Logic
    	try {
    		// user 생성 (admin 계정)
    		User user = new User();
    		user.setEmail("test@test");
    		user.setName("test");
    		user.setPassword("test");
    		user.setRegDate(new Date());
    		user.setAuth(1);
    		
    		em.persist(user);
    		
    		//Category 생성
    		Category category = new Category();
    		
    		category.setName("testCategory1");
    		
    		em.persist(category);
    		
    		//Category 생성
    		Category category2 = new Category();
    		
    		category2.setName("testCategory2");
    		
    		em.persist(category2);
    		
    		//Category 생성
    		Category category3 = new Category();
    		
    		category3.setName("testCategory3");
    		
    		em.persist(category3);
    		
    		// DVD 생성
    		DVD dvd = new DVD();
    		dvd.setName("dvdTest");
    		dvd.setCategory(category);
    		dvd.setCompany("dvdCompany");
    		dvd.setStatus(0);
    		
    		// DVD 생성
    		DVD dvd1 = new DVD();
    		dvd1.setName("dvdTest1");
    		dvd1.setCategory(category);
    		dvd1.setCompany("dvdCompany1");
    		dvd1.setStatus(0);
    		
    		// DVD 생성
    		DVD dvd2 = new DVD();
    		dvd2.setName("dvdTest2");
    		dvd2.setCategory(category);
    		dvd2.setCompany("dvdCompany2");
    		dvd2.setStatus(0);
    		
    		// DVD 생성
    		DVD dvd3 = new DVD();
    		dvd3.setName("dvdTest3");
    		dvd3.setCategory(category);
    		dvd3.setCompany("dvdCompany3");
    		dvd3.setStatus(0);
    		
    		// DVD 생성
    		DVD dvd4 = new DVD();
    		dvd4.setName("dvdTest4");
    		dvd4.setCategory(category);
    		dvd4.setCompany("dvdCompany4");
    		dvd4.setStatus(0);
    		
    		// Album 생성
    		Album album = new Album();
    		album.setName("albumTest");
    		album.setArtist("albumArtist");
    		album.setCategory(category2);
    		album.setStatus(0);
    		
    		// Album 생성
    		Album album1 = new Album();
    		album1.setName("albumTest1");
    		album1.setArtist("albumArtist1");
    		album1.setCategory(category2);
    		album1.setStatus(0);
    		
    		// Album 생성
    		Album album2 = new Album();
    		album2.setName("albumTest2");
    		album2.setArtist("albumArtist2");
    		album2.setCategory(category2);
    		album2.setStatus(0);
    		
    		// Album 생성
    		Album album3 = new Album();
    		album3.setName("albumTest3");
    		album3.setArtist("albumArtist3");
    		album3.setCategory(category2);
    		album3.setStatus(0);
    		
    		// Album 생성
    		Album album4 = new Album();
    		album4.setName("albumTest4");
    		album4.setArtist("albumArtist4");
    		album4.setCategory(category2);
    		album4.setStatus(0);
    		
    		// Book 생성
    		Book book = new Book();
    		book.setName("BookTest");
    		book.setISBN("002-30239-392");
    		book.setCategory(category3);
    		book.setStatus(0);
    		
    		// Book 생성
    		Book book1 = new Book();
    		book1.setName("BookTest1");
    		book1.setISBN("002-30239-3921");
    		book1.setCategory(category3);
    		book1.setStatus(0);
    		
    		// Book 생성
    		Book book2 = new Book();
    		book2.setName("BookTest2");
    		book2.setISBN("002-30239-3922");
    		book2.setCategory(category3);
    		book2.setStatus(0);
    		
    		// Book 생성
    		Book book3 = new Book();
    		book3.setName("BookTest3");
    		book3.setISBN("002-30239-3923");
    		book3.setCategory(category3);
    		book3.setStatus(0);
    		
    		// Book 생성
    		Book book4 = new Book();
    		book4.setName("BookTest4");
    		book4.setISBN("002-30239-3924");
    		book4.setCategory(category3);
    		book4.setStatus(0);
    		
    	} catch(Exception e) {
    		tx.rollback();
    		e.printStackTrace();
    	}
    	
    	// 6. TX Commit
    	tx.commit();
    	
    	// 7. Entity Manager Close
    	em.close();
    	
    	// 8. Entity Manager Factory 종료
    	emf.close();
	}
}
