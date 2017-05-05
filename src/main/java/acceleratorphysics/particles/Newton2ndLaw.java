package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.numerical.IVP;


/**
 * Concrete implementation of Newtons Second Law
 */
public abstract class Newton2ndLaw extends IVP {

    private final Particle particle;
    private double time;

    /**
     * Constructs IVP corresponding to newtons 2nd law on specified particle
     * @param particle particle
     */
    public Newton2ndLaw(Particle particle){
        super(2);
        this.particle = particle;
    }

    /**
     * gets particle
     * @return particle
     */
    protected Particle getParticle(){
        return particle;
    }

    @Override
    public Vector3 f(Vector3[] y, double x){
        return acceleration(y[0], y[1], x);
    }

    /**
     * Must implement acceleration as a function of particle velocity, position and time
     * @param r position
     * @param v velocity
     * @param t time
     * @return acceleration
     */
    protected abstract Vector3 acceleration(Vector3 r, Vector3 v, double t);

    @Override
    public Vector3[] y(){
        return new Vector3[]{particle.getPosition(), particle.getVelocity()};
    }

    @Override
    public double t(){
        return time;
    }

    @Override
    protected void increment(Vector3[] dy, double h){
        time += h;
        particle.setPosition(particle.getPosition().add(dy[0]));
        particle.setVelocity(particle.getVelocity().add(dy[1]));
        particle.setAcceleration(f());
    }
}
