import java.util.ArrayList;

/**
 * Created by robculclasure on 2/22/17.
 */
public class User {
    String name;
    String passWord;
    static ArrayList<String> messages = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
}
