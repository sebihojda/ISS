package src.bts.service;

import src.bts.domain.Programmer;
import src.bts.repository.ProgrammerDBRepository;

public class ProgrammerService {
    private final ProgrammerDBRepository programmerDBRepository;

    public ProgrammerService(ProgrammerDBRepository programmerDBRepository) {
        this.programmerDBRepository = programmerDBRepository;
    }

    public Programmer findOne(Long id){
        if(programmerDBRepository.findOne(id).isPresent())
            return programmerDBRepository.findOne(id).get();
        else
            return null;
    }
}
