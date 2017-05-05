package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;


/**
 * Arbitrary magnetic field (intended for use via anonymous class)
 */
public abstract class BField extends EMField {
    @Override
    public Vector3 E(Vector3 r, double t){
        return Vector3.ZERO;
    }
}
