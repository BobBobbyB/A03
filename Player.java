public class Player {
    private final String name;
    private int x;
    private int y;

    public Player(String name, int startX, int startY) {
        this.name = name;
        this.x = startX;
        this.y = startY;
    }

    public String getName() {
        return name;
    }

    public synchronized Position getPosition() {
        return new Position(x, y);
    }

    public synchronized boolean move(char command, int min, int max) {
        int newX = x;
        int newY = y;

        switch (Character.toLowerCase(command)) {
            case 'w':
                newY++;
                break;
            case 's':
                newY--;
                break;
            case 'a':
                newX--;
                break;
            case 'd':
                newX++;
                break;
            default:
                return false;
        }

        if (newX < min || newX > max || newY < min || newY > max) {
            return false;
        }

        x = newX;
        y = newY;
        return true;
    }
}
