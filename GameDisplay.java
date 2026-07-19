import java.util.Map;

public class GameDisplay {
    public synchronized void showPlayers(Map<String, Position> positions) {
        System.out.println("\n--- Player Positions ---");

        positions.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->
                        System.out.printf("%-15s %s%n",
                                entry.getKey(),
                                entry.getValue()));

        System.out.println("------------------------");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
