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

		private int x,y,points;
		private boolean newShoot;
		public Data() {
			// TODO Auto-generated constructor stub
		}
		public Data(int x, int y, int points, boolean newShoot) {
			this.x = x;
			this.y = y;
			this.newShoot = newShoot;
			this.points = points;
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
		
		public int getPoints() {
			return points;
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
		public RockToRemove(int rockToRemoveId) {
			this.rockToRemove = rockToRemoveId;
		}
		
		public int getRockToRemoveId() {
			return rockToRemove;
		}
	}
	
	public static class GameData {
		private String winner;
		public GameData() {
			// TODO Auto-generated constructor stub
		}
		public GameData(String winner) {
			this.winner = winner;
		}
		
		public String getWinner() {
			return winner;
		}
	}

	public static class PauseState {
		private boolean pauseState;

		public PauseState() {
			// TODO Auto-generated constructor stub
		}

		public PauseState(boolean pauseState) {
			this.pauseState = pauseState;
		}

		public boolean isPauseState() {
			return pauseState;
		}
	}
}
