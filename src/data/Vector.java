package data;

import static java.lang.Math.*;

public class Vector {
    private double x;
    private double y;

    private Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector dimVector(double x, double y) {
        return new Vector(x, y);
    }

    public static Vector magVector(double magnitude, double angle) {
        return new Vector(magnitude * cos(toRadians(angle)), magnitude * sin(toRadians(angle)));
    }

    public Vector copy() {
        return new Vector(x, y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double magnitude() {
        return sqrt(pow(x, 2) + pow(y, 2));
    }

    public double angle() {
        return x / magnitude();
    }

    public Vector sum(Vector other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Vector sub(Vector other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Vector scale(double scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }
}
