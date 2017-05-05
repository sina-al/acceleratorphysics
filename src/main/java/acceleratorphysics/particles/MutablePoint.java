package acceleratorphysics.particles;

import acceleratorphysics.util.Vector3;

abstract class MutablePoint implements Point {

    Vector3 position;
    Vector3 velocity;
    Vector3 acceleration;

    protected MutablePoint(State state){
        state.manifest(this);
    }

    @Override
    public Vector3 getPosition(){
        return position;
    }

    @Override
    public Vector3 getVelocity(){
        return velocity;
    }

    @Override
    public Vector3 getAcceleration(){
        return acceleration;
    }

    void setPosition(Vector3 position){
        this.position = position;
    }

    void setVelocity(Vector3 velocity){
        this.velocity = velocity;
    }

    void setAcceleration(Vector3 acceleration){
        this.acceleration = acceleration;
    }
}
