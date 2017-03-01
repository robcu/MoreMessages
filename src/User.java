import java.util.ArrayList;


public class User {
    String name;
    String passWord;
    ArrayList<String> messages = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
}
