package db;

import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUsersOperations {

    //in clasa asta doar se cauta in DB



    public boolean insert(User u) throws SQLException {

        // COD CARE SCRIE IN DB

        // daca are rezultate, citirea lor

        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/emag";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "vvv";
        int val = 0; // 1
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            PreparedStatement pSt = conn.prepareStatement("insert into users(username, password, isactive, isbuyer) values(?, ?, ?,?)");
            pSt.setString(1, u.getUsername());
            pSt.setString(2, u.getPassword());
            pSt.setBoolean(3, u.isActive());
            pSt.setBoolean(4, u.isBuyer());
            val = pSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        boolean ok = false;
        if (val != 0)
            ok = true;
        return ok;
    }

    public List<User> readAllUsers(boolean allDespiteStatus) {
        List<User> lu = new ArrayList<>();
        // citeste din db toti userii si returneaza lista lor

        try {

            // conectare la db cu incarcare driver
            final String URLDB = "jdbc:postgresql://localhost:5432/emag";
            final String USERNAMEDB = "postgres";
            final String PWDDB = "postgres";
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            String q;
            if (allDespiteStatus)
                q = "select * from users order by username asc ";
            else
                q = "select * from users where isActive=true order by username asc ";
            PreparedStatement pSt = conn.prepareStatement(q);

            // pSt.set...

            ResultSet rs = pSt.executeQuery();


            while (rs.next()) {

                String user = rs.getString("username").trim();
                String p = rs.getString("password").trim();
                boolean isa = rs.getBoolean("isactive");
                boolean isb = rs.getBoolean("isbuyer");

                long id = rs.getLong("id");

                User u = new User(user, p, isa, isb);
                u.setId(id);
                lu.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lu;
    }
    public Long searchUserForLogin(User user) {
        Long idUser = null;//presupun ca e null
        // citeste din db toti userii si returneaza lista lor

        try {
            // conectare la db cu incarcare driver
            final String URLDB = "jdbc:postgresql://localhost:5432/emag";
            final String USERNAMEDB = "postgres";
            final String PWDDB = "postgres";
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            String q = "select id from users where username=? and password=?";

            PreparedStatement pSt = conn.prepareStatement(q);

            pSt.setString(1, user.getUsername());
            pSt.setString(2, user.getPassword());

            ResultSet rs = pSt.executeQuery();//???? aici ii dam valoarea rezulata in urma rularii in pgadmin.

            //daca intra pe while inseamna ca a gasit ceva, daca nu gaseste si ID ramane null, nu intra pe while
            while (rs.next()) {
                idUser = rs.getLong("id");//si pune in idUser valoare care este in coloana id din pgadmin
                System.out.println("(a intrat in while din DbUsersOperations si s a logat)");
            }//daca intra pe aici macar o data inseamna ca este valorizat
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (idUser == null)
            System.out.println("Un cont cu aceast user si aceasta parola nu exista.");
        return idUser;
    }
    public boolean userType(Long idUser){
        boolean isBuyer = false;

        try {
            // conectare la db cu incarcare driver
            final String URLDB = "jdbc:postgresql://localhost:5432/emag";
            final String USERNAMEDB = "postgres";
            final String PWDDB = "postgres";
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            String q = "SELECT isBuyer FROM users WHERE id=?";

            PreparedStatement pSt = conn.prepareStatement(q);

            pSt.setLong(1, idUser);

            ResultSet rs = pSt.executeQuery();//???? aici ii dam valoarea rezulata in urma rularii in pgadmin.

            //daca intra pe while inseamna ca a gasit ceva, daca nu gaseste si ID ramane null, nu intra pe while
            while (rs.next()) {
                isBuyer = rs.getBoolean("isbuyer");//si pune in idUser valoare care este in coloana id din pgadmin
                System.out.println("(a intrat in while din DbUsersOperations si a verificat daca user cu acest id este buyer sau seller)");
            }//daca intra pe aici macar o data inseamna ca este valorizat
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (true)
            System.out.println("Posibil sa mearga posibil sa nu mearga");
        return isBuyer;

    }

}
