package network;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer{
	
	private Server server;
	
	public GameServer(int port) throws IOException {
		server = new Server();
		server.addListener(new NetworkListener());
		server.bind(port);
		server.start();
		Log.set(Log.LEVEL_DEBUG);
	}
	
	private void registerPackets() {
		Kryo kryo = server.getKryo();
		kryo.register(Packet.DefaultPacket.class);
	}
	
	

}
