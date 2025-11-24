import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Event {
    private static int counter = 1;
    private final int id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private boolean isCompleted;

    public Event(String title, String description, LocalDateTime dateTime) {
        this.id = counter++;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.isCompleted = false;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDateTime() { return dateTime; }
    public boolean isCompleted() { return isCompleted; }
    public void markComplete() { isCompleted = true; }

    @Override
    public String toString() {
        String status = isCompleted ? "Completed" : "Pending";
        String dt = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return String.format("ID: %d | Title: %s | Description: %s | Date: %s | Status: %s",
                id, title, description, dt, status);
    }
}

class EventManager {
    private final List<Event> events = new ArrayList<>();

    public void addEvent(String title, String description, LocalDateTime dateTime) {
        Event ev = new Event(title, description, dateTime);
        events.add(ev);
        System.out.println("Added: " + ev);
    }

    public void viewEvents() {
        if (events.isEmpty()) {
            System.out.println("No events to display.");
        } else {
            System.out.println("Current Events:");
            for (Event ev : events) {
                System.out.println(ev);
            }
        }
    }

    public void markComplete(int id) {
        Event ev = findEventById(id);
        if (ev != null) {
            ev.markComplete();
            System.out.println("Marked as complete: " + ev);
        } else {
            System.out.println("Event with ID " + id + " not found.");
        }
    }

    public void removeEvent(int id) {
        Event ev = findEventById(id);
        if (ev != null) {
            events.remove(ev);
            System.out.println("Removed: " + ev);
        } else {
            System.out.println("Event with ID " + id + " not found.");
        }
    }

    private Event findEventById(int id) {
        for (Event ev : events) {
            if (ev.getId() == id) return ev;
        }
        return null;
    }
}

class EventReminderApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dtFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        EventManager manager = new EventManager();
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addEvent(manager);
                case "2" -> manager.viewEvents();
                case "3" -> markEventComplete(manager);
                case "4" -> removeEvent(manager);
                case "5" -> {
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                }
                default -> System.out.println("Invalid choice. Enter 1-5.");
            }
        }

        scanner.close();
    }

    private static void showMenu() {
        System.out.println("\n---- Event Reminder Menu ----");
        System.out.println("1. Add Event");
        System.out.println("2. View Events");
        System.out.println("3. Mark Event as Complete");
        System.out.println("4. Remove Event");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addEvent(EventManager manager) {
        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
            System.out.print("Enter description: ");
            String desc = scanner.nextLine();
            System.out.print("Enter date & time (yyyy-MM-dd HH:mm): ");
            String dt = scanner.nextLine();
            LocalDateTime dateTime = LocalDateTime.parse(dt, dtFormatter);
            manager.addEvent(title, desc, dateTime);
        } catch (Exception e) {
            System.out.println("Invalid date/time format. Try again.");
        }
    }

    private static void markEventComplete(EventManager manager) {
        System.out.print("Enter event ID to mark as complete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            manager.markComplete(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }

    private static void removeEvent(EventManager manager) {
        System.out.print("Enter event ID to remove: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            manager.removeEvent(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
        }
    }
}