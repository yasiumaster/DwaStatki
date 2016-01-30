package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;
import control.ClientData;
import control.ServerData;
import model.RockData;

import java.util.List;

public class GameClient implements Sender {

    private Client client;

    public GameClient(ServerData serverData, List<RockData> rockData, List<Integer> toRemoveRocks,ClientData clientData) {
        client = new Client();
        client.addListener(new NetworkListener(serverData, rockData, toRemoveRocks,clientData));
        Log.set(Log.LEVEL_DEBUG);
    }
    
    public boolean isConnected() {
    	return client.isConnected();
    }

    @Override
    public void send(ClientData clientData) {
        /*int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		Packet.Data data = new Packet.Data(mouseX, mouseY);*/
        Packet.Data data = new Packet.Data(clientData.getShipX(), clientData.getShipY(), clientData.getPoints(), clientData.getIfNewShootAndReset());
        client.sendTCP(data);

    }

    public void send(int rockToRemoveId) {
        System.out.println("Send: " + rockToRemoveId);
        Packet.RockToRemove data = new Packet.RockToRemove(rockToRemoveId);
        client.sendTCP(data);
    }

    public void sendPauseState(boolean pauseState) {
        Packet.PauseState data;
        data = new Packet.PauseState(pauseState);
        client.sendTCP(data);
    }

    public void start(String ip, int port) throws Exception {
        registerPackets();
        client.start();
        client.connect(5000, ip, port);
    }

    private void registerPackets() {
        Kryo kryo = client.getKryo();
        kryo.register(Packet.DefaultPacket.class);
        kryo.register(Packet.Data.class);
        kryo.register(Packet.RocksPacket.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(model.RockData.class);
        kryo.register(Packet.RockToRemove.class);
        kryo.register(Packet.GameData.class);
        kryo.register(Packet.PauseState.class);
    }
}
