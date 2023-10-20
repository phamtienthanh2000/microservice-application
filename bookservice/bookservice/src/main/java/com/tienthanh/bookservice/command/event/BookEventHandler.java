package com.tienthanh.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tienthanh.bookservice.command.data.Book;
import com.tienthanh.bookservice.command.data.BookRepository;
import com.tienthanh.commonservice.event.BookUpdateStatusEvent;

@Component
public class BookEventHandler {
	@Autowired
	private BookRepository bookRepository;

	@EventHandler
	public void on(BookCreatedEvent event) {
		Book book = new Book();
		BeanUtils.copyProperties(event, book);
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookUpdatedEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setAuthor(event.getAuthor());
		book.setName(event.getName());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookDeletedEvent event) {

		bookRepository.deleteById(event.getBookId());

	}

	@EventHandler
	public void on(BookUpdateStatusEvent event) {
		Book book = bookRepository.getById(event.getBookId());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}

}
