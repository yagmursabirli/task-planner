

import java.time.LocalDate;

public class BirthdayMessageDecorator extends MessageDecorator {
    private LocalDate birthday;

    public BirthdayMessageDecorator(Message message, LocalDate birthday) {
        super(message);
        this.birthday = LocalDate.of(2025, 1, 16);
    }

    @Override
    public String getMessage() {
        LocalDate today = LocalDate.now();
        String birthdayMessage = today.equals(birthday) ? " Happy Birthday!" : "";
        return super.getMessage() + birthdayMessage;
    }
}
