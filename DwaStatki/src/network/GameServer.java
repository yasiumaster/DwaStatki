package network;

import java.io.IOException;

import org.newdawn.slick.GameContainer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer implements Sender{
	
	private Server server;
	
	public GameServer() {
		server = new Server();
		server.addListener(new NetworkListener());
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
	}

	@Override
	public void send(GameContainer gc) {
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		Packet.Data data = new Packet.Data(mouseX, mouseY);
		server.sendToAllTCP(data);
		
	}
	
	

}
