package controller;

import db.DbUsersOperations;
import entity.User;

public class UserManagementService {

    // inreg user

    // login user (du-te in db si vezi ca exista user cu nume si parola ca asta din u

    public Long login(User u) {

        DbUsersOperations dbUsersOperations = new DbUsersOperations();
        return dbUsersOperations.searchUserForLogin(u);
    }

    //intre

    //inregistrare user
}
