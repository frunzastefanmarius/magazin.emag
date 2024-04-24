package service;

import controller.BasketManagementService;
import controller.CategoryManagementService;
import controller.ProductManagementService;
import controller.UserManagementService;
import db.DbBasketOperations;
import db.DbProductsOperations;
import entity.*;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;

import java.util.Scanner;

public class emag {

    //aici fac metoda de inregistrare cu void

    Long idUser = -1L;//daca nu iese asa trimit parametrul idUser prin metoda
    //nu a iesit asa pentru ca am metode statice

    public static void start() {

        Scanner scanner = new Scanner(System.in);
        String input = null;
        do {
            System.out.println("Alegeti una din urmatoarele actiuni");
            System.out.println("1. Inregistrare");
            System.out.println("2. Logare");
            System.out.println("-------------------");

            try {
                input = scanner.nextLine();
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        register();
                        start();
                    case 2:
                        login();
                        start();
                    default:
                        System.out.println("Nu ai ales o optiune valida.");
                        start();
                }
            } catch (NumberFormatException e) {
                System.out.println("Te rugam sa alegi una din optiunile disponibile");
                start();
            }
        } while (input.equals(0));
    }

    public static void register() {
        boolean existentUser = false;
        System.out.println("Bine ai venit! Hai sa ne inregistram");

        do {
            Long idUser = null;
            existentUser = false;
            String usernameIntrodus;
            String pwdIntrodus;
            String username = null;
            String pwd = null;
            System.out.println("Te rugam sa introduci un username si o parola: ");
            Scanner sc = new Scanner(System.in);
            System.out.print("New Username:");
            usernameIntrodus = sc.nextLine();
            System.out.print("Pwd:");
            pwdIntrodus = sc.nextLine();

            if (usernameIntrodus.matches("[a-zA-Z]+")) {
                username = usernameIntrodus;
            } else {
                System.out.println("Username poate contine doar litere mari, litere mici si nu pot exista spatii. Te rugam sa incerci din nou");
                start();
            }
            if (pwdIntrodus.length() < 8 && pwdIntrodus.contains(" ")) {
                System.out.println("Parola trebuie sa fie mai lunga de 8 caractere si sa nu contina spatii.");
                start();
            } else {
                pwd = pwdIntrodus;
            }
            User u = new User(username, pwd);

//            DbUsersOperations db = new DbUsersOperations();
//
//            try {
//                db.insert(u);
//                System.out.println("Felicitari! Te-ai inregistrat cu succes, "+ username + "!");
//            } catch (SQLException e) {
//                existentUser = true;
//                System.out.println("Ne pare rau dar username " + username + " nu este disponibil. Te rugam sa incerci altul.");
//            }

            UserManagementService ums = new UserManagementService();

            try {
                ums.register(u);
                System.out.println("Felicitari! Te-ai inregistrat cu succes, " + username + "!");
            } catch (SQLException e) {
                existentUser = true;
                System.out.println("Ne pare rau dar username " + username + " nu este disponibil. Te rugam sa incerci altul.");
            }

        }
        while (existentUser);
    }

    public static Long login() {
        boolean loginSuccessfull = false;
        Long idUser = null;
        int attempts = 0;
        int maxAttempts = 3;
        String username;
        do {
            Scanner sc = new Scanner(System.in);
            System.out.print("Login User:");
            username = sc.nextLine();
            System.out.print("Pwd:");
            String pwd = sc.nextLine();
            User u = new User(username, pwd);
            UserManagementService ums = new UserManagementService();
            idUser = ums.login(u);

            if (loginSuccessfull) {
                attempts++;
                System.out.println("Mai aveti " + (maxAttempts - attempts) + " incercari.");
            }

        }
        while (idUser == null && attempts < maxAttempts);

        if (idUser == null) {
            System.out.println("Ati atins numarul maxim de incercari.");
        } else {
            loginSuccessfull = true;
            System.out.println("Salut " + username + "! Bine ai venit!");
        }
        if (loginSuccessfull) {
            showMenuOptions(idUser);
        }
        return idUser;
    }

    public static void showMenuOptions(Long idUser) {
        Scanner scanner = new Scanner(System.in);
        int userMenuOption;

        do {
            //aici user e deja logat si alege din meniu ce vrea sa faca
            System.out.println("Menu:");    //aici afisam pagina de "Home";
            System.out.println("1. Vizualizeaza lista de produse");
            System.out.println("2. Adauga un produs in cos");
            System.out.println("3. Afiseaza cosul de cumparaturi");
            System.out.println("4. Sterge un produs din cos");
            System.out.println("5. Afiseaza lista userilor");
            System.out.println("6. Adauga un produs");
            System.out.println("7. Afiseaza toate categoriile");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            userMenuOption = scanner.nextInt();
            DbBasketOperations dbb = new DbBasketOperations();
            boolean b = false;

            switch (userMenuOption) {
                case 1:
                    showAllProducts();
                    showMenuOptions(idUser);
                case 2:
                    addProductsInBasket(idUser);
                    showMenuOptions(idUser);
                case 3:
                    readBasket(idUser);
                    showMenuOptions(idUser);
                case 4:
                    deleteProductFromBasket(idUser);
                    showMenuOptions(idUser);
                case 5:
                    showAllUsers(b);
                    showMenuOptions(idUser);
                case 6:
                    addProducts(idUser);
                    showMenuOptions(idUser);
                case 7:
                    showAllCategories();
                    showMenuOptions(idUser);
                case 0:
                    endProgram();
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (userMenuOption != 0);
        start();
    }

    public static void showAllProducts() {
        System.out.println("Aceasta este lista de produse");
        // list products
        ProductManagementService pms = new ProductManagementService();
        List<ProductDisplay> lp = pms.showAllProducts();
        for (ProductDisplay p : lp) {
            System.out.println(p);
        }
    }

    public static void addProductsInBasket(Long idUser) {
        System.out.println("Adauga produse in cos:");
        Scanner sc = new Scanner(System.in);
        System.out.print("ce id pui in cos:");
        long idprod = sc.nextLong();

        Basket b = new Basket(idUser, idprod);

        BasketManagementService bms = new BasketManagementService();
        bms.insertInBasket(b);

    }

    public static void readBasket(Long idUser) {
        BasketManagementService bms = new BasketManagementService();
        List<BasketDisplay> lb = bms.readBasket(idUser);
        for (BasketDisplay basketOfCurrentUser : lb) {
            System.out.println(basketOfCurrentUser);
        }

    }

    public static void deleteProductFromBasket(Long idUser) {

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
    }

    public static void showAllUsers(boolean b) {
        int codUnic = 1234;
        System.out.println("Te rog sa introduci codul unic: ");
        Scanner sca = new Scanner(System.in);
        int codIntrodus = sca.nextInt();
        if (codIntrodus == codUnic) {
            b = true;
            UserManagementService ums = new UserManagementService();
            List<User> lu = ums.ShowAllUsers(b);

            for (User u : lu) {
                System.out.println(u);
            }
        } else {
            System.out.println("Codul introdus nu este corect!");
        }
    }

    public static void addProducts(Long idUser) {

        //aici trebuie sa adaugam partea de sout si sa completeze de la tastatura toate campurile necesare adaugarii produsului in DB
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Te rugam sa introduci datele necesare pentru a adauga produsul in cos.");
            System.out.println("nume produs: ");
            String name = sc.nextLine();
            System.out.println("descriere produs: ");
            String description = sc.nextLine();
            System.out.println("pret produs: ");
            Double price = Double.parseDouble(sc.nextLine());

            System.out.println("categorie produs: ");
            Long idCategoryIntrodus = Long.parseLong(sc.nextLine());
            Long idCategory;
            //trec prin toata lista de idCategory si daca gasesc idCategory introdus, il fac egal cu idCategory si merg mai departe

            if(true)
               idCategory=idCategoryIntrodus;
            Long iduser = idUser;
            Product p = new Product(name, description, price, iduser, idCategory);

            ProductManagementService pms = new ProductManagementService();
            pms.insert(p);
        } catch (NumberFormatException e) {
            System.out.println("Pretul nu poate contine litere.");
            addProducts(idUser);
        }

    }
    public static void showAllCategories(){
        System.out.println("Aceasta este lista de categorii:");

        CategoryManagementService cms = new CategoryManagementService();
        List<CategoryDisplay> lc = cms.showAllCategories();

        for (CategoryDisplay c : lc) {
            System.out.println(c);
        }
    }

    public static void endProgram() {
        System.out.println("==========");
        System.out.println("O zi buna!");
        System.out.println("==========");
        emag.start();
        //System.exit(0);
    }

//    public void pretProdus(String){
//
//    }

}
