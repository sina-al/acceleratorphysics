package acceleratorphysics.util;

/**
 * Represents immutable three dimensional euclidean vector
 */
public class Vector3 {

    private final double x;
    private final double y;
    private final double z;

    public static final Vector3 ZERO = new Vector3(0, 0, 0);
    public static final Vector3 ONES = new Vector3(1, 1, 1);
    public static final Vector3 I = new Vector3(1,0,0);
    public static final Vector3 J = new Vector3(0,1,0);
    public static final Vector3 K = new Vector3(0,0,1);

    private Vector3(){
        throw new AssertionError("Use acceleratorphysics.util.Vector3.ZERO");
    }

    /**
     * Constructs immutable 3d euclidean vector
     * @param x
     * @param y
     * @param z
     */
    public Vector3(double x, double y, double z){
        //if(Stream.of(x,y,z).anyMatch(e -> !Double.isFinite(e)))
        if(!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z))
            throw new IllegalArgumentException("Non-finite vector components.");
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * returns array of components
     * @return array of components
     */
    public double[] toArray(){
        return new double[]{x, y, z};
    }

    /**
     * x projection
     * @return x
     */
    public double getX(){
        return x;
    }

    /**
     * y projection
     * @return y
     */
    public double getY(){
        return y;
    }

    /**
     * z projection
     * @return z
     */
    public double getZ(){
        return z;
    }

    /**
     * this + v
     * @param v
     * @return this + v
     */
    public Vector3 add(Vector3 v){
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    /**
     * this - v
     * @param v
     * @return this - v
     */
    public Vector3 subtract(Vector3 v){
        return add(v.scale(-1D));
    }

    /**
     * this scaled by c
     * @param c
     * @return this scaled by c
     */
    public Vector3 scale(double c){
        return (c != 1 && c != 0)
                ? (new Vector3(x * c, y * c, z * c))
                : (c == 1) ? this : Vector3.ZERO;
    }

    /**
     * this dot v
     * @param v
     * @return this dot v
     */
    public double dot(Vector3 v){
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    /**
     * this cross v
     * @param v
     * @return this x v
     */
    public Vector3 cross(Vector3 v){
        return new Vector3(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z,
                this.x * v.y - this.y * v.x
        );
    }

    /**
     * gets norm
     * @return norm
     */
    public double norm(){
        return Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * this normalised
     * @return unit vector
     */
    public Vector3 unit(){
        return (this == Vector3.ZERO) ? this : this.scale(1D / norm());
    }

    public static double scalarTriple(Vector3 a, Vector3 b, Vector3 c){
        return a.dot(b.cross(c));
    }

    /**
     * Vector triple product
     * @param a
     * @param b
     * @param c
     * @return a x (b x c)
     */
    public static Vector3 vectorTriple(Vector3 a, Vector3 b, Vector3 c) {
        return a.cross(b.cross(c));
    }

    public String toString(){
        return x + "\t" + y + "\t" + z;
    }

    @Override // NB: NOT reliable. Only exists for the sake of unit testing.
    public boolean equals(Object obj) {
        Vector3 vector;
        try {
            vector = (Vector3)obj;
        } catch (ClassCastException ex) {
            return false;
        }
        return this.x == vector.x && this.y == vector.y && this.z == vector.z;
    }

}
