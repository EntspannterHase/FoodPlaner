import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IngredientEditingBox extends VBox {
    public IngredientEditingBox(IngredientManagerPane parent, Ingredient ingredient, Button button) {
        setId("IngredientEditingBox");

        TextField in_name = new TextField(), in_unit = new TextField();
        in_name.setText(ingredient.getName());
        in_unit.setText(ingredient.getUnit());
        Button btn_edit = new Button("speichern"), btn_cancel = new Button("abbrechen"), btn_remove = new Button("verwerfen");
        HBox btn_wrapper = new HBox();
        btn_wrapper.getChildren().addAll(btn_edit, btn_remove, btn_cancel);
        getChildren().addAll(in_name, in_unit, btn_wrapper);

        btn_edit.setOnAction(x -> {
            ingredient.setName(in_name.getText());
            ingredient.setUnit(in_unit.getText());
            button.setText(ingredient.getName());
            parent.goBack();
        });
        btn_remove.setOnAction(x -> {
            parent.remove(ingredient, button);
            parent.goBack();
        });
        btn_cancel.setOnAction(x -> {
            parent.goBack();
        });
    }
}
