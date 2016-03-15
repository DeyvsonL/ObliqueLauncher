package physics;

import data.Vector;
import static java.lang.Math.signum;

public class ObliqueLauncherPhysics {
    private double objectMass;
    private Vector currentPosition;
    private Vector currentVelocity;
    private Vector currentAcceleration;

    private double currentTime;
    private double timeInc;

    private double airResistance;
    private AirResistanceMode airResistanceMode;

    public enum AirResistanceMode {
        LINEAR, QUADRATIC
    }

    public ObliqueLauncherPhysics(double objectMass, Vector initialPosition, Vector launchVelocity,
                                  double gravity, double timeInc, double airResistance,
                                  AirResistanceMode airResistanceMode) {
        this.objectMass = objectMass;
        currentPosition = initialPosition.copy();
        currentVelocity = launchVelocity.copy();
        currentAcceleration = Vector.dimVector(0, gravity);
        currentTime = 0;
        this.timeInc = timeInc;
        this.airResistance = airResistance;
        this.airResistanceMode = airResistanceMode;
    }

    public Vector getCurrentPosition() {
        return currentPosition.copy();
    }

    public Vector getCurrentVelocity() {
        return currentVelocity.copy();
    }

    public Vector getCurrentAcceleration() {
        return currentAcceleration.copy();
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public void calculateNextStep() {
        currentTime += timeInc;

        currentAcceleration.set(
        		-currentVelocity.getX() * airResistance,
        		currentAcceleration.getY() - currentVelocity.getY() * airResistance
        );
        
        // Velocity is calculated in the end of the interval
        currentVelocity.set(
                currentVelocity.getX() + currentAcceleration.getX() * timeInc,
                currentVelocity.getY() + currentAcceleration.getY() * timeInc
        );

        currentPosition.set(
                currentPosition.getX() + currentVelocity.getX() * timeInc,
                currentPosition.getY() + currentVelocity.getY() * timeInc
        );
    }
}
