import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlPanel extends VBox {

    public ControlPanel() {
        super();

        reset.setPrefSize(145, 42);
        setMargin(reset, new Insets(margin));

        displayText(turn, turn_label, turn_text, margin);
        displayText(pieces, pieces_label, pieces_text, margin);
        displayText(taken, taken_label, taken_text, margin);
        displayText(winner, winner_label, winner_text, margin);

        getChildren().addAll(reset, turn, pieces, taken, winner);
    }

    private void displayText(HBox parent, Label label, TextField text, int margin) {
        parent.setMargin(label, new Insets(margin, 0, 0, margin));
        parent.setMargin(text, new Insets(margin / 2));
        text.setEditable(false);
        text.setPrefWidth(42);
    }

    protected void setPlayer(Player player) {
        this.player = player;
        turn_text.setText(String.valueOf(player.number));
        pieces_text.setText(String.valueOf(player.nbPieces));
        taken_text.setText(String.valueOf(player.score));
        if (player.score == 6)
            winner_text.setText(String.valueOf(player.number));
    }

    private int margin = 10;
    private Player player;

    /* Player turn */
    private Label turn_label = new Label("Player turn:\t");
    private TextField turn_text = new TextField("1");
    private HBox turn = new HBox(turn_label, turn_text);

    /* Player pieces */
    private Label pieces_label = new Label("Player pieces:\t");
    private TextField pieces_text = new TextField("0");
    private HBox pieces = new HBox(pieces_label, pieces_text);

    /* Pieces taken */
    private Label taken_label = new Label("Pieces taken:\t");
    private TextField taken_text = new TextField("0");
    private HBox taken = new HBox(taken_label, taken_text);

    /* Winner */
    private Label winner_label = new Label("Player win:\t");
    private TextField winner_text = new TextField("0");
    private HBox winner = new HBox(winner_label, winner_text);

    /* Reset */
    protected Button reset = new Button("Reset");
}
