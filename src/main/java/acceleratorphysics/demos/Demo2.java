package acceleratorphysics.demos;

import acceleratorphysics.numerical.IVP;
import acceleratorphysics.numerical.IVPSolver;
import acceleratorphysics.particles.Newton2ndLaw;
import acceleratorphysics.particles.Particle;
import acceleratorphysics.particles.ParticleFactory;
import acceleratorphysics.util.Vector3;

import static acceleratorphysics.particles.ParticleFactory.Newtonian;
import static acceleratorphysics.particles.ParticleFactory.Relativistic;

public class Demo2 {

    public static void main(String[] args){

        runSimulation(Newtonian);
        runSimulation(Relativistic);

    }

    private static void runSimulation(ParticleFactory factory){

        // Construct massive particle
        double kilograms = 3;
        Particle particle = factory.massiveParticle(kilograms);

        // Print initial state
        System.out.println("--INITIAL STATE-- \n" + particle);

        // Construct IVP
        double newtons = 1E6;
        Vector3 force = Vector3.I.scale(newtons);

        IVP constantLinearForce = new Newton2ndLaw(particle) {
            @Override
            protected Vector3 acceleration(Vector3 r, Vector3 v, double t) {
                return particle.getAcceleration(force);
            }
        };

        // Solve IVP
        double time = 1000;
        constantLinearForce.solve(IVPSolver.euler(1e-1), time);

        // Print final state
        System.out.println("--FINAL STATE-- \n" + particle + "\n\n");
    }
}
