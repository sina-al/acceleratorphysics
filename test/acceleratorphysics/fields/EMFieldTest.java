package acceleratorphysics.fields;

import acceleratorphysics.particles.ChargedParticle;
import acceleratorphysics.util.Vector3;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static acceleratorphysics.util.Vector3Test.assertVectorsEqual;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EMFieldTest {

    private static final double TOL = 1E-10;
    private static Random random = new Random();

    private EMField EMFieldStub;
    private Vector3 Bfield, Efield;

    private double t;
    private Vector3 r;


    private static Vector3 randomVector(){
        return new Vector3(random.nextDouble(),random.nextDouble(),random.nextDouble());
    }

    @Before
    public void setUp() throws Exception {

        r = randomVector();
        t = random.nextDouble();

        Efield = randomVector();
        Bfield = randomVector();

        EMFieldStub = new EMField() {
            @Override
            protected Vector3 E(Vector3 r, double t) {
                return Efield;
            }

            @Override
            protected Vector3 B(Vector3 r, double t) {
                return Bfield;
            }
        };
    }

    @After
    public void tearDown() throws Exception {
        EMFieldStub = null;
        Efield = null;
        Bfield = null;
    }

    @Test
    public void E() throws Exception {
        assertVectorsEqual("Electric Field:", Efield, EMFieldStub.E(r,t), TOL);
    }

    @Test
    public void B() throws Exception {
        assertVectorsEqual("Magnetic Field:", Bfield, EMFieldStub.B(r,t), TOL);
    }
    @Test
    public void lorentzForce() throws Exception {

        Vector3 V = randomVector();
        Vector3 R = randomVector();
        double q = random.nextDouble();
        double t = random.nextDouble();

        Vector3 expected = V.cross(Bfield);
        expected = expected.add(Efield);
        expected = expected.scale(q);

        Vector3 actual = EMFieldStub.lorentzForce(q,V,R,t);

        assertVectorsEqual("Lorentz force:", expected, actual, TOL);

    }
    
    @Test
    public void lorentzForceParticle() throws Exception {

        Vector3 position = randomVector();
        Vector3 velocity = randomVector();
        double charge = random.nextDouble();

        ChargedParticle mockParticle = mock(ChargedParticle.class);

        when(mockParticle.getPosition()).thenReturn(position);
        when(mockParticle.getVelocity()).thenReturn(velocity);
        when(mockParticle.getCharge()).thenReturn(charge);


        Vector3 actual = EMFieldStub.lorentzForce(mockParticle, t);

        Vector3 expected = velocity.cross(Bfield);
        expected = expected.add(Efield);
        expected = expected.scale(charge);

        assertVectorsEqual("Lorentz force:", expected, actual, TOL);

    }


}