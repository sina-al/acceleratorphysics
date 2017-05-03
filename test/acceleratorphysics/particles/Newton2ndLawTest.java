package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static acceleratorphysics.util.Vector3Test.assertVectorsEqual;
import static acceleratorphysics.util.Vector3Test.randomVector;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Newton2ndLawTest {

    private static final double TOL = 1E-10;

    private static Particle mockParticle;
    private static Vector3 position, velocity, acceleration;


    private static Vector3 constAcceleration;
    private static Newton2ndLaw newtonStub;

    @Before
    public void setUp() throws Exception {

        // Set up mock Particle
        position = randomVector();
        velocity = randomVector();
        acceleration = randomVector();

        mockParticle = mock(Particle.class);
        when(mockParticle.getPosition()).thenReturn(position);
        when(mockParticle.getVelocity()).thenReturn(velocity);
        when(mockParticle.getAcceleration()).thenReturn(acceleration);


        // Set up Newton2ndLaw stub
        constAcceleration = randomVector();
        newtonStub = new Newton2ndLaw(mockParticle) {
            @Override
            protected Vector3 acceleration(Vector3 r, Vector3 v, double t) {
                return constAcceleration;
            }
        };

    }

    @After
    public void tearDown() throws Exception {
        mockParticle = null;
        newtonStub = null;
        position = null;
        velocity = null;
        acceleration = null;
        constAcceleration = null;
    }

    @Test
    public void f() throws Exception {
        Vector3 expected = constAcceleration;
        Vector3 actual = newtonStub.f(new Vector3[]{position, velocity}, 0);

        assertVectorsEqual("Current acceleration:", expected, actual, TOL);
    }

    @Test
    public void acceleration() throws Exception {
        Vector3 expected = constAcceleration;
        Vector3 actual = newtonStub.acceleration(randomVector(), randomVector(), 0);

        assertVectorsEqual("Acceleration due to state:", expected, actual, TOL);
    }

    @Test
    public void y_r() throws Exception {
        assertVectorsEqual("Position:", position, newtonStub.y()[0],TOL);
    }

    @Test
    public void y_v() throws Exception {
        assertVectorsEqual("Velocity:", velocity, newtonStub.y()[1],TOL);
    }

    @Test
    public void increment() throws Exception {

        Vector3 dr = randomVector();
        Vector3 dv = randomVector();
        Vector3 da = randomVector();

        double dt = da.norm();

        newtonStub.increment(new Vector3[]{dr, dv}, dt);

        verify(mockParticle).setPosition(position.add(dr));
        verify(mockParticle).setVelocity(velocity.add(dv));
        verify(mockParticle).setAcceleration(constAcceleration);

        assertEquals("Time:", dt, newtonStub.t(), TOL);
    }


    @Test
    public void getOrder() throws Exception {
        assertEquals("Order:", 2, newtonStub.getOrder());
    }


}