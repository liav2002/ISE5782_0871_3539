package lighting;

import primitives.Color;

public abstract class Light {
    private Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }


    /**
     * Returns the intensity of the light
     *
     * @return The intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
