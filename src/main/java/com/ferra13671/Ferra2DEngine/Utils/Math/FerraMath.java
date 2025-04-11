package com.ferra13671.Ferra2DEngine.Utils.Math;

import com.ferra13671.Ferra2DEngine.Utils.Annotations.Experimental;

import java.util.ArrayList;
import java.util.Random;

/**
 * A utility for performing various mathematical calculations with almost minimal inaccuracy.
 * Unlike the Math class in java, all methods return float numbers instead of double.
 *
 * @author Ferra13671
 */

public class FerraMath {

    public static final float E = 2.7182818284590452354f;

    public static final float PI = 3.14159265358979323846f;
    public static final float Half_PI = PI * 0.5f;
    public static final float PI2 = PI * 2.0f;
    public static final float PI_INV = 1.0f / PI;

    public static final float DEGREES_TO_RADIANS = 0.017453292519943295f;

    public static final float RADIANS_TO_DEGREES = 57.29577951308232f;

    public static final float INFINITY = Float.POSITIVE_INFINITY;

    private static final Random random = new Random();


    /**
     * Convects angles to radians.
     *
     * @param deg   The degrees to be converted.
     * @return   Radians.
     */
    public static float degreesToRadians(float deg) {
        return deg * DEGREES_TO_RADIANS;
    }


    /**
     * Converts radians to degrees
     *
     * @param rad   The radians to be converted.
     * @return   Degrees.
     */
    public static float radiansToDegrees(float rad) {
        return rad * RADIANS_TO_DEGREES;
    }



    /**
     * Calculates the sine of the entered radians.
     *
     * @param rad   Radians for which the sine must be found.
     * @return   Sine of radians.
     */
    public static float sin_rad(float rad) {
        double i  = Math.rint(rad * PI_INV);
        float x  = rad - (float)(i * PI);
        float qs = 1-2*((int)i & 1);
        float x2 = x*x;
        double r;
        x = qs*x;
        r = -0.0000000000007371175496389783;
        r = r*x2 + 0.000000000160478927890934;
        r = r*x2 + -0.0000000250518726968975;
        r = r*x2 + 0.0000027557316456169086;
        r = r*x2 + -0.00019841269823583015;
        r = r*x2 + 0.008333333333277843;
        r = r*x2 + -0.1666666666666601;
        return (float)(x + x*x2*r);
    }


    /**
     * Calculates the sine of the entered degrees.
     *
     * @param deg   Degrees for which the sine must be found.
     * @return   Sine of degrees.
     */
    public static float sin_deg(float deg) {
        return sin_rad(degreesToRadians(deg));
    }


    /**
     * Calculates the cosine of the entered radians.
     *
     * @param rad   Radians for which the cosine must be found.
     * @return   Cosine of radians.
     */
    public static float cos_rad(float rad) {
        return sin_rad(rad + Half_PI);
    }


    /**
     * Calculates the cosine of the entered degrees.
     *
     * @param deg   Degrees for which the cosine must be found.
     * @return   Cosine of degrees.
     */
    public static float cos_deg(float deg) {
        return cos_rad(degreesToRadians(deg));
    }


    /**
     * Converts rectangular coordinates (x, y) to polar coordinates (r, theta) and returns theta.
     * <p>
     * <a href="https://math.stackexchange.com/questions/1098487/atan2-faster-approximation/1105038#answer-1105038">Original code</a>
     *
     * @param y   x coordinate.
     * @param x   y coordinate
     * @return Theta from polar coordinates (r, theta).
     */
    public static float atan2(float y, float x) {
        float ax = x >= 0.0f ? x : -x,
                ay = y >= 0.0f ? y : -y;
        float a = min(ax, ay) / max(ax, ay);
        float s = a * a;
        double r = ((-0.0464964749 * s + 0.15931422) * s - 0.327622764) * s * a + a;
        if (ay > ax)
            r = 1.57079637 - r;
        if (x < 0.0)
            r = 3.14159274 - r;
        return (float)(y >= 0 ? r : -r);
    }


    /**
     * Returns the product of the first two arguments added with the third argument.
     *
     * @param a   a value.
     * @param b   a value.
     * @param c   a value.
     * @return   a * b + c.
     */
    public static float fma(float a, float b, float c) {
        return a * b + c;
    }


    /**
     * Rounds the argument to the nearest whole number to the smaller side.
     *
     * @param value   The argument to be rounded.
     * @return   A rounded argument.
     */
    public static int floor(float value) {
        int a = (int)value;
        return value < (float)a ? a - 1 : a;
    }

    /**
     * Rounds the argument to the nearest whole number upwards.
     *
     * @param value   The argument to be rounded.
     * @return   A rounded argument.
     */
    public static int ceil(float value) {
        int a = (int)value;
        return value > (float)a ? a + 1 : a;
    }


