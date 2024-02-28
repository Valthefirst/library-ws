package org.nneji.libraryws.finessubdomain.businesslayer;

import org.nneji.libraryws.finessubdomain.datalayer.Fine;
import org.nneji.libraryws.finessubdomain.datalayer.FineIdentifier;
import org.nneji.libraryws.finessubdomain.datalayer.FineRepository;
import org.nneji.libraryws.finessubdomain.datamapperlayer.FineRequestMapper;
import org.nneji.libraryws.finessubdomain.datamapperlayer.FineResponseMapper;
import org.nneji.libraryws.finessubdomain.presentationlayer.FineRequestModel;
import org.nneji.libraryws.finessubdomain.presentationlayer.FineResponseModel;
import org.nneji.libraryws.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FineServiceImpl implements FineService{

    private final FineRepository fineRepository;
    private final FineResponseMapper fineResponseMapper;
    private final FineRequestMapper fineRequestMapper;

    public FineServiceImpl(FineRepository fineRepository, FineResponseMapper fineResponseMapper, FineRequestMapper fineRequestMapper) {
        this.fineRepository = fineRepository;
        this.fineResponseMapper = fineResponseMapper;
        this.fineRequestMapper = fineRequestMapper;
    }

    @Override
    public List<FineResponseModel> getAllFines() {
        return fineResponseMapper.entityListToResponseModelList(fineRepository.findAll());
    }

    @Override
    public FineResponseModel getFine(String fineId) {
        Fine fine = fineRepository.findByFineIdentifier_FineId(fineId);
        if (fine == null)
            throw new NotFoundException("Unknown fineId: " + fineId);
//        fine.getFineId().setFineId(fineId);
        return fineResponseMapper.entityToResponseModel(fine);
    }

    @Override
    public FineResponseModel addFine(FineRequestModel fineRequestModel) {
        Fine fine = fineRequestMapper.requestModelToEntity(fineRequestModel, new FineIdentifier());
        return fineResponseMapper.entityToResponseModel(fineRepository.save(fine));
    }

    @Override
    public FineResponseModel updateFine(FineRequestModel fineRequestModel, String fineId) {
        Fine existingFine = fineRepository.findByFineIdentifier_FineId(fineId);
        if (existingFine == null)
            throw new NotFoundException("Unknown fineId: " + fineId);
        Fine updatedFine = fineRequestMapper.requestModelToEntity(fineRequestModel, existingFine.getFineIdentifier());
        updatedFine.setId(existingFine.getId());
        return fineResponseMapper.entityToResponseModel(fineRepository.save(updatedFine));
    }

    @Override
    public void deleteFine(String fineId) {
        Fine fine = fineRepository.findByFineIdentifier_FineId(fineId);
        if (fine == null)
            throw new NotFoundException("Unknown fineId: " + fineId);
        fineRepository.delete(fine);
    }
}
