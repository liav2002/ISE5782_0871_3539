package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color intensity;

    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = new Color(Ia.getRGB().product(Ka));
    }

    /**
     * Returns the intensity of the light
     *
     * @return The intensity of the light.
     */
    public Color getIntensity(){
        return this.intensity;
    }
}