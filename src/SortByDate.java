
import java.util.List;

public class SortByDate implements TaskSortingStrategy {
    @Override
    public void sort(List<Task> tasks) {
        tasks.sort((t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline()));
    }
}
