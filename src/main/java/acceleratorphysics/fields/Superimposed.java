package acceleratorphysics.fields;

import acceleratorphysics.util.Vector3;

import java.util.Arrays;


/**
 * Superimposed EMField
 */
public class Superimposed extends EMField {

    private final EMField[] fields;

    private Superimposed(int i){
        fields = new EMField[i];
    }

    /**
     * Superimposes EMField objects
     * @param fieldsIn
     */
    public Superimposed(EMField... fieldsIn){
        this(fieldsIn.length);
        if(fieldsIn.length <= 1) {
            throw new IllegalArgumentException(
                    "Use Superimposed to superimpose 2 or more EMField objects."
            );
        }
        for (int i = 0; i < fieldsIn.length; i++){
            fields[i] = fieldsIn[i];
        }
    }

    @Override
    public Vector3 E(Vector3 r, double t){
        return Arrays.stream(fields)
                .map(f -> f.E(r,t))
                .reduce(Vector3.ZERO, Vector3::add);
    }

    @Override
    public Vector3 B(Vector3 r, double t){
        return Arrays.stream(fields)
                .map(f -> f.B(r, t))
                .reduce(Vector3.ZERO, Vector3::add);
    }
}
