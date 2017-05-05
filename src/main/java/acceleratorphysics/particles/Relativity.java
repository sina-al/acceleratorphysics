package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;

/**
 * A utility class for relativistic related methods / constants
 */
final class Relativity {

    private Relativity(){
        throw new AssertionError();
    }

    /**
     * Speed of light in SI units
     */
    static final double C = 2.99792458E8;

    /**
     * Gamma factor
     * @param u velocity
     * @return gamma
     */
    static double gamma(Vector3 u){
        return 1D / Math.sqrt(1D - u.dot(u) / (C * C));
    }


}
