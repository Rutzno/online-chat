package chat.server;

import java.io.*;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 8/16/2021
 */

public class SerializationUtils {
    /**
     * Serialize the given object to the file
     */
    public synchronized static void serialize(Object obj, String fileName) throws IOException {
        try (
                var fos = new FileOutputStream(fileName);
                var bos = new BufferedOutputStream(fos);
                var oos = new ObjectOutputStream(bos)
        ) {
            oos.writeObject(obj);
        }
    }

    /**
     * Deserialize to an object from the file
     */
    public static Object deserialize(String fileName) throws IOException, ClassNotFoundException {
        File file = new File(fileName);
        if (file.length() == 0) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object obj = ois.readObject();
            ois.close();
            return obj;
        }
    }
}
