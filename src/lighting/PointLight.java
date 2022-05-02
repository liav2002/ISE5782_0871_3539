package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    public PointLight(Color _intensity, Point _position, double _kC, double _kL, double _kQ) {
        super(_intensity);
        position = _position;
        kC = _kC;
        kL = _kL;
        kQ = _kQ;
    }

    public PointLight(Color _intensity, Point _position) {
        super(_intensity);
        position = _position;
        kC = 0;
        kL = 0;
        kQ = 0;
    }

    private Point position;


    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    public double getDistance(Point p){
        return p.distance(position);
    }

    private double kC = 1, kL = 0, kQ = 0;


    @Override
    public Color getIntensity(Point p) {
        double d2 = position.distanceSquared(p);
        double d = Math.sqrt(d2);
        double factor = kC + kL * d + kQ * d2;
        if (factor < 1.0)
        {
            return getIntensity();
        }
        return getIntensity().reduce(factor);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}