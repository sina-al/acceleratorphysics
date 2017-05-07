package acceleratorphysics.demos;

import acceleratorphysics.ParticleAccelerator;
import acceleratorphysics.fields.*;
import acceleratorphysics.numerical.IVP;
import acceleratorphysics.numerical.IVPSolver;
import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.particles.ParticleFactory;
import acceleratorphysics.particles.State;
import acceleratorphysics.util.OrbitTracker;
import acceleratorphysics.util.Vector3;

public class Demo0 {

    public static void main(String[] args){

        // Make particle
        ChargedParticle proton =  ParticleFactory.Newtonian.proton(
                State.zero()
                        .position(Vector3.ZERO)
                        .velocity(Vector3.I.scale(0.1))
        );

        // Make field
        EMField cyclotronField = new Superimposed(
                new Uniform(Type.MAGNETIC, Vector3.J.scale(1e-7)),
                new Sinusoid.Builder(Type.ELECTRIC)
                .setDirection(Vector3.I)
                .setPhase(Math.PI / 2)
                .setFrequency(0.66)
                .setLX(0.05)
                .setLY(0.05)
                .setAmplitude(1e-7)
                .build()
        );

        // Set up initial value problem
        IVP cyclotron = new ParticleAccelerator(proton, cyclotronField);

        // Set IVP to print position of proton & time at each revolution
        OrbitTracker tracker = new OrbitTracker(proton);
        cyclotron.addObserver(
                (o, arg) -> {
                    if (tracker.hasOrbited()){
                        System.out.println(
                                "Orbit:    " + tracker.getOrbitCount() + "\n"
                                + "Time :    " + cyclotron.t() + " s\n"
                                + "Position: " + proton.getPosition() + "\n\n"
                        );
                    }
                }
        );

        // Solve simulation for 10s (RK4 method with time step = 1e-3)
        cyclotron.solve(IVPSolver.rungeKutta4(1E-3), 10);

    }

}
