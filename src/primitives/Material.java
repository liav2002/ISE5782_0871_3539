package primitives;

public class Material {
    Double3 kD = Double3.ZERO;
    Double3 kS = Double3.ZERO;
    Double3 kT = Double3.ZERO;
    Double3 kR = Double3.ZERO;
    int nShininess = 0;

    public Double3 getKt() {
        return kT;
    }

    public Double3 getKr() {
        return kR;
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

    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

}