package network;

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
		public Data() {
			// TODO Auto-generated constructor stub
		}
		public Data(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
	}
}
