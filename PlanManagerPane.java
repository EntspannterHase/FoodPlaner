import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author EntspannterHase
 */
public class PlanManagerPane extends StackPane {

    private final BorderPane container = new BorderPane();

    private Dish[][] weekPlan;

    public PlanManagerPane() {
        setId("PlanManagerPane");
        getChildren().add(container);
        // top
        final HBox top = new HBox();
        top.setId("TopLayer");
        final Label top_label = new Label("Wochenplaner");
        final Button btn_create = new Button("Erstellen"),
                btn_filter = new Button("Filtern"),
                btn_export = new Button("exportieren");

        // top function
        btn_create.setOnAction(x -> createWeekPlan());
        btn_filter.setOnAction(x -> getChildren().add(FilterPane.getInstance()));
        btn_export.setOnAction(x -> exportDishesToCSV());
        container.setTop(top);
        top.getChildren().addAll(top_label, btn_create, btn_filter, btn_export);
    }


    private void exportDishesToCSV() {
        if (weekPlan == null) return;

        // init filechooser
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dateiort zum Speichern");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", ".csv"));
        fileChooser.setInitialFileName("dishplan");
        // choose the file
        final File file = fileChooser.showSaveDialog(this.getScene().getWindow());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return;
            }
        }
        //save the dishplan
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file), true, StandardCharsets.UTF_8)) {
            writer.println("Tageszeit,Montag,Dienstag,Mittwoch,Donnerstag,Freitag,Samstag,Sonntag");
            String print1 = printDishWeek("Mittagessen", weekPlan[0]),
                    print2 = printDishWeek("Abendessen", weekPlan[1]);
            writer.println(print1);
            writer.println(print2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refactored method
     * @param string
     * @param array
     * @return
     */
    private static String printDishWeek(String string, final Dish[] array) {
        for (Dish dish : array) {
            string += "," + dish.getName();
        }
        return string;
    }

    /**
     * Refactored method
     */
    private void createWeekPlan() {
        // graphical
        final HBox def = createTableBox(), mittag = createTableBox(), abend = createTableBox();
        def.setId("heading");
        final VBox vbox = new VBox();
        vbox.getChildren().addAll(def, mittag, abend);
        container.setCenter(vbox);

        // create a plan for the week
        FilterPane filter = FilterPane.getInstance();
        boolean[][] weekPattern = filter.getPattern();
        List<Ingredient> goodIngredients = filter.getGoodIngredients();
        List<Dish> dishSelection = DishFilter.getDishesWith(goodIngredients);
        weekPlan = DishFilter.getDishesWithWeekPattern(dishSelection, weekPattern);

        // set the content
        def.getChildren().addAll(new Label(""), new Label("Montag"), new Label("Dienstag"), new Label("Mittwoch"), new Label("Donnerstag"), new Label("Freitag"), new Label("Samstag"), new Label("Sonntag"));
        setLabel(mittag, weekPlan[0], "Mittag");
        setLabel(abend, weekPlan[1], "Abend");

        // create shoppinglist
        final Button btn_list = new Button("Zutaten ausdrucken");
        btn_list.setOnAction(y -> createShoppingList(weekPlan));
        vbox.getChildren().add(btn_list);
    }

    /**
     * Refactored method
     *
     * @return
     */
    private HBox createTableBox() {
        final HBox box = new HBox();
        box.setId("TableBox");
        return box;
    }

    /**
     * Refactored method
     *
     * @param box
     * @param header
     */
    private void setLabel(HBox box, Dish[] week, String header) {
        box.getChildren().add(new Label(header));
        for (int i = 0; i < 7; i++) {
            box.getChildren().addAll(new Label(week[i].getName()));
        }
    }

    /**
     * Refactored method
     *
     * @param dishList
     */
    private void createShoppingList(Dish[][] dishList) {
        final Dish shoppingList = Dish.getIngredients(dishList);

        // Button to go back to the plan
        final Button btn_back = new Button("zurück");
        final VBox container = new VBox();
        container.setId("ShoppingList");
        getChildren().add(container);

        // Label for the shoppinglist
        final Label heading = new Label("Einkaufsliste");
        heading.setStyle("-fx-font-size: 17");
        container.getChildren().addAll(btn_back, heading);

        // Add the ingredients
        for (Ingredient i : shoppingList.getIngredientMap().keySet()) {
            final Label label = new Label(shoppingList.getIngredientAmount(i) + " " + i);
            container.getChildren().add(label);
        }

        // Function to go back
        btn_back.setOnAction(z -> {
            getChildren().remove(container);
        });
    }

}