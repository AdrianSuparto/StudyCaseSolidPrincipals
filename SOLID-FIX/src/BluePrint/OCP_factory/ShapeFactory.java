package BluePrint.OCP_factory;

import BluePrint.SRP_LSP_shapes.*;
import BluePrint.SRP_LSP_shapes.ShapeBase;
import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ShapeFactory {
    public interface ShapeBuilder {
        ShapeBase build(int x, int y, Color color);
    }

    private final Map<String, ShapeBuilder> registry = new ConcurrentHashMap<>();

    private static final ShapeFactory instance = new ShapeFactory();
    public static ShapeFactory getInstance() { return instance; }

    private ShapeFactory() {
        // default registrations
        register("rectangle", (x,y,color) -> new RectangleShape(x,y,x,y,color));
        register("square", (x,y,color) -> new SquareShape(x,y,30,color));
        register("circle", (x,y,color) -> new CircleShape(x,y,15,color));
        register("line", (x,y,color) -> new LineShape(x,y,x+40,y+40,color));
        register("triangle", (x,y,color) -> new TriangleShape(x,y, x+30,y, x+15,y+40, color));
    }

    public void register(String key, ShapeBuilder builder) {
        registry.put(key.toLowerCase(), builder);
    }

    public ShapeBase create(String key, int x, int y, Color color) {
        ShapeBuilder b = registry.get(key.toLowerCase());
        if (b == null) throw new IllegalArgumentException("Unknown shape: " + key);
        return b.build(x,y,color);
    }
}
