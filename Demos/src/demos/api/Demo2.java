package demos.api;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.numerical.IVP;
import acceleratorphysics.numerical.IVPSolver;
import acceleratorphysics.particles.Newton2ndLaw;
import acceleratorphysics.particles.Particle;
import acceleratorphysics.particles.ParticleFactory;

/**
 * Demonstration of switching between relativistic and newtonian mechanics
 */
public class Demo2 {

    /**
     * Demonstrates choice of mechanics framework by printing before/after state of a
     * particle subjected to a constant force of 10000N in the x direction for a given time.
     * @param factory 'relativistic' or 'newtonian' particle factory
     */
    private Demo2(ParticleFactory factory) {

        // Construct massive particle
        double mass = 1;
        Particle particle = factory.massiveParticle(mass);

        // Print initial state
        System.out.println("\n\t\tAt t = 0 \n\n" + particle);

        // Accelerating particle due to constant force. (tries to accelerate to speed of light)
        Vector3 force = Vector3.I.scale(10000);
        IVP constantForce = new Newton2ndLaw(particle) {
            @Override
            protected Vector3 acceleration(Vector3 r, Vector3 v, double t) {
                return particle.getAcceleration(force);
            }
        };

        // Solve problem (does v.norm() reach C? )
        double C = 3E8;
        double time = C / (force.norm()/ mass);
        constantForce.solve(IVPSolver.euler(1E-2), time);

        // Print final state
        System.out.println("\t\tAt t = " + time + "\n\n" + particle);

    }

    /**
     *  Runs demo using Newtonian mechanics then Relativistic mechanics
     * @param args
     */
    public static void main(String[] args) {
        // NB: Could specify factory at RUNTIME using args if desired
        new Demo2(ParticleFactory.Newtonian);
        new Demo2(ParticleFactory.Relativistic);
    }
}
