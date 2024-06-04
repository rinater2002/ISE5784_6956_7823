package geometries;

import Primitives.Point;
import Primitives.*;

import java.util.List;

public interface intersectable {
    List<Point> findIntersections(Ray ray);
}
