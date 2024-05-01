package db;

import entity.Order;
import entity.OrderDisplay;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DbOrdersOperations {

    public boolean insert(Order Order) {

        final String URLDB = "jdbc:postgresql://localhost:5432/emag";
        final String USERNAMEDB = "postgres";
        final String PWDDB = "vvv";
        int val = 0; // 1
        try {
            Connection conn = DriverManager.getConnection(URLDB, USERNAMEDB, PWDDB);

            // rulare sql
            PreparedStatement pSt = conn.prepareStatement("insert into orders(creationDateTime, delivery, payment, idBasket) values(?, ?, ?, ?, ?)");
            pSt.setTimestamp(1, Order.getCreationDateTime());
            pSt.setBoolean(2, Order.isDelivery());
            pSt.setBoolean(2, Order.isPayment());
            pSt.setLong(2, Order.getIdBasket());

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
