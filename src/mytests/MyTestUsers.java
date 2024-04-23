package mytests;

import db.DbUsersOperations;
import entity.User;

import java.sql.SQLException;
import java.util.List;

public class MyTestUsers {

    public static void main(String[] args) {

    new MyTestUsers().testSelect(true);

    }

    private void testInsert () {
        User u = new User("ghita", "pwd", true, true);
        User u1 = new User("mariuca", "pwd", true, true);

        User um = new User("emag", "pwd", true, false);
        User um1 = new User("lidl", "pwd", true, false);

        DbUsersOperations db = new DbUsersOperations();
//        db.insert(u);
//        db.insert(u1);
//        db.insert(um);
        try {
            db.insert(um1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void testSelect(boolean b) {

        DbUsersOperations db = new DbUsersOperations();
        List<User> lu = db.readAllUsers(b);

        for(User u: lu) {
            System.out.println(u);
        }
    }
}
