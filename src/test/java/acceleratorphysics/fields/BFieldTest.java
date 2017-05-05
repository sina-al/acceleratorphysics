package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.util.Vector3Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class BFieldTest {

    private static final double TOL = 1E-10;

    private static BField bField;
    private static Vector3 b;

    private Vector3 r;
    private double t;

    @Before
    public void setUp() throws Exception {
        b = Vector3Test.randomVector();
        bField = new BField() {
            @Override
            protected Vector3 B(Vector3 r, double t) {
                return b;
            }
        };

        r = Vector3Test.randomVector();
        t = b.norm();
    }

    @After
    public void tearDown() throws Exception {
        b = null;
        bField = null;
    }

    @Test
    public void B() throws Exception {
        Vector3Test.assertVectorsEqual(b, bField.B(r,t), TOL);
    }

    @Test
    public void E() throws Exception {
        Vector3Test.assertVectorsEqual(Vector3.ZERO, bField.E(r,t), TOL);
    }

}
