package seng202.group5;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author Yu Duan
 */
public class AppEnvironment {

    private OrderManager orderManager;
    private Finance finance;
    private Stock stock;
    private History history;
    private MenuManager menuManager;
    private HashSet<String> acceptedFiles;
    private Till till;


    /**
     * The constructor for AppEnvironment
     */
    public AppEnvironment() {
        finance = new Finance();
        stock = new Stock();
        history = new History();
        menuManager = new MenuManager();
        acceptedFiles = new HashSet<>();
        acceptedFiles.add("stock.xml");
        acceptedFiles.add("menu.xml");
        acceptedFiles.add("history.xml");
        acceptedFiles.add("finance.xml");
        acceptedFiles.add("till.xml");
    }

    /**
     * Marshals the given object o into a xml file.
     *
     * @param c        The class of the object o.
     * @param o        The object you want to marshal into xml file.
     * @param fileName The name of the xml file.
     */
    public void objectToXml(Class c, Object o, String fileName, String fileDirectory) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(c.cast(o), System.out); //print to sys out so we can view and check
            jaxbMarshaller.marshal(c.cast(o), new File(fileDirectory + "/" + fileName));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts the xml file to an object o.
     *
     * @return an object o.
     */
    public Object xmlToObject(Class c, Object o, String fileName, String fileDirectory) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(c);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            o = c.cast(jaxbUnmarshaller.unmarshal(new File(fileDirectory + "/" + fileName)));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Given the hash map containing ingredient ids and the quantity, search for the corresponding ingredient for each id in the stock and return a
     * hashmap containing the ingredient and quantity.
     *
     * @param IngredientIDs Contains a string as the ingredient id and the value as the quantity.
     * @return A new hash map containing the string ids replaced with ingredient objects, while the value of the hash map is the quantity.
     */
    public HashMap<Ingredient, Integer> getIngredientsFromID(HashMap<String, Integer> IngredientIDs) {
        HashMap<Ingredient, Integer> ingredients = new HashMap<Ingredient, Integer>();
        for (Map.Entry<String, Integer> entry : IngredientIDs.entrySet()) {
            String ID = entry.getKey();
            Integer quantity = entry.getValue();
            Ingredient ingredient = stock.getIngredients().get(ID);
            ingredients.put(ingredient, quantity);
        }
        return ingredients;
    }


    /**
     * Given the hash map containing all the menu items, search through each menu item and get access it's recipe and fill up the ingredientsAmount hash map with ingredient objects using
     * the getIngredientsFromID method.
     *
     * @param menuItems Contains the menu items.
     */
    public void handleMenu(HashMap<String, MenuItem> menuItems) {
        for (Map.Entry<String, MenuItem> entry : menuItems.entrySet()) {
            MenuItem menuItem = entry.getValue();
            Recipe recipe = menuItem.getRecipe();
            HashMap<String, Integer> ingredientIDs = menuItem.getRecipe().getIngredientIDs();
            HashMap<Ingredient, Integer> recipeIngredients = getIngredientsFromID(ingredientIDs);
            recipe.setIngredientsAmount(recipeIngredients);
        }
    }

    public void stockXmlToObject(String fileDirectory) {
        stock = (Stock) xmlToObject(Stock.class, stock, "stock.xml", fileDirectory);
    }

    public void allObjectsToXml(String fileDirectory) {
        objectToXml(Stock.class, stock, "stock.xml", fileDirectory);
        objectToXml(History.class, history, "history.xml", fileDirectory);
        objectToXml(Finance.class, finance, "finance.xml", fileDirectory);
        objectToXml(MenuManager.class, menuManager, "menu.xml", fileDirectory);
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }


    public HashSet<String> getAcceptedFiles() {
        return acceptedFiles;
    }

    public void setAcceptedFiles(HashSet<String> acceptedFiles) {
        this.acceptedFiles = acceptedFiles;
    }
}