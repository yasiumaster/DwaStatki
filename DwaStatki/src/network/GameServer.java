package network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Rock;
import model.RockData;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import control.ClientData;
import control.ServerData;

public class GameServer implements Sender{
	
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
	}

	@Override
	public void send(ServerData serverData) {
/*		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		Packet.Data data = new Packet.Data(mouseX, mouseY);*/
		Packet.Data data = new Packet.Data(serverData.getShipX(), serverData.getShipY(), serverData.getIfNewShootAndReset());
		server.sendToAllTCP(data);
		//potencjalnie jakos do zastapienia przez listner.getConnection().sendTCP();
		
	}

	public void send(List<RockData> rockData) {

		Packet.RocksPacket data = new Packet.RocksPacket(rockData);
		server.sendToAllTCP(data);
		
	}
	
	

}
