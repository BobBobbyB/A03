import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.MqttException;

public class Main {
    public static void main(String[] args) {
        String playerName = readPlayerName(args);

        if (playerName.isEmpty() || playerName.contains("/")) {
            System.out.println(
                    "Player name cannot be empty or contain '/'."
            );
            return;
        }

        Player player = new Player(playerName, 50, 50);
        GameState gameState = new GameState();
        GameDisplay display = new GameDisplay();

        MqttGameClient mqttClient = null;

        try {
            mqttClient =
                    new MqttGameClient(player, gameState, display);

            mqttClient.connect();

            GameController controller =
                    new GameController(player, mqttClient, display);

            controller.run();
        } catch (MqttException e) {
            System.out.println("MQTT error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (mqttClient != null) {
                mqttClient.disconnect();
            }
        }

        System.out.println("Game stopped.");
    }

    private static String readPlayerName(String[] args) {
        if (args.length > 0) {
            return args[0].trim();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a unique player name: ");
        return scanner.nextLine().trim();
    }
}
