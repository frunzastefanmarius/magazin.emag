package db;

import entity.BasketDisplay;
import entity.CategoryDisplay;
import entity.ProductDisplay;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class DbCategoryOperations {

    public List<CategoryDisplay> readAllCategory() {
        List<CategoryDisplay> lc = new ArrayList<>();
        // citeste din db toti userii si returneaza lista lor

        try {

            // conectare la db cu incarcare driver
            final String URLDB = "jdbc:postgresql://localhost:5432/emag";
            final String USERNAMEDB = "postgres";
            final String PWDDB = "postgres";
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            String q = "SELECT id from categories;";
            PreparedStatement pSt = conn.prepareStatement(q);

            // pSt.set...

            ResultSet rs = pSt.executeQuery();


            while (rs.next()) {

                Long id = rs.getLong("id");


                CategoryDisplay p = new CategoryDisplay(id);//aici creaza un obiect de tipul ce vreau eu, si dupa il adauga in lista pe care o returneaza metoda.
                lc.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lc;
    }

}
