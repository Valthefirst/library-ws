package org.nneji.libraryws.accountssubdomain.businesslayer;

import org.nneji.libraryws.accountssubdomain.datalayer.Address;
import org.nneji.libraryws.accountssubdomain.datalayer.Patron;
import org.nneji.libraryws.accountssubdomain.datalayer.PatronIdentifier;
import org.nneji.libraryws.accountssubdomain.datalayer.PatronRepository;
import org.nneji.libraryws.accountssubdomain.datamapperlayer.PatronRequestMapper;
import org.nneji.libraryws.accountssubdomain.datamapperlayer.PatronResponseMapper;
import org.nneji.libraryws.accountssubdomain.presentationlayer.PatronRequestModel;
import org.nneji.libraryws.accountssubdomain.presentationlayer.PatronResponseModel;
import org.nneji.libraryws.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronServiceImpl implements PatronService{

    private final PatronRepository patronRepository;
    private final PatronResponseMapper patronResponseMapper;
    private final PatronRequestMapper patronRequestMapper;

    public PatronServiceImpl(PatronRepository patronRepository, PatronResponseMapper patronResponseMapper, PatronRequestMapper patronRequestMapper) {
        this.patronRepository = patronRepository;
        this.patronResponseMapper = patronResponseMapper;
        this.patronRequestMapper = patronRequestMapper;
    }

    @Override
    public List<PatronResponseModel> getPatrons() {
        List<Patron> patronList = patronRepository.findAll();
        return patronResponseMapper.entityListToResponseModelList(patronList);
    }

    @Override
    public PatronResponseModel getPatronById(String patronId) {
        Patron foundPatron = patronRepository.findByPatronIdentifier_PatronId(patronId);
        if (foundPatron == null) {
            throw new NotFoundException("Unknown patronId: " + patronId);
        }
        return patronResponseMapper.entityToResponseModel(foundPatron);
    }

    @Override
    public PatronResponseModel addPatron(PatronRequestModel patronRequestModel) {
        Address address = new Address(patronRequestModel.getStreetAddress(), patronRequestModel.getCity(),
                patronRequestModel.getProvince(), patronRequestModel.getCountry(), patronRequestModel.getPostalCode());

        Patron patron = patronRequestMapper.requestModelToEntity(patronRequestModel, new PatronIdentifier(), address);
        patron.setAddress(address);
        return patronResponseMapper.entityToResponseModel(patronRepository.save(patron));
    }

    @Override
    public PatronResponseModel updatePatron(PatronRequestModel patronRequestModel, String patronId) {
        Patron foundPatron = patronRepository.findByPatronIdentifier_PatronId(patronId);
        if (foundPatron == null) {
            throw new NotFoundException("Unknown patronId: " + patronId);
        }

        Address address = new Address(patronRequestModel.getStreetAddress(), patronRequestModel.getCity(),
                patronRequestModel.getProvince(), patronRequestModel.getCountry(), patronRequestModel.getPostalCode());

        Patron updatedPatron = patronRequestMapper.requestModelToEntity(patronRequestModel,
                foundPatron.getPatronIdentifier(), address);
        updatedPatron.setId(foundPatron.getId());
        return patronResponseMapper.entityToResponseModel(patronRepository.save(updatedPatron));
    }

    @Override
    public void removePatron(String patronId) {
        Patron foundPatron = patronRepository.findByPatronIdentifier_PatronId(patronId);
        if (foundPatron == null) {
            throw new NotFoundException("Unknown patronId: " + patronId);
        }
        patronRepository.delete(foundPatron);
    }
}
