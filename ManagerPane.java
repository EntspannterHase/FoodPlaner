import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;

/**
 * @author EntspannterHase
 */
public abstract class ManagerPane extends StackPane {

    /**
     * The first layer of the pane
     */
    protected final BorderPane container;
    /**
     * The container for the single Buttons
     */
    protected final FlowPane items;
    /**
     * The Button to create a new Dishable
     */
    protected final Button btn_add;

    /**
     * This constructor creates the ManagerPane
     * @param header the heading of the Pane
     * @param folder the folder, in which the data is
     * @param dataEnding the file ending of the loading data
     * @param loadingHandler the action after a file is loaded
     */
    public ManagerPane(String header, String folder, String dataEnding, LoadingHandler loadingHandler) {
        // ESSENTIALS
        this.container = new BorderPane();
        this.items = new FlowPane();
        this.btn_add = new Button("+");

        getChildren().add(container);
        setId(getClass().getSimpleName());

        // INIT THE SCROLLPANE FOR THE ITEMS-CONTAINER
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(items);
        container.setCenter(scrollPane);

        // INIT THE TOP
        HBox top = new HBox();
        top.setId("TopLayer");
        Label label = new Label(header);
        top.getChildren().addAll(label, btn_add);
        container.setTop(top);

        // LOADING THE FILES
        File files = new File(DishApplication.FOLDER_NAME + "/" + folder);
        if(files.exists()) {
            for(File data : Objects.requireNonNull(files.listFiles())) {
                if(data.getName().endsWith(dataEnding)) {
                    try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(data))) {
                        Object obj = in.readObject();
                        loadingHandler.handle(obj);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        if(!data.delete()) System.err.println("There is a problem with deleting the data");
                    }
                }
            }
        }

        // INIT THE BUTTONS
        initButtons();
        initAddButton();
    }

    /**
     * This is a refactored method to create a single Button, which represents a Dishable.
     * @param dishable The Dishable, which should be represented by this button
     * @return a new Button, which has to be added
     */
    public final Button createButton(Dishable dishable) {
        Button button = new Button(dishable.getName());
        button.setId("view");
        items.getChildren().add(button);
        return button;
    }

    // --- INITIALISATION ONLY---
    public abstract void initButtons();
    public abstract void initAddButton();

    // --- FUNCTIONAL ---
    public abstract void remove(Dishable dishable, Button button);
    public abstract void safe();

    /**
     * This method is to remove a created pop-up window.
     */
    public final void goBack() {
        getChildren().clear();
        getChildren().add(container);
    }

}
