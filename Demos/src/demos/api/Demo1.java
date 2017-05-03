package demos.api;


import acceleratorphysics.*;
import acceleratorphysics.fields.EMField;
import acceleratorphysics.fields.Type;
import acceleratorphysics.fields.Uniform;
import acceleratorphysics.numerical.IVP;
import acceleratorphysics.numerical.IVPSolver;
import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.particles.State;
import acceleratorphysics.particles.ParticleFactory;
import acceleratorphysics.util.OrbitTracker;
import acceleratorphysics.util.Vector3;
import acceleratorphysics.util.VectorReader;
import acceleratorphysics.util.VectorWriter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;

/**
 *  Demonstration of recording data
 */
public class Demo1 {

    private static ParticleFactory factory = ParticleFactory.Newtonian;

    /**
     * This demo records the trajectory of a proton in a uniform B field for 10 second
     * in a binary file.
     * Every time the proton makes an orbit, the state is printed to standard out.
     * The trajectory is solved using midpoint method with default step size = 0.1.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {

        // Define initial state
        State initialState = State.zero()
                .velocity(Vector3.I.scale(0.1));

        // Construct particle with initial state (using factory)
        ChargedParticle particle = factory.proton(initialState);

        // Construct field
        EMField field = new Uniform(Type.MAGNETIC, Vector3.J.scale(1E-7));

        // Specify initial value problem
        IVP circular = new ParticleAccelerator(particle,field);

        // Record path of trajectory
        Path path = Paths.get("proton_trajectory.dat");
        OutputStream outputStream = Files.newOutputStream(path);
        VectorWriter writer = new VectorWriter(outputStream);
        Observer pathObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                try{
                    writer.writeVector(particle.getPosition());
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                }
            }
        };
        circular.addObserver(pathObserver);

        // Print particle state at each orbit
        OrbitTracker orbitTracker = new OrbitTracker(particle);
        circular.addObserver(
                (o, arg) -> {
                    if(orbitTracker.hasOrbited()) {
                        System.out.println("Orbit: " + orbitTracker.getOrbitCount());
                        System.out.println("Particle State: ");
                        System.out.println(particle + "\n");
                    }
                }
        );

        // Chose numerical method to solve problem
        double timeStep = 0.1;
        IVPSolver solver = IVPSolver.euler(timeStep);

        // Solve problem
        double simulationTime = 10;
        circular.solve(solver, simulationTime);

        // Read first second of solution
        InputStream inputStream = Files.newInputStream(path);
        VectorReader reader = new VectorReader(inputStream);
        System.out.println("Trajectory (first second): ");
        for(int i = 0; i < 1/timeStep; i++) {
            System.out.println(reader.readVector());
        }

        // Close resources
        reader.close();
        writer.close();
    }
}

