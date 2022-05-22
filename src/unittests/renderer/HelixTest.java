/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package unittests.renderer;

import geometries.Cylinder;
import geometries.Plane;
import geometries.Sphere;
import geometries.Tube;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.lang.Math;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Camera Class
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */
public class HelixTest {
    private Scene scene = new Scene("Test scene")
           ;// .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

    /**
     * Bonus - Targil 7 - generate DNA (double helix).
     */
    @Test
    void testDNA() {
        double tension = 0.15; // the tension
        double rad = 0.15;  // the radios of the helix
        double size = 0.02;  // the size of sphere
        double length = 12;  // the total length
        double distance = 0.07; // from point to point
        int lines = 6;  // the number of sphere between 2 cylinders

        scene.geometries.add(
                new Plane(new Vector(0, 0, 1),
                        new Point(0, 0, -10))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)));

        int i = 0;
        for (double t = -length; t < length; t += distance) {
            // helix 1:
            Point hel1 = new Point(Math.cos(t) * rad, t * tension, Math.sin(t) * rad);
            scene.geometries.add(new Sphere(
                    hel1,
                    size)
                    .setEmission(new Color(BLUE).reduce(2))
                    .setMaterial(new Material()
                            .setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

            // helix 2:
            Point hel2 = new Point(Math.cos(t + Math.PI) * rad, t * tension, Math.sin(t + Math.PI) * rad);
            scene.geometries.add(new Sphere(
                    hel2,
                    size)
                    .setEmission(new Color(RED).reduce(2))
                    .setMaterial(new Material()
                            .setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

            if (i++ % lines == 0)
                scene.geometries.add(new Cylinder(0.005, hel2, hel1).setEmission(new Color(BLACK)));
        }
//        scene.geometries.add(new Cylinder(0.03, new Point(0, 0, 0), new Point(0.1, 0.1, 0.1)).setEmission(new Color(WHITE)));

        scene.lights.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point(14, -10, 4),
                        new Vector(-14, 10, -4)) //
                .setKl(4E-5).setKq(2E-7));


        Point source = new Point(10, -7, 3.5);

//        Camera camera = new Camera(
//                new Point(0, 0, 20),
//                new Vector(0, 0, -1),
//                new Vector(0, 1, 0)
        Camera camera = new Camera(
                source,
//                new Vector(-5, 10, -3.5),
                new Vector(source.scale(-1)),
                new Vector(0, 1, 2)
        )
                .setVPSize(6, 6)
                .setVPDistance(50);

        camera.setImageWriter(new ImageWriter("doubleHelix", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}
