package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;
import acceleratorphysics.util.Vector3Test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SuperimposedTest {

    private static final double TOL = 1E-10;

    private static double t;
    private static Vector3 E1,E2,B1,B2,r;
    private static Superimposed superimposed;

    @Before
    public void setUp() throws Exception {
        r = Vector3Test.randomVector();
        t = r.norm();

        E1 = Vector3Test.randomVector();
        E2 = Vector3Test.randomVector();
        B1 = Vector3Test.randomVector();
        B2 = Vector3Test.randomVector();

        superimposed = new Superimposed(
                new EMField() {
                    @Override
                    protected Vector3 E(Vector3 r, double t) {
                        return E1;
                    }

                    @Override
                    protected Vector3 B(Vector3 r, double t) {
                        return B1;
                    }
                },
                new EMField() {
                    @Override
                    protected Vector3 E(Vector3 r, double t) {
                        return E2;
                    }

                    @Override
                    protected Vector3 B(Vector3 r, double t) {
                        return B2;
                    }
                }
        );
    }

    @After
    public void tearDown() throws Exception {
        E1 = null; E2 = null; B1 = null; B2 = null;
        superimposed = null;
    }

    // FIXME: This test depends on Vector3Test.add()

    @Test
    public void e() throws Exception {
        Vector3Test.assertVectorsEqual(E1.add(E2), superimposed.E(r,t), TOL);
    }

    @Test
    public void b() throws Exception {
        Vector3Test.assertVectorsEqual(B1.add(B2), superimposed.B(r,t), TOL);
    }

}