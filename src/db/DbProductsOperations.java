package db;

import entity.Product;
import entity.ProductDisplay;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbProductsOperations {


    public boolean insert (Product p)  {

        // COD CARE SCRIE IN DB

        // daca are rezultate, citirea lor

        // conectare la db cu incarcare driver
        final String URLDB = "jdbc:postgresql://localhost:5432/emag";
        final String USERNAMEDB ="postgres";
        final String PWDDB ="vvv";
        int val = 0; // 1
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            PreparedStatement pSt = conn.prepareStatement("insert into products(name, description, price, iduser, idcategory) values (?, ?, ?,?,?)");
            pSt.setString(1, p.getName());
            pSt.setString(2, p.getDescription());
            pSt.setDouble(3, p.getPrice());
            pSt.setLong(4, p.getIduser());
            pSt.setLong(5, p.getIdCategory());
            val = pSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean ok = false;
        if(val!=0)
            ok=true;
        return ok;
    }

    public List<ProductDisplay> readAllProducts() {
        List<ProductDisplay> lp = new ArrayList<>();
        // citeste din db toti userii si returneaza lista lor

try {

    // conectare la db cu incarcare driver
    final String URLDB = "jdbc:postgresql://localhost:5432/emag";
    final String USERNAMEDB = "postgres";
    final String PWDDB = "postgres";
    Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

    // rulare sql
    String q="select products.id as id, products.name as name,products.description as description, products.price as price, users.username as vendorname, categories.name as categoryname \n" +
            "\tfrom products, users, categories\n" +
            "\twhere products.iduser=users.id \n" +
            "\tand products.idcategory=categories.id \n" +
            "\torder by users.username asc ";
    PreparedStatement pSt = conn.prepareStatement(q);

    // pSt.set...

    ResultSet rs = pSt.executeQuery();


    while (rs.next()) {

        String name = rs.getString("name").trim();
        String desc = rs.getString("description").trim();
        double price = rs.getDouble("price");

        String vendorname = rs.getString("vendorname").trim();
        String categoryname = rs.getString("categoryname").trim();
        long  id = rs.getLong("id");

        ProductDisplay p = new ProductDisplay( name,id, desc,String.valueOf(price), vendorname, categoryname);
        lp.add(p);
    }
}
catch (SQLException e) {
    e.printStackTrace();
}

        return lp;
    }




}
