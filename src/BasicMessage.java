

public class BasicMessage implements Message {
    private String day;
    private String date;

    public BasicMessage(String day, String date) {
        this.day = day;
        this.date = date;
    }

    @Override
    public String getMessage() {
        return day + ", " + date;
    }
}
