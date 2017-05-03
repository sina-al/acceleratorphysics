package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.util.Vector3Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniformTest {

    private static final double TOL = 1E-10;

    private static Uniform E;
    private static Uniform B;
    private static Vector3 value;

    private double t;
    private Vector3 r;

    @Before
    public void setUp() throws Exception {
        value = Vector3Test.randomVector();
        E = new Uniform(Type.ELECTRIC, value);
        B = new Uniform(Type.MAGNETIC, value);
        r = Vector3Test.randomVector();
        t = r.norm();
    }

    @After
    public void tearDown() throws Exception {
        value = null;
        E = null;
        B = null;
        r = null;
    }

    @Test
    public void e1() throws Exception {
        Vector3Test.assertVectorsEqual(value, E.E(r,t), TOL);
    }

    @Test
    public void b1() throws Exception {
        Vector3Test.assertVectorsEqual(Vector3.ZERO, E.B(r,t), TOL);
    }

    @Test
    public void e2() throws Exception {
        Vector3Test.assertVectorsEqual(Vector3.ZERO, B.E(r,t), TOL);
    }

    @Test
    public void b2() throws Exception {
        Vector3Test.assertVectorsEqual(value, B.B(r,t), TOL);
    }


}