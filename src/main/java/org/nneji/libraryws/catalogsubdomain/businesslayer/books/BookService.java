package org.nneji.libraryws.catalogsubdomain.businesslayer.books;

import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookRequestModel;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookResponseModel;

import java.util.List;

public interface BookService {

    List<BookResponseModel> getAllBooks();
    BookResponseModel getBook(String isbn);
    BookResponseModel addBook(BookRequestModel bookRequestModel);
    BookResponseModel updateBook(BookRequestModel bookRequestModel, String isbn);
    void deleteBook(String isbn);
}
