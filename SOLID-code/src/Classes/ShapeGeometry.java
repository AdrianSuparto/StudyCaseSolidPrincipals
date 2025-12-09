package Classes;

import java.awt.geom.Path2D;

public abstract class ShapeGeometry {
    protected Path2D.Float path;

    public ShapeGeometry() {
        this.path = new Path2D.Float();
    }

    public abstract void buildGeometry();

    public boolean containsPoint(int x, int y) {
        return path.contains(x, y);
    }

    public Path2D getPath() {
        return path;
    }
}