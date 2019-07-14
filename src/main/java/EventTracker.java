import java.util.HashMap;
import java.util.Map;

public class EventTracker extends Thread implements Tracker {

    private static EventTracker INSTANCE = new EventTracker();

    private Map<String, Integer> tracker;

    private EventTracker() {
        this.tracker = new HashMap<>();
    }

    synchronized public static EventTracker getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new EventTracker();
        } return INSTANCE;
    }

    @Override
    public Map<String, Integer> tracker() {
        return tracker;
    }

    synchronized public void push(String message) {
        tracker.put(message, 0);
        tracker.replace(message,tracker.get(message)+1);
    }

    synchronized public Boolean has(String message) {
        if ((tracker.get(message) != null) && tracker.get(message) > 0) {
        } return true;
    }

    synchronized public void handle(String message, EventHandler eventHandler) {
        eventHandler.handle();
        if (this.has(message)) {
            Integer currentValue = tracker.get(message);
            tracker.replace(message, currentValue - 1);
        }
    }

    // Do not use this. This constructor is for tests only
    // Using it breaks the singleton class
    EventTracker(Map<String, Integer> tracker) {
        this.tracker = tracker;
    }
}
