package com.tienthanh.bookservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tienthanh.bookservice.command.data.Book;
import com.tienthanh.bookservice.command.data.BookRepository;
import com.tienthanh.bookservice.query.model.BookResponseModel;
import com.tienthanh.bookservice.query.queries.GetAllBooksQuery;
import com.tienthanh.bookservice.query.queries.GetBookQuery;
import com.tienthanh.commonservice.model.BookResponseCommonModel;
import com.tienthanh.commonservice.query.GetDetailsBookQuery;

@Component
public class BookProjection {
	@Autowired
	private BookRepository bookRepository;

	@QueryHandler
	public BookResponseModel handle(GetBookQuery getBooksQuery) {
		BookResponseModel model = new BookResponseModel();
		Book book = bookRepository.getById(getBooksQuery.getBookId());
		BeanUtils.copyProperties(book, model);

		return model;
	}

	@QueryHandler
	List<BookResponseModel> handle(GetAllBooksQuery getAllBooksQuery) {
		List<Book> listEntity = bookRepository.findAll();
		List<BookResponseModel> listbook = new ArrayList<>();
		listEntity.forEach(s -> {
			BookResponseModel model = new BookResponseModel();
			BeanUtils.copyProperties(s, model);
			listbook.add(model);
		});
		return listbook;
	}

	@QueryHandler
	public BookResponseCommonModel handle(GetDetailsBookQuery getDetailsBookQuery) {
		BookResponseCommonModel model = new BookResponseCommonModel();
		Book book = bookRepository.getById(getDetailsBookQuery.getBookId());
		BeanUtils.copyProperties(book, model);

		return model;
	}

}
