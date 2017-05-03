package demos.api;

import acceleratorphysics.ParticleAccelerator;
import acceleratorphysics.util.Vector3;
import acceleratorphysics.fields.*;
import acceleratorphysics.numerical.IVP;
import acceleratorphysics.numerical.IVPSolver;
import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.particles.State;
import acceleratorphysics.particles.ParticleFactory;

/**
 * Demonstration of cyclotron simulation
 */
public class Demo3 {

    /**
     * Demonstrates construction of cyclotron simulation.
     * @param args
     */
    public static void main(String[] args){

        // Specify particle factory
        ParticleFactory factory = ParticleFactory.Newtonian;

        // Specify initial state
        double speed = 0.1;
        State initialState = State.zero().velocity(Vector3.I.scale(speed));

        // Construct proton
        ChargedParticle proton = factory.proton(initialState);

        // Specify electromagnetic field parameters
        double fluxDensity = 1E-7;
        double frequency = fluxDensity * (proton.getCharge() / proton.getMass());
        double radius = speed / frequency;

        // Construct uniform magnetic field
        EMField uniformB = new Uniform(Type.MAGNETIC, Vector3.J.scale(fluxDensity));

        // Construct time varying electric field
        EMField timeVaryingE =
                new Sinusoid.Builder(Type.ELECTRIC)
                .setDirection(Vector3.I)
                .setAmplitude(1E-7)
                .setFrequency(frequency)
                .setLY(0.05 * radius)
                .build();

        // Superimpose electric and magnetic fields
        EMField cyclotronField = new Superimposed(uniformB, timeVaryingE);

        // Specify initial value problem
        IVP cyclotron = new ParticleAccelerator(proton,cyclotronField);

        // Solve problem
        cyclotron.solve(IVPSolver.euler(0.03), 100);

    }
}
