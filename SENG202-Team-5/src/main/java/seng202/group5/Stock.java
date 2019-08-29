package seng202.group5;

import javax.xml.bind.annotation.*;
import java.util.HashMap;


/**
 * The Stock holds all ingredients in the database and methods to modify those ingredients.
 *
 * @author Michael Morgoun, Daniel Harris, Yu Duan
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Stock {


    private HashMap<String, Ingredient> ingredients;

    private HashMap<String, Integer> ingredientStock;

    /**
     * The builder for the Stock object.
     *
     * @param tempIngredientStock An initial stock for the object, leave empty if there is none.
     */
    public Stock(HashMap<String, Ingredient> tempIngredients, HashMap<String, Integer> tempIngredientStock) {
        ingredients = tempIngredients;
        ingredientStock = tempIngredientStock;
    }


    /**
     * The builder for the Stock object if there is no initial stock.
     */
    public Stock() {
        ingredients = new HashMap<String, Ingredient>();
        ingredientStock = new HashMap<String, Integer>();
    }


    /**
     * Adds an ingredient to the stock with a given id, unit, category and quantity.
     *
     * @param ingredient The ingredient to add
     * @param quantity   The initial quantity of the ingredient, leave empty if 0.
     */
    public void addNewIngredient(Ingredient ingredient, int quantity) {
        if (ingredients.containsKey(ingredient.getId())) {
            return;
        }

        ingredients.put(ingredient.getId(), ingredient);
        ingredientStock.put(ingredient.getId(), quantity);
    }


    /**
     * Adds an ingredient to the stock with a given ID, unit, category and with a quantity initialised to 0.
     *
     * @param ingredient The ingredient to add
     */
    public void addNewIngredient(Ingredient ingredient) {
        int quantity = 0;
        addNewIngredient(ingredient, quantity);
    }


    /**
     * Modifies the quantity of an ingredient already in the stock by changing the parameter quantity to the current
     * quantity. Returns a boolean true or false as to whether it was successful or not.
     *
     * @param id       The unique ID of the ingredient.
     * @param quantity The new quantity of that ingredient.
     * @return A boolean true/false on whether the change was successful.
     */
    public boolean modifyQuantity(String id, int quantity) {
        if (ingredientStock.containsKey(id)) {
            ingredientStock.replace(id, quantity);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Ingredient stock getter.
     *
     * @return The HashMap ingredientStock
     */
    public HashMap<String, Integer> getIngredientStock() {
        return ingredientStock;
    }


    /**
     * Returns a specific ingredients quantity.
     *
     * @param id The ID of the specific ingredient
     * @return The quantity of the ingredient
     */
    public int getIngredientQuantity(String id) {
        if (ingredientStock.containsKey(id)) {
            return ingredientStock.get(id);
        } else {
            return 0;
        }
    }

    public HashMap<String, Ingredient> getIngredients() {
        return ingredients;
    }

    public Ingredient getIngredientFromID(String id) {
        if (ingredients.containsKey(id)) {
            return ingredients.get(id);
        } else {
            return null;
        }
    }

    public void setIngredients(HashMap<String, Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredientStock(HashMap<String, Integer> ingredientStock) {
        this.ingredientStock = ingredientStock;
    }

}
