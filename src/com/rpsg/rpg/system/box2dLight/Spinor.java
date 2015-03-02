package com.rpsg.rpg.system.box2dLight;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.StringBuilder;

public class Spinor {
  float real;
  float complex;

  private static final float COSINE_THRESHOLD = 0.001f;

  public Spinor() {

  }

  public Spinor(float angle) {
    set(angle);
  }

  public Spinor(Spinor copyFrom) {
    set(copyFrom);
  }

  public Spinor(float real, float complex) {
    set(real, complex);
  }

  public Spinor set(float angle) {
    angle /= 2;
    set((float) Math.cos(angle), (float) Math.sin(angle));
    return this;
  }

  public Spinor set(Spinor copyFrom) {
    set(copyFrom.real, copyFrom.complex);
    return this;
  }

  public Spinor set(float real, float complex) {
    this.real = real;
    this.complex = complex;

    return this;
  }

  public Spinor scale(float t) {
    real *= t;
    complex *= t;
    return this;
  }

  public Spinor invert() {
    complex = -complex;
    scale(len2());
    return this;
  }

  public Spinor add(Spinor other) {
    real += other.real;
    complex += other.complex;
    return this;
  }
  
  public Spinor add(float angle) {
    angle /= 2;
    real += Math.cos(angle);
    complex += Math.sin(angle);
    return this;
  }

  public Spinor sub(Spinor other) {
    real -= other.real;
    complex -= other.complex;
    return this;
  }
  
  public Spinor sub(float angle) {
    angle /= 2;
    real -= Math.cos(angle);
    complex -= Math.sin(angle);
    return this;
  }

  public float len() {
    return (float) Math.sqrt(real * real + complex * complex);
  }

  public float len2() {
    return real * real + complex * complex;
  }

  public Spinor mul(Spinor other) {
    set(real * other.real - complex * other.complex, real * other.complex
        + complex * other.real);
    return this;
  }

  public Spinor nor() {
    float length = len();
    real /= length;
    complex /= length;
    return this;
  }

  public float angle() {
    return (float) Math.atan2(complex, real) * 2;
  }

  public Spinor lerp(Spinor end, float alpha, Spinor tmp) {
    scale(1 - alpha);
    tmp.set(end).scale(alpha);
    add(tmp);
    nor();
    return this;
  }

  public Spinor slerp(Spinor dest, float t) {
    float tr, tc, omega, cosom, sinom, scale0, scale1;

    // cosine
    cosom = real * dest.real + complex * dest.complex;

    // adjust signs
    if (cosom < 0) {
      cosom = -cosom;
      tc = -dest.complex;
      tr = -dest.real;
    } else {
      tc = dest.complex;
      tr = dest.real;
    }

    // coefficients
    if (1f - cosom > COSINE_THRESHOLD) {
      omega = (float) Math.acos(cosom);
      sinom = (float) Math.sin(omega);
      scale0 = (float) Math.sin((1f - t) * omega) / sinom;
      scale1 = (float) Math.sin(t * omega) / sinom;
    } else {
      scale0 = 1f - t;
      scale1 = t;
    }

    // final calculation
    complex = scale0 * complex + scale1 * tc;
    real = scale0 * real + scale1 * tr;

    return this;
  }
  
  @Override public String toString() {
    StringBuilder result = new StringBuilder();
    float radians = angle();
    result.append("radians: ");
    result.append(radians);
    result.append(", degrees: ");
    result.append(radians * MathUtils.radiansToDegrees);
    return result.toString();
  }
}
