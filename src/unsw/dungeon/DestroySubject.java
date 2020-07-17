package unsw.dungeon;

public interface DestroySubject {
    public void registerObserver(DestroyObserver o);
    public void removeObserver(DestroyObserver o);
    public void notifyObservers();
}