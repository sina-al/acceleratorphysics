package demos.api;

import acceleratorphysics.ParticleAccelerator;
import acceleratorphysics.fields.Type;
import acceleratorphysics.util.Vector3;
import acceleratorphysics.fields.EMField;
import acceleratorphysics.fields.Uniform;
import acceleratorphysics.numerical.IVP;
import acceleratorphysics.numerical.IVPSolver;
import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.particles.State;
import acceleratorphysics.particles.ParticleFactory;

/**
 * Demonstration of basic usage of API
 */
public class Demo0 {

    private static ParticleFactory factory = ParticleFactory.Newtonian;

    /**
     * This demo determines the state of a proton in a uniform B field after 10 seconds.
     * The trajectory is solved using euler method with default step size.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Define initial state
        State initialState = State.zero()
                .velocity(Vector3.I.scale(0.1));

        // Construct particle with initial state (using factory)
        ChargedParticle particle = factory.proton(initialState);

        // Construct field
        EMField field = new Uniform(Type.MAGNETIC, Vector3.J.scale(1E-7));

        // Specify initial value problem
        IVP circular = new ParticleAccelerator(particle,field);

        // Chose numerical method to solve problem
        IVPSolver solver = IVPSolver.euler();

        // Solve problem
        double simulationTime = 10;
        circular.solve(solver, simulationTime);

        //Print final state
        System.out.println("After " + simulationTime + " s :");
        System.out.println(particle);
    }
}
