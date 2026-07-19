import java.nio.charset.StandardCharsets;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttGameClient implements MqttCallback {
    private static final String BROKER = "tcp://localhost:1883";
    private static final String TOPIC_BASE = "csc364/game/players/";
    private static final String WILDCARD_TOPIC = TOPIC_BASE + "+";

    private final Player localPlayer;
    private final GameState gameState;
    private final GameDisplay display;
    private final MqttClient client;

    public MqttGameClient(
            Player localPlayer,
            GameState gameState,
            GameDisplay display
    ) throws MqttException {
        this.localPlayer = localPlayer;
        this.gameState = gameState;
        this.display = display;

        String clientId =
                "game-" + localPlayer.getName() + "-" + System.currentTimeMillis();

        this.client = new MqttClient(BROKER, clientId);
        this.client.setCallback(this);
    }

    public void connect() throws MqttException {
        client.connect();
        client.subscribe(WILDCARD_TOPIC, 2);

        display.showMessage("Connected to " + BROKER);
        display.showMessage("Subscribed to " + WILDCARD_TOPIC);

        publishLocalPosition();
    }

    public void publishLocalPosition() throws MqttException {
        Position position = localPlayer.getPosition();
        String payload = MessageCodec.encode(localPlayer.getName(), position);

        MqttMessage message =
                new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
        message.setQos(2);

        client.publish(TOPIC_BASE + localPlayer.getName(), message);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        String payload =
                new String(message.getPayload(), StandardCharsets.UTF_8);

        try {
            PlayerUpdate update = MessageCodec.decode(payload);

            gameState.updatePlayer(
                    update.getPlayerName(),
                    update.getPosition()
            );

            display.showPlayers(gameState.getSnapshot());
        } catch (IllegalArgumentException e) {
            display.showMessage("Ignored invalid message: " + payload);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        display.showMessage("Connection lost: " + cause.getMessage());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    public void disconnect() {
        try {
            if (client.isConnected()) {
                client.disconnect();
            }
            client.close();
        } catch (MqttException e) {
            display.showMessage(
                    "Error while closing MQTT client: " + e.getMessage()
            );
        }
    }
}
