import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DishFilterTest {

    public static Ingredient[] ingredients = {
            new Ingredient("Zucker", "g"), //0
            new Ingredient("Milch", "g"),//1
            new Ingredient("Honig", "g"),//2
            new Ingredient("Mais", "g"),//3
            new Ingredient("Reis", "g"),//4
            new Ingredient("Mehl", "g"),//5
            new Ingredient("Weizen", "g"),//6
            new Ingredient("Tomaten", "g"),//7
            new Ingredient("Schwein", "g"),//8
            new Ingredient("Huhn", "g"),//9
            new Ingredient("Spaghetti", "g"),//10
            new Ingredient("Knoblauch", "g"),//11
            new Ingredient("Wasser", "g"),//12
            new Ingredient("Hefe", "g"),//13
    };
    public static Dish[] dishes = {
            new Dish("Spaghetti Carbonara"),
            new Dish("Spaghetti Bolognese"),
            new Dish("Milchreis"),
            new Dish("Geschnetzeltes"),
            new Dish("Tomatensuppe"),
            new Dish("Brot"),
            new Dish("Reiskuchen"),
    };

    static {
        dishes[0].set(ingredients[10], 80);
        dishes[1].set(ingredients[10], 80);
        dishes[2].set(ingredients[4], 80);
        dishes[3].set(ingredients[8], 80);
        dishes[4].set(ingredients[7], 80);
        dishes[5].set(ingredients[6], 80);
        dishes[6].set(ingredients[13], 80);

        dishes[0].set(ingredients[1], 20);
        dishes[0].set(ingredients[8], 10);
        dishes[0].set(ingredients[11], 5);
    }

    /*
    @Test
    public void getDishesWithoutTest() {
        List<Dish> actual = DishFilter.getDishesWithout(ingredients[10]);
        List<Dish> expected = new ArrayList<>();
        for (int i = 2; i < dishes.length; i++) {
            expected.add(dishes[i]);
        }
        assertEquals(expected, actual);

        actual = DishFilter.getDishesWithout(ingredients[10], ingredients[4], ingredients[8], ingredients[13]);
        expected.remove(dishes[2]);
        expected.remove(dishes[3]);
        expected.remove(dishes[6]);
        assertEquals(expected, actual);
    }

    @Test
    public void getDishesWithTest() {
        List<Dish> actual = DishFilter.getDishesWith(ingredients[10]);
        List<Dish> expected = new ArrayList<>();
        expected.add(dishes[0]);
        expected.add(dishes[1]);
        assertEquals(expected, actual);

        actual = DishFilter.getDishesWith(ingredients[10], ingredients[1], ingredients[8], ingredients[11]);
        expected.remove(dishes[1]);
        assertEquals(expected, actual);
    }

     */
}
