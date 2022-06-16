

package unittests.renderer;

import geometries.Cylinder;
import geometries.Plane;
import geometries.Sphere;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.io.IOException;
import java.lang.Math;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;


import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;


import static java.awt.Color.*;

/**
 * Testing Camera Class
 *
 * @author1 Eyal Seckbach
 * @author2 Liav Ariel
 */
public class HelixTest {
    Scene scene = new Scene("Test scene");


    Point source = new Point(1, 10, 1);

    Camera camera = new Camera(
            source,
            new Vector(source.scale(-1)),
            new Vector(0, 1, -10)
    )
            .setVPSize(6, 6)
            .setVPDistance(50)
            .setMultithreading(8);

    @Test
    void testGIF() throws IOException {

//        scene.geometries.add(
//                new Plane(
//                        new Vector(0, 1, 0),
//                        new Point(1, 10, -4))
//                        .setEmission(new Color(BLACK))
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)));


        scene.lights.add(
                new SpotLight(
                        new Color(700, 400, 400),
                        new Point(12.5, -9, 4),
                        new Vector(-12.5, 9, -4)) //
                        .setKl(4E-5).setKq(2E-7));

        Scene s;
        int frames = 60; // the number of frames
        double jump = Math.PI * 2 / frames;

        String pwd = Paths.get("").toAbsolutePath() + "\\images\\";
        String file = "img";

        ImageOutputStream output = new FileImageOutputStream(new File(pwd + "gif\\dna.gif"));
        GifSequenceWriter writer = new GifSequenceWriter(output, 5, 50, true);


        for (double i = 0; i < Math.PI * 2; i += jump) {
            Instant start = Instant.now();
            s = getSnapshot(i);
            camera.setImageWriter(new ImageWriter(file, 1000, 1000))
                    .setRayTracer(new RayTracerBasic(s))
                    .renderImage()
                    .writeToImage();

            Instant finish = Instant.now();
            System.out.println("Render: " + Math.round(i / jump) + ", time: " + Duration.between(start, finish).toMillis() + "ms");

            BufferedImage next = ImageIO.read(new File(pwd + file + ".png"));
            writer.writeToSequence(next);
        }
        writer.close();
        output.close();
    }

    Scene getSnapshot(double degree) {
        double tension = 0.5; // the tension
        double rad = 0.15;  // the radios of the helix
        double size = 0.02;  // the size of sphere
        double length = 25;  // the total length
        double distance = 0.1; // from point to point
        int lines = 6 * 1;  // the number of sphere between 2 cylinders
        Scene s = scene.clone();  // copy the existing scene

        int i = 0;
        for (double t = -length; t < length; t += distance) {
            // helix 1:
            Point hel1 = new Point(Math.cos(t + degree) * rad, t * tension, Math.sin(t + degree) * rad);
            s.geometries.add(new Sphere(
                    hel1,
                    size)
                    .setEmission(new Color(BLUE).reduce(2))
                    .setMaterial(new Material()
                            .setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

            // helix 2:
            Point hel2 = new Point(Math.cos(t + Math.PI + degree) * rad, t * tension, Math.sin(t + Math.PI + degree) * rad);
            s.geometries.add(new Sphere(
                    hel2,
                    size)
                    .setEmission(new Color(RED).reduce(2))
                    .setMaterial(new Material()
                            .setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

            if (i++ % lines == 0)
                s.geometries.add(new Cylinder(0.005, hel2, hel1).setEmission(new Color(WHITE)));
        }
        System.out.println("Rendering");
        return s;
    }
}
