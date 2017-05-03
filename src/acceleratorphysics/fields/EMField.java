package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.particles.ChargedParticle;


/**
 * General electromagnetic field
 */
public abstract class EMField {

    protected abstract Vector3 E(Vector3 r, double t);
    protected abstract Vector3 B(Vector3 r, double t);

    /**
     * Returns lorentz force on a charged particle at time t
     * @param p particle
     * @param t time
     * @return lorentz force
     */
    public Vector3 lorentzForce(ChargedParticle p, double t){
        Vector3 r = p.getPosition();
        Vector3 v = p.getVelocity();
        double q = p.getCharge();
        return lorentzForce(q, v, r, t);
    }

    /**
     * Returns lorentz force on a charged particle at time t
     * @param q charge of particle
     * @param v velocity of particle
     * @param r position of particle
     * @param t time
     * @return lorentz force
     */
    public Vector3 lorentzForce(double q, Vector3 v, Vector3 r, double t){
        return v.cross(B(r,t)).add(E(r,t)).scale(q);
    }

}

