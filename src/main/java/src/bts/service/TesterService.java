package src.bts.service;

import src.bts.domain.Tester;
import src.bts.repository.TesterDBRepository;

public class TesterService {

    private final TesterDBRepository testerDBRepository;

    public TesterService(TesterDBRepository testerDBRepository) {
        this.testerDBRepository = testerDBRepository;
    }

    public Tester findOne(Long id){
        if(testerDBRepository.findOne(id).isPresent())
            return testerDBRepository.findOne(id).get();
        else
            return null;
    }
}
