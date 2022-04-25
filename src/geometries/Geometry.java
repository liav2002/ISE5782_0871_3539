/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

    /**
     * the color emission of the geometry
     */
    protected Color emission = Color.BLACK;

    /**
     * gets geometries normal
     *
     * @param p
     * @return geometries normal
     */
    public abstract Vector getNormal(Point p);

    /**
     * This function returns the emission color of the material
     *
     * @return The emission color of the material.
     */
    public Color getEmission() {
        return this.emission;
    }

    /**
     * Sets the emission color of the geometry and returns the geometry.
     *
     * @param emission The color of the light emitted by the material.
     * @return The Geometries object itself.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    private Material material = new Material();

    public Material getMaterial() {
        return this.material;
    }
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
