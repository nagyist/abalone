import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.control.SkinBase;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import java.util.ArrayList;

class CustomControlSkin extends SkinBase<CustomControl> implements Skin<CustomControl> {
    public CustomControlSkin(CustomControl cc) { super(cc); }
}

class CustomControl extends Control {

    public CustomControl(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;

        /* Default skin + generate game board */
        setSkin(new CustomControlSkin(this));

        initGame();

        /* Reset game */
        controlPanel.reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetGame();
            }
        });

        /* Mouse click listener */
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY)
                    clickOnPiece(event);
            }
        });

        /* Mouse drag listener */
        setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseDraggedCoord = new Vector2(event.getX(), event.getY());
                Vector3 cellCoord = mouseDraggedCoord.to3d().round();
                Cell cell = abaloneBoard.at(cellCoord);
                mouseDragged = true;
            }
        });

        /* Mouse released listener */
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (mouseDragged && selectedCells.size() > 0) {
                    Vector2 cellCoord2d = new Vector2(event.getX(), event.getY());
                    final boolean[] hasMoved = {false};
                    int n = cellCoord2d.border(mouseDraggedCoord);
                    if (selectedCells.size() == 1) {
                        if (gameLogic.canMove(n, selectedCells.get(0))) {
                            Cell neighbor = abaloneBoard.at(selectedCells.get(0).getNeighbor(n));
                            neighbor.push(cellCoord2d.border(mouseDraggedCoord), selectedCells.get(0));
                            hasMoved[0] = true;
                        }
                    } else {
                        if (gameLogic.canMove(n, selectedCells)) {
                            selectedCells.forEach(c -> {
                                Cell neighbor = abaloneBoard.at(c.getNeighbor(n));
                                neighbor.push(cellCoord2d.border(mouseDraggedCoord), c);
                                hasMoved[0] = true;
                            });
                        }
                    }
                    if (hasMoved[0]) {
                        highlightedCells.forEach(Cell::unhighlight);
                        highlightedCells.clear();
                        selectedCells.forEach(Cell::unselectPiece);
                        selectedCells.clear();
                        gameLogic.switchPlayer();
                    }
                }
                else if (event.getButton() == MouseButton.PRIMARY)
                    clickOnPiece(event);
                mouseDragged = false;
            }
        });

        /* Key press listener */
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.CONTROL)
                    control = true;
                if (event.getCode() == KeyCode.SPACE)
                    resetGame();
            }
        });

        /* Key released listener */
        setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.CONTROL)
                    control = false;
            }
        });
    }

    private void resetGame() {
        AbaloneApplication.stage.setScene(AbaloneApplication.newScene(numberOfPlayers));
    }

    protected void initGame() {

        getChildren().removeAll();

        abaloneBoard = new AbaloneBoard(sizeOfBoard);
        controlPanel = new ControlPanel();

        /* Set all players & places their pieces */
        gameLogic = new GameLogic(controlPanel, abaloneBoard, this);
        gameLogic.init.initPlayers(numberOfPlayers);
        gameLogic.init.initGutters(sizeOfBoard);
        gameLogic.init.initPieces(numberOfPlayers);
        gameLogic.setPlayer();

        main = new HBox(controlPanel, abaloneBoard);
        getChildren().add(main);
    }

    private void clickOnPiece(MouseEvent event) {
        Vector3 cellCoord = new Vector2(event.getX() - Cell.size * 2, event.getY() + ((Math.sqrt(3) / 2) * Cell.size)).to3d().round();
        Cell cell = abaloneBoard.at(cellCoord);

        if (cell != null && !cell.isEmpty()) {
            if (!control) {
                selectedCells.forEach(Cell::unselectPiece);
                selectedCells.clear();
                highlightedCells.forEach(Cell::unhighlight);
                highlightedCells.clear();
            }
            if (!gameLogic.selectionValid(selectedCells, cell))
                return;
            if (!selectedCells.contains(cell)) {
                selectedCells.add(cell);
                cell.selectPiece();
                if (selectedCells.size() == 1) {
                    for (int i = 0; i < 6; i++) {
                        if (gameLogic.canMove(i, cell)) {
                            abaloneBoard.at(cell.getNeighbor(i)).highlight();
                            highlightedCells.add(abaloneBoard.at(cell.getNeighbor(i)));
                        }
                    }
                }
                else {
                    highlightedCells.forEach(Cell::unhighlight);
                    highlightedCells.clear();
                    for (Cell selectedCell : selectedCells) {
                        for (int i = 0; i < 6; i++) {
                            if (gameLogic.canMove(i, selectedCells)) {
                                abaloneBoard.at(selectedCell.getNeighbor(i)).highlight();
                                highlightedCells.add(abaloneBoard.at(selectedCell.getNeighbor(i)));
                            }
                        }
                    }
                    for (int i = 0; i < 6; i++) {
                        System.out.println(gameLogic.canMove(i, selectedCells));

                    }
                }
            }
        }
    }

    private int sizeOfBoard = 5;
    protected int numberOfPlayers;

    private HBox main;
    private ControlPanel controlPanel;
    private AbaloneBoard abaloneBoard;
    private GameLogic gameLogic;

    private boolean control = false;
    private boolean mouseDragged;
    private Vector2 mouseDraggedCoord;

    private ArrayList<Cell> selectedCells = new ArrayList<>();
    private ArrayList<Cell> highlightedCells = new ArrayList<>();
}