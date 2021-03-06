package seng202.group5.gui;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seng202.group5.information.Ingredient;
import seng202.group5.information.MenuItem;
import seng202.group5.information.Recipe;
import seng202.group5.information.TypeEnum;
import seng202.group5.logic.Order;

import java.io.FileInputStream;
import java.util.*;

/**
 * This controller handles all the functionality with the making a new order or
 * editing an existing order.
 *
 * @author Shivin Gaba
 */
public class OrderController extends GeneralController {

    private MenuItem item;

    @FXML
    private Label totalCostDisplay;

    @FXML
    private CheckBox vegan;

    @FXML
    private CheckBox vegetarian;

    @FXML
    private CheckBox glutenFree;

    @FXML
    private CheckBox mains;

    @FXML
    private CheckBox sides;

    @FXML
    private CheckBox beverages;

    @FXML
    private Text orderIDText;

    @FXML
    private TableView<Ingredient> ingredientInfoTable;

    @FXML
    private TableColumn<Ingredient, String> ingredientNameCol;

    @FXML
    private TableColumn<Ingredient, String> ingredientQuantityCol;

    @FXML
    private Text recipeText;

    @FXML
    private Text costText;

    @FXML
    private Text menuItemName;

    @FXML
    private JFXButton modifyIngredientsButton;

    @FXML
    private TilePane tilePane;

    @FXML
    private JFXComboBox<SortType> sortingBox;

    @FXML
    private Text promptText;

    @FXML
    private AnchorPane tilePaneContainer;

    @FXML
    private TableView<MenuItem> currentOrderTable;

    @FXML
    private TableColumn<MenuItem, String> itemNameCol;

    private Order currentOrder;

    private ArrayList<MenuItem> allItems;

    private Map<MenuItem, Integer> orderItemsMap;

    @FXML
    private TableColumn<MenuItem, String> itemQuantityCol;

    @FXML
    private Button removeItemButton;

    @FXML
    private TableColumn<MenuItem, String> itemPriceCol;
    private SortType sortingType = SortType.NAME;

