package seng202.group5;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng202.group5.logic.OrderManager;
import seng202.group5.logic.Stock;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test for the methods defined in the  Menu Item class where
 * @author Shivin Gaba
 */

class MenuItemTest {

    private MenuItem testBurger;
    private Order testOrder;
    private OrderManager testOrderManager;
    private Stock testStock;
    private MenuItem m;
    private Recipe testRecipe;
    private Ingredient chickenPatty;
    private Ingredient cheese;

    @BeforeEach
    void init() {

        HashMap<Ingredient, Integer> ingredients = new HashMap<>();
        HashMap<String, Integer> ingredientStock = new HashMap<String, Integer>();
        HashMap<String, Order> transactionHistory = new HashMap<String, Order>();
        testRecipe = new Recipe("Cheeseburger", "It's raw.");
        String menuName = "Chicken burger";
        Money cost = Money.parse("NZD 5.00");
        String uniqueId = "23";
        boolean someInMenu = true;
        m = new MenuItem(menuName, testRecipe, cost, "1000", true);
       // Recipe testRecipe_1 = new Recipe("Chicken burger", "Steps to chicken burger");
        HashSet<DietEnum> ingredientInfo = new HashSet<>() {{
            add(DietEnum.GLUTEN_FREE);

        }};
        chickenPatty = new Ingredient("chicken", "kg", "meat", "12", Money.parse("NZD 5"), ingredientInfo);
        cheese = new Ingredient("cheese", "kg", "dairy", "12", Money.parse("NZD 2"), ingredientInfo);
        testRecipe.addIngredient(chickenPatty, 2);
        testRecipe.addIngredient(cheese, 1);
    }

    /**\
     * The below test checks if the  calculateMakingcost method works as anticipated.
     */

    @Test
        void testCalculateMakingCost(){

            Money makingCost = m.calculateMakingCost();
            Money actualCost = Money.parse("NZD 12.00");
            Money actualCost_1 = Money.parse("NZD 10.00");
            assertTrue(makingCost.equals(actualCost));
            assertEquals(makingCost.getAmount(), actualCost.getAmount());
            testRecipe.removeIngredient(cheese);
            HashMap<Ingredient, Integer> ingredients = testRecipe.getIngredientsAmount();
            makingCost = m.calculateMakingCost();
            assertEquals(makingCost.getAmount(), actualCost_1.getAmount());
    }

    /**
     * The below test checks if the the calculate final cost method works the same way as anticipated.
     */

        @Test
        void calculateFinalCost(){
            Money markupCost = Money.parse("NZD 5.00");
            Money makingCost = m.calculateMakingCost();
            Money sellingCost = m.calculateFinalCost();
            Money finalCost = Money.parse("NZD 17");
            assertEquals(sellingCost.getAmountMajorInt(),finalCost.getAmountMajorInt());
            testRecipe.removeIngredient(chickenPatty,1);
            Money updatedSellingCost = m.calculateFinalCost();
            Money updatedFinalCost = Money.parse("NZD 12");
            assertEquals(updatedSellingCost.getAmountMajorInt(),updatedFinalCost.getAmountMajorInt());
            testRecipe.removeIngredient(chickenPatty);
            Money updatedSellingCost_2 = m.calculateFinalCost();
            Money updatedFinalCost_2 = Money.parse("NZD 7");
            assertEquals(updatedSellingCost.getAmountMajorInt(),updatedFinalCost.getAmountMajorInt());

    }
}
