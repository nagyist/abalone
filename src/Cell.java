import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;

public class Cell extends Group {

    private Vector3[] directions = new Vector3[] {
        new Vector3(1, 0, -1), /* NE */
        new Vector3(1, -1, 0), /* E  */
        new Vector3(0, -1, 1), /* SE */
        new Vector3(-1, 0, 1), /* SW */
        new Vector3(-1, 1, 0), /* W  */
        new Vector3(0, 1, -1)  /* NW */
    };


    public Cell(Vector3 center) {
        this.center = center;
        borders.setFill(fillColor);
        borders.setStroke(borderColor);
        borders.setStrokeWidth(1);
        borders.getPoints().addAll(
            getCorner(0).x, getCorner(0).y,
            getCorner(1).x, getCorner(1).y,
            getCorner(2).x, getCorner(2).y,
            getCorner(3).x, getCorner(3).y,
            getCorner(4).x, getCorner(4).y,
            getCorner(5).x, getCorner(5).y,
            getCorner(0).x, getCorner(0).y);
        getChildren().addAll(borders);
    }

    protected Player getPlayer() { return (piece.player); }

    protected void setGutter() {
        borders.setFill(gutterColor);
        gutter = true;
    }

    protected void selectPiece() { piece.select(true); }

    protected void unselectPiece() {
        if (piece != null) piece.select(false);
    }

    protected void setPiece(Piece piece) {
        this.piece = piece;
        getChildren().add(piece);
        piece.relocate(20, 20);
    }

    protected boolean isEmpty() { return (piece == null); }

    protected void removePiece() {
        if (piece != null) {
            piece.select(false);
            piece.setCell(null);
            getChildren().remove(piece);
            piece = null;
        }
    }

    protected Vector3 getNeighbor(int direction) {
        return this.center.add(directions[direction]);
    }

    protected Boolean isNeighbor(Vector3 cellCoord) {
        return cellCoord.distance(this.center) <= 1;
    }

    protected Vector2 getCorner(int direction) {
        int angle_deg = 60 * direction + 30;
        double angle_rad = Math.PI / 180 * angle_deg;
        return (relativeCenter.add(new Vector2(size * Math.cos(angle_rad), size * Math.sin(angle_rad))));
    }

    /*
    The push method basically push his piece in direction it has been push to
    It then replace his piece by the one it has been push by.
    TODO: Handle gutter
    */
    protected void push(int to, Cell cell) {
        Cell neighbor = abaloneBoard.at(getNeighbor(to));

        if (piece != null && neighbor != null)
            neighbor.push(to, this);

        setPiece(cell.piece);
        cell.removePiece();

        if (gutter)
            //removePiece();
            GameLogic.killPiece(this);
    }

    public void highlight() {
        if (!gutter)
            borders.setFill(fillColorHighlight);
    }

    public void unhighlight() {
        if (!gutter)
            borders.setFill(fillColor);
    }

    @Override
    public String toString() {
        return ("Cell -> center: " + center.toString());
    }

    protected AbaloneBoard abaloneBoard;

    protected static int size = 40;
    protected Vector3 center;
    private Vector2 relativeCenter = new Vector2(size, size);
    private Polyline borders = new Polyline();

    protected Piece piece = null;
    protected boolean gutter = false;

    /* Colors */
    private Paint borderColor = Color.DARKSLATEGRAY;
    private Paint fillColor = Color.NAVAJOWHITE;
    private Paint gutterColor = Color.GRAY;
    private Paint fillColorHighlight = Color.CADETBLUE;
}