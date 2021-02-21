import model.UserBuilder;
import persistence.TransactionHandler;
import persistence.TransactionQueryDataClass;
import persistence.UserHandler;
import persistence.UserQueryDataClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import static java.lang.Double.*;
import static java.lang.Integer.parseInt;

public class PaySQLITE {

    UserBuilder userBuilder = new UserBuilder();
    UserHandler userHandler;
    TransactionHandler transactionHandler;

    public PaySQLITE(UserHandler userHandler, TransactionHandler transactionHandler) {

            this.userHandler = userHandler;
            this.transactionHandler = transactionHandler;

    }

    public void addPersonUser(String personUserString) {

        var parametersForNewPersonUser = new ArrayList<>(
                Arrays.asList(personUserString.split(",")));
        try {
            var personUser = userBuilder.createPersonUser(parametersForNewPersonUser.get(0),
                    parseInt(parametersForNewPersonUser.get(1)),
                    parseDouble(parametersForNewPersonUser.get(2)),
                    parametersForNewPersonUser.get(3),
                    parametersForNewPersonUser.get(4),
                    parseInt(parametersForNewPersonUser.get(5)));
            try {
                userHandler.addUser(personUser);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            } catch (Exception e) {
                System.out.println("BAD INPUT");
        }

    }

    public void addCompanyUser(String companyUserString) {
        var parametersForNewCompany = new ArrayList<>(
                Arrays.asList(companyUserString.split(",")));
        try {
            var companyUser = userBuilder.createCompanyUser(parametersForNewCompany.get(0),
                    parseInt(parametersForNewCompany.get(1)),
                    parseDouble(parametersForNewCompany.get(2)),
                    parametersForNewCompany.get(3),
                    parametersForNewCompany.get(4),
                    parseInt(parametersForNewCompany.get(5)),
                    parseInt(parametersForNewCompany.get(6)));
            try {
                userHandler.addUser(companyUser);
                System.out.println("SUCCESSFULLY ADDED USER!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("BAD INPUT");
        }
    }

    public void deleteUser(String userName) {
        try {
            userHandler.deleteUser(userName);
            System.out.println("SUCCESSFULLY DELETED USER: " + userName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeTransaction(String transactionString) {

        var parametersForTransaction = new ArrayList<>(
                Arrays.asList(transactionString.split(",")));
        try {
            transactionHandler.transferMoneyFromUserToUser(parametersForTransaction.get(0),
                    parametersForTransaction.get(1),
                    parseDouble(parametersForTransaction.get(2)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public UserQueryDataClass getAllUserNamesAndBalances() {

        return userHandler.getAllUsers();
    }

    public TransactionQueryDataClass getAllTransactions() {

        return transactionHandler.getAllTransactions();
    }

    public void printTransactions() {

        System.out.println(getAllTransactions());
    }

    public void printUserNamesAndBalances() {

        System.out.println(getAllUserNamesAndBalances());
    }

    public void presentWelcomeScreen() {
        System.out.println("WELCOME ADMINISTRATOR,\n\nYOU HAVE FULL ACCESS TO ALL THE FEATURES, WHAT A HOOT, RIGHT?");
    }

    public void presentChoices() {
        System.out.println("WHAT WOULD YOU LIKE TO DO?\n\nPRESS THE KEY ASSOCIATED WITH EACH ACTION\nCREATE USER FOR COMPANY[1], CREATE USER FOR PERSON[2], DELETE USER[3],\nVIEW ALL USERNAMES AND BALANCES[4], VIEW FULL TRANSACTION HISTORY[5],MAKE TRANSACTION[6], EXIT[0]");
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        presentWelcomeScreen();

        while (true) {
            presentChoices();
            var choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("GOODBYE!");
                break;
            }
            if (choice == 1) {
                System.out.println("TO ADD A COMPANY USER, SUPPLY WITH COMMA-SEPARATED VALUES:\n\nuserName, phoneNumber, balance, String companyName, companyCountry, CVR, cardNumber ");
                scanner.nextLine();
                var companyUserParameterString = scanner.nextLine();
                addCompanyUser(companyUserParameterString);

            }
            if (choice == 2) {
                System.out.println("TO ADD A PERSON USER, SUPPLY WITH COMMA-SEPARATED VALUES:\n\n userName, phoneNumber, balance, firstName, String lastName, int cardNumber ");
                scanner.nextLine();
                var personUserParameterString = scanner.nextLine();
                addPersonUser(personUserParameterString);

            }
            if (choice == 3) {

                System.out.println("TO DELETE A USER, SUPPLY WITH USERNAME");
                scanner.nextLine();
                var userName = scanner.nextLine();
                try {
                    deleteUser(userName);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if (choice == 4) {
                printUserNamesAndBalances();
            }
            if (choice == 5) {
                printTransactions();
            }
            if (choice == 6) {
                System.out.println("TO MAKE TRANSACTION SUPPLY COMMA-SEPERATED TRANSACTION IN FORM:\nFROM_USER_NAME, TO_USER_NAME, AMOUNT");
                scanner.nextLine();
                var transactionString = scanner.nextLine();
                makeTransaction(transactionString);
            }
        }
    }


    public static void main(String[] args) {
        PaySQLITE paySQLITE = new PaySQLITE(new UserHandler(), new TransactionHandler());
        paySQLITE.start();

    }
}
