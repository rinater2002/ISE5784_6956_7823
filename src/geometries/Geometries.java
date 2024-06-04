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

    public void add(intersectable... geometries) {
        Collections.addAll(this.geometries,geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (this.geometries.isEmpty()) return null; // if have no intersections
        List<Point> result = null;
        for (var item: this.geometries) { //for all geometries in the list
            List<Point> itemList = item.findIntersections(ray);
            if(itemList!=null) {
                if(result==null) {
                    result=new LinkedList<Point>();
                }
                result.addAll(itemList);
            }
        }
        return result;
    }

}
