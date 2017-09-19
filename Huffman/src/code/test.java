package code;
import java.io.BufferedReader;
public class test {




try {
    File file = new File("input");
    BufferedReader reader = new BufferedReader(new FileReader(file));

    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }

} catch (IOException e) {
    e.printStackTrace();
} finally {
    try {
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}