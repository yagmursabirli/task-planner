

public class NotificationsDecorator extends MessageDecorator {
    private String notification;

    public NotificationsDecorator(Message message, String notification) {
        super(message);
        this.notification = notification;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " | Notifications: " + notification;
    }
}
