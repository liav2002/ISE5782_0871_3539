package renderer;
import primitives.*;
import lighting.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {

    private static final Double3 INITIAL_K = new Double3(1.0);
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * A constructor
     *
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * The function receives a ray and returns the color of the closest intersection point of the ray with the scene
     *
     * @param ray The ray that we want to trace.
     * @return The color of the closest intersection point.
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> myPoints = scene.geometries.findGeoIntersections(ray);
        if (myPoints != null) {
            GeoPoint myPoint = ray.findClosestGeoPoint(myPoints);
            return calcColor(myPoint, ray);
        }
        return scene.background;
    }

    /**
     * It calculates the color of a given point on a given geometry, by adding the emission of the geometry to the local
     * effects of the geometry, and then adding the global effects of the geometry if the recursion level is greater than 1
     *
     * @param geoPoint The closest point of intersection between the ray and the scene.
     * @param ray the ray that hit the geometry
     * @param level the recursion level.
     * @param k the ratio of the current ray's color to the color of the previous ray.
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = geoPoint.geometry.getEmission()
                .add(calcLocalEffects(geoPoint, ray,k));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * > The function calculates the color of a given point on a given ray, by calculating the color of the point on the
     * ray, and adding the ambient light to it
     *
     * @param gp The point on the surface of the object that we're calculating the color for.
     * @param ray the ray that hit the object
     * @return The color of the point.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * It calculates the color of the point by calculating the color of the reflected and refracted rays
     *
     * @param geopoint The point on the geometry that the ray intersected with.
     * @param ray the ray that hit the geometry
     * @param level the recursion level.
     * @param k the color of the light that is reflected from the current point.
     * @return The color of the point.
     */
    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = geopoint.geometry.getMaterial();
        Double3 kkr = k.product(material.kR);
        Vector n = geopoint.geometry.getNormal(geopoint.point);
        if (kkr.biggerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(n, geopoint.point, ray.getDir());
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(material.kR));
            }
        }
        Double3 kkt = k.product(material.kT);
        if (kkt.biggerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(n, geopoint.point, ray.getDir());
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null) {
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(material.kT));
            }
        }
        return color;
    }


    /**
     * The function calculates the color of a point on a geometry, by calculating the color of the light sources that
     * affect the point, and the color of the reflected rays from the point
     *
     * @param geoPoint The point on the geometry that the ray intersects with.
     * @param ray the ray that hit the point
     * @param k the color of the geometry
     * @return The color of the point.
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        Color color = Color.BLACK;
        for (LightSource lightSource : this.scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(geoPoint,lightSource,l,n);//transparency(l, n, geopoint, nv);
                var x = ktr.product(k);
                if (x.biggerThan(MIN_CALC_COLOR_K)){
                    Color intensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(calcDiffusive(material.getKd(), l, n, intensity),
                            calcSpecular(material.getKs(), l, n, v, material.getnShininess(), intensity));
                }
            }
        }
        return color;
    }

    /**
     * "Calculate the diffusive component of the light intensity by multiplying the intensity of the light by the diffusive
     * coefficient of the material and the dot product of the light vector and the normal vector."
     *
     * The function takes in the diffusive coefficient of the material, the light vector, the normal vector, and the
     * intensity of the light. It returns the diffusive component of the light intensity
     *
     * @param kD The diffuse coefficient of the material.
     * @param l the vector from the point on the surface to the light source
     * @param n the normal vector of the surface
     * @param intensity the color of the light source
     * @return The color of the diffuse reflection.
     */
    private Color calcDiffusive(Double3 kD, Vector l, Vector n, Color intensity) {
        return intensity.scale(kD.scale(Math.abs(l.dotProduct(n))));
    }

    /**
     * "Calculate the specular component of the light's contribution to the color of the surface at the given point."
     *
     * The function takes the following parameters:
     *
     * * kS: The specular reflectivity of the surface.
     * * l: The direction of the light.
     * * n: The normal of the surface at the point.
     * * v: The direction of the viewer.
     * * nShininess: The shininess of the surface.
     * * intensity: The intensity of the light
     *
     * @param kS The specular coefficient.
     * @param l the vector from the point to the light source
     * @param n normal vector
     * @param v the vector from the point to the camera
     * @param nShininess The shininess of the material.
     * @param intensity the color of the light source
     * @return The color of the point on the surface of the object.
     */
    private Color calcSpecular(Double3 kS, Vector l, Vector n, Vector v, int nShininess, Color intensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        double vr = alignZero(-v.dotProduct(r));
        if(vr <= 0)
            return  Color.BLACK;
        return intensity.scale(kS.scale(Math.pow(vr, nShininess)));
    }

    /**
     * > Construct a reflected ray from a point, a normal vector, and an incident vector
     *
     * @param n The normal vector of the surface at the point of intersection.
     * @param point The point of intersection
     * @param v the vector from the point to the light source
     * @return A ray that is reflected off the surface of the object.
     */
    private Ray constructReflectedRay(Vector n, Point point, Vector v) {
        return new Ray(point, v.subtract(n.scale(2 * v.dotProduct(n))), n);
    }

    /**
     * Construct a refracted ray from the given point, with the given normal and direction.
     *
     * @param n the normal vector of the surface
     * @param point The point of intersection between the ray and the surface.
     * @param v the direction of the incoming ray
     * @return A new ray with the same direction as the original ray, but with a new origin.
     */
    private Ray constructRefractedRay(Vector n, Point point, Vector v) {
        return new Ray(point, v, n);
    }

    /**
     * If the ray from the point to the light source intersects with any opaque object, then the point is in shadow
     *
     * @param light the light source
     * @param gp the point on the geometry that we're shading
     * @param l the vector from the light source to the point on the geometry
     * @param n the normal vector of the point
     * @return The color of the pixel.
     */
    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point));
        if (intersections != null) {
            for (GeoPoint intersection : intersections) {
                if (intersection.geometry.getMaterial().kT == Double3.ZERO) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * The function calculates the transparency of a point on a geometry, by checking if there are any other geometries
     * between the point and the light source
     *
     * @param geoPoint The point on the geometry that we're currently shading.
     * @param ls the light source
     * @param l the vector from the point to the light source
     * @param n the normal vector of the point on the geometry
     * @return The transparency of the point.
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance = ls.getDistance(geoPoint.point);
        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return new Double3(1.0); //no intersection
        Double3 ktr = new Double3(1.0);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                //ktr *= gp.geometry.getMaterial().kT;
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * It finds the closest intersection point of a ray with the scene's geometries
     *
     * @param ray The ray that we want to find the closest intersection point to.
     * @return The closest intersection point of the ray with the scene.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(geoPoints);
    }
}