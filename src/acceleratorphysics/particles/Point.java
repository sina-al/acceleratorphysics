package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;


/**
 * Defines contract of a point of a line (path) in three dimensional euclidean space
 */
interface Point {

    Vector3 getPosition();
    Vector3 getVelocity();
    Vector3 getAcceleration();

    // found out about this formula afterwards

    /**
     * Returns radius of curvature
     * @return radius of curvature
     */
    default double radiusOfCurvature(){

        double av = getAcceleration().dot(getVelocity());
        double a = getAcceleration().norm();
        double v = getVelocity().norm();

        return (v * v * v) / Math.sqrt(v * v * a * a - (av * av));
    }

}
