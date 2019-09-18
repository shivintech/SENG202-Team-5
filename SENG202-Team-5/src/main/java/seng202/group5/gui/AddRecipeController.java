package seng202.group5.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seng202.group5.AppEnvironment;
import seng202.group5.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddRecipeController extends GeneralController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField makingPriceField;

    @FXML
    private TextField totalPriceField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Ingredient> ingredientsTable;

    @FXML
    private TableColumn<Ingredient, String> ingredientCol;

    @FXML
    private TableColumn<Ingredient, ComboBox> quantityCol;

    @Override
    public void pseudoInitialize() {
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList(
                getAppEnvironment().getStock().getIngredients().values());
        ingredientCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientsTable.setItems(ingredients);
        System.out.println(getAppEnvironment().getStock().getIngredients().get("13"));
    }

    public void saveRecipe() {
        String name = nameField.getText();
        String makingPrice = makingPriceField.getText();
        String totalPrice = totalPriceField.getText();
        closeScreen();

    }

    public void closeScreen() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }


}
