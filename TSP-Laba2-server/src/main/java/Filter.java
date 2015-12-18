import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Filter extends Remote{

    SerializableBufferedImage medFilter5(SerializableBufferedImage originFile) throws RemoteException;

}
