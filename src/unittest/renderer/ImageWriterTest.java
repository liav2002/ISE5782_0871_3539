/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package unittest.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void image() {
        Color white = new Color(255, 255, 255);
        Color blue = new Color(0, 0, 255);
        ImageWriter Image = new ImageWriter("Pic", 800, 500);
        double rX = Image.getNx() / 16.0;
        double rY = Image.getNy() / 10.0;
        for (int j = 0; j < 500; j++) {
            for (int i = 0; i < 800; i++) {
                if ((i % rX == 0 && i != 0) || (j % rY == 0 && j != 0)) {
                    Image.writePixel(i, j, white);
                } else {
                    Image.writePixel(i, j, blue);
                }
            }
        }

        Image.writeToImage();
    }
}