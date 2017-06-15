/* Basically, Vector2 is used to contain screen-based coordinates */
public class Vector2 {

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    protected Vector3 to3d() {
        double x1 = (x - (AbaloneApplication.width / 2)) / Cell.size;
        double y1 = (y - (AbaloneApplication.height / 2)) / Cell.size;

        double x2 = (Math.sqrt(3.0) / 3.0) * x1 + (-1.0 / 3.0) * y1;
        double y2 = (2.0 / 3.0) * y1;

        return (new Vector3(x2, (-x2 - y2), y2));
    }

    protected Vector2 add(Vector2 v) {
        return (new Vector2(this.x + v.x, this.y + v.y));
    }

    protected Vector2 mul(Vector2 v) { return (new Vector2(this.x * v.x, this.y * v.y)); }

    protected Vector2 mul(double n) {
        return (new Vector2(this.x * n, this.y * n));
    }

    protected int border(Vector2 v) {
        double angle = Math.atan2(this.x - v.x, this.y - v.y);
        return ((int)Math.floor(Math.abs(Math.toDegrees(angle) - 180) / 60));
    }

    @Override
    public String toString() {
        return ("(" + this.x + ", " + this.y + ")");
    }

    protected double x, y;
}
