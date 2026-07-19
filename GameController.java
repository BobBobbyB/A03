import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttException;

public class GameController {
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 99;

    private final Player player;
    private final MqttGameClient mqttClient;
    private final GameDisplay display;

    public GameController(
            Player player,
            MqttGameClient mqttClient,
            GameDisplay display
    ) {
        this.player = player;
        this.mqttClient = mqttClient;
        this.display = display;
    }

    public void run() {
        display.showMessage("Controls: W=up, S=down, A=left, D=right, Q=quit");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Move (W/A/S/D/Q): ");
                String input = scanner.nextLine().trim().toLowerCase();

                if (input.isEmpty()) {
                    continue;
                }

                char command = input.charAt(0);

                if (command == 'q') {
                    return;
                }

                boolean moved =
                        player.move(command, MIN_COORDINATE, MAX_COORDINATE);

                if (!moved) {
                    display.showMessage(
                            "Invalid move or board boundary reached."
                    );
                    continue;
                }

                try {
                    mqttClient.publishLocalPosition();
                } catch (MqttException e) {
                    display.showMessage(
                            "Could not publish movement: " + e.getMessage()
                    );
                }
            }
        }
    }
}
