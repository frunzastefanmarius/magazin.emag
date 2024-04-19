import controller.UserManagementService;
import db.DbBasketOperations;
import db.DbProductsOperations;
import db.DbUsersOperations;
import entity.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
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
                    boolean existentUser = false;//TODO:de aici
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
                            existentUser = true;
                            System.out.println("nu se poate cu acest user deoarece este luat, reincearca");
                        }
                    }
                    while (existentUser);//TODO:pana aici
                    break;
                case 2:
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
                        UserManagementService um = new UserManagementService();
                        idUser = um.login(u);
                        attempts++;
                    }
                    while (idUser == null && attempts<maxAttempts);

                    if (idUser==null){

                    }
                    System.out.println("salut usere cu id:" + idUser);

                    int menuOption;

                    do {
                        //aici user e deja logat si alege din meniu ce vrea sa faca
                        System.out.println("Menu:");    //aici afisam pagina de "Home";
                        System.out.println("1. Vizualizeaza lista de produse");
                        System.out.println("2. Adauga un produs in cos");
                        System.out.println("3. Afisez cosul");
                        System.out.println("3. Sterge un produs din cos");
                        System.out.println("0. Exit");
                        System.out.print("Enter your choice: ");
                        menuOption = scanner.nextInt();
                        DbBasketOperations dbb = new DbBasketOperations();


                        switch (menuOption) {

                            case 1:
                                System.out.println("Aceasta este lista de produse");
                                // list products
                                DbProductsOperations db = new DbProductsOperations();
                                List<ProductDisplay> lp = db.readAllProducts();

                                for (ProductDisplay p : lp) {
                                    System.out.println(p);
                                }

                                break;
                            case 2:
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

                                break;
                            case 3:
                                // afisez cosului userului logat

                                List<BasketDisplay> lb = dbb.readBasketOfAUser(idUser);
                                for (BasketDisplay bask : lb) {
                                    System.out.println(bask);
                                }
                                // cer din nou cosul de la db
                                lb = dbb.readBasketOfAUser(idUser);
                                for (BasketDisplay bask : lb) {
                                    System.out.println(bask);
                                }
                                break;
                            case 4:

                                // sterg din cos

                                System.out.print("ce id sterg din cos:");
                                Scanner sca = new Scanner(System.in);
                                Long idCosDeSters = sca.nextLong();

                                dbb.deleteBasketItem(idCosDeSters);
                                // cer din nou cosul de la db
                                lb = dbb.readBasketOfAUser(idUser);
                                for (BasketDisplay bask : lb) {
                                    System.out.println(bask);
                                }
                                break;
                            case 0:
                                System.out.println("Multumim ca ai vizitat palicatia noastra. Te mai asteptam!");
                                break;
                            default:
                                System.out.println("Invalid choice. Please enter a valid option.");
                        }

                    } while (menuOption != 0);




            }

        } while (choice != 0);

    }
}
