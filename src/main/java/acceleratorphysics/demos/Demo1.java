package acceleratorphysics.demos;

import acceleratorphysics.ParticleAccelerator;
import acceleratorphysics.fields.EMField;
import acceleratorphysics.fields.Type;
import acceleratorphysics.fields.Uniform;
import acceleratorphysics.numerical.IVP;
import acceleratorphysics.numerical.IVPSolver;
import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.particles.Particle;
import acceleratorphysics.particles.ParticleFactory;
import acceleratorphysics.particles.State;
import acceleratorphysics.util.Vector3;
import acceleratorphysics.util.VectorWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.Observer;


public class Demo1 {

    public static void main(String[] args) {

        ChargedParticle particle = ParticleFactory.Newtonian.proton(
                State.zero().velocity(Vector3.I.scale(0.1))
        );

        EMField magneticField = new Uniform(Type.MAGNETIC, Vector3.J.scale(1e-7));

        IVP protonInMagneticField = new ParticleAccelerator(particle, magneticField);

        try ( VectorWriter writer = new VectorWriter(
                    Files.newOutputStream(
                            Paths.get("proton-trajectory.dat")
                    )
              )
        ) {
            protonInMagneticField.addObserver(pathRecorder(particle, writer));
            protonInMagneticField.solve(IVPSolver.midpoint(), 100);

        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }

    }

    private static Observer pathRecorder(Particle particle, VectorWriter writer){
        return new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                try {
                    writer.writeVector(particle.getPosition());
                } catch (IOException ex) {
                    ex.printStackTrace(System.err);
                }
            }
        };
    }
}
