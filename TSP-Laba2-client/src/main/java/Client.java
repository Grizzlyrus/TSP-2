import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        File file = new File(args[0]);

        try(FileInputStream fis = new FileInputStream(file)) {

            File file1 = new File(args[1]);
            BufferedImage bufferedImage = ImageIO.read(fis);
            SerializableBufferedImage serializableBufferedImage = new SerializableBufferedImage(bufferedImage);

            Registry reg = LocateRegistry.getRegistry(3229);
            Filter stub = (Filter) reg.lookup("Server");

            SerializableBufferedImage result = stub.medFilter5(serializableBufferedImage);

            ImageIO.write(result.get(), "png", file1);

        } catch (ConnectException e) {
            System.out.println("Сервер не доступен.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не указаны все файлы.");
        }
    }
}