    /**
     * Rounds the argument to the nearest even number.
     * If the argument is equally close to two integers, the method will favor the larger number.
     *
     * @param value   The argument to be rounded.
     * @return   A rounded argument.
     */
    public static int round(float value) {
        float a = value - (int) value;
        return a < 0.5f ? (int) value : (int) value + 1;
    }


    /**
     * Returns the absolute value of the argument. If the argument is non-negative, returns its value.
     * If the argument is negative, its negation is returned.
     *
     * @param value   The argument whose absolute value is to be determined.
     * @return   The absolute value of the argument.
     */
    public static float abs(float value) {
        return value >= 0.0F ? value : -value;
    }


    /**
     * Returns f × 2^scaleFactor
     *
     * @param f   number to be scaled by a power of two.
     * @param scaleFactor   power of 2 used to scale {@code f}
     * @return   f × 2^scaleFactor
     */
    public static float scalb(float f, int scaleFactor) {
        return f * simplePow(2, scaleFactor);
    }


    /**
     * A simpler and more trivial way of raising a number to the right degree.
     * Unlike the other methods, you can only raise a number to an integer degree.
     *
     * @param number   The number to be raised to a degree.
     * @param degree   Necessary degree.
     * @return   A number raised to a degree.
     */
    public static float simplePow(float number, int degree) {
        if (degree == 0) return 1;
        if (degree == 1) return number;

        if (degree < 0) return 1 / simplePow(number, -degree);
        float a = number;

        for (int i = 1; i < degree; i++) {
            a = a * number;
        }
        return a;
    }


