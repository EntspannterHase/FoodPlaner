import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author EntspannterHase
 */
public class Ingredient implements Serializable, Dishable {

    private String name;
    private String unit;

    /**
     * The list in which all ingredients are saved.
     */
    public static final List<Ingredient> ingredients = new ArrayList<>();

    /**
     * Creates an ingredient and manages the list.
     * @param name the name of the ingredient
     * @param unit the unit of the ingredient
     */
    public Ingredient(String name, String unit) {
        this.name = name;
        this.unit = unit;
        updateIngredientList(this);
    }

    /**
     * Checks if the ingredients of the dish already exists in the list.
     * @param dish the dish, which has to be checked
     */
    public static void updateIngredientList(Dish dish) {
        // ist ein Ingredient schon vorhanden, dann ändere das Ingredient in dish
        if (dish == null) return;
        HashMap<Ingredient, Double> map = dish.getIngredientMap();

        Ingredient[] dish_ingredients = map.keySet().toArray(new Ingredient[0]);

        for (Ingredient ingredient : dish_ingredients) {
            final int idx = ingredients.indexOf(ingredient);
            if (idx != -1) {
                double amount = map.get(ingredient);
                dish.remove(ingredient);
                dish.set(ingredients.get(idx), amount);
            } else {
                ingredients.add(ingredient);
            }
        }
    }

    /**
     * Checks if the ingredient is already existing in the list.
     * @param ingredient the ingredient, which has to be checked
     */
    public static void updateIngredientList(Ingredient ingredient) {
        System.out.println(ingredient);
        if (!ingredients.contains(ingredient)) ingredients.add(ingredient);
    }

    // --- OBJECT OVERRIDES ---
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof final Ingredient in) {
            return name.equals(in.name) && unit.equals(in.unit);
        }
        return false;
    }

    @Override
    public String toString() {
        return unit + " " + name;
    }

    // --- GETTER AND SETTER ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