    @Override
    public void pseudoInitialize() {
        super.pseudoInitialize();
        allItems = new ArrayList<>();
        allItems.addAll(getAppEnvironment().getMenuManager().getMenuItems().values());
        filterItems();
        currentOrder = getAppEnvironment().getOrderManager().getOrder();
        sortingBox.setItems(FXCollections.observableArrayList(SortType.values()));
        sortingBox.setValue(sortingType);

        currentOrderTable();
        orderIDText.setText(currentOrder.getId());

        tilePaneContainer.widthProperty().addListener((width) -> {
            double newWidth = tilePaneContainer.getWidth();
            tilePane.setMinWidth(newWidth);
            tilePane.setPrefWidth(newWidth);
            tilePane.setMaxWidth(newWidth);
        });
        modifyIngredientsButton.setDisable(true);
        currentOrderTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue == null) {
                recipeText.setText("");
                ingredientInfoTable.getItems().clear();
            } else {
                item = newValue;
                recipeText.setText(newValue.getRecipe().getRecipeText());
                populateIngredientsTable();
                modifyIngredientsButton.setDisable(false);
            }
        }));
    }

    /**
     * This function sorts the menu item by its price
     *
     * @param event an event that caused this to happen
     */

    public void sortItems(ActionEvent event) {
        sortingType = sortingBox.getValue();
        filterItems();
    }

    /**
     * Adds all the menu items in the menu to the tile pane
     *
     * @param items the items to add to the pane
     */
    public void populateTilePane(Collection<MenuItem> items) {
        if (tilePane != null) {
            tilePane.setPrefTileWidth(304);
            tilePane.setPrefTileHeight(200);
            ObservableList<Node> buttons = tilePane.getChildren();
            buttons.clear();
            ArrayList<MenuItem> sortedItems = new ArrayList<>(items);

            if (sortingType == SortType.NAME) {
                sortedItems.sort(Comparator.comparing(MenuItem::getItemName));
            } else if (sortingType == SortType.PRICE) {
                sortedItems.sort(Comparator.comparing(MenuItem::getTotalCost));
            }

            for (MenuItem item : sortedItems) {
                JFXButton tempButton = new JFXButton(item.getItemName());
                tempButton.setContentDisplay(ContentDisplay.TOP);
                tempButton.setStyle("-fx-font-size: 20; ");
                tempButton.setPrefWidth(304);
                tempButton.setPrefHeight(200);
                tempButton.setMaxWidth(304);
                tempButton.setMaxHeight(200);
                ImageView imageView = new ImageView(getItemImage(item));
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(144);
                imageView.setFitWidth(256);

                tempButton.setGraphic(imageView);
                TilePane.setMargin(tempButton, new Insets(5));
                tempButton.setOnAction((ActionEvent event) -> addItemToOrder(item));
                buttons.add(tempButton);
            }
        }

    }

    /**
     * Filters the menu items in the menu based on the check boxes on the order screen
     */
    public void filterItems() {
        ArrayList<MenuItem> filteredMenuItems = new ArrayList<>();
        if (allItems != null) {
            filteredMenuItems = new ArrayList<>(allItems);
        }
        ArrayList<MenuItem> filteredItems = new ArrayList<>(filteredMenuItems);


        if (glutenFree.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (!item.getRecipe().isGlutenFree()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (vegan.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (!item.getRecipe().isVegan()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (vegetarian.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (!item.getRecipe().isVegetarian()) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (!mains.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (item.getItemType() == TypeEnum.MAIN) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (!sides.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (item.getItemType() == TypeEnum.SIDE) {
                    filteredMenuItems.remove(item);
                }
            }
        }
        filteredItems = new ArrayList<>(filteredMenuItems);
        if (!beverages.isSelected()) {
            for (MenuItem item : filteredItems) {
                if (item.getItemType() == TypeEnum.BEVERAGE) {
                    filteredMenuItems.remove(item);
                }
            }
        }

        populateTilePane(filteredMenuItems);

    }

    private Image getItemImage(MenuItem item) {
        Image itemImage = new Image(getClass().getResourceAsStream("/images/default.jpg"));
        try {

            itemImage = new Image(new FileInputStream(getAppEnvironment().getImagesFolderPath() + "/" + item.getImageString()));
        } catch (Exception ignored) {

        }
        return itemImage;
    }

    /**
     * This function removes the selected item from the current order when clicked on the remove button and updates the invoice display
     */
    @FXML
    private void removeSelectedItem() {
        currentOrder.removeItem(currentOrderTable.getSelectionModel().getSelectedItem(), true);
        boolean someOrder = this.currentOrderTable.getItems().remove(this.currentOrderTable.getSelectionModel().getSelectedItem());
        if (someOrder) removeItemButton.setDisable(false);
    }

    /**
     * Updates the display with items using the selected item.
     *
     * @param newItem the new item with updated quantities and categories.
     */
    public void setMenuItem(MenuItem newItem) {
        item = newItem;
        for (int i = 0; i < currentOrderTable.getItems().size(); i++) {
            if (currentOrderTable.getItems().get(i).equals(newItem))
                currentOrderTable.getSelectionModel().select(i);
        }
        totalCostDisplay.setText(currentOrder.getTotalCost().toString());
    }

    /**
     * This method adds the selected menu Item to the stock only if the valid amount of ingredients are available.
     * Otherwise displays the appropriate message if the order can/cannot be added.
     * Calls setMenuItem() if adding the item is successful.
     */
    public void addItemToOrder(MenuItem item) {
        if (currentOrder.addItem(item, 1)) {
            promptText.setText(item.getItemName() + " was added to the current order.");
            promptText.setFill(Color.GREEN);
            currentOrderTable();
            setMenuItem(item);
        } else {
            promptText.setText("Some ingredients are low in stock!\n" + item.getItemName() + " was not added to the current order");
            promptText.setFill(Color.RED);
        }
        totalCostDisplay.setText(currentOrder.getTotalCost().toString());
    }

    /**
     * This method shows the amount of ingredients in a selected menu item
     */

    public void populateIngredientsTable() {
        Recipe currentRecipe = item.getRecipe();
        Map<Ingredient, Integer> recipeIngredientsMap = currentRecipe.getIngredientsAmount();
        List<Ingredient> recipeIngredients = new ArrayList<>(recipeIngredientsMap.keySet());
        ingredientNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientQuantityCol.setCellValueFactory(data -> {
            int quantity = recipeIngredientsMap.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));
        });
        ingredientInfoTable.setItems(FXCollections.observableArrayList(recipeIngredients));
        ingredientInfoTable.refresh();
        ingredientInfoTable.getSortOrder().add(ingredientNameCol);
        ingredientInfoTable.sort();
    }

    /**
     * This function populates the mini invoice screen with the selected menu items along with their quantity which are the part of the current order
     */

    public void currentOrderTable() {
        orderItemsMap = currentOrder.getOrderItems();
        List<MenuItem> orderItems = new ArrayList<>(orderItemsMap.keySet());
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemQuantityCol.setCellValueFactory(data -> {
            int quantity = orderItemsMap.get(data.getValue());
            return new SimpleStringProperty(Integer.toString(quantity));

        });

        currentOrderTable.setItems(FXCollections.observableArrayList(orderItems));
        currentOrderTable.refresh();
    }

    /**
     * This function clears whole order when clicked on the cancel button on the mini invoice screen.
     */
    @FXML
    private void cancelCurrentOrder() {
        currentOrder = getAppEnvironment().getOrderManager().getOrder();
        currentOrder.setStock(getAppEnvironment().getStock());
        currentOrder.clearItemsInOrder();
        pseudoInitialize();
    }

    /**
     * Changes screen to the add ingredient screen to select ingredients to add to the recipe by passing in the item.
     *
     * @param actionEvent an event that caused this to happen
     */
    public void addExtraIngredientScreen(ActionEvent actionEvent) {
        AddExtraIngredientController controller =
                (AddExtraIngredientController) changeScreen(actionEvent, "/gui/addExtraIngredient.fxml");
        MenuItem currentItem = currentOrderTable.getSelectionModel().getSelectedItem();
        controller.setMenuItem(currentItem);
        controller.setCurrentOrder(currentOrder);
        controller.updateStock();
        controller.setOpenMode("Order");
        controller.initializeTable();
    }

    /**
     * A method to set the current order for the alternate order screen in history
     *
     * @param order the order to set the current order to
     */
    protected void setCurrentOrder(Order order) {
        currentOrder = order;
        currentOrderTable();
    }

    /**
     * A method to get the selected item for the alternate order screen in history
     *
     * @return the selected item
     */
    protected MenuItem getSelectedItem() {
        return item;
    }

    private enum SortType {
        NAME,
        PRICE;

        public String toString() {
            switch (this) {
                case NAME:
                    return "Name";
                case PRICE:
                    return "Price";
                default:
                    return "";
            }
        }
    }


}