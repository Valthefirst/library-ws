package org.nneji.libraryws.catalogsubdomain.businesslayer;

import org.nneji.libraryws.catalogsubdomain.datalayer.books.Book;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.BookRepository;
import org.nneji.libraryws.catalogsubdomain.datalayer.books.ISBN;
import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.Catalog;
import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.CatalogIdentifier;
import org.nneji.libraryws.catalogsubdomain.datalayer.catalog.CatalogRepository;
import org.nneji.libraryws.catalogsubdomain.datamapperlayer.book.BookRequestMapper;
import org.nneji.libraryws.catalogsubdomain.datamapperlayer.book.BookResponseMapper;
import org.nneji.libraryws.catalogsubdomain.datamapperlayer.catalog.CatalogRequestMapper;
import org.nneji.libraryws.catalogsubdomain.datamapperlayer.catalog.CatalogResponseMapper;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookRequestModel;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.books.BookResponseModel;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.catalog.CatalogRequestModel;
import org.nneji.libraryws.catalogsubdomain.presentationlayer.catalog.CatalogResponseModel;
import org.nneji.libraryws.utils.exceptions.DuplicateISBNException;
import org.nneji.libraryws.utils.exceptions.InUseException;
import org.nneji.libraryws.utils.exceptions.InvalidISBNException;
import org.nneji.libraryws.utils.exceptions.NotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogBooksServiceImpl implements CatalogBooksService {

    private final CatalogRepository catalogRepository;
    private final BookRepository bookRepository;
    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogRequestMapper catalogRequestMapper;
    private final BookResponseMapper bookResponseMapper;
    private final BookRequestMapper bookRequestMapper;

    public CatalogBooksServiceImpl(CatalogRepository catalogRepository, BookRepository bookRepository, CatalogResponseMapper catalogResponseMapper, CatalogRequestMapper catalogRequestMapper, BookResponseMapper bookResponseMapper, BookRequestMapper bookRequestMapper) {
        this.catalogRepository = catalogRepository;
        this.bookRepository = bookRepository;
        this.catalogResponseMapper = catalogResponseMapper;
        this.catalogRequestMapper = catalogRequestMapper;
        this.bookResponseMapper = bookResponseMapper;
        this.bookRequestMapper = bookRequestMapper;
    }

    // Methods for the catalogs
    @Override
    public List<CatalogResponseModel> getAllCatalogs() {
        List<Catalog> catalogs = catalogRepository.findAll();
        return catalogResponseMapper.entityListToResponseModelList(catalogs);
    }

    @Override
    public CatalogResponseModel getCatalog(String catalogId) {
        Catalog catalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if (catalog == null)
            throw new NotFoundException("Unknown catalogId provided: " + catalogId);
        return catalogResponseMapper.entityToResponseModel(catalog);
    }

    @Override
    public CatalogResponseModel addCatalog(CatalogRequestModel catalogRequestModel) {
        return catalogResponseMapper.entityToResponseModel(catalogRepository
                .save(catalogRequestMapper.requestModelToEntity(catalogRequestModel, new CatalogIdentifier())));
    }

    @Override
    public CatalogResponseModel updateCatalog(CatalogRequestModel catalogRequestModel, String catalogId) {
        Catalog existingCatalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if (existingCatalog == null)
            throw new NotFoundException("Unknown catalogId provided: " + catalogId);

        Catalog updatedCatalog = catalogRequestMapper.requestModelToEntity(catalogRequestModel, existingCatalog.getCatalogIdentifier());
        updatedCatalog.setId(existingCatalog.getId());
        return catalogResponseMapper.entityToResponseModel(catalogRepository.save(updatedCatalog));
    }

    @Override
    public void deleteCatalog(String catalogId) {
        Catalog existingCatalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        if (existingCatalog == null)
            throw new NotFoundException("Unknown catalogId provided: " + catalogId);

        if (existingCatalog.getSize() > 0)
            throw new InUseException("Catalog has books and cannot be deleted");

        catalogRepository.delete(existingCatalog);
    }


    // Methods for the books
    @Override
    public List<BookResponseModel> getAllBooksInCatalog(String catalogId) {
        if (catalogRepository.findByCatalogIdentifier_CatalogId(catalogId) == null)
            throw new NotFoundException("Unknown catalogId provided: " + catalogId);

        //add query params

        return bookResponseMapper.entityListToResponseModelList(
                bookRepository.findAllByCatalogIdentifier_CatalogId(catalogId));
    }

    @Override
    public BookResponseModel getBookInCatalog(String catalogId, Long isbn) {
        if (catalogRepository.findByCatalogIdentifier_CatalogId(catalogId) == null)
            throw new NotFoundException("Unknown catalogId provided: " + catalogId);

        if (isbn != 10 && isbn != 13) {
            throw new InvalidISBNException("ISBN must be 10 or 13 digits long.");
        }

        Book book = bookRepository.findByIsbn_Isbn(isbn);
        if (book == null) {
            throw new NotFoundException("Unknown ISBN provided: " + isbn);
        }
        return bookResponseMapper.entityToResponseModel(book);
    }

    @Override
    public BookResponseModel addBookInCatalog(String catalogId, BookRequestModel bookRequestModel) {
        if (catalogRepository.findByCatalogIdentifier_CatalogId(catalogId) == null)
            throw new NotFoundException("Unknown catalogId provided: " + catalogId);

        if (bookRequestModel.getIsbn() != 10 && bookRequestModel.getIsbn() != 13) {
            throw new InvalidISBNException("ISBN must be 10 or 13 digits long.");
        }

        Book book = bookRequestMapper.requestModelToEntity(bookRequestModel, new ISBN(bookRequestModel.getIsbn()),
                new CatalogIdentifier(catalogId));

        // To increment the number of books in the catalogue
        Catalog catalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        catalog.setSize(catalog.getSize() + 1);

        try {
            return bookResponseMapper.entityToResponseModel(bookRepository.save(book));
        }
        catch (DataAccessException e) {
            if(e.getMessage().contains("constraint [isbn]")) {
                throw new DuplicateISBNException("The catalog already contains a book with isbn: " + bookRequestModel.getIsbn());
            }
            throw new InvalidISBNException("Could not save the new book.");
        }
    }

    @Override
    public BookResponseModel updateBookInCatalog(String catalogId, Long isbn, BookRequestModel bookRequestModel) {
        if (catalogRepository.findByCatalogIdentifier_CatalogId(catalogId) == null)
            throw new NotFoundException("Unknown catalogId provided: " + catalogId);

        if (isbn != 10 && isbn != 13) {
            throw new InvalidISBNException("ISBN must be 10 or 13 digits long.");
        }

        Book existingBook = bookRepository.findByIsbn_Isbn(isbn);
        if (existingBook == null) {
            throw new NotFoundException("Unknown ISBN: " + isbn);
        }

        Book updatedBook = bookRequestMapper.requestModelToEntity(bookRequestModel, existingBook.getIsbn(),
                existingBook.getCatalogIdentifier());
        updatedBook.setId(existingBook.getId());

        Book response = bookRepository.save(updatedBook);
        return bookResponseMapper.entityToResponseModel(response);
    }

    @Override
    public void deleteBookInCatalog(String catalogId, Long isbn) {
        if (catalogRepository.findByCatalogIdentifier_CatalogId(catalogId) == null)
            throw new NotFoundException("Unknown catalogId provided: " + catalogId);

        if (isbn != 10 && isbn != 13) {
            throw new InvalidISBNException("ISBN must be 10 or 13 digits long.");
        }

        Book existingBook = bookRepository.findByIsbn_Isbn(isbn);

        if (existingBook == null) {
            throw new NotFoundException("Unknown ISBN: " + isbn);
        }

        // To decrement the number of books in the catalogue
        Catalog catalog = catalogRepository.findByCatalogIdentifier_CatalogId(catalogId);
        catalog.setSize(catalog.getSize() - 1);

        bookRepository.delete(existingBook);
    }
}
