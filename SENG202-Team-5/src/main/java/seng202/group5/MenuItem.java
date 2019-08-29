package seng202.group5;

import org.joda.money.Money;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains methods to update the stock, removes the stock , calculates the making and selling price for the menu item.
 * @author Shivin Gaba
 */
public class MenuItem {

    /**
     * Name of the dish/item on the menu.
     */
    private String itemName;
    /**
     * The recipe object that has the list of the ingredients involved to make that menu item
     */
    private Recipe recipe;
    /**
     * The actual cost it costs the producer to produce a particular recipe
     */
    private float productionCost;
    /**
     * The final cost of the menu item that after the mark up
     */
    private float sellingCost;
    /**
     * The unique id related to every item on the menu
     */
    private String id;
    /**
     * The ingredient that can be added or removed from the menu item.
     */
    private Ingredient someIngredeint;
    /**
     *The amount specifies how much of a particular ingredient needs to be removed or added.
     */
    private int amount;

    /**
     *
     * @param someItemName is the name of an item on the menu
     * @param someRecipe is the recipe for a an item on the menu
     * @param makingCost is the actual cost of production
     * @param markupCost is the final selling cost of the menu item
     * @param uniqueId is the unique id related to each menu item
     */

    MenuItem(String someItemName, Recipe someRecipe, float makingCost, float markupCost, String uniqueId){

        itemName = someItemName;
        recipe = someRecipe;
        productionCost = makingCost;
        sellingCost = markupCost;
        id = uniqueId;

    }

    MenuItem(String someItemName, Recipe someRecipe, float makingCost, float markupCost, String uniqueId, Ingredient randomIngredeint, int someAmount){

        itemName = someItemName;
        recipe = someRecipe;
        productionCost = makingCost;
        sellingCost = markupCost;
        id = uniqueId;
        someIngredeint = randomIngredeint;
        amount = someAmount;


    }

    /**
     * This method calls the addIngredient method in the Recipe class which takes the ingredient object and the amount as the input
     * and modifies the ingredientsAmount hash map accordingly.
     */
    public void addStock(){
        //ingredeintMapping.put(someIngredeint.getId(), someIngredeint);
        recipe.addIngredient(someIngredeint, amount);
    }

    /**
     * This method calls the removeIngredient method in the Recipe class which takes the ingredient object and the amount as the input
     * and modifies the ingredientsAmount hash map accordingly.This method is only called when a particular ingredient has to be fully
     * omitted from the recipe.
     */

    public void removeStock(){
        recipe.removeIngredient(someIngredeint, amount);
    }

    /**
     * This method calls the editIngredient method in the Recipe class which takes the ingredient object and the amount as the input
     * and modifies the ingredientsAmount hash map accordingly.
     */
    public void editStock() {
        recipe.editRecipe(someIngredeint, amount);
    }

    /**
     * This method provides access to the ingredientAmount hash map which holds the amount and the ingredient in a particular
     * Recipe which can be further used to calculate the cost of each menu item.
     */
    public HashMap getingredientMapping(){
        return recipe.getIngredientAmount();
    }

    /**
     * This method runs a loop over the ingredientAmount hash map and calculates the total cost of making a menu item in NZD
     * @return the making cost of the recipe in the form of the money object
     */
    public Money calculateMakingCost(){
        float recipeMakingCost= 0;
        HashMap<Ingredient, Integer> ingredients = getingredientMapping();
        for (Map.Entry<Ingredient, Integer> eachIngredient : ingredients.entrySet()) {
            Ingredient someIngredient = eachIngredient.getKey();
            //Ingredient i = ingredeintMapping.get(ingredintsID);
            Integer amount = eachIngredient.getValue();
            recipeMakingCost += amount*someIngredient.getCost();
        }
        return  Money.parse("NZD " + recipeMakingCost);

    }

    /**
     * This function adds a markup of 2.5 times the actual making cost of a menu item.
     * @return the selling cost of the menu item in the form of the Money object in NZD
     */

    public Money calulateFinalCost(){
        Money finalCost = calculateMakingCost();
        return(finalCost.multipliedBy(2.5, RoundingMode.DOWN));

    }

}
