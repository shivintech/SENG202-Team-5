package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.Test;
import seng202.group5.logic.Stock;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Transaction;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class IDGeneratorTest {

    private IDGenerator generator = new IDGenerator();

    @Test
    public void testUniqueIDs() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add(generator.newID());
        }

        HashSet<String> set = new HashSet<String>(list);
        assertEquals(set.size(), list.size());
    }

    @Test
    public void testClassIDs() {
        Ingredient ingredient = new Ingredient(null, null, null, Money.parse("NZD 0.10"));
        Order order = new Order(new Stock());
        MenuItem item = new MenuItem(null, null, Money.parse("NZD 0.10"), false);
        Transaction transaction = new Transaction(null, null, null, null);

        ArrayList<String> ids = new ArrayList<String>();
        ids.add(ingredient.getID());
        ids.add(order.getID());
        ids.add(item.getID());
        ids.add(transaction.getTransactionID());

        for (int i = 0; i < 4; i++) {
            for (String idd : ids) {
                if (ids.indexOf(idd) != i) {
                    assertNotEquals(ids.get(i), idd);
                }
            }
        }
    }

    @Test
    public void testSetId() {
        String lastID = "1";
        generator.setLastID(lastID);

        assertTrue(generator.newID() != lastID);
    }

}
