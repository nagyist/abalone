import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbaloneApplication extends Application {

    private int numberOfPlayers() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Number of Players");
        alert.setHeaderText("With how many players do you want to play?");
        alert.setContentText("Choose your option.");

        List<Integer> choices = new ArrayList<>();
        choices.add(2);
        choices.add(3);
        choices.add(4);
        /*
        choices.add(5);
        choices.add(6);
        */

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(2, choices);
        dialog.setTitle("Number of Players");
        dialog.setHeaderText("How many players?");
        dialog.setContentText("Choose your option:");

        Optional<Integer> result = dialog.showAndWait();
        return (result.isPresent() ? result.get() : 0);
    }

    protected static Scene newScene(int numberOfPlayers) {
        return (new Scene(new StackPane(new CustomControl(numberOfPlayers)), width, height));
    }

    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        primaryStage.setTitle("Abalone");
        primaryStage.setResizable(false);

        int numberOfPlayers = numberOfPlayers();
        if (numberOfPlayers == 0)
            return;

        primaryStage.setScene(newScene(numberOfPlayers));
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

    protected static final int width = 950;
    protected static final int height = 700;
    protected static Stage stage;
}
