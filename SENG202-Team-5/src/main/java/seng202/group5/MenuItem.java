package seng202.group5;

import org.joda.money.Money;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains methods to update the stock, removes the stock , calculates the making and selling price for the menu item.
 *
 * @author Shivin Gaba, James Kwok
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class    MenuItem {

    /**
     * Name of the dish/item on the menu.
     */
    private String itemName;
    /**
     * The recipe object that has the list of the ingredients involved to make that menu item
     */
    private Recipe recipe;
    /**
     * The final cost of the menu item that after the mark up
     */
    private Money markupCost;
    /**
     * The unique id related to every item on the menu
     */
    private String id;
    /**
     * Whether or not this item is in the menu
     */
    private boolean inMenu;


    MenuItem() {
    }

    /**
     * @param tempItemName is the name of an item on the menu
     * @param tempRecipe   is the recipe for a an item on the menu
     * @param tempMarkupCost   is the cost added to the ingredient cost of the menu item
     * @param uniqueId     is the unique id related to each menu item
     */

    public MenuItem(String tempItemName, Recipe tempRecipe, Money tempMarkupCost, String uniqueId, boolean tempInMenu) {
        itemName = tempItemName;
        recipe = tempRecipe;
        markupCost = tempMarkupCost;
        id = uniqueId;
        inMenu = tempInMenu;
    }


    /**
     * This method runs a loop over the ingredientAmount hash map and calculates the total cost of making a menu item in NZD
     *
     * @return the making cost of the recipe in the form of the money object in NZD
     */
    public Money calculateMakingCost() {
        Money recipeMakingCost = Money.parse("NZD 00.00");
        HashMap<Ingredient, Integer> ingredients = recipe.getIngredientsAmount();
        for (Map.Entry<Ingredient, Integer> eachIngredient : ingredients.entrySet()) {
            Ingredient ingredient = eachIngredient.getKey();
            Integer amount = eachIngredient.getValue();
            recipeMakingCost = recipeMakingCost.plus(ingredient.getCost().multipliedBy(amount));
        }
        return recipeMakingCost;
    }

    /**
     * This function adds a markup to the total cost of the ingredients based
     * on the markup cost
     *
     * @return the selling cost of the menu item in the form of the Money object in NZD
     */
    public Money calculateFinalCost() {
        return calculateMakingCost().plus(markupCost);

    }

    public boolean isInMenu() {
        return inMenu;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setMarkupCost(Money markupCost) {
        this.markupCost = markupCost;
    }

}





