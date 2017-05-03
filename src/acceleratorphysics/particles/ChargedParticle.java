package acceleratorphysics.particles;

import acceleratorphysics.fields.EMField;
import acceleratorphysics.fields.EField;
import acceleratorphysics.util.Vector3;

/**
 * Charged particle
 */
public class ChargedParticle extends Particle implements Charged {

    static final double EPSILON_NOUGHT = 8.854187817E-12;

    private final double charge;

    /**
     * Coulomb field due to this particle
     */
    private final EMField coulombField = new EField() {
        @Override
        public Vector3 E(Vector3 r, double t) {
            return r.subtract(getPosition())
                    .scale(charge / (4 * Math.PI * EPSILON_NOUGHT));
        }
    };

    /**
     * Constructs charged particle
     * @param framework framework
     * @param state initial state
     * @param mass rest mass
     * @param charge charge
     */
    ChargedParticle(Framework framework, State state, double mass, double charge) {
        super(framework, state, mass);
        this.charge = charge;
    }

    @Override
    public double getCharge(){
        return charge;
    }

    @Override
    public EMField getField(){
        return coulombField;
    }

}
