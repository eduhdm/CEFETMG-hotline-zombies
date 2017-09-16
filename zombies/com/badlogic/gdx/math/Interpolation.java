/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.math;

import com.badlogic.gdx.math.MathUtils;

public abstract class Interpolation {
    public static final Interpolation linear = new Interpolation(){

        @Override
        public float apply(float a) {
            return a;
        }
    };
    public static final Interpolation fade = new Interpolation(){

        @Override
        public float apply(float a) {
            return MathUtils.clamp(a * a * a * (a * (a * 6.0f - 15.0f) + 10.0f), 0.0f, 1.0f);
        }
    };
    public static final Pow pow2 = new Pow(2);
    public static final PowIn pow2In = new PowIn(2);
    public static final PowOut pow2Out = new PowOut(2);
    public static final Pow pow3 = new Pow(3);
    public static final PowIn pow3In = new PowIn(3);
    public static final PowOut pow3Out = new PowOut(3);
    public static final Pow pow4 = new Pow(4);
    public static final PowIn pow4In = new PowIn(4);
    public static final PowOut pow4Out = new PowOut(4);
    public static final Pow pow5 = new Pow(5);
    public static final PowIn pow5In = new PowIn(5);
    public static final PowOut pow5Out = new PowOut(5);
    public static final Interpolation sine = new Interpolation(){

        @Override
        public float apply(float a) {
            return (1.0f - MathUtils.cos(a * 3.1415927f)) / 2.0f;
        }
    };
    public static final Interpolation sineIn = new Interpolation(){

        @Override
        public float apply(float a) {
            return 1.0f - MathUtils.cos(a * 3.1415927f / 2.0f);
        }
    };
    public static final Interpolation sineOut = new Interpolation(){

        @Override
        public float apply(float a) {
            return MathUtils.sin(a * 3.1415927f / 2.0f);
        }
    };
    public static final Exp exp10 = new Exp(2.0f, 10.0f);
    public static final ExpIn exp10In = new ExpIn(2.0f, 10.0f);
    public static final ExpOut exp10Out = new ExpOut(2.0f, 10.0f);
    public static final Exp exp5 = new Exp(2.0f, 5.0f);
    public static final ExpIn exp5In = new ExpIn(2.0f, 5.0f);
    public static final ExpOut exp5Out = new ExpOut(2.0f, 5.0f);
    public static final Interpolation circle = new Interpolation(){

        @Override
        public float apply(float a) {
            if (a <= 0.5f) {
                return (1.0f - (float)Math.sqrt(1.0f - a * (a *= 2.0f))) / 2.0f;
            }
            a -= 1.0f;
            return ((float)Math.sqrt(1.0f - a * (a *= 2.0f)) + 1.0f) / 2.0f;
        }
    };
    public static final Interpolation circleIn = new Interpolation(){

        @Override
        public float apply(float a) {
            return 1.0f - (float)Math.sqrt(1.0f - a * a);
        }
    };
    public static final Interpolation circleOut = new Interpolation(){

        @Override
        public float apply(float a) {
            return (float)Math.sqrt(1.0f - a * (a -= 1.0f));
        }
    };
    public static final Elastic elastic = new Elastic(2.0f, 10.0f, 7, 1.0f);
    public static final ElasticIn elasticIn = new ElasticIn(2.0f, 10.0f, 6, 1.0f);
    public static final ElasticOut elasticOut = new ElasticOut(2.0f, 10.0f, 7, 1.0f);
    public static final Swing swing = new Swing(1.5f);
    public static final SwingIn swingIn = new SwingIn(2.0f);
    public static final SwingOut swingOut = new SwingOut(2.0f);
    public static final Bounce bounce = new Bounce(4);
    public static final BounceIn bounceIn = new BounceIn(4);
    public static final BounceOut bounceOut = new BounceOut(4);

    public abstract float apply(float var1);

    public float apply(float start, float end, float a) {
        return start + (end - start) * this.apply(a);
    }

