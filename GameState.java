import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {
    private final Map<String, Position> positions = new ConcurrentHashMap<>();

    public void updatePlayer(String playerName, Position position) {
        positions.put(playerName, position);
    }

    public Map<String, Position> getSnapshot() {
        return Map.copyOf(positions);
    }
}
