package seng202.group5;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import org.joda.money.Money;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import seng202.group5.exceptions.InsufficientCashException;

/**
 * Finance class records order history, refunds past orders and calculates change.
 */
public class Finance {

    private HashMap<String, Transaction> transactionHistory;
    private ArrayList<Money> denomination;
    /**
     * Temporary id generator for testing purposes.
     */
    private int tempId;

    public Finance() {
        tempId = 0;
        transactionHistory = new HashMap<>();
        denomination = new ArrayList<>();
        denomination.add(Money.parse("NZD 50.00"));
        denomination.add(Money.parse("NZD 20.00"));
        denomination.add(Money.parse("NZD 10.00"));
        denomination.add(Money.parse("NZD 5.00"));
        denomination.add(Money.parse("NZD 2.00"));
        denomination.add(Money.parse("NZD 1.00"));
        denomination.add(Money.parse("NZD 0.50"));
        denomination.add(Money.parse("NZD 0.20"));
        denomination.add(Money.parse("NZD 0.10"));
    }

    /**
     * Refunds a previous order and returns the list of notes to return in descending size order.
     *
     * @param ID the id of the order to refund
     * @return a list of doubles representing money in descending size order
     */
    public ArrayList<Money> refund(String ID) {
        Transaction refundedOrder = transactionHistory.get(ID);
        Money refund = Money.parse("NZD 0");
        if (!refundedOrder.getRefunded()) {
            refundedOrder.Refund();
            refund = refundedOrder.getTotalPrice();
        }
        return calcChange(refund);
    }

    /**
     * Saves order to database and returns a list of notes to return as change.
     *
     * @param totalCost   the total cost of the order
     * @param amountPayed a lost of doubles representing money in the denominations payed
     * @param time        the Date and time the order occurred at
     * @return a list of doubles representing money in descending size order
     * @throws InsufficientCashException Throws error when total cost is negative or the total cost is higher than the amount payed
     */
    public ArrayList<Money> pay(Money totalCost, ArrayList<Money> amountPayed, int time) throws InsufficientCashException {
        // the time probably needs to be a long instead
        Money payedSum = Money.parse("NZD 0");
        Money changeSum = Money.parse("NZD 0");
        DateTime date =  new DateTime(DateTimeZone.UTC);
        for (Money money: amountPayed)
        {
            payedSum = payedSum.plus(money);
        }
        if (totalCost.isGreaterThan(payedSum) || totalCost.isNegative()) {
            throw new InsufficientCashException();
        }
        ArrayList<Money> change = calcChange(payedSum.minus(totalCost));
        for (Money money: change)
        {
            changeSum = changeSum.plus(money);
        }
        transactionHistory.put("test"+tempId++ , new Transaction(date, time, changeSum, totalCost));
        return change;
    }

    /**
     * returns a list containing total profits, average profits, and other information to be displayed on the finance screen over the imputed time period
     *
     * @param startDate the first date to search from
     * @param endDate   the last date to search to
     * @return a list of doubles representing  total profits, average profits, and other things
     */
    public ArrayList<Money> totalCalculator(DateTime startDate, DateTime endDate) {
        Money total = Money.parse("NZD 0");
        for (Transaction order : transactionHistory.values()) {
            if (order.getDate().compareTo(startDate) >= 0 && order.getDate().compareTo(endDate) <= 0) {
                total = total.plus(order.getTotalPrice());
            }
        }
        ArrayList<Money> totals = new ArrayList<>();
        totals.add(total);
        long daysBetween = (Days.daysBetween(startDate.toLocalDate(), endDate.toLocalDate()).getDays()) + 1;
        totals.add(total.dividedBy(daysBetween, RoundingMode.DOWN));
        System.out.println(totals);
        return totals;
    }
    /**
     * returns a list containing the change need to be returned
     *
     * @param change the first date to search from
     * @return returns a list containing the change need to be returned
     */
    public ArrayList<Money> calcChange(Money change) {


        ArrayList<Money> totalChange = new ArrayList<>();
        change = change.plus(Money.parse("NZD 0.03"));

        Money minimin = Money.parse("NZD 0.09");
        while (change.isGreaterThan(minimin)) {
            // Could this be done with a sorted list of denominations instead?
            // There is a lot of repeated code
            for (Money value: denomination)
            {
                while (change.isGreaterThan(value)) {

                    totalChange.add(value);
                    change = change.minus(value);
                }
            }
        }
        return totalChange;
    }

}
