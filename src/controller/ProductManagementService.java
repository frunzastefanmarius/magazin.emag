package controller;

import db.DbProductsOperations;
import db.DbUsersOperations;
import entity.Product;
import entity.ProductDisplay;
import entity.User;

import java.awt.*;
import java.util.List;

public class ProductManagementService {
    DbProductsOperations db = new DbProductsOperations();


    public List<ProductDisplay> showAllProducts() {
        return db.readAllProducts();
    }

    public boolean insert(Product p) {
        return db.insert(p);
    }

    public List<ProductDisplay> idProductForOrder(Long idUser) {
        return db.readIdForOrder(idUser);
    }

    public  List<ProductDisplay> idProductForAddInBasket(){
        return db.readAllIdProduct();
    }
}
