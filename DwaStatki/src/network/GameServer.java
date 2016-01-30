package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.Packet.GameData;
import model.Rock;
import model.RockData;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import control.ClientData;
import control.ServerData;

public class GameServer implements Sender {

    private Server server;
    private NetworkListener listener;

    public GameServer(ClientData clientData) {
        server = new Server();
        listener = new NetworkListener(clientData);
        server.addListener(listener);
        Log.set(Log.LEVEL_DEBUG);
    }

    public void start(int port) throws IOException {
        registerPackets();
        server.bind(port);
        server.start();
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        kryo.register(Packet.DefaultPacket.class);
        kryo.register(Packet.Data.class);
        kryo.register(Packet.RocksPacket.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(model.RockData.class);
        kryo.register(Packet.RockToRemove.class);
        kryo.register(Packet.GameData.class);
        kryo.register(Packet.PauseState.class);
    }

    @Override
    public void send(ServerData serverData) {
/*		int mouseX = gc.getInput().getMouseX();
        int mouseY = gc.getInput().getMouseY();
		Packet.Data data = new Packet.Data(mouseX, mouseY);*/
        Packet.Data data = new Packet.Data(serverData.getShipX(), serverData.getShipY(), serverData.getPoints(), serverData.getIfNewShootAndReset());
        server.sendToAllTCP(data);

    }

    public void send(int rockToRemoveId) {
        System.out.println("Send: " + rockToRemoveId);
        Packet.RockToRemove data = new Packet.RockToRemove(rockToRemoveId);
        server.sendToAllTCP(data);

    }

    public void sendPauseState(boolean pauseState) {
        Packet.PauseState data;
        data = new Packet.PauseState(pauseState);
        server.sendToAllTCP(data);
    }

    public void send(List<RockData> rockData) {

        Packet.RocksPacket data = new Packet.RocksPacket(rockData);
        server.sendToAllTCP(data);

    }

    public void send(String winner) {

        Packet.GameData data = new Packet.GameData(winner);
        server.sendToAllTCP(data);

    }

}
