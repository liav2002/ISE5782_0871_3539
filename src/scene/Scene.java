/*
 *  Mini Project in Introduction to Software Engineering.
 *
 *  @author1  Liav Ariel (212830871), halkadar@g.jct.ac.il
 *  @author2  Eyal Seckbach (324863539), seyal613@gmail.com
 *
 *  Lecture: Yair Goldstein.
 */

package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight;
    public Geometries geometries;

    public List<LightSource> lights = new LinkedList<>();

    public Scene(String n) {
        this.name = n;
        this.background = Color.BLACK;
        this.ambientLight = new AmbientLight();
        this.geometries = new Geometries();
    }

    public Scene() {
        this.background = Color.BLACK;
        this.ambientLight = new AmbientLight();
        this.geometries = new Geometries();
    }

    public Scene setLights(List<LightSource> L){
        this.lights = L;
        return this;
    }

    public Scene setBackground(Color c) {
        this.background = c;
        return this;
    }

    public Scene setAmbientLight(AmbientLight al) {
        this.ambientLight = al;
        return this;
    }

    public Scene setGeometries(Geometries g) {
        this.geometries = g;
        return this;
    }

    public Scene clone(){
        return new Scene()
                .setBackground(this.background)
                .setAmbientLight(this.ambientLight)
                .setLights(this.lights)
                .setGeometries(this.geometries.clone());
    }


}
