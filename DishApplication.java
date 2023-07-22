import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

/**
 * @author EntspannterHase
 */
public class DishApplication extends Application {

    public static final String FOLDER_NAME;
    static {
        String folderName = System.getenv("APPDATA");
        if(folderName == null || folderName.length() == 0) System.exit(1);

        folderName = folderName.replaceAll("\\\\", "/");
        File appData = new File(folderName);
        if(!appData.exists()) System.exit(2);

        FOLDER_NAME = appData + "/WishADish";
    }

    private IngredientManagerPane mg_ingredient;
    private DishManagerPane mg_dish;

    /**
     * The method to the start the application.
     * @param stage The stage on which the application runs.
     */
    @Override
    public void start(Stage stage) {
        // functional
        checkFolder(FOLDER_NAME, FOLDER_NAME + "/ingredients", FOLDER_NAME + "/dishes");
        PlanManagerPane mg_plan = new PlanManagerPane();
        mg_dish = new DishManagerPane();
        mg_ingredient = new IngredientManagerPane();

        // graphical
        BorderPane parent = new BorderPane();
        parent.setPrefSize(750d, 500d);
        parent.setCenter(mg_plan);
        Button btn_plan = new Button("Wochenplan"),
                btn_dish = new Button("Gerichte"),
                btn_ingredient = new Button("Zutaten");
        btn_plan.setOnAction(x -> parent.setCenter(mg_plan));
        btn_dish.setOnAction(x -> parent.setCenter(mg_dish));
        btn_ingredient.setOnAction(x -> parent.setCenter(mg_ingredient));
        HBox upperbox = new HBox();
        upperbox.setId("SelectionMenu");
        upperbox.getChildren().addAll(btn_plan, btn_dish, btn_ingredient);
        parent.setTop(upperbox);

        // styling
        Scene scene = new Scene(parent);
        stage.setTitle("WishADish");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png"))));

        scene.getStylesheets().add("/style.css");

        // essentials for start
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Refactored method to check the file structure
     * @param paths the path to the folder
     */
    private void checkFolder(String... paths) {
        for(String path : paths){
            final File folder = new File(path);
            if (!folder.exists() && !folder.mkdirs()) System.err.println("Something went wrong creating the folders");
        }
    }

    /**
     * This method stops the application and safes the used objects
     * @throws Exception if there is an error
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        mg_dish.safe();
        mg_ingredient.safe();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
