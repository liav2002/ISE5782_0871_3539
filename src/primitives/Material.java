package primitives;

public class Material {


    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public Double3 getKd() {
        return kD;
    }

    public Double3 getKs() {
        return kS;
    }

    public int getnShininess() {
        return nShininess;
    }

    public Material setKd(double d) {
        this.kS = new Double3(d);
        return this;
    }

    public Material setKs(double d) {
        this.kD = new Double3(d);
        return this;
    }

    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    Double3 kD = Double3.ZERO, kS = Double3.ZERO;
    int nShininess = 0;


}