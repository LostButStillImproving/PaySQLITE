package persistence;
import java.util.ArrayList;

public class TransactionQueryDataClass {

    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    ArrayList<String> fromUserName = new ArrayList<>();
    ArrayList<String> toUserName = new ArrayList<>();
    ArrayList<Double> amounts = new ArrayList<>();

    public void appendtimes(String time) {
        times.add(time);
    }

    public void appendFromUserNames(String username) {
        fromUserName.add(username);
    }

    public void appendToUserName(String username) {
        toUserName.add(username);
    }

    public void appendDates(String date) {
        dates.add(date);
    }

    public void appendAmounts(Double amount) {
        amounts.add(amount);
    }

    public String toString() {

        int numberOfTransactions = dates.size();
        StringBuilder toString = new StringBuilder();
        toString.append(String.format("%-15s %-15s %-15s %-15s %-1s  %n", "DATE", "TIME", "FROM", "TO", "AMOUNT"));
        toString.append("-------------------------------------------------------------------------\n");
        for (int i = 0; i < numberOfTransactions; i++) {
            toString.append(String.format("%-15s %-15s %-15s %-15s %-1s %n",
                    dates.get(i),
                    times.get(i),
                    fromUserName.get(i),
                    toUserName.get(i),
                    amounts.get(i)));
        }
        toString.append("-------------------------------------------------------------------------");

        return toString.toString();
    }
}