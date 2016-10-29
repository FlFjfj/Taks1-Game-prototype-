package com.fjfj.testvr.utility;

public class Vector3 {

     /** the x-component of this vector **/
    public float x;
    /** the y-component of this vector **/
    public float y;
    /** the z-component of this vector **/
    public float z;

    public final static Vector3 X = new Vector3(1, 0, 0);
    public final static Vector3 Y = new Vector3(0, 1, 0);
    public final static Vector3 Z = new Vector3(0, 0, 1);
    public final static Vector3 Zero = new Vector3(0, 0, 0);

    public Vector3 () {}

    /** Creates a vector with the given components
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component */
    public Vector3 (float x, float y, float z) {
        this.set(x, y, z);
    }

    /** Creates a vector from the given vector
     * @param vector The vector */
    public Vector3(final Vector3 vector) {
        this.set(vector);
    }

    /** Creates a vector from the given array. The array must have at least 3 elements.
     *
     * @param values The array */
    public Vector3 (final float[] values) {
        this.set(values[0], values[1], values[2]);
    }

    /** Creates a vector from the given array. The array must have at least 3 elements.
     *
     * @param values The array */
    public Vector3 set (Vector3 values) {
        return this.set(values.x, values.y, values.z);
    }

    /** Sets the vector to the given components
     *
     * @param x The x-component
     * @param y The y-component
     * @param z The z-component
     * @return this vector for chaining */
    public Vector3 set (float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /** Sets the components from the array. The array must have at least 3 elements
     *
     * @param values The array
     * @return this vector for chaining */
    public Vector3 set (final float[] values) {
        return this.set(values[0], values[1], values[2]);
    }

    public Vector3 cpy () {
        return new Vector3(this);
    }

    public Vector3 add (final Vector3 vector) {
        return this.add(vector.x, vector.y, vector.z);
    }

    /** Adds the given vector to this component
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @param z The z-component of the other vector
     * @return This vector for chaining. */
    public Vector3 add (float x, float y, float z) {
        return this.set(this.x + x, this.y + y, this.z + z);
    }

    /** Adds the given value to all three components of the vector.
     *
     * @param values The value
     * @return This vector for chaining */
    public Vector3 add (float values) {
        return this.set(this.x + values, this.y + values, this.z + values);
    }

    public Vector3 sub (final Vector3 a_vec) {
        return this.sub(a_vec.x, a_vec.y, a_vec.z);
    }

    /** Subtracts the other vector from this vector.
     *
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @param z The z-component of the other vector
     * @return This vector for chaining */
    public Vector3 sub (float x, float y, float z) {
        return this.set(this.x - x, this.y - y, this.z - z);
    }

    /** Subtracts the given value from all components of this vector
     *
     * @param value The value
     * @return This vector for chaining */
    public Vector3 sub (float value) {
        return this.set(this.x - value, this.y - value, this.z - value);
    }

    public Vector3 scl (float scalar) {
        return this.set(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public Vector3 scl (final Vector3 other) {
        return this.set(x * other.x, y * other.y, z * other.z);
    }

    /** Scales this vector by the given values
     * @param vx X value
     * @param vy Y value
     * @param vz Z value
     * @return This vector for chaining */
    public Vector3 scl (float vx, float vy, float vz) {
        return this.set(this.x * vx, this.y * vy, this.z * vz);
    }

    public Vector3 mulAdd (Vector3 vec, float scalar) {
        this.x += vec.x * scalar;
        this.y += vec.y * scalar;
        this.z += vec.z * scalar;
        return this;
    }


    public Vector3 mulAdd (Vector3 vec, Vector3 mulVec) {
        this.x += vec.x * mulVec.x;
        this.y += vec.y * mulVec.y;
        this.z += vec.z * mulVec.z;
        return this;
    }

    /** @return The euclidean length */
    public static float len (final float x, final float y, final float z) {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    public float len () {
        return (float)Math.sqrt(x * x + y * y + z * z);
    }

    /** @return The squared euclidean length */
    public static float len2 (final float x, final float y, final float z) {
        return x * x + y * y + z * z;
    }

    public float len2 () {
        return x * x + y * y + z * z;
    }

    /** @param vector The other vector
     * @return Whether this and the other vector are equal */
    public boolean idt (final Vector3 vector) {
        return x == vector.x && y == vector.y && z == vector.z;
    }

    /** @return The euclidean distance between the two specified vectors */
    public static float dst (final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        final float a = x2 - x1;
        final float b = y2 - y1;
        final float c = z2 - z1;
        return (float)Math.sqrt(a * a + b * b + c * c);
    }

    public float dst (final Vector3 vector) {
        final float a = vector.x - x;
        final float b = vector.y - y;
        final float c = vector.z - z;
        return (float)Math.sqrt(a * a + b * b + c * c);
    }

    /** @return the distance between this point and the given point */
    public float dst (float x, float y, float z) {
        final float a = x - this.x;
        final float b = y - this.y;
        final float c = z - this.z;
        return (float)Math.sqrt(a * a + b * b + c * c);
    }

    /** @return the squared distance between the given points */
    public static float dst2 (final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
        final float a = x2 - x1;
        final float b = y2 - y1;
        final float c = z2 - z1;
        return a * a + b * b + c * c;
    }

    public float dst2 (Vector3 point) {
        final float a = point.x - x;
        final float b = point.y - y;
        final float c = point.z - z;
        return a * a + b * b + c * c;
    }

    /** Returns the squared distance between this point and the given point
     * @param x The x-component of the other point
     * @param y The y-component of the other point
     * @param z The z-component of the other point
     * @return The squared distance */
    public float dst2 (float x, float y, float z) {
        final float a = x - this.x;
        final float b = y - this.y;
        final float c = z - this.z;
        return a * a + b * b + c * c;
    }

    public Vector3 nor () {
        final float len2 = this.len2();
        if (len2 == 0f || len2 == 1f) return this;
        return this.scl(1f / (float)Math.sqrt(len2));
    }

    /** @return The dot product between the two vectors */
    public static float dot (float x1, float y1, float z1, float x2, float y2, float z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public float dot (final Vector3 vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }

    /** Returns the dot product between this and the given vector.
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @param z The z-component of the other vector
     * @return The dot product */
    public float dot (float x, float y, float z) {
        return this.x * x + this.y * y + this.z * z;
    }

    /** Sets this vector to the cross product between it and the other vector.
     * @param vector The other vector
     * @return This vector for chaining */
    public Vector3 crs (final Vector3 vector) {
        return this.set(y * vector.z - z * vector.y, z * vector.x - x * vector.z, x * vector.y - y * vector.x);
    }

    /** Sets this vector to the cross product between it and the other vector.
     * @param x The x-component of the other vector
     * @param y The y-component of the other vector
     * @param z The z-component of the other vector
     * @return This vector for chaining */
    public Vector3 crs (float x, float y, float z) {
        return this.set(this.y * z - this.z * y, this.z * x - this.x * z, this.x * y - this.y * x);
    }

    public boolean isUnit () {
        return isUnit(0.000000001f);
    }

    public boolean isUnit (final float margin) {
        return Math.abs(len2() - 1f) < margin;
    }
    public boolean isZero () {

        return x == 0 && y == 0 && z == 0;
    }

    public boolean isZero (final float margin) {
        return len2() < margin;
    }

    public boolean isOnLine (Vector3 other, float epsilon) {
        return len2(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x) <= epsilon;
    }

    public boolean isCollinear (Vector3 other, float epsilon) {
        return isOnLine(other, epsilon) && hasSameDirection(other);
    }

    public boolean hasSameDirection (Vector3 vector) {
        return dot(vector) > 0;
    }

    public boolean hasOppositeDirection (Vector3 vector) {
        return dot(vector) < 0;
    }

    public Vector3 lerp (final Vector3 target, float alpha) {
        x += alpha * (target.x - x);
        y += alpha * (target.y - y);
        z += alpha * (target.z - z);
        return this;
    }

    /** Spherically interpolates between this vector and the target vector by alpha which is in the range [0,1]. The result is
     * stored in this vector.
     *
     * @param target The target vector
     * @param alpha The interpolation coefficient
     * @return This vector for chaining. */
    public Vector3 slerp (final Vector3 target, float alpha) {
        final float dot = dot(target);
        // If the inputs are too close for comfort, simply linearly interpolate.
        if (dot > 0.9995 || dot < -0.9995) return lerp(target, alpha);

        // theta0 = angle between input vectors
        final float theta0 = (float)Math.acos(dot);
        // theta = angle between this vector and result
        final float theta = theta0 * alpha;

        final float st = (float)Math.sin(theta);
        final float tx = target.x - x * dot;
        final float ty = target.y - y * dot;
        final float tz = target.z - z * dot;
        final float l2 = tx * tx + ty * ty + tz * tz;
        final float dl = st * ((l2 < 0.0001f) ? 1f : 1f / (float)Math.sqrt(l2));

        return scl((float)Math.cos(theta)).add(tx * dl, ty * dl, tz * dl).nor();
    }

    public Vector3 limit (float limit) {
        return limit2(limit * limit);
    }

    public Vector3 limit2 (float limit2) {
        float len2 = len2();
        if (len2 > limit2) {
            scl((float)Math.sqrt(limit2 / len2));
        }
        return this;
    }

    public Vector3 setLength (float len) {
        return setLength2(len * len);
    }

    public Vector3 setLength2 (float len2) {
        float oldLen2 = len2();
        return (oldLen2 == 0 || oldLen2 == len2) ? this : scl((float)Math.sqrt(len2 / oldLen2));
    }

    public Vector3 clamp (float min, float max) {
        final float len2 = len2();
        if (len2 == 0f) return this;
        float max2 = max * max;
        if (len2 > max2) return scl((float)Math.sqrt(max2 / len2));
        float min2 = min * min;
        if (len2 < min2) return scl((float)Math.sqrt(min2 / len2));
        return this;
    }
    public boolean epsilonEquals (final Vector3 other, float epsilon) {
        if (other == null) return false;
        if (Math.abs(other.x - x) > epsilon) return false;
        if (Math.abs(other.y - y) > epsilon) return false;
        if (Math.abs(other.z - z) > epsilon) return false;
        return true;
    }

    /** Compares this vector with the other vector, using the supplied epsilon for fuzzy equality testing.
     * @return whether the vectors are the same. */
    public boolean epsilonEquals (float x, float y, float z, float epsilon) {
        if (Math.abs(x - this.x) > epsilon) return false;
        if (Math.abs(y - this.y) > epsilon) return false;
        if (Math.abs(z - this.z) > epsilon) return false;
        return true;
    }

    public Vector3 setZero () {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
    }
}