package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;

/**
 * Homogeneous electromagnetic field
 */
public class Uniform extends EMField {

    private final EMField field;

    public Uniform(Type type, Vector3 value){
        switch (type){
            case ELECTRIC:
                field = new EField() {
                    @Override
                    public Vector3 E(Vector3 r, double t) {
                        return value;
                    }
                };
                break;
            case MAGNETIC:
                field = new BField() {
                    @Override
                    public Vector3 B(Vector3 r, double t) {
                        return value;
                    }
                };
                break;
            default:
                throw new IllegalArgumentException("Incompatible field type");
        }
    }

    @Override
    public Vector3 E(Vector3 r, double t){
        return field.E(r, t);
    }

    @Override
    public Vector3 B(Vector3 r, double t){
        return field.B(r, t);
    }

}
