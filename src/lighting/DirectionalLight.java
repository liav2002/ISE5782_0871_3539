package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }

    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        direction = _direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }


}