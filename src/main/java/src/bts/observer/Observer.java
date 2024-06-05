package src.bts.observer;

import src.bts.utils.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
