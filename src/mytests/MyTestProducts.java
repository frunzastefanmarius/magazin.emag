package mytests;

import db.DbProductsOperations;
import db.DbUsersOperations;
import entity.Product;
import entity.ProductDisplay;
import entity.User;

import java.util.List;

public class MyTestProducts {

    public static void main(String[] args) {

    new MyTestProducts().testInsert();

    }

    private void testInsert () {


        Product p1= new Product("laptop dell", "laptop dell cu procesor de ala s memorie tot de aia si toate alea sa te prosteasca ", 4500, 1,1 );
        Product p2= new Product("laptop apple", "laptop apple m3 cu alea bune  ", 14500, 4,1 );

        Product p3= new Product("salam pikok", "ala polonez ", 25, 2,1 );
        Product p4= new Product("apa borsec", "apa de aia cu aia  ", 3.99, 3,1 );


        DbProductsOperations dbProductsOperations = new DbProductsOperations();
        dbProductsOperations.insert(p1);
        dbProductsOperations.insert(p2);
        dbProductsOperations.insert(p3);
        dbProductsOperations.insert(p4);

    }
    private void testSelect() {

        DbProductsOperations db = new DbProductsOperations();
        List<ProductDisplay> lp = db.readAllProducts();

        for(ProductDisplay p: lp) {
            System.out.println(p);
        }

    }
}
