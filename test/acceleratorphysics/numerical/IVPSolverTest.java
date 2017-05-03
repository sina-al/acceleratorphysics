package acceleratorphysics.numerical;

import acceleratorphysics.util.Vector3;
import org.junit.After;
import org.junit.Before;

import java.util.Random;


public class IVPSolverTest {

    private static final double TOL = 1E-10;
    private static Random random = new Random();

    /**
     * Example IVP : y'' = y' + y
     */
    private static IVP ivpStub;

    /**
     * Initial conditions y(0) = 1; y'(0) = 1
     */
    private static final Vector3[] init =
            new Vector3[] {Vector3.ONES, Vector3.ONES};

    /**
     * Time step for IVPSolver
     */
    private static double h;

    /**
     * Number of iterations to to take
     */
    private static int steps;

    /**
     *  y'' = y' + y
     * @param Y y[0] = y ; y[1] = y'
     * @param T time
     * @return y''
     */
    private static Vector3 F(Vector3[] Y, double T){
        return Y[0].add(Y[1]);
    }

    /**
     * Sets up a stubbed IVP corresponding to the 2nd order ODE y'' = f(y', y, t) = y' + y
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        steps = random.nextInt();
        h = random.nextDouble();

        ivpStub = new IVP(2) {

            Vector3[] y = new Vector3[]{init[0], init[1]};
            double t = 0;

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
                y[1] = y[1].add(dy[0]);
                t += h;
            }
        };
    }

    @After
    public void tearDown() throws Exception {
        ivpStub = null;
    }




}