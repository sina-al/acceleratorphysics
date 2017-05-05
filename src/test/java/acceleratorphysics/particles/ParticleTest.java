package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static acceleratorphysics.util.Vector3Test.assertVectorsEqual;
import static acceleratorphysics.util.Vector3Test.randomVector;
import static org.junit.Assert.assertEquals;


/**
 * Not exactly a unit test for Particle, but rather a test of integration between:
 * - Particle
 * - RelativisticMaterialPoint
 * - NewtonianMaterialPoint
 * - MutablePoint
 * - Point
 * - State
 */
public class ParticleTest { // FIXME: lazy (not UNIT test)

    private static final double C = 2.99792458E8;
    private static final double TOL = 1E-10;

    private static Random random = new Random();

    private static double restMass;
    private static Particle newtonian;
    private static Particle relativistic;

    private static Vector3 r, v, a;

    @Before
    public void setUp() throws Exception {

        r = randomVector();
        v = randomVector();
        a = randomVector();

        State state = State.zero()
                .position(r)
                .velocity(v)
                .acceleration(a);

        restMass = random.nextDouble();
        newtonian = new Particle(Framework.NEWTONIAN, state, restMass);
        relativistic = new Particle(Framework.RELATIVISTIC, state, restMass);

    }

    @Test
    public void getMassN() throws Exception {

        assertEquals("Newtonian Mass:", restMass, newtonian.getMass(), TOL);
    }

    @Test
    public void getMassR() throws Exception {

        double expected = restMass / Math.sqrt(1.0 - (v.dot(v)/ (C * C)));
        double actual = newtonian.getMass();

        assertEquals("Relativistic Mass:", expected, actual, TOL);
    }

    @Test
    public void getEnergyN() throws Exception {

        double expected = 0.5 * restMass * v.dot(v);
        double actual = newtonian.getEnergy();

        assertEquals("Newtonian Energy:", expected, actual, TOL);
    }

    @Test
    public void getEnergyR() throws  Exception {

        double expected = restMass/Math.sqrt(1.0 - (v.dot(v)/(C*C))) * C * C;
        double actual = relativistic.getEnergy();

        assertEquals("Relativistic Energy:", expected, actual, TOL);

    }

    @Test
    public void getAccelerationN() throws Exception {

        Vector3 force = randomVector();

        Vector3 actual = newtonian.getAcceleration(force);
        Vector3 expected = force.scale(1.0/restMass);

        assertVectorsEqual("Newtonian Acceleration:", expected, actual, TOL);
    }

    @Test
    public void getAccelerationR() throws Exception {

        Vector3 force = randomVector();

        Vector3 actual = relativistic.getAcceleration(force);

        Vector3 expected = force;
        expected = expected.subtract(v.scale(force.dot(v)/(C*C)));
        expected = expected.scale(Math.sqrt(1.0 - (v.dot(v)/(C*C)))/restMass);

        assertVectorsEqual("Relativistic Acceleration:",
                expected, actual, TOL);

    }

    @Test
    public void getMomentumN() throws Exception {

        assertVectorsEqual("Newtonian Momentum:",
                v.scale(restMass), newtonian.getMomentum(), TOL);

    }

    @Test
    public void getMomentum() throws Exception {

        Vector3 actual = relativistic.getMomentum();
        Vector3 expected = v.scale(restMass/Math.sqrt(1.0 - (v.dot(v)/(C*C))));

        assertVectorsEqual("Relativistic momentum:", expected, actual, TOL);
    }

}