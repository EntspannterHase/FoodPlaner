import javafx.scene.control.Button;

import java.io.*;

/**
 * @author EntspannterHase
 */
public class IngredientManagerPane extends ManagerPane {

    public IngredientManagerPane() {
        super("Zutaten", "ingredients", ".igd", x -> {
            Ingredient ingredient = (Ingredient) x;
            Ingredient.updateIngredientList(ingredient);
        });
    }

    // --- INITIALISATION ---

    /**
     * This method creates a new Ingredient.
     *
     * @param name The name of the new ingredient.
     * @param unit The unit of the new ingredient.
     * @return the editingbox for the new ingredient
     */
    public IngredientEditingBox createIngredient(String name, String unit) {
        Ingredient ingredient = new Ingredient(name, unit);
        Button button = createButton(ingredient);
        IngredientEditingBox editingBox = new IngredientEditingBox(this, ingredient, button);
        button.setOnAction(x -> getChildren().add(editingBox));
        return editingBox;
    }

    @Override
    public void initAddButton() {
        btn_add.setOnAction(x -> {
            IngredientEditingBox editingBox = createIngredient("Neue Zutat", "Gramm");
            getChildren().add(editingBox);
        });
    }

    @Override
    public void initButtons() {
        for (Ingredient ingredient : Ingredient.ingredients) {
            Button button = createButton(ingredient);
            button.setOnAction(x -> getChildren().add(new IngredientEditingBox(this, ingredient, button)));
        }
    }

    // --- FUNCTIONAL ---
    @Override
    public void remove(Dishable dishable, Button button) {
        // check if the ingredient isnt part of a dish
        Ingredient.ingredients.remove((Ingredient) dishable);
        items.getChildren().remove(button);
    }

    @Override
    public void safe() {
        File folder = new File(DishApplication.FOLDER_NAME + "/ingredients");
        int i = 0;
        for (Ingredient in : Ingredient.ingredients) {
            File data = new File(folder.getPath() + "/in" + i++ + ".igd");
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(data))) {
                data.createNewFile();
                out.writeObject(in);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
