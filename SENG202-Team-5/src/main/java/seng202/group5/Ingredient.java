package seng202.group5;

/**
 * The Ingredient class records all the base data for each ingredient in the database which include its name, price, category,
 * id and its p[rice.
 *
 * @author Shivin Gaba
 */
public class Ingredient {

    /**
     * Name of the ingredient used in thge recipe
     **/
    private String name;
    /**
     * The quantity of a particular ingredient on hand
     **/
    private int unit;
    /**
     * Category that ingredient belongs to like poultry, met or bred.
     **/
    private String category;
    /**
     * Unique id used to identify every ingredient in the database
     **/
    private int id;
    /**
     * The price for a single unit of a ingredient
     **/
    private float price;
    /**
     * Boolean when True means the product is Gluten Free
     **/
    private boolean isGlutenFree;
    /**
     * Boolean when True means the ingredient is vegetarian
     */
    private boolean isVegetarian;
    /**
     * Boolean when True means the ingredient is vegan
     **/
    private boolean isVegan;


    Ingredient(String tempName, int tempUnit, String tempCategory, int tempId, float tempPrice) {

        name = tempName;
        unit = tempUnit;
        category = tempCategory;
        id = tempId;
        price = tempPrice;
    }

    Ingredient(String tempName, int tempUnit, String tempCategory, int tempId, float tempPrice, boolean glutenFree, boolean vegetarian, boolean vegan) {

        name = tempName;
        unit = tempUnit;
        category = tempCategory;
        id = tempId;
        price = tempPrice;
        isGlutenFree = glutenFree;
        isVegetarian = vegetarian;
        isVegan = vegan;

    }

    /**
     * Returns the name of the of the ingredient
     **/
    public String getName() {
        return name;
    }

    /**
     * Returns the number of units of the ingredient on hand
     **/
    public int getUnit() {
        return unit;
    }

    /**
     * Returns the category of the ingredient if its  a spice, meat or bread.
     **/
    public String getCategory() {
        return category;
    }

    /**
     * Returns the Unique id for every ingredient
     **/
    public int getId() {
        return id;
    }

    /**
     * This method sets the unique id for the new ingredient added to the stock
     *
     * @param someId Id for the new Ingredient added to the stock
     */
    public void setId(int someId) {
        id = someId;
    }

    /**
     * This method sets the name to the ingredient added to the stock
     *
     * @param someName name of the new ingredient
     */
    public void setName(String someName) {
        name = someName;
    }

    /**
     * @param someCategory Category for the newly added ingredient like drink,food,spice etc.
     */
    public void setCategory(String someCategory) {
        category = someCategory;
    }

    /**
     * This method sets the boolean True for the ingredient if it is vegan
     */

    public void setVegan() {
        isVegan = true;
    }

    /**
     * This method sets the boolean True for the ingredient if it is vegetarian
     */
    public void setVegetarian() {
        isVegetarian = true;
    }

    /**
     * This method sets the boolean True for the ingredient if it is gluten free
     */
    public void setGlutenFree() {
        isGlutenFree = true;
    }

    /**
     * Returns the cost of each ingredient
     **/
    public float getCost() {
        return price;
    }

    /**
     * Returns True if the ingredient is Vegan
     **/

    public boolean getGlutenFree() {
        return isGlutenFree;
    }

    /**
     * Returns True if the ingredient is Vegan
     **/

    public boolean getVegetarian() {
        return isVegetarian;
    }

    /**
     * Returns True if the ingredient is Vegan
     **/

    public boolean getVegan() {
        return isVegan;
    }

    /**
     * @param other ingredient that is compared to th current ingredient
     * @return True if the two compared ingredients are the same
     */
    public boolean equals(Ingredient other) {
        return id == other.id;
    }

}