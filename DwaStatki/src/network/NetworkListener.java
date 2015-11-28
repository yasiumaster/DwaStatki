package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetworkListener extends Listener {
	@Override
	public void connected(Connection arg0) {
		// TODO Auto-generated method stub
		super.connected(arg0);
	}
	
	@Override
	public void disconnected(Connection arg0) {
		// TODO Auto-generated method stub
		super.disconnected(arg0);
	}
	
	@Override
	public void received(Connection arg0, Object arg1) {
		// TODO Auto-generated method stub
		super.received(arg0, arg1);
	}

}
