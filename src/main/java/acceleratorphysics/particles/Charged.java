package acceleratorphysics.particles;

import acceleratorphysics.fields.EMField;

/**
 * Defines contract for charged objects.
 */
interface Charged {

    /**
     * gets charge
     * @return charge
     */
    double getCharge();

    /**
     * gets EMField due to this charge
     * @return emfield due to this charge
     */
    EMField getField();

}
