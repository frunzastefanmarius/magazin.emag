package controller;

import db.DbUsersOperations;
import entity.User;

import java.sql.SQLException;

public class UserManagementService {


    // login user (du-te in db si vezi ca exista user cu nume si parola ca asta din u

    public String register(User u) throws SQLException {

        String afisezBack = "User inregistrat cu succes.";
        DbUsersOperations dbUsersOperations = new DbUsersOperations();
        dbUsersOperations.insert(u);
        return afisezBack;

    }

    public Long login(User u) {

        DbUsersOperations dbUsersOperations = new DbUsersOperations();
        return dbUsersOperations.searchUserForLogin(u);
    }

}
