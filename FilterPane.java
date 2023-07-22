import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterPane extends VBox {
    public FilterPane() {
        super();
        setId(getClass().getSimpleName());

        final Label header = new Label("Filter");
        header.setId("FilterPaneHeader");
        getChildren().add(header);

        final CheckBox[][] checkBoxes = initWeekSelection();
        final HashMap<CheckBox, Ingredient> ingredientCheckBoxes = initDishWithSelection();

        final Button btn_save = new Button("übernehmen"), btn_back = new Button("verwerfen");
        final HBox btn_wrapper = new HBox(btn_save, btn_back);
        btn_save.setOnAction(x -> {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 7; j++) {
                    pattern[i][j] = checkBoxes[i][j].isSelected();
                }
            }
            goodIngredients.clear();
            for (CheckBox checkBox : ingredientCheckBoxes.keySet()) {
                if (checkBox.isSelected()) goodIngredients.add(ingredientCheckBoxes.get(checkBox));
            }
            ((StackPane) getParent()).getChildren().remove(this);
        });
        btn_back.setOnAction(x -> {
            ((StackPane) getParent()).getChildren().remove(this);
        });

        getChildren().add(btn_wrapper);
    }

    private CheckBox[][] initWeekSelection() {
        final HBox weekTableHeading = new HBox(),
                weekTableLunch = new HBox(),
                weekTableDinner = new HBox();

        weekTableHeading.setId("WeekTableHeading");
        weekTableHeading.getChildren().addAll(new Label(""), new Label("Mo"), new Label("Di"), new Label("Mi"), new Label("Do"), new Label("Fr"), new Label("Sa"), new Label("So"));

        final CheckBox[][] checkBoxes = new CheckBox[2][];
        checkBoxes[0] = completeWeekTable("Mittag", weekTableLunch);
        checkBoxes[1] = completeWeekTable("Abend", weekTableDinner);

        createEntry("Wochentage", "WeekEntry", weekTableHeading, weekTableLunch, weekTableDinner);

        return checkBoxes;
    }

    private HashMap<CheckBox, Ingredient> initDishWithSelection() {
        final HashMap<CheckBox, Ingredient> ingredientCheckBoxes = new HashMap<>();
        final VBox dishWithIngredientBox = new VBox();
        final ScrollPane dishWithScrollPane = new ScrollPane();

        dishWithScrollPane.setFitToHeight(true);
        dishWithScrollPane.setFitToWidth(true);
        dishWithScrollPane.setContent(dishWithIngredientBox);

        // TODO: Update the content of this view
        for (Ingredient ingredient : Ingredient.ingredients) {
            CheckBox box = new CheckBox(ingredient.getName());
            dishWithIngredientBox.getChildren().add(box);
            ingredientCheckBoxes.put(box, ingredient);
        }

        createEntry("enthaltene Zutaten", "DishWithEntry", dishWithScrollPane);

        return ingredientCheckBoxes;
    }

    private void createEntry(String header, String id, Node... children) {
        final VBox entry = new VBox();
        entry.setId(id);
        final RadioButton heading = new RadioButton(header);
        entry.getChildren().add(heading);
        heading.setOnAction(x -> {
            if (heading.isSelected()) entry.getChildren().addAll(children);
            else entry.getChildren().removeAll(children);
        });

        getChildren().add(entry);
    }

    private boolean[][] pattern = DishFilter.createWeekPattern();
    private List<Ingredient> goodIngredients = new ArrayList<>();

    public boolean[][] getPattern() {
        return pattern;
    }

    public List<Ingredient> getGoodIngredients() {
        return goodIngredients;
    }

    private CheckBox[] completeWeekTable(String firstEntry, HBox box) {
        box.getChildren().add(new Label(firstEntry));
        box.setId("WeekTable");
        final CheckBox[] checkBoxes = new CheckBox[7];
        for (int i = 0; i < 7; i++) {
            checkBoxes[i] = new CheckBox();
            checkBoxes[i].setSelected(true);
            box.getChildren().add(checkBoxes[i]);
        }
        return checkBoxes;
    }

    // --- Singleton ---
    private static FilterPane filterPane;

    public static FilterPane getInstance() {
        if (filterPane == null) {
            filterPane = new FilterPane();
        }
        return filterPane;
    }
}
