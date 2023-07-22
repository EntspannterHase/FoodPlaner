import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DishEditingBox extends VBox {

    /**
     * A wrapper class to safe the references of the radiobutton combination.
     */
    static class Tuple {
        Ingredient ingredient;
        RadioButton btn;
        TextField input;

        Tuple(Ingredient ingredient, RadioButton btn, TextField input) {
            this.ingredient = ingredient;
            this.btn = btn;
            this.input = input;
        }
    }

    /**
     * The constructor of the DishEditingBox
     * @param parent
     * @param dish
     * @param button
     */
    public DishEditingBox(DishManagerPane parent, Dish dish, Button button) {
        setId("DishEditingBox");

        final TextField nameInput = new TextField();
        nameInput.setText(dish.getName());

        // graphical
        final ScrollPane ingredientScrollPane = new ScrollPane();
        final VBox ingredientBox = new VBox();
        ingredientBox.setId("List");
        ingredientScrollPane.setContent(ingredientBox);
        ingredientScrollPane.setFitToWidth(true);
        ingredientScrollPane.setFitToHeight(true);

        // tuples to safe references
        final Tuple[] tuples = loadIngredientsAndGetTuples(dish, ingredientBox);

        // Buttons at the bottom
        final Button btn_edit = new Button("speichern"),
                btn_cancel = new Button("abbrechen"),
                btn_remove = new Button("verwerfen");
        final HBox btn_wrapper = new HBox(btn_edit, btn_remove, btn_cancel);

        // add function to the buttons
        btn_edit.setOnAction(x -> {
            dish.setName(nameInput.getText());
            button.setText(dish.getName());

            safeChanges(dish, tuples, nameInput);

            parent.goBack();
        });
        btn_remove.setOnAction(x -> {
            parent.remove(dish, button);
            parent.goBack();

        });
        btn_cancel.setOnAction(x -> {
            parent.goBack();
        });

        // add everything to the layer
        getChildren().addAll(nameInput, ingredientScrollPane, btn_wrapper);
    }

    /**
     * Refactored method to get a better view throw the code
     * @param dish
     * @param ingredientBox
     * @return
     */
    private Tuple[] loadIngredientsAndGetTuples(Dish dish, VBox ingredientBox){
        final int size = Ingredient.ingredients.size();
        final Tuple[] tuples = new Tuple[size];

        // Error, because there are no ingredients
        if (size == 0) {
            final Label label = new Label("Es gibt noch keine Zutaten. Füge welche hinzu");
            ingredientBox.getChildren().add(label);
            return tuples;
        }

        // create a tuple for every existing ingredient
        int i = 0;
        for(Ingredient ingredient : Ingredient.ingredients) {
            final HBox box = new HBox();
            box.setId("ingredient_wrapper");
            final RadioButton btn = new RadioButton(ingredient.getName());
            final TextField amountInput = new TextField();
            final Label label = new Label(ingredient.getUnit());
            box.getChildren().addAll(btn, amountInput, label);

            // only selects the radiobutton if the ingredient is part of the dish
            double amount = dish.getIngredientAmount(ingredient);
            if (amount != 0d) {
                btn.setSelected(true);
                amountInput.setText(String.valueOf(amount));
            }

            // add to changeTupleList
            tuples[i] = new Tuple(ingredient, btn, amountInput);

            ingredientBox.getChildren().addAll(box);
            i++;
        }
        return tuples;
    }

    /**
     *
     * @param dish
     * @param tuples
     * @param nameInput
     */
    private void safeChanges(Dish dish, Tuple[] tuples, TextField nameInput) {
        for (Tuple tuple : tuples) {
            if (tuple.btn.isSelected()) {
                double value = 1d;
                try {
                    value = Double.parseDouble(tuple.input.getText());
                    if (value < 0) value = 1d;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } finally {
                    nameInput.setText("" + value);
                }
                dish.set(tuple.ingredient, value);
                System.out.println(value + " " + tuple.ingredient);
            }
        }
    }
}
