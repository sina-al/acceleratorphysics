package acceleratorphysics.particles;

import acceleratorphysics.fields.EMField;
import acceleratorphysics.fields.Superimposed;
import acceleratorphysics.util.Vector3;


// TODO: TEST ME
public final class ChargedBunch extends Bunch<ChargedParticle> implements Charged {

    private final EMField coulombField;

    public ChargedBunch(State state, ChargedParticle... particlesIn){
        super(state, particlesIn);
        coulombField = new Superimposed(
                particles.stream()
                        .map(ChargedParticle::getField)
                        .toArray(EMField[]::new)
        );
    }

    @Override
    public EMField getField(){
        return coulombField;
    }

    @Override
    public double getCharge(){
        return particles.stream()
                .mapToDouble(ChargedParticle::getCharge)
                .sum();
    }

    public double potentialEnergy(){
        double k = 0; // FIXME:  find this constant
        double EPE = 0;
        for (int i = 0; i < particles.size(); i++){
            ChargedParticle particle_i = particles.get(i);
            double Qi = particle_i.getCharge();
            Vector3 Ri = particle_i.getPosition();
            for (int j = 0; j < particles.size(); j++) {
                if (i == j) {
                    continue;
                }
                ChargedParticle particle_j = particles.get(j);
                double Qj = particle_j.getCharge();
                Vector3 Rj = particle_j.getPosition();

                EPE += (Qi * Qj) / Ri.subtract(Rj).norm();
            }
        }
        return EPE * k;
    }
}
