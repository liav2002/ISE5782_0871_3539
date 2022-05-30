package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * It's an abstract class that provides a constructor for a scene and an abstract method for tracing a ray
 */
public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene s) {
        this.scene = s;
    }

    public abstract Color traceRay(Ray ray);


}
