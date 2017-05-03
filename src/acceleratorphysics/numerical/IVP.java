package acceleratorphysics.numerical;

import acceleratorphysics.util.Vector3;

import java.util.Observable;

/**
 * Defines contract for initial value problem of 2nd order in euclidean space.
 * Concrete implementations must represent the problem in the form
 *
 *      y'' = f(y', y, t)
 *
 */
public abstract class IVP extends Observable {

    private final int order; // = 2. may be generalised in future development

    /**
     * Constructs initial value problem of order order
     * @param order order
     */
    protected IVP(int order){
        this.order = order;
    }

    /**
     * gets order of initial value problem
     * @return
     */
    public int getOrder(){
        return order;
    }

    /**
     * Concrete implementation of this method defines the form of the problem
     * such that
     * y'' = f(y', y, t)
     * @param y y[0] = y   y[1] = y'
     * @param t time
     * @return y''
     */
    protected abstract Vector3 f(Vector3[] y, double t);

    /**
     * Returns value of solution at current mesh point
     * @return y[1] = y'(now) , y[0] = y(now)
     */
    protected abstract Vector3[] y();

    /**
     * Returns time of current mesh point
     * @return time at now
     */
    public abstract double t();

    /**
     * Returns current mesh point plus h
     * @param h increment to current time
     * @return current time + h
     */
    protected double t(double h){
        return t() + h;
    }

    /**
     * Returns current value of y''
     * @return y''(now)
     */
    protected Vector3 f(){
        return f(y(),t());
    }

    /**
     * This method must be implemented such that if
     *
     *      y() returns y_(i) and t() returns t_(i)
     *
     * then
     *
     *      y() returns y_(i) + dy and t() returns t_(i) + h
     *
     * after this method is called.
     *
     * @param dy vector of increments Y_(i+1) - Y(i)
     * @param h step size
     */
    protected abstract void increment(Vector3[] dy, double h);

    // FIXME:
    // 1) not perfect. doesn't get all mistakes.
    // 2) choreograph some exception acrobatics to funnel cause
    // from either test1, test2 or (order == N);

    /**
     * Tests if this concrete implementation is implemented consistently
     * @return true if correctly implemented
     */
    private boolean implementedCorrectly(){
        final int N = y().length;
        try {
            f(); // test one
            Vector3[] zeros = new Vector3[N];
            for(int i = 0; i < zeros.length; i++){
                zeros[i] = Vector3.ZERO;
            }
            increment(zeros, 0D); // test two
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
        return order == N;
    }

    /**
     * Exception thrown when this interface is implemented improperly
     */
    public static class InconsistentImplementationException extends RuntimeException {

        int declaredOrder;
        int implementedOrder;

        InconsistentImplementationException(int declaredOrder, int implementedOrder){
            this.declaredOrder = declaredOrder;
            this.implementedOrder = implementedOrder;
        }

        // FIXME: make message.

    }


    /**
     * Iterates this IVP using specified numerical method for specified time
     * @param solver numerical method
     * @param time time
     * @return this IVP object
     */
    public final IVP solve(IVPSolver solver, double time){
        if(!implementedCorrectly()) {
            throw new InconsistentImplementationException(order, y().length);
        }
        double t0 = t();
        while (t()-t0 < time){
            increment(solver.dy(this), solver.stepSize()); // 'visit' happens here
            setChanged();
            notifyObservers();
        }
        return this;
    }
}




