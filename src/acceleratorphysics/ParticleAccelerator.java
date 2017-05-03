package acceleratorphysics;

import acceleratorphysics.fields.EMField;
import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.particles.Newton2ndLaw;
import acceleratorphysics.util.Vector3;

/**
 * Initial value problem for a charged particle in an electromagnetic field.
 */
public class ParticleAccelerator extends Newton2ndLaw {

    private final EMField field;

    /**
     * Constructs a particle accelerator initial value problem of a charged particle in an electromagnetic field
     * @param chargedParticle charged particle
     * @param field electromagnetic field
     */
    public ParticleAccelerator(ChargedParticle chargedParticle, EMField field){
        super(chargedParticle);
        this.field = field;
    }

    @Override
    protected Vector3 acceleration(Vector3 r, Vector3 v, double t){
        ChargedParticle particle = (ChargedParticle)getParticle();
        double Q = particle.getCharge();
        return particle.getAcceleration(field.lorentzForce(Q, v, r, t));
    }

}
