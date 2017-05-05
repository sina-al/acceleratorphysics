package acceleratorphysics.particles;

import acceleratorphysics.util.RandomVector;
import acceleratorphysics.util.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

import static acceleratorphysics.util.RandomVector.Distribution;

// TODO: TEST ME

/**
 * Represents a bunch of particles.
 * @param <T>
 */
public class Bunch<T extends Particle>  implements Massive, Point {

    final ArrayList<T> particles;

    // FIXME:  only public for BunchAccelerator (see class javadoc)
    public Particle[] getParticles(){
        return particles.toArray(new Particle[particles.size()]);
    }


    /**
     * Construct punch from vararg list of particles
     * @param state initial state
     * @param particlesIn list of particles
     */
    Bunch(State state, T... particlesIn){
        particles = new ArrayList<>(Arrays.asList(particlesIn));
        state.manifest(this);
        particlesIn = null; // heap pollution.
    }

    /**
     * Randomly distributes positions of particles in this bunch
     * @param position centre
     * @param spread dispersion from centre
     * @param distribution distribution type
     */
    void setPosition(Vector3 position, Vector3 spread, Distribution distribution){
        RandomVector randomVector = new RandomVector(distribution, position, spread);
        for (T particle: particles) {
            particle.setPosition(randomVector.next());
        }
    }

    /**
     * Randomly distributes velocities of particles in this bunch
     * @param velocity centre
     * @param spread dispersion from centre
     * @param distribution distribution type
     */
    void setVelocity(Vector3 velocity, Vector3 spread, Distribution distribution){
        RandomVector randomVector = new RandomVector(distribution, velocity, spread);
        for (T particle: particles) {
            particle.setVelocity(randomVector.next());
        }
    }

    /**
     * Randomly distributes accelerations of particles in this bunch
     * @param acceleration centre
     * @param spread dispersion from centre
     * @param distribution distribution type
     */
    void setAcceleration(Vector3 acceleration, Vector3 spread, Distribution distribution){
        RandomVector randomVector = new RandomVector(distribution, acceleration, spread);
        for (T particle: particles) {
            particle.setAcceleration(randomVector.next());
        }
    }

    /**
     * Returns the mean average of specified Vector3 quantities of particles in this bunch
     * @param vector3Function Vector3 quantity of particle
     * @return mean average of quantity from particles in this bunch
     */
    private Vector3 average(Function<Particle, Vector3> vector3Function){
        return particles.stream()
                .map(vector3Function)
                .reduce(Vector3.ZERO, Vector3::add)
                .scale(1.0/particles.size());
    }

    /**
     * gets mean average acceleration of bunch
     * @return mean average acceleration
     */
    public Vector3 getAcceleration(){
        return average(Particle::getAcceleration);
    }

    /**
     * gets mean average velocity of bunch
     * @return mean average velocity
     */
    public Vector3 getVelocity(){
        return average(Particle::getVelocity);
    }

    /**
     * gets mean average position of bunch
     * @return mean average position
     */
    public Vector3 getPosition(){
        return average(Particle::getPosition);
    }

    /**
     * gets collective mass of bunch
     * @return mass
     */
    public double getMass(){
        return particles.stream()
                .mapToDouble(Particle::getMass)
                .sum();
    }

    /**
     * returns collective energy of bunch
     * @return energy
     */
    public double getEnergy(){
        return particles.stream()
                .mapToDouble(Particle::getEnergy)
                .sum();
    }

}
