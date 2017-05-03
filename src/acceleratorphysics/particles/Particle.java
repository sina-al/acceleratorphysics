package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;

/**
 * Represents a massive Particle
 */
public class Particle {

    private final MaterialPoint point;
    private final String type;

    /**
     * Constructs particle with mass in an initial state according to a framework
     * @param framework
     * @param state
     * @param mass
     */
    Particle(Framework framework, State state, double mass) {
        if(mass <= 0)
            throw new IllegalArgumentException("Massless particle not supported.");
        point = framework.newMaterialPoint(state, mass);
        type = framework.toString();
    }

    @Deprecated
    Particle(State state, double mass) {
        this(Framework.NEWTONIAN, state, mass);
    }

    /**
     * Gets mass
     * @return mass
     */
    public double getMass(){
        return point.getMass();
    }

    /**
     * Gets energy
     * @return energy
     */
    public double getEnergy(){
        return point.getEnergy();
    }

    /**
     * Gets position
     * @return position
     */
    public Vector3 getPosition(){
        return point.getPosition();
    }

    /**
     * Gets velocity
     * @return velocity
     */
    public Vector3 getVelocity(){
        return point.getVelocity();
    }

    /**
     * Gets acceleration
     * @return acceleration
     */
    public Vector3 getAcceleration(){
        return point.getAcceleration();
    }

    /**
     * Gets acceleration due to force
     * @param force
     * @return acceleration due to force
     */
    public Vector3 getAcceleration(Vector3 force){
        return point.getAcceleration(force);
    }

    /**
     * Gets momentum
     * @return momentum
     */
    public Vector3 getMomentum(){
        return point.getMomentum();
    }

    /**
     * Gets radius of curvature
     * @return radius of curvature
     */
    public double radiusOfCurvature(){
        return point.radiusOfCurvature();
    }

    /**
     * Sets position
     * @param position
     */
    void setPosition(Vector3 position){
        point.setPosition(position);
    }

    /**
     * Sets velocity
     * @param velocity
     */
    void setVelocity(Vector3 velocity){
        point.setVelocity(velocity);
    }

    /**
     * Sets acceleration
     * @param acceleration
     */
    void setAcceleration(Vector3 acceleration){
        point.setAcceleration(acceleration);
    }

    public String toString(){
        return type + " Particle Object: " +
                "\nMass:         " + getMass() +
                "\nPosition:     " + getPosition() +
                "\nVelocity:     " + getVelocity() +
                "\nAcceleration: " + getAcceleration() + "\n";
    }
}
