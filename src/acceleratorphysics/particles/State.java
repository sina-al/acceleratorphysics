package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.util.RandomVector.Distribution;

/**
 * Singleton class used to initialise Particle / Bunch objects
 * with an initial state.
 */
public final class State {

    private Vector3 r0, v0, a0;        // States
    private Vector3 dr0, dv0, da0;     // Spreads (for bunch)
    private Distribution distribution; // Distribution (for bunch)

    private State(){
        r0 = Vector3.ZERO;
        v0 = Vector3.ZERO;
        a0 = Vector3.ZERO;

        dr0 = Vector3.ZERO;
        dv0 = Vector3.ZERO;
        da0 = Vector3.ZERO;

        distribution = Distribution.UNIFORM;
    }

    private static final State instance = new State();

    /**
     * Sets position of this state
     * @param r0 initial position
     * @return this state
     */
    public State position(Vector3 r0){
        this.r0 = r0;
        return this;
    }


    /**
     * Sets position dispersion of this state
     * @param dr0 dispersion in initial position
     * @return this state
     */
    public State positionDispersion(Vector3 dr0){
        this.dr0 = dr0;
        return this;
    }

    /**
     * Sets velocity of this state
     * @param v0 initial velocity
     * @return this state
     */
    public State velocity(Vector3 v0){
        this.v0 = v0;
        return this;
    }

    /**
     * Sets velocity dispersion of this state
     * @param dv0 dispersion in initial velocity
     * @return this state
     */
    public State velocityDispersion(Vector3 dv0){
        this.dv0 = dv0;
        return this;
    }

    /**
     * Sets acceleration of this state
     * @param a0 initial velocity
     * @return this state
     */
    public State acceleration(Vector3 a0){
        this.a0 = a0;
        return this;
    }

    /**
     * Sets acceleration dispersion of this state
     * @param da0 dispersion in initial velocity
     * @return this state
     */
    public State accelerationDispersion(Vector3 da0){
        this.da0 = da0;
        return this;
    }

    /**
     * Sets this state to the state of a particle
     * @param particle particle
     * @return this state
     */
    public static State of(Particle particle){
        return instance
                .position(particle.getPosition())
                .velocity(particle.getVelocity())
                .acceleration(particle.getAcceleration());
    }

    /**
     * Point of access to this singleton. Sets everything to zero
     * @return zero state
     */
    public static State zero(){
        return instance
                .position(Vector3.ZERO)
                .velocity(Vector3.ZERO)
                .acceleration(Vector3.ZERO);
    }

    /**
     * Manifests this state into mutable point
     * @param point mutable point
     */
    void manifest(MutablePoint point){

        point.setPosition(r0);
        point.setVelocity(v0);
        point.setAcceleration(a0);

    }

    /**
     * Manifests this state into a bunch
     * @param bunch
     */
    void manifest(Bunch bunch){

        bunch.setPosition(r0, dr0, distribution);
        bunch.setVelocity(v0, dv0, distribution);
        bunch.setAcceleration(a0, da0, distribution);

    }

}
