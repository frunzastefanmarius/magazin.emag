package service;

import controller.UserManagementService;
import db.DbBasketOperations;
import db.DbProductsOperations;
import db.DbUsersOperations;
import entity.Basket;
import entity.BasketDisplay;
import entity.ProductDisplay;
import entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class emag {

    //aici fac metoda de inregistrare cu void

    public static void start(){

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Alegeti una din urmatoarele actiuni");
            System.out.println("1. Inregistrare");
            System.out.println("2. Logare");
            System.out.println("-------------------");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    register();
                    start();
                    break;
                case 2:
                    login();
                default:
                    System.out.println("nu ai ales o optiune valida.");
                    start();
            }
        } while (choice != 0);
    }
    public static void register(){
        boolean existentUser = false;
        do {
            existentUser = false;
            System.out.println("Salut , hai sa ne inregistram ");
            Scanner sc = new Scanner(System.in);
            System.out.print("New User:");
            String username = sc.nextLine();
            System.out.print("Pwd:");
            String pwd = sc.nextLine();
            User u = new User(username, pwd);

            DbUsersOperations db = new DbUsersOperations();
            try {
                db.insert(u);
            } catch (SQLException e) {
                existentUser=true;
                System.out.println("nu se poate cu acest user deoarece este luat, reincearca");
            }
        }
        while (existentUser);
    }

    public static Long login(){
        boolean loginSuccessfull = false;
        Long idUser = null;
        int attempts = 0;
        int maxAttempts = 3;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("Login User:");
            String username = sc.nextLine();
            System.out.print("Pwd:");
            String pwd = sc.nextLine();
            User u = new User(username, pwd);
            UserManagementService ums = new UserManagementService();
            idUser = ums.login(u);
            attempts++;
        }
        while (idUser == null && attempts<maxAttempts);

        if (idUser==null){
            System.out.println("ne pare rau. nu am gasit un cont cu acest user si aceasta parola.");

        }else {
            loginSuccessfull = true;
            System.out.println("salut usere cu id:" + idUser);
        }
        if (loginSuccessfull){
            showMenuOptions();
        }
        return idUser;
    }

    public static void showMenuOptions(){
        Scanner scanner = new Scanner(System.in);
        int userMenuOption;

        do {
            //aici user e deja logat si alege din meniu ce vrea sa faca
            System.out.println("Menu:");    //aici afisam pagina de "Home";
            System.out.println("1. Vizualizeaza lista de produse");
            System.out.println("2. Adauga un produs in cos");
            System.out.println("3. Afisez cosul");
            System.out.println("3. Sterge un produs din cos");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            userMenuOption = scanner.nextInt();
            DbBasketOperations dbb = new DbBasketOperations();


            switch (userMenuOption) {
                case 1: readAllProducts();
                //case 2: addProductsInBasket();
                //case 3: displayBasket();
                //case 4: deleteProductFromBasket();
                case 0: endProgram();
                default: System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (userMenuOption != 0);
        start();
    }

    public static void readAllProducts(){
        System.out.println("Aceasta este lista de produse");
        // list products
        DbProductsOperations db = new DbProductsOperations();
        List<ProductDisplay> lp = db.readAllProducts();

        for (ProductDisplay p : lp) {
            System.out.println(p);
        }
        showMenuOptions();
    }

    public static void addProductsInBasket(Long idUser){
        System.out.println("Adauga produse in cos:");
        // simulam add in cos a produselor
        Scanner sc = new Scanner(System.in);
        System.out.print("ce id pui in cos:");
        Long idprod = sc.nextLong();

        Basket b = new Basket(idUser, idprod);

        DbBasketOperations dbBasketOperations = new DbBasketOperations();
        dbBasketOperations.insert(b);

        System.out.print("ce id pui in cos:");
        idprod = sc.nextLong();
        b = new Basket(idUser, idprod);
        dbBasketOperations.insert(b);
        showMenuOptions();
    }
    public static void displayBasket(Long idUser){
        // afisez cosului userului logat
        DbBasketOperations dbb = new DbBasketOperations();
        List<BasketDisplay> lb = dbb.readBasketOfAUser(idUser);
        for (BasketDisplay bask : lb) {
            System.out.println(bask);
        }
        // cer din nou cosul de la db
        lb = dbb.readBasketOfAUser(idUser);
        for (BasketDisplay bask : lb) {
            System.out.println(bask);
        }
        showMenuOptions();
    }
    public static void deleteProductFromBasket(Long idUser){

        // sterg din cos

        System.out.print("ce id sterg din cos:");
        Scanner sca = new Scanner(System.in);
        Long idCosDeSters = sca.nextLong();
        DbBasketOperations dbb = new DbBasketOperations();
        List<BasketDisplay> lb = dbb.readBasketOfAUser(idUser);
        dbb.deleteBasketItem(idCosDeSters);
        // cer din nou cosul de la db
        lb = dbb.readBasketOfAUser(idUser);
        for (BasketDisplay bask : lb) {
            System.out.println(bask);
        }
        showMenuOptions();
    }
    public static void endProgram(){
        System.out.println("==========");
        System.out.println("O zi buna!");
        System.out.println("==========");
        emag.start();
        //System.exit(0);
    }

}
