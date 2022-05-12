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
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;
import java.lang.Math;

import static java.awt.Color.BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Camera Class
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */
public class HelixTest {
    private Scene scene = new Scene("Test scene");
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * Bonus - Targil 7 - generate DNA.
     */
    @Test
    void testDNA() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);

        //intialized sphere's colors
        Color colors[] = {new Color(255, 0, 0), new Color(0, 0, 255)}; // red and blue (red for helix1 blue for helix 2), we try to generate DNA.

        //add helix 1:
        for (int i = 0, t = 10; i < 20; ++i, t +=10)
        {
            scene.geometries.add(new Sphere(new Point(Math.sin(t), Math.cos(t), 50d), 50d).setEmission(colors[0]) //
                    .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)));
        }

        //add lights
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("doubleHelix", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
