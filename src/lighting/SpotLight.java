package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    /**
     * Build SpotLight object.
     *
     * @param _intensity: color's intensity.
     * @param _position:  point's position.
     * @param _direction: vector's direction.
     */
    public SpotLight(Color _intensity, Point _position, Vector _direction) {
        super(_intensity, _position, 0, 0, 0);
        direction = _direction.normalize();
    }

    public SpotLight(Color _intensity, Point _position, Vector _direction, double _kC, double _kL, double _kQ) {
        super(_intensity, _position, _kC, _kL, _kQ);
        direction = _direction.normalize();
    }

    private Vector direction;

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(Math.max(0, direction.dotProduct(getL(p).normalize())));
    }
}