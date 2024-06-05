package src.bts.service;

import src.bts.domain.Bug;
import src.bts.observer.Observable;
import src.bts.observer.Observer;
import src.bts.repository.BugDBRepository;
import src.bts.utils.BTSEvent;
import src.bts.utils.ServiceType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class BugService implements Observable<BTSEvent> {

    private List<Observer<BTSEvent>> observers = new ArrayList<>();

    private final BugDBRepository bugDBRepository;

    public BugService(BugDBRepository bugDBRepository) {
        this.bugDBRepository = bugDBRepository;
    }

    public List<Bug> findAll(){
        return StreamSupport.stream(bugDBRepository.findAll().spliterator(), false).toList();
    }

    public void add(String name, String description, Integer severity, String condition){
        Bug newBug = new Bug(name, description, severity, condition);
        /*if(bugDBRepository.save(newBug).isPresent())
            throw new IllegalArgumentException("(Service) SQL Statement Failed!");*/
        bugDBRepository.save(newBug);
        notifyObservers(new BTSEvent(ServiceType.Bug));
    }

    public void remove(Long id){
        /*if(bugDBRepository.delete(id).isEmpty())
            throw new IllegalArgumentException("(Service) SQL Statement Failed!");*/
        bugDBRepository.delete(id);
        notifyObservers(new BTSEvent(ServiceType.Bug));
    }

    public void update(Long id, String name, String description, Integer severity, String condition){
        Bug newBug = new Bug(name, description, severity, condition);
        newBug.setId(id);
        /*if(bugDBRepository.update(newBug).isPresent())
            throw new IllegalArgumentException("(Service) SQL Statement Failed!");*/
        bugDBRepository.update(newBug);
        notifyObservers(new BTSEvent(ServiceType.Bug));
    }

    @Override
    public void addObserver(Observer<BTSEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<BTSEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(BTSEvent t) {
        observers.forEach(observer -> observer.update(t));
    }
}
