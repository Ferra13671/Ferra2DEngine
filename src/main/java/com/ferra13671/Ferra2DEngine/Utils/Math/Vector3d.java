package com.ferra13671.Ferra2DEngine.Utils.Math;

/**
 * @author Ferra13671
 */

public class Vector3d {
    public double x;
    public double y;
    public double z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double dot(Vector3d r) {
        return x * r.getX() + y * r.getY() + z * r.getZ();
    }

    public Vector3d normalized() {
        double length = length();

        return new Vector3d(x / length, y / length, z / length);
    }

    public double cross(Vector3d r) {
        return x * r.getY() - y * r.getX() - z * r.getZ();
    }

    public Vector3d lerp(Vector3d dest, float lerpFactor) {
        return dest.sub(this).mul(lerpFactor, lerpFactor, lerpFactor).add(this);
    }

    public Vector3d add(Vector3d r) {
        return new Vector3d(x + r.getX(), y + r.getY(), z + r.getZ());
    }

    public Vector3d add(double _x, double _y, double _z) {
        return new Vector3d(x + _x, y + _y, z + _z);
    }

    public Vector3d sub(Vector3d r) {
        return new Vector3d(x - r.getX(), y - r.getY(), z - r.getZ());
    }

    public Vector3d sub(double _x, double _y, double _z) {
        return new Vector3d(x - _x, y - _y, z - _z);
    }

    public Vector3d mul(Vector3d r) {
        return new Vector3d(x * r.getX(), y * r.getY(), z * r.getZ());
    }

    public Vector3d mul(double _x, double _y, double _z) {
        return new Vector3d(x * _x, y * _y, z * _z);
    }

    public Vector3d div(Vector3d r) {
        return new Vector3d(x / r.getX(), y / r.getY(), z / r.getZ());
    }

    public Vector3d div(double _x, double _y, double _z) {
        return new Vector3d(x / _x, y / _y, z / _z);
    }

    public Vector3d abs() {
        return new Vector3d(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public String toString() {
        return "(" + x + " " + y + " " + z + ")";
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean equals(Vector3d r) {
        return x == r.getX() && y == r.getY();
    }

    public Vector3d set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector3d set(Vector3d r) {
        this.x = r.x;
        this.y = r.y;
        return this;
    }
}
