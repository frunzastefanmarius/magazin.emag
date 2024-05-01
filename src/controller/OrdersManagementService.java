package controller;

import db.DbOrdersOperations;
import entity.Order;

import java.util.List;

public class OrdersManagementService {

    public boolean insertInOrder(Order o){
        DbOrdersOperations dbOrdersOperations = new DbOrdersOperations();
        return dbOrdersOperations.insert(o);
    }
}
