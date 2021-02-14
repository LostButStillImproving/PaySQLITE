import model.UserBuilder;
import persistence.TransactionHandler;
import persistence.UserHandler;

import java.sql.SQLException;
import java.util.HashMap;

public class PaySQLITE {

    UserBuilder userBuilder = new UserBuilder();
    UserHandler userHandler;
    TransactionHandler transactionHandler;

    public PaySQLITE(UserHandler userHandler, TransactionHandler transactionHandler) {

            this.userHandler = userHandler;
            this.transactionHandler = transactionHandler;
    }

    public void addPersonUser(String userName, int phoneNumber, double balance,
                                     String firstName, String lastName, int cardNumber) throws SQLException {

        var personUser = userBuilder.createPersonUser(userName, phoneNumber, balance,
                                                             firstName, lastName,cardNumber);

        userHandler.addUser(personUser);
    }

    public void addCompanyUser(String userName, int phoneNumber, double balance, String name,
                               String country, int CVR, int cardNumber) throws SQLException {

        var companyUser = userBuilder.createCompanyUser(userName, phoneNumber, balance, name,
                                                                country, CVR, cardNumber);

        userHandler.addUser(companyUser);
    }

    public void makeTransaction(String userNameFrom, String userNameTo, double amount) throws SQLException {

        transactionHandler.transferMoneyFromUserToUser(userNameFrom, userNameTo, amount);
    }

    public HashMap<String, Double> getAllUserNamesAndBalances() {


        var users = userHandler.getAllUsers();
        var userNames = users.getUserNames();
        var balances = users.getBalances();

        var userNamesBalancesHashMap = new HashMap<String, Double>();

        for (int i = 0; i < userNames.size(); i++) {
            userNamesBalancesHashMap.put(userNames.get(i), balances.get(i));
        }

        return userNamesBalancesHashMap;
    }

    public void printUserNamesAndBalances(HashMap<String, Double> userNamesAndBalances) {
        System.out.println();
        userNamesAndBalances.forEach((k,v) -> System.out.println(k + "   " + v));

    }

    public static void main(String[] args) throws SQLException {
    PaySQLITE paySQLITE = new PaySQLITE(new UserHandler(), new TransactionHandler());
    //paySQLITE.addPersonUser("mike", 222, 200., "mike", "rasmussen",
      //      2323);
    paySQLITE.makeTransaction("mike", "To", 1000000);
    paySQLITE.printUserNamesAndBalances(paySQLITE.getAllUserNamesAndBalances());
    }
}
