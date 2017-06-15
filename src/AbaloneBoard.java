import javafx.scene.Group;
import javafx.scene.layout.Pane;
import java.util.HashMap;

class AbaloneBoard extends Pane {

    public AbaloneBoard(int n) {
        this.cells = new HashMap<>();

        /* Generation */
        for (int x = -n; x <= n; x++) {
            for (int y = Math.max(-n, -x - n); y <= Math.min(n, -x + n); y++) {
                int z = -x - y;
                this.cells.put(new Vector3(x, y, z), new Cell(new Vector3(x, y, z)));
            }
        }

        /* Drawing */
        cells.forEach((k, v) -> {
            getChildren().add(v);
            v.abaloneBoard = this;
            relocateAny(v, v.center.to2d().x, v.center.to2d().y);
        });
    }

    /* Generic relocate */
    private <Type extends Group> void relocateAny(Type obj, double x, double y) {
        obj.relocate(x - Cell.size + ((Cell.size * 20)/2), y - Cell.size + ((((Math.sqrt(3)/2)*Cell.size) * 20)/2));
    }

    protected Cell at(Vector3 position) {
        return cells.get(position);
    }

    protected HashMap<Vector3, Cell> cells;
}