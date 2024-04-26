package controller;

import db.DbUsersOperations;
import entity.User;

import java.sql.SQLException;
import java.util.List;

public class UserManagementService {


    // login user (du-te in db si vezi ca exista user cu nume si parola ca asta din u

    public String register(User u) throws SQLException {

        DbUsersOperations dbUsersOperations = new DbUsersOperations();
        dbUsersOperations.insert(u);
        return "Inregistrat cu succes";
    }

    public Long login(User u) {

        DbUsersOperations dbUsersOperations = new DbUsersOperations();
        return dbUsersOperations.searchUserForLogin(u);
    }

    public List<User> ShowAllUsers(boolean b){
        DbUsersOperations db = new DbUsersOperations();
        List<User> lu = db.readAllUsers(b);
        return lu;
    }
    public boolean checkUserType(Long idUser){
        DbUsersOperations db = new DbUsersOperations();
        boolean buyerOrSeller = db.userType(idUser);
        return buyerOrSeller;
    }

}
