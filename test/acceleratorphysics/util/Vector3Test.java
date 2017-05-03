package acceleratorphysics.util;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class Vector3Test {

    private static Random random = new Random();

    private final double TOL = 1e-10;

    private static Vector3 A;
    private static Vector3 B;

    private double Ax,Ay,Az,Bx,By,Bz;

    public static Vector3 randomVector(){
        return new Vector3(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public static void assertVectorsEqual(String msg, Vector3 exp, Vector3 act, double tol){
        assertEquals(msg + " x components unequal.", exp.getX(),act.getX(),tol);
        assertEquals(msg + " y components unequal.", exp.getY(),act.getY(),tol);
        assertEquals(msg + " z components unequal.", exp.getZ(),act.getZ(),tol);
    }

    public static void assertVectorsEqual(Vector3 exp, Vector3 act, double tol) {
        assertVectorsEqual("", exp, act, tol);
    }

    @Before
    public void setUp() throws Exception {
        Ax = random.nextDouble();
        Ay = random.nextDouble();
        Az = random.nextDouble();
        Bx = random.nextDouble();
        By = random.nextDouble();
        Bz = random.nextDouble();
        A = new Vector3(Ax,Ay,Az);
        B = new Vector3(Bx,By,Bz);
    }

    @After
    public void tearDown() throws  Exception {
        A = null;
        B = null;
    }

    @Test(expected=IllegalArgumentException.class)
    public void nonFiniteVector(){
        Vector3 v = new Vector3(0d,0d,1.0d/0);
    }

    @Test
    public void toArray() throws Exception {
        double[] array = A.toArray();
        assertEquals(Ax, array[0], TOL);
        assertEquals(Ay, array[1], TOL);
        assertEquals(Az, array[2], TOL);
    }

    @Test
    public void getX() throws Exception {
        assertEquals(Ax, A.getX(), TOL);
    }

    @Test
    public void getY() throws Exception {
        assertEquals(Ay, A.getY(), TOL);
    }

    @Test
    public void getZ() throws Exception {
        assertEquals(Az, A.getZ(), TOL);
    }

    @Test
    public void add() throws Exception {
        Vector3 C = A.add(B);
        Vector3 expected = new Vector3(Ax+Bx, Ay+By, Az+Bz);
        assertVectorsEqual(expected, C, TOL);
    }

    @Test
    public void subtract() throws Exception {
        Vector3 C = A.subtract(B);
        Vector3 expected = new Vector3(Ax-Bx, Ay-By, Az-Bz);
        assertVectorsEqual(expected, C, TOL);
    }

    @Test
    public void scale() throws Exception {
        double k = random.nextDouble();
        Vector3 actual = A.scale(k);
        Vector3 expected = new Vector3(Ax*k, Ay*k, Az*k);
        assertVectorsEqual(expected, actual, TOL);
    }

    @Test(expected=IllegalArgumentException.class)
    public void scaleInf() throws Exception {
        A.scale(1d/0.0);
    }

    @Test
    public void scale0() throws Exception {
        assertVectorsEqual(Vector3.ZERO, A.scale(0), TOL);
    }

    @Test
    public void scale1() throws Exception {
        assertVectorsEqual(A,  A.scale(1), TOL);
    }

    @Test
    public void dot() throws Exception {
        double actual = A.dot(B);
        double expected = Ax*Bx + Ay*By + Az*Bz;
        assertEquals(expected, actual, TOL);
    }

    @Test
    public void cross() throws Exception {
        Vector3 actual = A.cross(B);
        Vector3 expected =
                new Vector3(
                        (Ay*Bz) - (Az*By),
                        (Az*Bx) - (Ax*Bz),
                        (Ax*By) - (Ay*Bx)
                );
        assertVectorsEqual(expected, actual, TOL);
    }

    @Test
    public void norm() throws Exception {
        double actual = Math.sqrt((Ax*Ax) + (Ay*Ay) + (Az*Az));
        assertEquals(A.norm(), actual, TOL);
    }

    @Test
    public void unit() throws Exception {
        double norm = Math.sqrt((Ax*Ax) + (Ay*Ay) + (Az*Az));
        Vector3 expected = new Vector3(Ax/norm, Ay/norm, Az/norm);
        Vector3 actual = A.unit();
        assertVectorsEqual(expected, actual, TOL);
    }


}