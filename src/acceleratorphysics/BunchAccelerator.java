package acceleratorphysics;

import acceleratorphysics.fields.EMField;
import acceleratorphysics.fields.Superimposed;
import acceleratorphysics.numerical.IVPSolver;
import acceleratorphysics.particles.ChargedBunch;
import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.particles.Particle;

import java.util.Observable;


// Proposed solution 1
/**
 * Temporary workaround for accelerating a bunch of particles.
 * Future development will involve a refactor IVP/IVPSolver to 'IVPSystem' such that
 * it defines a contract for a SYSTEM of equations rather than a single initial value problem.
 * (This should be approached by adding a dimension to the appropriate arrays)
 * This will pave the way for implementation of more general problem solvers such as that of
 * a system of interacting particles (as is done here).
 */
public class BunchAccelerator extends Observable {

    private EMField field;
    private ParticleAccelerator[] accelerators;

    public BunchAccelerator(ChargedBunch bunch, EMField extField){

        field = new Superimposed(bunch.getField(), extField);

        Particle[] particles = bunch.getParticles();
        accelerators = new ParticleAccelerator[particles.length];

        for (int i = 0; i < particles.length;  i++){
            accelerators[i] = new ParticleAccelerator((ChargedParticle)particles[i], field);
        }

    }

    public void solve(IVPSolver solver, double time){

        double h = solver.stepSize();

        while (time > 0) {

            for (ParticleAccelerator accelerator : accelerators) {
                accelerator.solve(solver, h);
            }

            time -= h;
            hasChanged();
            notifyObservers();
        }
    }



}
