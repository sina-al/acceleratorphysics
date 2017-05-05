package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;

/**
 * Arbitrary electric field (intended for use via anonymous class)
 */
public abstract class EField extends EMField{
    @Override
    public Vector3 B(Vector3 r, double t){
        return Vector3.ZERO;
    }
}
