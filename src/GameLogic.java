import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameLogic {

    public GameLogic(ControlPanel controlPanel, AbaloneBoard abaloneBoard, CustomControl cc) {
        this.abaloneBoard = abaloneBoard;
        this.controlPanel = controlPanel;
        this.cc = cc;
        init = new InitBoard();
        playerTurn = 0;
    }

    protected static void killPiece(Cell cell) {
        --cell.getPlayer().nbPieces;
        cell.removePiece();
        if (player.incScore() == 6) {
            setPlayer();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("We have a winner!");
            alert.setHeaderText(null);
            alert.setContentText("Player " + player.number + " won the game.");
            alert.showAndWait();
            cc.initGame();
        }
    }

    protected Boolean selectionValid(ArrayList<Cell> selectedCells, Cell lastSelected) {

        /* Same player */
        if (lastSelected.getPlayer() != player)
            return false;

        /* None is selected or full */
        if (selectedCells.size() == 0) return true;
        if (selectedCells.size() >= 3) return false;

        if (!selectedCells.stream().anyMatch(c -> c.center.distance(lastSelected.center) == 1))
            return false;

        /* Are on the same axis */
        if (selectedCells.stream().allMatch(c -> c.center.x == lastSelected.center.x))
            return true;
        else if (selectedCells.stream().allMatch(c -> c.center.y == lastSelected.center.y))
            return true;
        else if (selectedCells.stream().allMatch(c -> c.center.z == lastSelected.center.z))
            return true;
        return false;
    }

    protected boolean canMove(int to, Cell cell) {
        ArrayList<Cell> pushedCells = new ArrayList<Cell>();
        pushedCells.add(cell);

        /* If player's weight is more than the opponent */
        for (Cell neighbor = abaloneBoard.at(cell.getNeighbor(to));
             neighbor != null && (!neighbor.isEmpty() || neighbor.gutter);
             neighbor = abaloneBoard.at(neighbor.getNeighbor(to))) {
            pushedCells.add(neighbor);
        }

        ArrayList<Cell> pushedCellsWithoutGutter = pushedCells.stream().filter(cell1 -> !cell1.gutter).collect(Collectors.toCollection(ArrayList::new));
        int weight = weight(pushedCellsWithoutGutter);

        /* If not pushing more than 3 own pieces in a row */
        if (weight > 3)
            return false;
        if (weight <= pushedCellsWithoutGutter.size() - weight)
            return false;

        /* If not pushing own piece in gutter */
        if (pushedCells.size() >= 2) {
            Cell gutter = pushedCells.get(pushedCells.size() - 1);
            Cell last = pushedCells.get(pushedCells.size() - 2);
            if (gutter.gutter && !last.isEmpty() && last.getPlayer() == player) {
                return false;
            }
        }
        /* If not pushing discontinually own pieces */
        return true;
    }

    protected boolean canMove(int to, ArrayList<Cell> cells) {
        for (Cell cell : cells) {
            if (!abaloneBoard.at(cell.getNeighbor(to)).isEmpty()) return false;
        }
        return true;
    }

    private int weight(ArrayList<Cell> cells) {
        int count = 0;
        for (Cell cell:cells) {
            if (cell.isEmpty() || cell.gutter || cell.getPlayer() != player)
                return count;
            count++;
        }
        return count;
    }

    protected void switchPlayer() {
        playerTurn = (playerTurn == players.length - 1) ? 0 : playerTurn + 1;
        setPlayer();
    }

    protected static void setPlayer() {
        player = players[playerTurn];
        controlPanel.setPlayer(player);
    }

    protected static Player[] players;
    protected static Player player;
    private static int playerTurn;

    protected static AbaloneBoard abaloneBoard;
    protected static ControlPanel controlPanel;
    protected static CustomControl cc;
    protected InitBoard init;
}