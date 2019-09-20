package seng202.group5.testXmlFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.AppEnvironment;
import seng202.group5.logic.Finance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FinanceXmlTest {

    AppEnvironment appEnvironment = new AppEnvironment();
    Finance finance;
    String testDirectory = System.getProperty("user.dir") + "\\src\\test\\java\\seng202\\group5\\testXmlFiles";

    @BeforeEach
    public void testUnmarshallFinance() {
        appEnvironment.financeXmlToObject(testDirectory);
        finance = appEnvironment.getFinance();
        assertEquals(1, finance.getTransactionHistory().size());
    }

    @Test
    public void testTransactionDateTimeIsInFinance() {
        LocalDateTime dateTime = finance.getTransactionHistory().get("12").getDateTime();
        assertTrue(dateTime instanceof LocalDateTime);
    }

    @Test
    public void testTransactionChangeIsInFinance() {
        String change = finance.getTransactionHistory().get("12").getChange().toString();
        assertEquals("NZD 0.00", change);
    }

    @Test
    public void testTransactionTotalPriceIsInFinance() {
        String totalPrice = finance.getTransactionHistory().get("12").getTotalPrice().toString();
        assertEquals("NZD 0.00", totalPrice);
    }

    @Test
    public void testTransactionIDIsInFinance() {
        String transactionId = finance.getTransactionHistory().get("12").getTransactionID();
        assertEquals("12", transactionId);
    }

    @Test
    public void testTransactionIsRefundedBooleanIsInFinance() {
        Boolean isRefunded = finance.getTransactionHistory().get("12").isRefunded();
        assertFalse(isRefunded);
    }

    @Test
    public void testTransactionOrderIdIsInFinance() {
        String orderId = finance.getTransactionHistory().get("12").getOrderID();
        assertEquals("11", orderId);
    }
}