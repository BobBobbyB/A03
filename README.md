# ASGN03 MQTT
Archie Phyo & Micah Chen

## Classes

- `Main` - starts the application
- `Player` - stores and moves the local player
- `Position` - stores x and y coordinates
- `GameState` - stores all known player positions
- `MqttGameClient` - connects, publishes, subscribes, and handles callbacks
- `GameController` - reads keyboard commands
- `GameDisplay` - prints player positions
- `MessageCodec` - converts messages to and from text
- `PlayerUpdate` - represents a decoded MQTT update

## Topic structure

Each player publishes to:

```text
csc364/game/players/<player-name>
```

Every client subscribes to:

```text
csc364/game/players/+
```

## Message format

```text
playerName,x,y
```

Example:

```text
player1,51,50
```

## Start Mosquitto

```bash
mosquitto -v
```

## Compile

```bash
javac -cp "lib/org.eclipse.paho.client.mqttv3-1.2.5.jar" *.java
```

## Run player 1

```bash
java -cp ".:lib/org.eclipse.paho.client.mqttv3-1.2.5.jar" Main player1
```

## Run player 2

```bash
java -cp ".:lib/org.eclipse.paho.client.mqttv3-1.2.5.jar" Main player2
```

Use W, A, S, and D to move. Use Q to quit.
