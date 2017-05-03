package acceleratorphysics.util;

import acceleratorphysics.particles.Particle;

/**
 * Tracks orbit of an orbiting Particle
 */
public class OrbitTracker {

    private final Particle trackedParticle;
    private final Vector3 initialTangent;

    private boolean approachingPeak = false; // initialised to show intent
    private double lastMeasure;
    private int orbitCount;

    private OrbitTracker(){
        throw new AssertionError();
    }

    /**
     * Constructs new orbit tracker that tracks particle
     * @param particle
     */
    public OrbitTracker(Particle particle){
        initialTangent = particle.getVelocity().unit();
        trackedParticle = particle;
        lastMeasure = measureOrbit();
    }

    /**
     * Measures the dot product between the normalised initial velocity and the normalised current velocity
     * @return
     */
    private double measureOrbit(){
        return initialTangent.dot(trackedParticle.getVelocity().unit());
    }

    /**
     * Returns true if orbited
     * @return true at full revolution
     */
    public boolean hasOrbited(){
        double thisMeasure = measureOrbit();
        boolean approachingTrough = thisMeasure <= lastMeasure;
        boolean atPeak = approachingPeak && approachingTrough;
        // boolean atTrough = !approachingPeak && !approachingTrough
        approachingPeak = !approachingTrough;
        lastMeasure = thisMeasure;
        if (atPeak) orbitCount++;
        return atPeak;
    }

    /**
     * Returns number of orbits
     * @return number of orbits
     */
    public int getOrbitCount(){
        return orbitCount;
    }
}

