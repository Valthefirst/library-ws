package org.nneji.libraryws.cataloguesubdomain.businesslayer.books;

import org.nneji.libraryws.cataloguesubdomain.datalayer.books.Author;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.Book;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.BookRepository;
import org.nneji.libraryws.cataloguesubdomain.datalayer.books.ISBN;
import org.nneji.libraryws.cataloguesubdomain.datalayer.catalogue.CatalogueIdentifier;
import org.nneji.libraryws.cataloguesubdomain.datamapperlayer.books.BookRequestMapper;
import org.nneji.libraryws.cataloguesubdomain.datamapperlayer.books.BookResponseMapper;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.books.BookRequestModel;
import org.nneji.libraryws.cataloguesubdomain.presentationlayer.books.BookResponseModel;
import org.nneji.libraryws.utils.exceptions.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final BookRequestMapper bookRequestMapper;
    private final BookResponseMapper bookResponseMapper;

    public BookServiceImpl(BookRepository bookRepository, BookRequestMapper bookRequestMapper, BookResponseMapper bookResponseMapper) {
        this.bookRepository = bookRepository;
        this.bookRequestMapper = bookRequestMapper;
        this.bookResponseMapper = bookResponseMapper;
    }

    @Override
    public List<BookResponseModel> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return bookResponseMapper.entityListToResponseModelList(books);
    }

    @Override
    public BookResponseModel getBook(String isbn) {
        Book book = bookRepository.findByIsbn_Isbn(isbn);
        if (book == null) {
            throw new NotFoundException("Unknown customerId: " + isbn);
        }
        return bookResponseMapper.entityToResponseModel(book);
    }

    @Override
    public BookResponseModel addBook(BookRequestModel bookRequestModel) {
//        Author author = new Author(bookRequestModel.getAuthorFirstName(), bookRequestModel.getAuthorLastName());
        Book book = bookRequestMapper.requestModelToEntity(bookRequestModel, new ISBN(bookRequestModel.getIsbn())/*, author*/,
                new CatalogueIdentifier(bookRequestModel.getCatalogueId()));
//        book.setAuthor(author);

        return bookResponseMapper.entityToResponseModel(bookRepository.save(book));
    }

    @Override
    public BookResponseModel updateBook(BookRequestModel bookRequestModel, String isbn) {
        Book existingBook = bookRepository.findByIsbn_Isbn(isbn);
        if (existingBook == null) {
            throw new NotFoundException("Unknown customerId: " + isbn);
        }

//        Author author = new Author(bookRequestModel.getAuthorFirstName(), bookRequestModel.getAuthorLastName());

        Book updatedBook = bookRequestMapper.requestModelToEntity(bookRequestModel, existingBook.getIsbn()/*, author*/,
                existingBook.getCatalogueIdentifier());
        updatedBook.setId(existingBook.getId());

        Book response = bookRepository.save(updatedBook);
        return bookResponseMapper.entityToResponseModel(response);
    }

    @Override
    public void deleteBook(String isbn) {
        Book existingBook = bookRepository.findByIsbn_Isbn(isbn);

        if (existingBook == null) {
            throw new NotFoundException("Unknown customerId: " + isbn);
        }
        bookRepository.delete(existingBook);
    }
}
