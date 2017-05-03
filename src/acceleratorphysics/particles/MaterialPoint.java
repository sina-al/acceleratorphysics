package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;

/**
 * Represents a massive mutable point
 */
abstract class MaterialPoint extends MutablePoint implements Massive {

    /**
     * Constructs material point with initial state
     * @param state state
     */
    MaterialPoint(State state){
        super(state);
    }

    /**
     * Returns momentum
     * @return momentum
     */
    abstract Vector3 getMomentum();

    /**
     * Returns acceleration due to force
     * @param force
     * @return acceleration due to force
     */
    abstract Vector3 getAcceleration(Vector3 force);

}
