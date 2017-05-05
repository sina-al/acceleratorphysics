package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;

import static acceleratorphysics.particles.Relativity.*;

/**
 * Enumeration of mechanics frameworks with corresponding implementations of MaterialPoints
 * (quantities such as mass, energy, momentum and acceleration due to force depend on mechanics framework)
 */
enum Framework {

    NEWTONIAN, RELATIVISTIC;

    MaterialPoint newMaterialPoint(State state, double mass){

        switch (this) {

            case NEWTONIAN:
                return new MaterialPoint(state) {

                    @Override
                    Vector3 getMomentum() {
                        return velocity.scale(mass);
                    }

                    @Override
                    Vector3 getAcceleration(Vector3 force) {
                        return force.scale(1.0/mass);
                    }

                    @Override
                    public double getMass() {
                        return mass;
                    }

                    @Override
                    public double getEnergy() {
                        return 0.5 * mass * velocity.dot(velocity);
                    }

                };

            case RELATIVISTIC:
                return new MaterialPoint(state) {

                    @Override
                    Vector3 getMomentum() {
                        return velocity.scale(gamma(velocity)*mass);
                    }

                    @Override
                    Vector3 getAcceleration(Vector3 force) {
                        return force.subtract(velocity.scale(force.dot(velocity) / (C * C)))
                                .scale(1.0 / (gamma(velocity) * mass));
                    }

                    @Override
                    public double getMass() {
                        return gamma(velocity) * mass;
                    }

                    @Override
                    public double getEnergy() {
                        return gamma(velocity) * mass * C * C;
                    }
                };

            default:
                throw new AssertionError(
                        "Implementation of MaterialPoint according to "
                        + this.name()
                        + " must be developed."
                );
        }
    }


    @Override
    public String toString(){
        String name = name();
        return name.substring(0,1) + name.substring(1).toLowerCase();
    }

}
