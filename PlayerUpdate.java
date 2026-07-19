public class PlayerUpdate {
    private final String playerName;
    private final Position position;

    public PlayerUpdate(String playerName, Position position) {
        this.playerName = playerName;
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Position getPosition() {
        return position;
    }
}
