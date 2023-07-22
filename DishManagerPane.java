import javafx.scene.control.Button;

import java.io.*;

/**
 * @author EntspannterHase
 */
public class DishManagerPane extends ManagerPane {

    public DishManagerPane() {
        super("Gerichte", "dishes",".dsh", x -> {
            Dish dish = (Dish) x;
            Dish.addDishToList(dish);
        });
    }

    // --- INITIALISATION ---
    @Override
    public void initAddButton() {
        btn_add.setOnAction(x -> {
            DishEditingBox editingBox = createDish("Neues Gericht");
            getChildren().add(editingBox);
        });
    }

    @Override
    public void initButtons() {
        for(Dish dish : Dish.getAllDishes()) {
            Button button = createButton(dish);
            button.setOnAction(x -> getChildren().add(new DishEditingBox(this, dish, button)));
        }
    }

    // --- FUNCTIONAL ---

    /**
     * This method creates a new Dish.
     * @param name The name of the dish.
     * @return the EditingBox for the new dish.
     */
    public DishEditingBox createDish(String name) {
        Dish dish = new Dish(name);
        Button button = createButton(dish);
        DishEditingBox editingBox = new DishEditingBox(this, dish, button);
        button.setOnAction(x -> getChildren().add(editingBox));
        return editingBox;
    }

    @Override
    public void remove(Dishable dish, Button button) {
        Dish.removeFromList((Dish) dish);
        items.getChildren().remove(button);
    }

    @Override
    public void safe() {
        File folder = new File(DishApplication.FOLDER_NAME + "/dishes");
        int i = 0;
        for(Dish dish : Dish.getAllDishes()) {
            File data = new File(folder.getPath() + "/di" + i++ + ".dsh");
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(data))) {
                data.createNewFile();
                out.writeObject(dish);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
