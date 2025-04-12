import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHandling {

    public static void writeString(String string, Path file){
        try{
            Files.writeString(file, string);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String readString(Path file){
        try{
            return Files.readString(file);
        } catch (IOException e){
            e.printStackTrace();
            return "";
        }
    }

    public static void createFile(Path file){
        try{
            Files.createFile(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static java.util.stream.Stream lines(Path file){
        try{
            return Files.lines(file);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}

