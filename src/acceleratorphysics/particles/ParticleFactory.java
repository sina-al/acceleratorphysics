package acceleratorphysics.particles;

import java.util.stream.DoubleStream;

/**
 * Abstract factory for Particle objects
 */
public abstract class ParticleFactory {

    /**
     * Newtonian Particle Factory
     */
    public static final ParticleFactory Newtonian = new ParticleFactory() {
        @Override
        Framework framework() {
            return Framework.NEWTONIAN;
        }
    };

    /**
     * Relativistic Particle Factory
     */
    public static final ParticleFactory Relativistic = new ParticleFactory() {
        @Override
        Framework framework() {
            return Framework.RELATIVISTIC;
        }
    };

    public static final double PROTON_MASS = 1.672621898E-27D;
    public static final double PROTON_CHARGE = 1.1602176620E-19;

    public static final double ELECTRON_MASS = 9.10938356E-31;
    public static final double ELECTRON_CHARGE = -1.60217662E-19;

    /**
     * Returns framework corresponding to this abstract factory
     * @return framework
     */
    abstract Framework framework();

    /**
     * Constructs new massive particle with initial state and mass
     * @param state initial state
     * @param mass mass
     * @return new particle
     */
    public Particle massiveParticle(State state, double mass){
        return new Particle(framework(), state, mass);
    }

    /**
     * Constructs new massive particle with mass at origin and zero velocity / acceleration
     * @param mass mass
     * @return new particle
     */
    public Particle massiveParticle(double mass) {
        return massiveParticle(State.zero(), mass);
    }

    /**
     * Constructs copy of particle
     * @param particle particle to be copied
     * @return copy of particle
     */
    public Particle massiveParticle(Particle particle){
        return massiveParticle(State.of(particle), particle.getMass());
    }

    /**
     * Constructs new charged particle with mass and charge and initial state
     * @param state initial state
     * @param mass mass
     * @param charge charge
     * @return new charged particle
     */
    public ChargedParticle chargedParticle(State state, double mass, double charge){
        return new ChargedParticle(framework(), state, mass, charge);
    }

    /**
     * Constructs new charged particle at origin with zero velocity/acceleration
     * @param mass mass
     * @param charge charge
     * @return new charged particle
     */
    public ChargedParticle chargedParticle(double mass, double charge){
        return chargedParticle(State.zero(), mass, charge);
    }

    /**
     * Contructs copy of a charged particle
     * @param chargedParticle charged particle to be copied
     * @return copy of charged particle
     */
    public ChargedParticle chargedParticle(ChargedParticle chargedParticle){
        return chargedParticle(State.of(chargedParticle),
                chargedParticle.getMass(), chargedParticle.getCharge());
    }

    /**
     * Constructs new proton
     * @param state initial state
     * @return new proton
     */
    public ChargedParticle proton(State state) {
        return chargedParticle(state, PROTON_MASS, PROTON_CHARGE);
    }

    /**
     * Constructs new proton at origin with zero velocity/acceleration
     * @return new proton
     */
    public ChargedParticle proton(){
        return proton(State.zero());
    }

    /**
     * Constructs new electron
     * @param state initial state
     * @return new electron
     */
    public ChargedParticle electron(State state){
        return chargedParticle(state, ELECTRON_MASS, ELECTRON_CHARGE);
    }

    /**
     * Constructs new electron in default initial state
     * @return new electron
     */
    public Particle electron(){
        return electron(State.zero());
    }

    // Bunches

    /**
     * Constructs a bunch of massive particles
     * @param state initial state
     * @param masses array of masses of each particle
     * @return new bunch
     */
    public Bunch<Particle> massiveBunch(State state, double[] masses){
        return new Bunch<Particle>(state,
                (Particle[])DoubleStream.of(masses)
                .mapToObj(m -> massiveParticle(state, m))
                .toArray()
        );
    }

    /**
     * Constructs a bunch of massive charged particles
     * @param state initial state
     * @param mass array of masses of each particle
     * @param charge array of charged of each particle
     * @return new charged bunch
     */
    public ChargedBunch chargedBunch(State state, double[] mass, double[] charge){
        if(mass.length != charge.length){
            throw new IllegalArgumentException("Masses and Charges are unequal in number");
        }
        int n = mass.length;
        ChargedParticle[] particles = new ChargedParticle[n];
        for (int i = 0; i < n; i++){
            particles[i] = chargedParticle(state, mass[i], charge[i]);
        }
        return new ChargedBunch(state, particles);
    }

    /**
     * Construct bunch of N protons
     * @param state initial state
     * @param N number of protons
     * @return bunch of protons
     */
    public ChargedBunch protonBunch(State state, int N){
        ChargedParticle[] protons = new ChargedParticle[N];
        for (int i = 0; i < N; i++){
            protons[i] = proton(state);
        }
        return new ChargedBunch(state, protons);
    }

    /**
     * Constructs bunch of N electrons
     * @param state initial state
     * @param N number of electrons
     * @return bunch of electrons
     */
    public ChargedBunch electronBunch(State state, int N){
        ChargedParticle[] electrons = new ChargedParticle[N];
        for (int i = 0; i < N; i++){
            electrons[i] = electron(state);
        }
        return new ChargedBunch(state, electrons);
    }

}


