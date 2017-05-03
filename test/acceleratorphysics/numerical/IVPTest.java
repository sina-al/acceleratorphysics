package acceleratorphysics.numerical;

import acceleratorphysics.util.Vector3;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static acceleratorphysics.util.Vector3Test.assertVectorsEqual;
import static acceleratorphysics.util.Vector3Test.randomVector;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IVPTest {

    private static final double TOL = 1E-10;

    /**
     * Mock object for IVPSolver
     */
    private static IVPSolver mockSolver;

    /**
     * Time step for IVPSolver
     */
    private static final double h = 1;

    /**
     * Mock iterations for the IVPSolver
     */
    private static Vector3[] mockDY;

    /**
     * Example IVP : y'' = f(y',y,t) = y' + y
     */
    private static IVP ivpStub;

    /**
     * Order of the example IVP
     */
    private static final int order = 2;

    /**
     * Initial condition for IVP
     */
    private static final Vector3[] y0 = new Vector3[]{Vector3.ONES, Vector3.ONES};

    /**
     * Implementation of the second order ordinary differential equation
     * given by y'' = f(y',y,t) = y'+y
     * @param y derivatives of y (index denotes order)
     * @param t time
     * @return 2nd derivative y'' (prescribes form of 2nd order ODE)
     */
    private static Vector3 F(Vector3[] y, double t){
        return y[0].add(y[1]);
    }


    @Before
    public void setUp() throws Exception {

        // Stub an IVP
        ivpStub = new IVP(order) {

            Vector3[] y = new Vector3[]{y0[0], y0[1]}; // state
            double t = 0;                              // time

            @Override
            protected Vector3 f(Vector3[] y, double t) {
                return F(y,t);
            }

            @Override
            protected Vector3[] y() {
                return y;
            }

            @Override
            public double t() {
                return t;
            }

            @Override
            protected void increment(Vector3[] dy, double h) {
                y[0] = y[0].add(dy[0]);
                y[1] = y[1].add(dy[1]);
                t += h;
            }
        };

        // Mock an IVPSolver
        mockSolver = mock(IVPSolver.class);

         // Set mock increments
        mockDY = new Vector3[]{randomVector(), randomVector()};

        // Stub in euler method with step size h and mock increments
        when(mockSolver.stepSize()).thenReturn(h);
        when(mockSolver.dy(ivpStub)).thenReturn(mockDY);
    }

    @After
    public void tearDown() throws Exception {
        ivpStub = null;
        mockSolver = null;
    }

    /**
     * Tests order of ODE
     * @throws Exception
     */
    @Test
    public void getOrder() throws Exception {
        assertEquals(order, ivpStub.getOrder());
    }


    /**
     * Test f() after 0 iterations
     * @throws Exception
     */
    @Test
    public void f_0() throws Exception {

        Vector3 actual = ivpStub.f();
        Vector3 expected = F(y0, 0);

        assertVectorsEqual(actual, expected, TOL);
    }

    /**
     * Test for correct increment due to IVP.increment(Vector3[],double)
     * @throws Exception
     */
    @Test
    public void increment() throws Exception {

        Vector3 dy0 = randomVector();
        Vector3 dy1 = randomVector();

       ivpStub.increment(new Vector3[]{dy0, dy1},h);

       Vector3[] newY = new Vector3[]{
               y0[0].add(dy0), y0[1].add(dy1)
       };

        assertVectorsEqual(F(newY,0), ivpStub.f(), TOL);
        assertEquals(h, ivpStub.t(), TOL);
    }

    /**
     * Tests IVP interaction with IVPSolver by ensuring correct number of calls to IVPSolve.dy(IVP).
     * @throws Exception
     */
    @Test
    public void solve() throws Exception {

        int iterations = 10;
        double time = h * iterations;

        ivpStub.solve(mockSolver, time);

        // 'iteration' calls to mockSolver.dy(IVP) should have been made.
        verify(mockSolver, times(iterations)).dy(ivpStub);

    }

    /**
     * Tests IVP has been appropriately modified by IVPSolver
     * @throws Exception
     */
    @Test
    public void solve_state() throws Exception {

        int iterations = 15;
        double time = h * iterations;

        ivpStub.solve(mockSolver, time);


        assertEquals("Time not consistent", time, ivpStub.t(), TOL);

        // Expected state should be initial condition + iteration * mockDY
        Vector3 expectedY0 = y0[0].add(mockDY[0].scale(iterations));
        Vector3 expectedY1 = y0[1].add(mockDY[1].scale(iterations));

        // Actual state
        Vector3[] actualY = ivpStub.y();

        assertVectorsEqual(expectedY0, actualY[0], TOL);
        assertVectorsEqual(expectedY1, actualY[1], TOL);
    }


    /**
     * Tests if exception is thrown when implementation of f() is inconsistent
     * with implementation of y()
     * @throws Exception
     */
    @Test(expected= IVP.InconsistentImplementationException.class)
    public void bad_implementation1() throws Exception {

        ivpStub = new IVP(2) {
            @Override
            protected Vector3 f(Vector3[] y, double t) {
                return y[3];  // here y().length is 2 but f calls y[3]
            }

            @Override
            protected Vector3[] y() {
                return new Vector3[2];
            }

            @Override
            public double t() {
                return 0;
            }

            @Override
            protected void increment(Vector3[] dy, double h) {
                return;
            }
        };


        ivpStub.solve(mockSolver, 10*h);

    }


    /**
     * Tests if exception is thrown when declared order is inconsistent
     * with implementation of y()
     * @throws Exception
     */
    @Test(expected= IVP.InconsistentImplementationException.class)
    public void bad_implementation2() throws Exception {

        ivpStub = new IVP(3) {
            @Override
            protected Vector3 f(Vector3[] y, double t) {
                // y[n>2] is not called
                return y[1].add(y[2]);  // here y().length is consistent with f
            }

            @Override
            protected Vector3[] y() {
                return new Vector3[2]; // but not consistent with declared order 3
            }

            @Override
            public double t() {
                return 0;
            }

            @Override
            protected void increment(Vector3[] dy, double h) {
                return;
            }
        };

        ivpStub.solve(mockSolver,h*10);

    }
}