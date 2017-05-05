package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.util.Vector3Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class EFieldTest {

    private static final double TOL = 1E-10;

    private static EField eField;
    private static Vector3 e;

    private Vector3 r;
    private double t;

    @Before
    public void setUp() throws Exception {
        e = Vector3Test.randomVector();
        eField = new EField() {
            @Override
            protected Vector3 E(Vector3 r, double t) {
                return e;
            }
        };

        r = Vector3Test.randomVector();
        t = e.norm();
    }

    @After
    public void tearDown() throws Exception {
        e = null;
        eField = null;
    }

    @Test
    public void E() throws Exception {
        Vector3Test.assertVectorsEqual(e, eField.E(r,t), TOL);
    }

    @Test
    public void B() throws Exception {
        Vector3Test.assertVectorsEqual(Vector3.ZERO, eField.B(r,t), TOL);
    }

}