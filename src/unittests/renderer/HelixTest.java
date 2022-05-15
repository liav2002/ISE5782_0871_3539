/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package unittests.renderer;

import geometries.Sphere;
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
            .setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

    /**
     * Bonus - Targil 7 - generate DNA (double helix).
     */
    @Test
    void testDNA() {

        //add helix 1:
        for (double t = 0; t < 37.4; t += 0.2) {
            scene.geometries.add(new Sphere(new Point(Math.cos(t) * 500, t * 250, Math.sin(t) * 100), 100)
                    .setEmission(new Color(BLUE).reduce(2))
                    .setMaterial(new Material().setKd(0.4).setKs(0.3)
                            .setShininess(100).setKt(0.3).setKr(0)));
        }

        //add helix 2:
        for (double t = 0; t < 37.4; t += 0.2) {
            scene.geometries.add(new Sphere(new Point(Math.cos(t) * 500, t * 250 + 750, Math.sin(t) * 1000), 100)
                    .setEmission(new Color(RED).reduce(2))
                    .setMaterial(new Material().setKd(0.4).setKs(0.3)
                            .setShininess(100).setKt(0.3).setKr(0)));
        }

        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(2000, 5000, 0), new Vector(-50, 100, 30)) //
                        .setKl(0.0004).setKq(0.0000006));

        scene.lights.add(new SpotLight(new Color(800, 500, 250), new Point(30, 10, -100), new Vector(-2, -2, -2))
                .setKl(0.001).setKq(0.00004));

        Camera camera = new Camera(new Point(100, 5500, 50000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150) //
                .setVPDistance(1000);

        camera.setImageWriter(new ImageWriter("doubleHelix", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
