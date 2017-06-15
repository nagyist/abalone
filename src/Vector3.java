/* Vector3 is used to store game logic coordinates */
public class Vector3 {

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    protected Vector3 add(Vector3 v) {
        return (new Vector3(this.x + v.x, this.y + v.y, this.z + v.z));
    }

    protected double distance(Vector3 v) {
        return ((Math.abs(this.x - v.x) + Math.abs(this.y - v.y) + Math.abs(this.z - v.z)) / 2);
    }

    /* Probably not Models.Vector3 specific */
    protected Vector2 to2d() {
        double x = (Math.sqrt(3) * this.x + (Math.sqrt(3) / 2) * this.z) * Cell.size;
        double y = ((3.0 / 2.0) * this.z) * Cell.size;
        return (new Vector2(x, y));
    }

    protected Vector3 round() {
        double x1 = Math.round(x);
        double y1 = Math.round(y);
        double z1 = Math.round(z);

        double dx = Math.abs(x1 - x);
        double dy = Math.abs(y1 - y);
        double dz = Math.abs(z1 - z);

        if (dx > dy && dx > dz) {
            x1 = -y1 - z1;
        }
        else if (dy > dz) {
            y1 = -x1 - z1;
        }
        else {
            z1 = -x1 - y1;
        }
        return (new Vector3(x1, y1, z1));
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Vector3))return false;
        Vector3 v = (Vector3)other;
        return (this.x == v.x && this.y == v.y && this.z == v.z);
    }

    @Override
    public int hashCode() {
        int hash = 42;

        hash += ((int)this.x ^ ((int)this.x >> 2));
        return (hash + (int)x + (int)y + (int)z);
    }

    @Override
    public String toString() {
        return ("(" + this.x + ", " + this.y + ", " + this.z + ")");
    }

    protected double x, y, z;
}
