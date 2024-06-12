package Implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History {
    private String userId;
    private List<Date> successDates;

    public History(String userId) {
        this.userId = userId;
        this.successDates = new ArrayList<>();
    }

    public void addSuccess(Date date) {
        this.successDates.add(date);
    }

    public List<Date> viewHistory() {
        return successDates;
    }
}