    public static class SwingIn
    extends Interpolation {
        private final float scale;

        public SwingIn(float scale) {
            this.scale = scale;
        }

        @Override
        public float apply(float a) {
            return a * a * ((this.scale + 1.0f) * a - this.scale);
        }
    }

    public static class SwingOut
    extends Interpolation {
        private final float scale;

        public SwingOut(float scale) {
            this.scale = scale;
        }

        @Override
        public float apply(float a) {
            return a * a * ((this.scale + 1.0f) * (a -= 1.0f) + this.scale) + 1.0f;
        }
    }

    public static class Swing
    extends Interpolation {
        private final float scale;

        public Swing(float scale) {
            this.scale = scale * 2.0f;
        }

        @Override
        public float apply(float a) {
            if (a <= 0.5f) {
                return a * a * ((this.scale + 1.0f) * (a *= 2.0f) - this.scale) / 2.0f;
            }
            a -= 1.0f;
            return a * a * ((this.scale + 1.0f) * (a *= 2.0f) + this.scale) / 2.0f + 1.0f;
        }
    }

    public static class BounceIn
    extends BounceOut {
        public BounceIn(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public BounceIn(int bounces) {
            super(bounces);
        }

        @Override
        public float apply(float a) {
            return 1.0f - super.apply(1.0f - a);
        }
    }

    public static class BounceOut
    extends Interpolation {
        final float[] widths;
        final float[] heights;

        public BounceOut(float[] widths, float[] heights) {
            if (widths.length != heights.length) {
                throw new IllegalArgumentException("Must be the same number of widths and heights.");
            }
            this.widths = widths;
            this.heights = heights;
        }

        public BounceOut(int bounces) {
            if (bounces < 2 || bounces > 5) {
                throw new IllegalArgumentException("bounces cannot be < 2 or > 5: " + bounces);
            }
            this.widths = new float[bounces];
            this.heights = new float[bounces];
            this.heights[0] = 1.0f;
            switch (bounces) {
                case 2: {
                    this.widths[0] = 0.6f;
                    this.widths[1] = 0.4f;
                    this.heights[1] = 0.33f;
                    break;
                }
                case 3: {
                    this.widths[0] = 0.4f;
                    this.widths[1] = 0.4f;
                    this.widths[2] = 0.2f;
                    this.heights[1] = 0.33f;
                    this.heights[2] = 0.1f;
                    break;
                }
                case 4: {
                    this.widths[0] = 0.34f;
                    this.widths[1] = 0.34f;
                    this.widths[2] = 0.2f;
                    this.widths[3] = 0.15f;
                    this.heights[1] = 0.26f;
                    this.heights[2] = 0.11f;
                    this.heights[3] = 0.03f;
                    break;
                }
                case 5: {
                    this.widths[0] = 0.3f;
                    this.widths[1] = 0.3f;
                    this.widths[2] = 0.2f;
                    this.widths[3] = 0.1f;
                    this.widths[4] = 0.1f;
                    this.heights[1] = 0.45f;
                    this.heights[2] = 0.3f;
                    this.heights[3] = 0.15f;
                    this.heights[4] = 0.06f;
                }
            }
            float[] arrf = this.widths;
            arrf[0] = arrf[0] * 2.0f;
        }

        @Override
        public float apply(float a) {
            a += this.widths[0] / 2.0f;
            float width = 0.0f;
            float height = 0.0f;
            int n = this.widths.length;
            for (int i = 0; i < n; ++i) {
                width = this.widths[i];
                if (a <= width) {
                    height = this.heights[i];
                    break;
                }
                a -= width;
            }
            float z = 4.0f / width * height * (a /= width);
            return 1.0f - (z - z * a) * width;
        }
    }

    public static class Bounce
    extends BounceOut {
        public Bounce(float[] widths, float[] heights) {
            super(widths, heights);
        }

        public Bounce(int bounces) {
            super(bounces);
        }

        private float out(float a) {
            float test = a + this.widths[0] / 2.0f;
            if (test < this.widths[0]) {
                return test / (this.widths[0] / 2.0f) - 1.0f;
            }
            return super.apply(a);
        }

        @Override
        public float apply(float a) {
            if (a <= 0.5f) {
                return (1.0f - this.out(1.0f - a * 2.0f)) / 2.0f;
            }
            return this.out(a * 2.0f - 1.0f) / 2.0f + 0.5f;
        }
    }

    public static class ElasticOut
    extends Elastic {
        public ElasticOut(float value, float power, int bounces, float scale) {
            super(value, power, bounces, scale);
        }

        @Override
        public float apply(float a) {
            a = 1.0f - a;
            return 1.0f - (float)Math.pow(this.value, this.power * (a - 1.0f)) * MathUtils.sin(a * this.bounces) * this.scale;
        }
    }

    public static class ElasticIn
    extends Elastic {
        public ElasticIn(float value, float power, int bounces, float scale) {
            super(value, power, bounces, scale);
        }

        @Override
        public float apply(float a) {
            if ((double)a >= 0.99) {
                return 1.0f;
            }
            return (float)Math.pow(this.value, this.power * (a - 1.0f)) * MathUtils.sin(a * this.bounces) * this.scale;
        }
    }

    public static class Elastic
    extends Interpolation {
        final float value;
        final float power;
        final float scale;
        final float bounces;

        public Elastic(float value, float power, int bounces, float scale) {
            this.value = value;
            this.power = power;
            this.scale = scale;
            this.bounces = (float)bounces * 3.1415927f * (float)(bounces % 2 == 0 ? 1 : -1);
        }

        @Override
        public float apply(float a) {
            if (a <= 0.5f) {
                return (float)Math.pow(this.value, this.power * (a - 1.0f)) * MathUtils.sin((a *= 2.0f) * this.bounces) * this.scale / 2.0f;
            }
            a = 1.0f - a;
            return 1.0f - (float)Math.pow(this.value, this.power * (a - 1.0f)) * MathUtils.sin((a *= 2.0f) * this.bounces) * this.scale / 2.0f;
        }
    }

    public static class ExpOut
    extends Exp {
        public ExpOut(float value, float power) {
            super(value, power);
        }

        @Override
        public float apply(float a) {
            return 1.0f - ((float)Math.pow(this.value, (- this.power) * a) - this.min) * this.scale;
        }
    }

    public static class ExpIn
    extends Exp {
        public ExpIn(float value, float power) {
            super(value, power);
        }

        @Override
        public float apply(float a) {
            return ((float)Math.pow(this.value, this.power * (a - 1.0f)) - this.min) * this.scale;
        }
    }

    public static class Exp
    extends Interpolation {
        final float value;
        final float power;
        final float min;
        final float scale;

        public Exp(float value, float power) {
            this.value = value;
            this.power = power;
            this.min = (float)Math.pow(value, - power);
            this.scale = 1.0f / (1.0f - this.min);
        }

        @Override
        public float apply(float a) {
            if (a <= 0.5f) {
                return ((float)Math.pow(this.value, this.power * (a * 2.0f - 1.0f)) - this.min) * this.scale / 2.0f;
            }
            return (2.0f - ((float)Math.pow(this.value, (- this.power) * (a * 2.0f - 1.0f)) - this.min) * this.scale) / 2.0f;
        }
    }

    public static class PowOut
    extends Pow {
        public PowOut(int power) {
            super(power);
        }

        @Override
        public float apply(float a) {
            return (float)Math.pow(a - 1.0f, this.power) * (float)(this.power % 2 == 0 ? -1 : 1) + 1.0f;
        }
    }

    public static class PowIn
    extends Pow {
        public PowIn(int power) {
            super(power);
        }

        @Override
        public float apply(float a) {
            return (float)Math.pow(a, this.power);
        }
    }

    public static class Pow
    extends Interpolation {
        final int power;

        public Pow(int power) {
            this.power = power;
        }

        @Override
        public float apply(float a) {
            if (a <= 0.5f) {
                return (float)Math.pow(a * 2.0f, this.power) / 2.0f;
            }
            return (float)Math.pow((a - 1.0f) * 2.0f, this.power) / (float)(this.power % 2 == 0 ? -2 : 2) + 1.0f;
        }
    }

}

