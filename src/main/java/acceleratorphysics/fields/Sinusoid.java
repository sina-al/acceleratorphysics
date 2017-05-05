package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;

/**
 * Sinusoidal time-varying electromagnetic field
 */
public class Sinusoid extends EMField {

    private final EMField field;

    private final Vector3 cavityCentre;
    private final double Lx;
    private final double Ly;
    private final double Lz;

    private final Vector3 direction;
    private final double amplitude;
    private final double frequency; // angular
    private final double phase;

    private Sinusoid(
            Type fieldType,
            Vector3 cavityCentre, double Lx, double Ly, double Lz,
            Vector3 direction, double amplitude, double frequency, double phase
    ){
        switch (fieldType) {
            case ELECTRIC:
                field = new EField() {
                    @Override
                    public Vector3 E(Vector3 r, double t) {
                        return Field(r, t);
                    }
                };
                break;
            case MAGNETIC:
                field = new BField() {
                    @Override
                    public Vector3 B(Vector3 r, double t) {
                        return Field(r, t);
                    }
                };
                break;
            default:
                throw new AssertionError(); // FIXME: improve
        }
        this.cavityCentre = cavityCentre;
        this.Lx = Lx;
        this.Ly = Ly;
        this.Lz = Lz;
        this.direction = direction;
        this.amplitude = amplitude;
        this.frequency = frequency;
        this.phase = phase;
    }

    /**
     * Sinusoid Builder
     */
    public static class Builder {

         private Type fieldType;
         private Vector3 cavityCentre, direction;
         private double Lx, Ly, Lz, amplitude, frequency, phase;

         public Builder(Type fieldType){
             this.fieldType = fieldType;
             Lx = Double.POSITIVE_INFINITY; // FIXME: will autoboxing affect performance?
             Ly = Double.POSITIVE_INFINITY;
             Lz = Double.POSITIVE_INFINITY;
         }

         public Builder setCavityCentre(Vector3 r){
             cavityCentre = r;
             return this;
         }

         public Builder setLX(double x){
             Lx = x;
             return this;
         }

         public Builder setLY(double y){
             Ly = y;
             return this;
         }

         public Builder setLZ(double z){
             Lz = z;
             return this;
         }

         public Builder setDirection(Vector3 r){
             direction = r.unit();
             return this;
         }

         public Builder setAmplitude(double A){
             amplitude = A;
             return this;
         }

         public Builder setFrequency(double omega){
             frequency = omega;
             return this;
         }

         public Builder setPhase(double phi){
             phase = phi;
             return this;
         }

         public Sinusoid build(){
             if (cavityCentre == null) cavityCentre = Vector3.ZERO;
             return new Sinusoid(fieldType, cavityCentre, Lx, Ly, Lz,
                     direction, amplitude, frequency, phase);
         }
    }

    /**
     * Returns true if r is inside accelerating cavity
     * @param r position
     * @return true if in cavity
     */
    private boolean inCavity(Vector3 r){
        return Math.abs(r.getX() - cavityCentre.getX()) <= Lx &&
               Math.abs(r.getY() - cavityCentre.getY()) <= Ly &&
               Math.abs(r.getZ() - cavityCentre.getZ()) <= Lz;
    }

    /**
     * Magnitude of field at time t
     * @param t time
     * @return magnitude of field
     */
    private double magnitude(double t){
        return amplitude * Math.sin(frequency * t + phase);
    }

    /**
     * Returns value of EMField at position r and time t
     * @param r position
     * @param t time
     * @return value of EMField
     */
    private Vector3 Field(Vector3 r, double t){
        return (inCavity(r)) ? direction.scale(magnitude(t)) : Vector3.ZERO;
    }

    @Override
    public Vector3 E(Vector3 r, double t){
        return field.E(r, t);
    }

    @Override
    public Vector3 B(Vector3 r, double t){
        return field.B(r, t);
    }
}
