package seng202.group5.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seng202.group5.AppEnvironment;
import seng202.group5.Ingredient;

import java.util.ArrayList;
import java.util.List;

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
    private TableColumn<Ingredient, Integer> quantityCol;

    private AppEnvironment appEnvironment;

    @FXML
    public void pseudoInitialize() {
        System.out.println(appEnvironment);
        List<Ingredient> ingredients = new ArrayList<>(appEnvironment.getStock().getIngredients().values());
        ingredientCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityCol.setCellFactory(col -> new SpinnerTableCell<>(1,100,1,1));
        ingredientsTable.setItems(FXCollections.observableArrayList(ingredients));

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

    public void setAppEnvironment(AppEnvironment input) {
        appEnvironment = input;
    }

}
