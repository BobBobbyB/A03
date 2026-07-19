public final class MessageCodec {
    private MessageCodec() {
    }

    public static String encode(String playerName, Position position) {
        return playerName + "," + position.getX() + "," + position.getY();
    }

    public static PlayerUpdate decode(String payload) {
        String[] parts = payload.split(",");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid message format: " + payload);
        }

        String playerName = parts[0].trim();
        int x = Integer.parseInt(parts[1].trim());
        int y = Integer.parseInt(parts[2].trim());

        return new PlayerUpdate(playerName, new Position(x, y));
    }
}
