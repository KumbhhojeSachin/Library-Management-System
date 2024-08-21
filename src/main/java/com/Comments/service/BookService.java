package com.Comments.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Comments.model.Book;
import com.Comments.repository.BookRepository;

@Service
public class BookService {

	private final BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> listBooks() {
		return bookRepository.findAll();
	}

	public Book issueBook(Long id) {
		Book book = bookRepository.findById(id).orElseThrow();
		book.setIssued(true);
		return bookRepository.save(book);
	}

	public Book returnBook(Long id) {
		Book book = bookRepository.findById(id).orElseThrow();
		book.setIssued(false);
		return bookRepository.save(book);
	}

	public List<Book> searchBooks(String title) {
		return bookRepository.findByTitleContainingIgnoreCase(title);
	}
}