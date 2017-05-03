package acceleratorphysics.numerical;

import acceleratorphysics.util.Vector3;

// TODO: TEST ME
/**
 * Defines contract for a numerical method that solves initial value problems.
 * Also contains static factory of such numerical methods.
 */
public interface IVPSolver {

    double DEFAULT_STEPSIZE = 1E-3;

    /**
     * Name of numerical method
     */
    String name();

    /**
     * Returns step size (allows future development of variable step size methods)
     * @return step size
     */
    double stepSize();

    /**
     * Returns array of increments for Y to take Yi -> Yi+1
     * where Y[0] = y , y[1] = y'
     * @param ivp initial value problem
     * @return new Vector[]{ yi+1 - yi , y'i+1 - y'i }
     */
    Vector3[] dy(IVP ivp);

    /**
     * Returns euler method
     * @param h step size
     * @return euler method
     */
    static IVPSolver euler(double h){
        return new IVPSolver() {

            @Override
            public String name(){
                return "EULER";
            }

            @Override
            public double stepSize() {
                return h;
            }

            @Override
            public Vector3[] dy(IVP ivp) {

                Vector3[] dy = ivp.y();

                return new Vector3[]{dy[1].scale(h), ivp.f().scale(h)};
            }
        };
    }

    /**
     * Euler method
     * @return euler method with default step size
     */
    static IVPSolver euler(){
        return euler(DEFAULT_STEPSIZE);
    }

    /**
     * Euler cromer method
     * @param h step size
     * @return euler cromer method
     */
    static IVPSolver eulerCromer(double h) {
        return new IVPSolver() {
            @Override
            public String name() {
                return "EULERCROMER";
            }

            @Override
            public double stepSize() {
                return h;
            }

            @Override
            public Vector3[] dy(IVP ivp) {

                Vector3[] dy = new Vector3[2];

                dy[1] = ivp.f().scale(h);
                dy[0] = ivp.y()[1].add(dy[1]).scale(h);

                return dy;
            }
        };
    }

    /**
     * Euler cromer method
     * @return euler cromer method with default step size
     */
    static IVPSolver eulerCromer(){
        return euler(DEFAULT_STEPSIZE);
    }

    /**
     * Midpoint method
     * @param h step size
     * @return midpoint method
     */
    static IVPSolver midpoint(double h){
        return new IVPSolver() {
            @Override
            public String name() {
                return "MIDPOINT";
            }

            @Override
            public double stepSize() {
                return h;
            }

            @Override
            public Vector3[] dy(IVP ivp) {

                // Current iteration
                Vector3[] y = ivp.y();

                // Half iteration using euler method
                Vector3[] euler = euler(h/2).dy(ivp);
                y[0] = y[0].add(euler[0]);
                y[1] = y[1].add(euler[1]);

                // Mid-points
                Vector3[] dy = new Vector3[2];
                dy[1] = ivp.f(y,ivp.t(h/2)).scale(h);
                dy[0] = y[1].scale(h);

                return dy;
            }
        };
    }

    /**
     * Mid point method
     * @return mid point method with default step size
     */
    static IVPSolver midpoint(){
        return midpoint(DEFAULT_STEPSIZE);
    }

    /**
     * Velocity verlet method
     * @param h step size
     * @return velocity verlet method
     */
    static IVPSolver velocityVerlet(double h){
        return new IVPSolver() {
            @Override
            public String name() {
                return "VELOCITYVERLET";
            }

            @Override
            public double stepSize() {
                return h;
            }

            @Override
            public Vector3[] dy(IVP ivp) {

                Vector3[] y = ivp.y(); // current y, y'
                Vector3 f = ivp.f();  // current y''

                Vector3[] dy = new Vector3[2]; // increments

                dy[0] = y[1].add(f.scale(h/2)).scale(h);

                y[0] = y[0].add(dy[0]); // update y
                y[1] = y[1].add(f.scale(h));
                Vector3 future_f = ivp.f(y,ivp.t(h));

                dy[1] = f.scale(h/2).add(future_f.scale(h/2));

                return dy;
            }
        };
    }

    /**
     * 4th order Runge-Kutta method
     * @param h step size
     * @return 4th order Runge-Kutta method
     */
    static IVPSolver rungeKutta4(double h) {

        return new IVPSolver() {
            @Override
            public String name() {
                return "RK4";
            }

            @Override
            public double stepSize() {
                return h;
            }


            @Override
            public Vector3[] dy(IVP ivp){

                Vector3[] y0 = ivp.y();

                Vector3 k0 = y0[1].scale(h);
                Vector3 l0 = ivp.f().scale(h);

                Vector3[] dy0 = new Vector3[2];
                dy0[0] = y0[0].add(k0.scale(0.5));
                dy0[1] = y0[1].add(l0.scale(0.5));

                Vector3 k1 = y0[1].add(l0.scale(0.5)).scale(h);
                Vector3 l1 = ivp.f(dy0, ivp.t(0.5 * h)).scale(h);

                Vector3[] dy1 = new Vector3[2];
                dy1[0] = y0[0].add(k1.scale(0.5));
                dy1[1] = y0[1].add(l1.scale(0.5));

                Vector3 k2 = y0[1].add(l1.scale(0.5)).scale(h);
                Vector3 l2 = ivp.f(dy1, ivp.t(0.5 * h)).scale(h);

                Vector3[] dy2 = new Vector3[2];
                dy2[0] = y0[0].add(k2);
                dy2[1] = y0[1].add(l2);

                Vector3 k3 = y0[1].add(l2).scale(h);
                Vector3 l3 = ivp.f(dy2, ivp.t(h)).scale(h);


                Vector3[] dy = new Vector3[2];

                dy[0] = k0
                        .add(k1.scale(2D))
                        .add(k2.scale(2D))
                        .add(k3)
                        .scale(1D/6);

                dy[1] = l0
                        .add(l1.scale(2D))
                        .add(l2.scale(2D))
                        .add(l3)
                        .scale(1D/6);

                return dy;
            }
        };
    }

    /**
     * 4th order Runge-Kutta method
     * @return 4th order Runge-Kutta method with default step size
     */
    static IVPSolver rungeKutta4(){
        return rungeKutta4(DEFAULT_STEPSIZE);
    }

}