    /**
     * Raises a number to the desired degree.

     * Copied and modified from the {@code compute(final double x, final double y)} method
     * from the {@code FdLibm.Pow (java 17)} class.
     *
     * @Note The average approximate error compared to the standard method is 0.00000011886641%.
     *
     *
     * @param number   The number to be raised to a degree.
     * @param degree   Necessary degree.
     * @return   A number raised to a degree.
     */
    @Experimental("I don't know if this method will be slightly faster than the unmodified method since testing is still being done.")
    public static float pow(float number, float degree) {
        double z;
        double r, t, u, v, w;
        float s;
        int i, j, k, n;

        if (degree == 0.0)
            return 1.0f;

        if (Float.isNaN(number) || Float.isNaN(degree))
            return number + degree;

        final float degree_abs = abs(degree);
        double number_abs   = abs(number);
        if (degree == 2.0) {
            return number * number;

        } else if (degree_abs == 1.0) {
            return (degree == 1.0) ? number : 1.0f / number;
        } else if (degree_abs == INFINITY) {
            if (number_abs == 1.0)
                return 0.0f;
            else if (number_abs > 1.0)
                return (degree >= 0) ? degree : 0.0f;
            else
                return (degree < 0) ? -degree : 0.0f;
        }

        final int hi_number = __HI(number);
        int i_number = hi_number & 2147483647;
        int degree_is_int  = 0;
        if (hi_number < 0) {
            if (degree_abs >= 9007199254740992.0)
                degree_is_int = 2;
            else if (degree_abs >= 1.0) {
                long degree_abs_as_long = (long) degree_abs;
                if ( ((double) degree_abs_as_long) == degree_abs) {
                    degree_is_int = 2 -  (int)(degree_abs_as_long & 0x1L);
                }
            }
        }

        if (number_abs == 0.0 || number_abs == INFINITY || number_abs == 1.0) {
            z = number_abs;
            if (degree < 0.0)
                z = 1.0/z;
            if (hi_number < 0) {
                if (((i_number - 1072693248) | degree_is_int) == 0) {
                    z = 1.0;
                } else if (degree_is_int == 1)
                    z = -1.0 * z;
            }
            return (float) z;
        }

        n = (hi_number >> 31) + 1;

        if ((n | degree_is_int) == 0)
            return 1.0f;

        s = 1.0f;
        if ( (n | (degree_is_int - 1)) == 0)
            s = -1.0f;

        double p_h, p_l, t1, t2;
        if (degree_abs > 2147485695.9999995) {

            if (number_abs < 0.9999995231628418)
                return (float)(degree < 0.0 ? INFINITY : s * 0.0);
            if (number_abs > 1.0000009536743162)
                return (float)(degree > 0.0 ? INFINITY : s * 0.0);

            t = number_abs - 1.0;
            u = 1.4426950216293335 * t;
            v =  t * 0.000000019259629911266175 - ((t * t) * (0.5 - t * (0.3333333333333333333333 - t * 0.25))) * 1.4426950408889634;
            t1 = __LO0(u + v);
            t2 = v - (t1 - u);
        } else {

            double z_h, z_l, ss, s2, s_h, s_l, t_h;
            n = 0;
            if (i_number < 1048576) {
                number_abs *= 9007199254740992.0;
                n -= 53;
                i_number = __HI(number_abs);
            }
            n  += ((i_number) >> 20) - 1023;
            j  = i_number & 1048575;
            i_number = j | 1072693248;

            final double DP_H, DP_L;
            final float BP;

            if (j <= 235662) {
                k = 0;
                DP_H = 0.0;
                DP_L = 0.0;
                BP = 1.0f;
            }
            else if (j < 767610) {
                k = 1;
                DP_H = 0.5849624872207642;
                DP_L = 0.00000001350039202129749;
                BP = 1.5f;
            }
            else {
                k = 0;
                DP_H = 0.0;
                DP_L = 0.0;
                BP = 1.0f;
                n += 1;
                i_number -= 1048576;
            }
            number_abs = __HI(number_abs, i_number);

            u = number_abs - BP;
            v = 1.0 / (number_abs + BP);
            ss = u * v;
            s_h = __LO0(ss);
            t_h = __HI(0, ((i_number >> 1) | 536870912) + 524288 + (k << 18) );
            s_l = v * ((u - s_h * t_h) - s_h * (number_abs - (t_h - BP)));
            s2 = ss * ss;
            r = s2 * s2* (0.5999999999999946 + s2 * (0.4285714285785502 + s2 * (0.33333332981837743 + s2 * (0.272728123808534 + s2 * (0.23066074577556175 + s2 * 0.20697501780033842)))));
            r += s_l * (s_h + ss);
            s2  = s_h * s_h;
            t_h = __LO0(3.0 + s2 + r);
            u = s_h * t_h;
            v = s_l * t_h + (r - ((t_h - 3.0) - s2)) * ss;
            p_h = u + v;
            p_h = __LO0(p_h);
            z_h = 0.9617967009544373 * p_h;
            z_l = -0.000000007028461650952758 * p_h + (v - (p_h - u)) * 0.9617966939259756 + DP_L;
            t1 = __LO0((((z_h + z_l) + DP_H) + n));
            t2 = z_l - (((t1 - n) - DP_H) - z_h);
        }

        // Split up degree into (y1 + y2) and compute (y1 + y2) * (t1 + t2)
        double y1 = __LO0(degree);
        p_l = (degree - y1) * t1 + degree * t2;
        p_h = y1 * t1;
        z = p_l + p_h;
        j = __HI(z);
        i = __LO(z);
        if (j >= 1083179008) {                           // z >= 1024
            if (((j - 1083179008) | i)!=0)               // if z > 1024
                return INFINITY;                     // Overflow
            else {
                if (p_l + 0.00000000000000008008566259537294 > z - p_h)
                    return INFINITY;   // Overflow
            }
        } else if ((j & 2147483647) >= 1083231232 ) {        // z <= -1075
            if (((j - -1064252416) | i)!=0)           // z < -1075
                return (float)(s * 0.0);           // Underflow
            else {
                if (p_l <= z - p_h)
                    return (float)(s * 0.0);      // Underflow
            }
        }
        i = j & 2147483647;
        k = (i >> 20) - 1023;
        n = 0;
        if (i > 1071644672) {
            n = j + (1048576 >> (k + 1));
            k = ((n & 2147483647) >> 20) - 1023;
            t = __HI(0, (n & ~(1048575 >> k)) );
            n = ((n & 1048575) | 1048576) >> (20 - k);
            if (j < 0)
                n = -n;
            p_h -= t;
        }
        t = __LO0(p_l + p_h);
        u = t * 0.6931471824645996;
        v = (p_l - (t - p_h)) * 0.6931471805599453 + t * -0.000000001904654299957768;
        z = u + v;
        w = v - (z - u);
        t  = z * z;
        t1  = z - t * (0.16666666666666602 + t * (-0.0027777777777015593 + t * (0.00006613756321437934 + t * (-0.0000016533902205465252 + t * 0.000000041381367970572385))));
        r  = (z * t1) / (t1 - 2.0) - (w + z * w);
        z  = 1.0 - (r - z);
        j  = __HI(z);
        j += (n << 20);
        if ((j >> 20) <= 0)
            z = scalb((float)z, n);
        else {
            int z_hi = __HI(z);
            z_hi += (n << 20);
            z = __HI(z, z_hi);
        }
        return (float)(s * z);
    }

    public static int applyRange(int number, int min, int max) {
        return Math.min(Math.max(min, number), max);
    }


