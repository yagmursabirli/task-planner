import javax.swing.*;

public interface Subject {
    public JTable addObserver(Observer observer);
    public void notifyObservers();
    public void removeObserver(Observer observer);
}
