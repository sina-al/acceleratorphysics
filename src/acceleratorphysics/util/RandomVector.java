package acceleratorphysics.util;

import java.util.Random;

/**
 * Began as a private nested class in Bunch (for implementation only)
 * but much neater here and perhaps even useful for client.
 */
// TODO: TEST MEH!
public class RandomVector {

    public enum Distribution {
        UNIFORM, GAUSSIAN
    }

    private final Random randomX = new Random();
    private final Random randomY = new Random();
    private final Random randomZ = new Random();
    
    private final Vector3 dispersion;
    private final Vector3 location;
    
    Distribution distribution;

    private RandomVector(){
        throw new AssertionError();
    }
    
    public RandomVector(Distribution distribution, Vector3 centre, Vector3 dispersion){
        this.dispersion = dispersion;
        this.location = centre;
        
        this.distribution = distribution;
    }

    /**
     * Returns next random vector according to distribution of this RandomVector
     * @return
     */
    public Vector3 next(){
        switch (distribution) {
            case UNIFORM:
                return nextUniform();
            case GAUSSIAN:
                return nextGaussian();
            default:
                return Vector3.ZERO;
        }
    }

    /**
     * Returns next pseudorandom uniformly distributed Vector3
     * @return random Vector3
     */
    public Vector3 nextUniform() {
        return new Vector3(
                dispersion.getX() * (randomX.nextDouble() - .5) + location.getX(),
                dispersion.getY() * (randomY.nextDouble() - .5) + location.getY(),
                dispersion.getZ() * (randomZ.nextDouble() - .5) + location.getZ()
        );
    }

    /**
     * Returns next pseudorandom gaussian distributed Vector3
     * @return random Vector3
     */
    public Vector3 nextGaussian() {
        return new Vector3(
                dispersion.getX() * randomX.nextGaussian() + location.getX(),
                dispersion.getY() * randomY.nextGaussian() + location.getY(),
                dispersion.getZ() * randomZ.nextGaussian() + location.getZ()
        );
    }             
}
