package db;

import entity.Basket;
import entity.BasketDisplay;
import entity.Product;
import entity.ProductDisplay;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbBasketOperations {


    public boolean insert(Basket b) {

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
            PreparedStatement pSt = conn.prepareStatement("insert into basket(iduser, idproduct) values(?, ?)");
            pSt.setLong(1, b.getIdUser());
            pSt.setLong(2, b.getIdProduct());
            val = pSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean ok = false;
        if (val != 0)
            ok = true;
        return ok;
    }

    public List<BasketDisplay> readBasketOfAUser(Long idUser) {
        List<BasketDisplay> lp = new ArrayList<>();

        try {
            final String URLDB = "jdbc:postgresql://localhost:5432/emag";
            final String USERNAMEDB = "postgres";
            final String PWDDB = "postgres";
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            String q = "select products.name as name, basket.id as id from products,basket \n" +
                    "\t where basket.iduser=? \n" +
                    "and basket.idproduct=products.id";
            PreparedStatement pSt = conn.prepareStatement(q);

            pSt.setLong(1, idUser);

            ResultSet rs = pSt.executeQuery();


            while (rs.next()) {

                String name = rs.getString("name").trim();
                long id = rs.getLong("id");

                BasketDisplay p = new BasketDisplay(id, name);
                lp.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lp;
    }


    public boolean deleteBasketItem(Long idBasket) {

        final String URLDB = "jdbc:postgresql://localhost:5432/emag";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "vvv";
        int val = 0; // 1
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            PreparedStatement pSt = conn.prepareStatement("delete from basket where id = ?");
            pSt.setLong(1, idBasket);

            val = pSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean ok = false;
        if (val != 0)
            ok = true;
        return ok;

    }


}
