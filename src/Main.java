import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {
    static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {

        Spark.init();

        Spark.get("/", (request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);
                    HashMap m = new HashMap();

                    String passWord = session.attribute("userPassword");

                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {

                        if (user.passWord.contentEquals(passWord)) {
                            m.put("messages", user.messages);
                            return new ModelAndView(m, "messages.html");
                        } else {
                            return new ModelAndView(m, "login.html");
                        }
                    }
                },
                new MustacheTemplateEngine()
        );

        Spark.post("/create-user", (request, response) -> {
            String name = request.queryParams("userName");
            String passWord = request.queryParams("userPassword");

            User user = users.get(name);
            if (user == null) {
                user = new User(name);
                user.passWord = passWord;
                users.put(name, user);
            }

            //users.putIfAbsent(name, new User(name));        //replaces lines User through closing if

            Session session = request.session();
            session.attribute("userName", name);
            session.attribute("userPassword", passWord);

            response.redirect("/");
            return "";
        });

        Spark.post("/create-message", (request, response) -> {
            String message = request.queryParams("message");
            Session session = request.session();

            User user = users.get(session.attribute("userName"));

            user.messages.add(message);

            response.redirect("/");
            return "";
        });

        Spark.post("/logout", (request, response) -> {
            Session session = request.session();
            session.invalidate();

            response.redirect("/");
            return "";
        });

        Spark.post("/delete-message", (request, response) -> {
            String messageNumber = request.queryParams("delete");
            Session session = request.session();

            User user = users.get(session.attribute("userName"));

            user.messages.remove(Integer.parseInt(messageNumber) - 1);
            response.redirect("/");
            return "";
        });

        Spark.post("/edit-message", (request, response) -> {
            String message = request.queryParams("editMessage");
            String editNumber = request.queryParams("editNumber");
            Session session = request.session();

            User user = users.get(session.attribute("userName"));

            user.messages.set(Integer.parseInt(editNumber) - 1, message);

            response.redirect("/");
            return "";
        });

    }
}
