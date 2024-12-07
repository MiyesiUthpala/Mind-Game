package mindgameinterface;

import client.model.Score;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ScoreBoardInterface extends Remote {

     List<Score> getScoreList(String game) throws RemoteException;

     void addScore(Score score)  throws RemoteException;
}
