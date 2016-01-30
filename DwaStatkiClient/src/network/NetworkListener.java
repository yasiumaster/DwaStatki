package network;

import java.util.List;

import control.ClientData;
import model.RockData;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import control.GameHelper;
import control.ServerData;

public class NetworkListener extends Listener {

    private ServerData serverData;
    private ClientData clientData;
    private List<RockData> rockData;
    private List<Integer> toRemoveRocks;

    public NetworkListener(ServerData serverData, List<RockData> rockData, List<Integer> toRemoveRocks, ClientData clientData) {
        this.serverData = serverData;
        this.rockData = rockData;
        this.toRemoveRocks = toRemoveRocks;
        this.clientData = clientData;
    }

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
    public void received(Connection connection, Object obj) {
        if (obj instanceof Packet.Data) {
            //System.out.println("Data recived");
            Packet.Data revicePacket = (Packet.Data) obj;
            //System.out.println("Mouse X: " + revicePacket.getX() + " Mouse Y:" + revicePacket.getY());
            serverData.setShipX(revicePacket.getX());
            serverData.setShipY(revicePacket.getY());
            serverData.setPoints(revicePacket.getPoints());
            if (revicePacket.getNewShoot()) {
                serverData.shoot();
            }
        }
        if (obj instanceof Packet.RocksPacket) {
            //System.out.println("RocketPacket recived");
            Packet.RocksPacket revicePacket = (Packet.RocksPacket) obj;
            List<RockData> revicedRockData = revicePacket.getRocksList();
            int lastIndex = revicedRockData.size() - 1;
            rockData.add(revicedRockData.get(lastIndex));
            //System.out.println("Recived rock dataId: " + revicedRockData.get(lastIndex).getId());
        }
        if (obj instanceof Packet.RockToRemove) {
            Packet.RockToRemove revicePacket = (Packet.RockToRemove) obj;
            Integer recivedRockId = revicePacket.getRockToRemoveId();
            System.out.println("Listener: to remove: " + recivedRockId);
            toRemoveRocks.add(recivedRockId);
        }
        if (obj instanceof Packet.GameData) {
            Packet.GameData revicePacket = (Packet.GameData) obj;
            String recivedWinner = revicePacket.getWinner();
            GameHelper.setWinner(recivedWinner);
        }

        if (obj instanceof Packet.PauseState) {
            Packet.PauseState receivedPacket = (Packet.PauseState) obj;
            serverData.setGamePaused(receivedPacket.isPauseState());
        }
    }

}
