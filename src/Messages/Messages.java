package Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * An entity class that represents a message
 * @author Group0031
 */
public class Messages extends Observable {
    private final String message;
    private final List<Observer> observers = new ArrayList<>();
    /**
     * Create a message
     * @param message String representing a message
     */
    public Messages(String message){
        this.message = message;
    }

    /**
     * Update all observers
     * @param manager a AdminMessageManager
     */
    public void notifyAllObservers(AdminMessageManager manager){
        for (Observer observer : observers) {
            observer.update(this, manager);
        }
    }

    /**
     * Adds an observer to observers
     * @param o observer
     */
    public void AddObserver(Observer o){
        this.observers.add(o);
    }

    /**
     * Displays message as a string
     * @return String that represents the message
     */
    public String toString(){
        return this.message;
    }
}