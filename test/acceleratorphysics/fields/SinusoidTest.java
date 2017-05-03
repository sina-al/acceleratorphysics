package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.util.Vector3Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SinusoidTest {

    private static final double TOL = 1E-10;
    private static final Random random = new Random();

    private static double amplitude;
    private static double frequency;
    private static double phase;

    private static Vector3 direction;
    private static Vector3 centre;

    private static double xbound;
    private static double ybound;
    private static double zbound;

    private static double t;


    private static Sinusoid sinusoidE, sinusoidB;

    private static Vector3 expected;

    @Before
    public void setUp() throws Exception {
        amplitude = random.nextDouble();
        frequency = random.nextDouble();
        phase = random.nextDouble();

        direction = Vector3Test.randomVector();
        centre = Vector3Test.randomVector();

        xbound = random.nextDouble();
        ybound = random.nextDouble();
        zbound = random.nextDouble();


        sinusoidE = new Sinusoid.Builder(Type.ELECTRIC)
                .setAmplitude(amplitude)
                .setFrequency(frequency)
                .setPhase(phase)
                .setDirection(direction)
                .setCavityCentre(centre)
                .setLX(xbound)
                .setLY(ybound)
                .setLZ(zbound)
                .build();


        sinusoidB = new Sinusoid.Builder(Type.MAGNETIC)
                .setAmplitude(amplitude)
                .setFrequency(frequency)
                .setPhase(phase)
                .setDirection(direction)
                .setCavityCentre(centre)
                .setLX(xbound)
                .setLY(ybound)
                .setLZ(zbound)
                .build();

        t = random.nextDouble();

        expected = direction.unit()
                .scale(amplitude * Math.sin(frequency*t + phase));

    }

    @After
    public void tearDown() throws Exception {
        sinusoidB = null;
        sinusoidE = null;
        direction = null;
        centre = null;
        expected = null;
    }

    @Test
    public void e1() throws Exception {


        Vector3 inBound = new Vector3(
                random.nextDouble()*xbound,
                random.nextDouble()*ybound,
                random.nextDouble()*zbound);

        //Chose r in bound
        Vector3 r = centre.add(inBound);

        Vector3 actual = sinusoidE.E(r, t);
        Vector3Test.assertVectorsEqual(expected, actual, TOL);
    }


    @Test
    public void e2x() throws Exception {

        // Choose r out of bound in x direction
        Vector3 r = centre.add(Vector3.I.scale((1+random.nextDouble())*xbound));
        Vector3 actual = sinusoidE.E(r, t);
        Vector3Test.assertVectorsEqual(Vector3.ZERO, actual, TOL);

    }

    @Test
    public void e2y() throws Exception {

        // Choose r out of bound in y direction
        Vector3 r = centre.add(Vector3.J.scale((1+random.nextDouble())*ybound));
        Vector3 actual = sinusoidE.E(r, t);
        Vector3Test.assertVectorsEqual(Vector3.ZERO, actual, TOL);

    }

    @Test
    public void e2z() throws Exception {

        // Choose r out of bound in z direction
        Vector3 r = centre.add(Vector3.K.scale((1+random.nextDouble())*zbound));
        Vector3 actual = sinusoidE.E(r, t);
        Vector3Test.assertVectorsEqual(Vector3.ZERO, actual, TOL);

    }

    @Test
    public void b0() throws Exception {
        Vector3 r = Vector3Test.randomVector();
        Vector3Test.assertVectorsEqual(Vector3.ZERO,sinusoidE.B(r,t),TOL);
    }

    @Test
    public void b1() throws Exception {

        Vector3 inBound = new Vector3(
                random.nextDouble()*xbound,
                random.nextDouble()*ybound,
                random.nextDouble()*zbound);

        //Chose r in bound
        Vector3 r = centre.add(inBound);

        Vector3 actual = sinusoidB.B(r, t);
        Vector3Test.assertVectorsEqual(expected, actual, TOL);

    }

    @Test
    public void b2x() throws Exception {

        // Choose r out of bound in x direction
        Vector3 r = centre.add(Vector3.I.scale((1+random.nextDouble())*xbound));
        Vector3 actual = sinusoidB.B(r, t);
        Vector3Test.assertVectorsEqual(Vector3.ZERO, actual, TOL);

    }

    @Test
    public void b2y() throws Exception {

        // Choose r out of bound in y direction
        Vector3 r = centre.add(Vector3.J.scale((1+random.nextDouble())*ybound));
        Vector3 actual = sinusoidB.B(r, t);
        Vector3Test.assertVectorsEqual(Vector3.ZERO, actual, TOL);

    }

    @Test
    public void b2z() throws Exception {

        // Choose r out of bound in z direction
        Vector3 r = centre.add(Vector3.K.scale((1+random.nextDouble())*zbound));
        Vector3 actual = sinusoidB.B(r, t);
        Vector3Test.assertVectorsEqual(Vector3.ZERO, actual, TOL);

    }

    @Test
    public void e0() throws Exception {
        Vector3 r = Vector3Test.randomVector();
        Vector3Test.assertVectorsEqual(Vector3.ZERO,sinusoidB.E(r,t),TOL);
    }

}