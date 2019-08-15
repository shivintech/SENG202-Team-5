package seng202.group5;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the menu, includes creating recipes and items, and removing items from the menu.
 */
public class MenuManager {
    private Map<String, MenuItem> itemList;

    /**
     *
     * @param ingredients   contains all the ingredients and their quantities required for the recipe
     * @param recipeText    the recipes instructions
     * @return              a Recipe
     */
    public Recipe createRecipe(Map<Ingredient, Int> ingredients, String recipeText) {
    }

    /**
     *
     * @param name      the name of the item
     * @param recipe    the recipe for this item
     * @param cost      the cost of this item
     * @param inMenu    true if the item is in the menu, false otherwise
     */
    public void createItem(String name, Recipe recipe, float cost, boolean inMenu) {

    }

    /**
     *
     * @param ID    the ID of the item
     * @return      true if the item is removed, false if the item does not exist
     */
    public boolean removeItem(String ID) {

    }
}