//package org.nneji.libraryws.catalogsubdomain.businesslayer.books;
//
//import org.nneji.libraryws.catalogsubdomain.datalayer.books.Book;
//import org.nneji.libraryws.catalogsubdomain.datalayer.books.BookRepository;
//import org.nneji.libraryws.catalogsubdomain.datalayer.books.ISBN;
//import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.Catalog;
//import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.CatalogIdentifier;
//import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.CatalogRepository;
//import org.nneji.libraryws.catalogsubdomain.datamapperlayer.book.BookRequestMapper;
//import org.nneji.libraryws.catalogsubdomain.datamapperlayer.book.BookResponseMapper;
//import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookRequestModel;
//import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookResponseModel;
//import org.nneji.libraryws.utils.exceptions.InvalidInputException;
//import org.nneji.libraryws.utils.exceptions.NotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookServiceImpl implements BookService{
//
//    private final BookRepository bookRepository;
//    private final BookRequestMapper bookRequestMapper;
//    private final BookResponseMapper bookResponseMapper;
//    private final CatalogRepository catalogRepository;
//
//    public BookServiceImpl(BookRepository bookRepository, BookRequestMapper bookRequestMapper, BookResponseMapper bookResponseMapper, CatalogRepository catalogRepository) {
//        this.bookRepository = bookRepository;
//        this.bookRequestMapper = bookRequestMapper;
//        this.bookResponseMapper = bookResponseMapper;
//        this.catalogRepository = catalogRepository;
//    }
//
//    @Override
//    public List<BookResponseModel> getAllBooks() {
//        List<Book> books = bookRepository.findAll();
//        return bookResponseMapper.entityListToResponseModelList(books);
//    }
//
//    @Override
//    public BookResponseModel getBook(String isbn) {
//        Book book = bookRepository.findByIsbn_Isbn(isbn);
//        if (book == null) {
//            throw new NotFoundException("Unknown customerId: " + isbn);
//        }
//        return bookResponseMapper.entityToResponseModel(book);
//    }
//
//    @Override
//    public BookResponseModel addBook(BookRequestModel bookRequestModel) {
//        if (!(bookRequestModel.getIsbn().length() == 10 || bookRequestModel.getIsbn().length() == 13)){
//            throw new InvalidInputException("Invalid ISBN provided " + bookRequestModel.getIsbn());
//        }
//        Book book = bookRequestMapper.requestModelToEntity(bookRequestModel, new ISBN(bookRequestModel.getIsbn())/*, author*/,
//                new CatalogIdentifier(bookRequestModel.getCatalogId()));
//
//        // To increment the number of books in the catalogue
//        String catalogueId = bookRequestModel.getCatalogId();
//        Catalog catalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogueId);
//        catalog.setSize(catalog.getSize() + 1);
//
//        return bookResponseMapper.entityToResponseModel(bookRepository.save(book));
//    }
//
//    @Override
//    public BookResponseModel updateBook(BookRequestModel bookRequestModel, String isbn) {
//        Book existingBook = bookRepository.findByIsbn_Isbn(isbn);
//        if (existingBook == null) {
//            throw new NotFoundException("Unknown customerId: " + isbn);
//        }
//
////        Author author = new Author(bookRequestModel.getAuthorFirstName(), bookRequestModel.getAuthorLastName());
//
//        Book updatedBook = bookRequestMapper.requestModelToEntity(bookRequestModel, existingBook.getIsbn()/*, author*/,
//                existingBook.getCatalogIdentifier());
//        updatedBook.setId(existingBook.getId());
//
//        Book response = bookRepository.save(updatedBook);
//        return bookResponseMapper.entityToResponseModel(response);
//    }
//
//    @Override
//    public void deleteBook(String isbn) {
//        Book existingBook = bookRepository.findByIsbn_Isbn(isbn);
//
//        if (existingBook == null) {
//            throw new NotFoundException("Unknown customerId: " + isbn);
//        }
//        bookRepository.delete(existingBook);
//    }
//}
