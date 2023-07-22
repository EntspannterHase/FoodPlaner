import java.io.Serializable;
import java.util.*;

/**
 * @author EntspannterHase
 */
public class Dish implements Serializable, Dishable {

    // --- CLASS METHODS AND ATTRIBUTES ---
    private static final List<Dish> dishes = new ArrayList<>();

    public static final Dish DUMMY = new Dish();

    private Dish() {
        this.name = " - ";
        ingredientMap = new HashMap<>();
    }

    /**
     *
     * @return a copy of the original dishes list
     */
    public static List<Dish> getAllDishes() {
        return List.copyOf(dishes);
    }

    /**
     * This method generates a weekly plan. If there is one dish at least it returns a new list containing 14 dishes.
     * Otherwise it will return an empty list.
     *
     * @return A list with 14 dishes if there is min. one dish else it returns a new list.
     */
    public static List<Dish> createWeekPlan() {
        if (dishes.size() == 0) {
            return new LinkedList<>();
        }

        // TODO: Filter angeben und die Top-Scorer werden ausgewählt

        LinkedList<Dish> week = new LinkedList<>();
        for (int i = 0; i < 14; i++) {
            final int zufall = new Random().nextInt(0, dishes.size());
            week.add(dishes.get(zufall));
        }
        return week;
    }

    /**
     * This method creates the shoppinglist for all dishes in the week
     * @param list the weekly dishes
     * @return the shopping list
     */
    public static Dish getIngredients(Dish[][] list) {
        if (list == null || list.length == 0) return new Dish("");

        final Dish dish = new Dish();
        for (Dish[] array : list){
            for (Dish d : array) {
                for (Ingredient i : d.ingredientMap.keySet()) {
                    double amount = dish.getIngredientAmount(i) + d.getIngredientAmount(i);
                    dish.set(i, amount);
                }
            }
        }
        return dish;
    }

    /**
     * Add the new dish, if it is not null and isn't part of the dish-list
     *
     * @param dish the dish, which should be added
     */
    public static void addDishToList(Dish dish) {
        if (dish == null || dishes.contains(dish)) return;
        dishes.add(dish);

        // check ingredients
        Ingredient.updateIngredientList(dish);
    }

    public static void removeFromList(Dish dish) {
        dishes.remove(dish);
    }

    // --- OBJECT METHODS AND ATTRIBUTES ---
    private final HashMap<Ingredient, Double> ingredientMap;
    private String name;

    /**
     * Creates a new dish.
     *
     * @param name the name of the dish.
     */
    public Dish(String name) {
        this.name = name;
        ingredientMap = new HashMap<>();
        addDishToList(this);
    }

    // --- FUNCTIONAL ---

    /**
     * Puts the ingredient with the amount in the ingredientMap, if it isn't already a part of it.
     *
     * @param ingredient the ingredient
     * @param amount     the amount
     */
    public void set(Ingredient ingredient, double amount) {
        ingredientMap.put(ingredient, amount);
    }

    /**
     * Removes an ingredient if it is contained in the ingredientMap.
     *
     * @param ingredient The ingredient, which have to be removed.
     */
    public void remove(Ingredient ingredient) {
        this.ingredientMap.remove(ingredient);
    }

    /**
     * This method is to get the amount of the ingredient, if it is in the ingredientMap.
     *
     * @param ingredient The ingredient.
     * @return The amount if the ingredient is contained.
     */
    public double getIngredientAmount(Ingredient ingredient) {
        if (ingredientMap.containsKey(ingredient)) {
            double sol = ingredientMap.get(ingredient);
            return sol;
        } else return 0d;
    }

    /**
     * Get the ingredientMap.
     *
     * @return the ingredientMap
     */
    public HashMap<Ingredient, Double> getIngredientMap() {
        return ingredientMap;
    }

    // --- OBJECT OVERRIDES ---
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Dish dish) {
            return dish.name != null && dish.name.equals(this.name) && dish.ingredientMap.equals(this.ingredientMap);
        }
        return false;
    }

    @Override
    public String toString() {
        Ingredient[] ingredients = ingredientMap.keySet().toArray(new Ingredient[0]);
        StringBuilder text = new StringBuilder(name + "[");
        for (Ingredient ingredient : ingredients) {
            text.append(" ").append(ingredientMap.get(ingredient)).append(ingredient);
        }
        return text + "]";
    }

    // --- GETTER AND SETTER ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
