package lt.vcs.vcs_project.utils;

import java.io.*;

public class IOObjectStreamUtils {
    public static Object readFirstObjectFromFile(String fileName) throws FileNotFoundException, InvalidClassException {
        try (
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))
        ) {
            return inputStream.readObject();
        }  catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (InvalidClassException e) {
            throw new InvalidClassException(e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeObjectToFile(String fileName, Object o) {
        try (
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))
        ) {
            outputStream.writeObject(o);
        }  catch (IOException e) {
            e.printStackTrace();
        }

    }
}