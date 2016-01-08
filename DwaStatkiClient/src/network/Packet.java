package network;

import java.util.ArrayList;
import java.util.List;

import model.Rock;
import model.RockData;

public class Packet {
	
	public static class DefaultPacket {
		String msg;
		
		public DefaultPacket() {
			// TODO Auto-generated constructor stub
		}
		
		public DefaultPacket(String msg) {
			this.msg = msg;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return msg;
		}
		
	}
	
	public static class Data {

		private int x,y;
		private boolean newShoot;
		public Data() {
			// TODO Auto-generated constructor stub
		}
		public Data(int x, int y, boolean newShoot) {
			this.x = x;
			this.y = y;
			this.newShoot = newShoot;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public boolean getNewShoot() {
			return newShoot;
		}
	}
	
	public static class RocksPacket {
		private List<RockData> rockData;
		public RocksPacket() {
			// TODO Auto-generated constructor stub
		}
		
		public RocksPacket(List<RockData> rockData) {
			this.rockData = rockData;
		}
		
		public List<RockData> getRocksList() {
			return rockData;
		}
		
	}
	
	public static class RockToRemove {
		private int rockToRemove;
		public RockToRemove() {
			// TODO Auto-generated constructor stub
		}
		
		public int getRockToRemoveId() {
			return rockToRemove;
		}
	}
}
