package unsw.dungeon;

public interface EntrySubject {
    public void addEntryObserver(EntryObserver e);
    public void removeEntryObserver(EntryObserver e);
    public void notifyEntryObservers();
    public String getTargetFile();
}
