package geometries;

import Primitives.Point;
import Primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements intersectable {
    List<intersectable> geometries=new LinkedList<intersectable>();

    public Geometries(intersectable... geometries) {
       add(geometries);
    }

    private void add(intersectable... geometries) {
        Collections.addAll(this.geometries,geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}
