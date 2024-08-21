package com.Comments.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Comments.model.ApiResponse;
import com.Comments.model.Book;
import com.Comments.model.BookDTO;
import com.Comments.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<BookDTO>>> getAllBooks() {
		List<BookDTO> books = bookService.listBooks().stream().map(this::convertToDTO).collect(Collectors.toList());
		return ResponseEntity.ok(new ApiResponse<>("success", "Books fetched successfully", books));
	}

	@PostMapping("/issue/{id}")
	public ResponseEntity<ApiResponse<BookDTO>> issueBook(@PathVariable Long id) {
		Book book = bookService.issueBook(id);
		return ResponseEntity.ok(new ApiResponse<>("success", "Book issued successfully", convertToDTO(book)));
	}

	@PostMapping("/return/{id}")
	public ResponseEntity<ApiResponse<BookDTO>> returnBook(@PathVariable Long id) {
		Book book = bookService.returnBook(id);
		return ResponseEntity.ok(new ApiResponse<>("success", "Book returned successfully", convertToDTO(book)));
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<BookDTO>>> searchBooks(@RequestParam String title) {
		List<BookDTO> books = bookService.searchBooks(title).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
		return ResponseEntity.ok(new ApiResponse<>("success", "Books found", books));
	}

	// Helper method to convert Book entity to BookDTO
	private BookDTO convertToDTO(Book book) {
		return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.isIssued());
	}
}