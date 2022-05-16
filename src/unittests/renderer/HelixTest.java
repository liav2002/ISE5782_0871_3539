/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package unittests.renderer;

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
            .setAmbientLight(new AmbientLight(new Color(WHITE).reduce(5), 0.05));

    /**
     * Bonus - Targil 7 - generate DNA (double helix).
     */
    @Test
    void testDNA() {
        double tension = 0.05; // the tension
        double rad = 0.06;  // the radios of the helix
        double size = 0.01;  // the size of sphere
        double length = 5;  // the total length
        double distance = 0.1; // from point to point
        scene.geometries.add(
                new Plane(new Vector(0, 0, 1),
                        new Point(0, 0, -10))
                        .setEmission(new Color(WHITE))
        );

        for (double t = -length; t < length; t += distance) {
            //add helix 1:
            Point hel1 = new Point(Math.cos(t) * rad, t * tension,
                    Math.sin(t) * rad * 10);
            scene.geometries.add(new Sphere(
                    hel1,
                    size)
                    .setEmission(new Color(BLUE).reduce(2))
                    .setMaterial(new Material().setKd(0.4).setKs(0.3)
                            .setShininess(10).setKt(0.3).setKr(0)));

            //add helix 2:
            Point hel2 = new Point(Math.cos(t) * rad, t * tension + tension * Math.PI, Math.sin(t) * rad * 10);
            scene.geometries.add(new Sphere(
                    hel2,
                    size)
                    .setEmission(new Color(RED).reduce(2))
                    .setMaterial(new Material().setKd(0.4).setKs(0.3)
                            .setShininess(10).setKt(0.3).setKr(0)));
        }


        scene.lights.add(
                new SpotLight(new Color(GREEN).reduce(2),
                        new Point(50, 10, 50),
                        new Vector(-0.2, 0, -1)
                )
        );



        Camera camera = new Camera(new Point(0, 0, 70),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0)
        ).rotate(0, 0, 45)
                .setVPSize(6, 6)
                .setVPDistance(1000);

        camera.setImageWriter(new ImageWriter("doubleHelix", 500, 500))
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}
