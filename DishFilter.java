import java.util.*;

public class DishFilter {

    /**
     * Creates a weekly plan with filtering the following ingredients
     */
    public static List<Dish> getDishesWithout(Ingredient... ingredients) {
        final List<Dish> dishes = Dish.getAllDishes(),
                sol = new ArrayList<>();

        for (Dish dish : dishes) {
            boolean isValid = true;
            for (Ingredient ingredient : ingredients) {
                if (dish.getIngredientAmount(ingredient) != 0d) {
                    isValid = false;
                    break;
                }
            }
            if (isValid)
                sol.add(dish);
        }

        return sol;
    }

    /**
     * TODO: Priorität festlegen
     * @param ingredients
     * @return
     */
    public static List<Dish> getDishesWith(List<Ingredient> ingredients) {
        final List<Dish> dishes = Dish.getAllDishes(),
                sol = new ArrayList<>();

        for (Dish dish : dishes) {
            boolean isValid = true;
            for (Ingredient ingredient : ingredients) {
                if (dish.getIngredientAmount(ingredient) == 0d) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                sol.add(dish);
            }
        }

        return sol;
    }

    /**
     *
     * @return a week pattern(boolean[2][7]) the first array is for lunch, the second for dinner
     */
    public static boolean[][] createWeekPattern() {
        final boolean[][] pattern = new boolean[2][7];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                pattern[i][j] = true;
            }
        }
        return pattern;
    }

    /**
     * TODO: keine zufällige Verteilung mehr
     * @param dishes
     * @return
     */
    public static Dish[][] getDishesWithWeekPattern(List<Dish> dishes, boolean[][] pattern) {
        if (dishes == null || dishes.size() == 0 || pattern == null || pattern.length != 2) {
            return new Dish[2][7];
        }

        final Dish[][] week = new Dish[2][7];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                week[i][j] = pattern[i][j] ? getRandomDish(dishes) : Dish.DUMMY;
                System.out.println(week[i][j]);
            }
        }
        return week;
    }

    /**
     * Simple week without any filters.
     *
     * @param dishes
     * @return
     */
    public static Dish[][] getDishes(List<Dish> dishes) {
        if (dishes == null || dishes.size() == 0) {
            return new Dish[2][7];
        }

        Dish[][] week = new Dish[2][7];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                week[i][j] = getRandomDish(dishes);
            }
        }
        return week;
    }

    private static Dish getRandomDish(List<Dish> dishes) {
        final int zufall = new Random().nextInt(0, dishes.size());
        return dishes.get(zufall);
    }

}
