package unsw.dungeon;

public interface BoulderSubject {
    void registerObserver(BoulderObserver o);
    void notifyBoulderObservers();
    int getLastX();
    int getLastY();
    int getX();
    int getY();
}