    private static final float[] half = {0.5f, -0.5f};
    private static final double[] ln2HI   ={  0x1.62e42feep-1, -0x1.62e42feep-1};
    private static final double[] ln2LO   ={  0x1.a39ef35793c76p-33, -0x1.a39ef35793c76p-33};

    public static float exp(float degree) {
        double y;
        double hi = 0.0;
        double lo = 0.0;
        double c;
        double t;
        int k = 0;
        int xsb;
        int hi_degree;

        hi_degree  = __HI(degree);
        xsb = (hi_degree >> 31) & 1;               /* sign a bit of degree */
        hi_degree &= 2147483647;               /* high word of |degree| */

        /* filter out non-finite argument */
        if (hi_degree >= 1082535490) {                  /* if |degree| >= 709.78... */
            if (hi_degree >= 2146435072) {
                if (((hi_degree & 1048575) | __LO(degree)) != 0)
                    return degree + degree;                /* NaN */
                else
                    return (xsb == 0) ?  degree : 0.0f;    /* exp(+-inf) = {inf, 0} */
            }
            if (degree > 709.782712893384)
                return INFINITY;
            if (degree < -745.1332191019411) // unsigned compare needed here?
                return 0.0f;
        }

        /* argument reduction */
        if (hi_degree > 1071001154) {           /* if  |degree| > 0.5 ln2 */
            if(hi_degree < 1072734898) {       /* and |degree| < 1.5 ln2 */
                hi = degree - ln2HI[xsb];
                lo = ln2LO[xsb];
                k = 1 - xsb - xsb;
            } else {
                k  = (int)(1.4426950408889634 * degree + half[xsb]);
                t  = k;
                hi = degree - t * 0.6931471803691238;    /* t*ln2HI is exact here */
                lo = t * 0.00000000019082149292705877;
            }
            degree = (float)(hi - lo);
        } else if (hi_degree < 0x3e300000)  {     /* when |degree|<2**-28 */
            if (1.0e+300 + degree > 1.0f)   //in no way convert the number 1.0e+300
                return 1.0f + degree; /* trigger inexact */
        }

        /* degree is now in primary range */
        t  = degree * degree;
        c  = degree - t * (0x1.555555555553ep-3 + t * (-0x1.6c16c16bebd93p-9 + t * (0x1.1566aaf25de2cp-14 + t * (-0x1.bbd41c5d26bf1p-20 + t * 0x1.6376972bea4d0p-25))));
        if (k == 0)
            return (float)(1.0f - ((degree*c)/(c - 2.0) - degree));
        else
            y = 1.0f - ((lo - (degree*c)/(2.0 - c)) - hi);

        if(k >= -1021) {
            y = __HI(y, __HI(y) + (k << 20)); /* add k to y's exponent */
            return (float)y;
        } else {
            y = __HI(y, __HI(y) + ((k + 1000) << 20)); /* add k to y's exponent */
            return (float)(y * 0x1.0p-1000);
        }
    }


    public static float min(float a, float b) {
        return a <= b ? a : b;
    }


    public static float max(float a, float b) {
        return a >= b ? a : b;
    }

    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }

    public static int mirrorNumber(int minValue, int value, int maxValue) {
        if (value == maxValue)
            return minValue;
        if (value == minValue)
            return maxValue;
        if (value > maxValue) {
            return minValue - (value - maxValue);
        }
        if (value < minValue) {
            return maxValue + (minValue - value);
        }

        minValue--;
        maxValue++;

        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> revertedNumbers = new ArrayList<>();
        for (int i = minValue; i < maxValue + 1; i++) {
            numbers.add(i);
        }
        for (int i = numbers.size() - 1; i > 0; i--) {
            revertedNumbers.add(numbers.get(i));
        }
        return revertedNumbers.get(value);
    }

    public static double getDistance(Vector2d from, Vector2d to) {
        double sX = Math.pow(to.x - from.x, 2);
        double sZ = Math.pow(to.y - from.y, 2);

        return Math.sqrt(sX + sZ);
    }

    public static int generateInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static float generateFloat(float min, float max) {
        return (float) Math.min(max, ( Math.random() * ((max * 1.1) - min) ) + min);
    }






    private static int __HI(double number) {
        long transducer = Double.doubleToRawLongBits(number);
        return (int)(transducer >> 32);
    }

    private static double __HI(double number, int high) {
        long transX = Double.doubleToRawLongBits(number);
        return Double.longBitsToDouble((transX & 4294967295L) |
                ( ((long)high)) << 32 );
    }

    private static int __LO(double number) {
        long transducer = Double.doubleToRawLongBits(number);
        return (int)transducer;
    }

    private static double __LO0(double number) {
        long transX = Double.doubleToRawLongBits(number);
        return Double.longBitsToDouble((transX & -4294967296L));
    }
}
