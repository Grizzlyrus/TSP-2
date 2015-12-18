import java.awt.*;
import java.awt.image.BufferedImage;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;


public class Server implements Filter{

    public SerializableBufferedImage medFilter5(SerializableBufferedImage originFile) {
        BufferedImage origin =  originFile.get();
        BufferedImage result;

        result = new BufferedImage(origin.getWidth(), origin.getHeight(), BufferedImage.TYPE_INT_RGB);
        result.createGraphics().drawImage(origin, 0, 0, Color.BLACK, null);

        int[] window;
        for (int i = 1; i < origin.getWidth() - 1; i++) {
            for (int j = 1; j < origin.getHeight() - 1; j++) {
                window = getCrossArray(i,j,origin);
                Arrays.sort(window);
                result.setRGB(i, j, window[2]);
            }
        }
        return new SerializableBufferedImage(result);
    }

    private int[] getCrossArray(int i, int j, BufferedImage bufimg){
        int []array = new int[5];
        array[0] = bufimg.getRGB(i, j - 1);
        array[1] = bufimg.getRGB(i - 1, j);
        array[2] = bufimg.getRGB(i, j);
        array[3] = bufimg.getRGB(i + 1, j);
        array[4] = bufimg.getRGB(i, j + 1);
        return array;
    }



    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        Server server = new Server();
        Filter stub = (Filter) UnicastRemoteObject.exportObject(server, 0);

        Registry reg = LocateRegistry.createRegistry(3229);
        reg.bind("Server", stub);
    }
}
