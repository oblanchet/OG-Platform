/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.model.option.pricing.analytic;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.opengamma.analytics.financial.model.volatility.BlackFormulaRepository;
import com.opengamma.analytics.math.statistics.distribution.BivariateNormalDistribution;
import com.opengamma.analytics.math.statistics.distribution.ProbabilityDistribution;

/**
 * Bjerksund and Stensland model test.
 * 
 */
public class BjerksundStenslandModelTest extends AmericanAnalyticOptionModelTest {

  private static final ProbabilityDistribution<double[]> BIVARIATE_NORMAL = new BivariateNormalDistribution();

  @Test
  public void test() {
    // Deprecated form used for this test
    super.assertValid(new BjerksundStenslandModelDeprecated(), 1e-4);
  }

  @Test(enabled = false)
  public void priceTest() {
    final double s0 = 120;
    final double r = 0.08;
    final double q = 0.12;
    final double b = r - q;
    final double k = 100.0;
    final double t = 0.25;
    final double sigma = 0.3;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();
    final double eurPrice = Math.exp(-r * t) * BlackFormulaRepository.price(s0 * Math.exp(b * t), k, t, sigma, true);
    final double amPrice = bs.price(s0, k, r, b, t, sigma, true);
    System.out.println(eurPrice + "\t" + amPrice);
  }

  //[PLAT-2944]
  @Test
  public void earlyExciseTest() {
    final double s0 = 10.0;
    final double r = 0.0;
    final double b = 0.05;
    final double k = 13.0;
    final double t = 0.25;
    final double sigma = 0.3;
    final boolean isCall = false;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();
    final double amPrice = bs.price(s0, k, r, b, t, sigma, isCall);
    assertTrue(amPrice >= (k - s0));
  }

  @Test
  public void adjointTest() {
    final double[] s0Set = new double[] {60, 90, 100, 110, 160 };
    final double k = 100;
    final double[] rSet = new double[] {0.0, 0.1 };
    final double[] bSet = new double[] {-0.04, 0.0, 0.04, 0.11 };
    final double sigma = 0.35;
    final double t = 0.5;
    final boolean[] tfSet = new boolean[] {true, false };

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();
    final double eps = 1e-5;

    for (final double s0 : s0Set) {
      for (final double r : rSet) {
        for (final double b : bSet) {
          final double[] parms = new double[] {s0, k, r, b, t, sigma };
          final int n = parms.length;

          for (final boolean isCall : tfSet) {
            final double price = bs.price(s0, k, r, b, t, sigma, isCall);
            final double[] sense = bs.getPriceAdjoint(s0, k, r, b, t, sigma, isCall);
            assertEquals("price " + s0 + " " + r + " " + b + " " + isCall, price, sense[0], 1e-13);

            for (int i = 0; i < n; i++) {
              final double delta = (1 + Math.abs(parms[i])) * eps;
              final double[] temp = Arrays.copyOf(parms, n);
              temp[i] += delta;
              final double up = bs.price(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], isCall);
              temp[i] -= 2 * delta;
              final double down = bs.price(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], isCall);
              //  System.out.println("debug " + i + " " + s0 + " " + r + " " + b + " " + isCall + "\t" + up + "\t" + price + "\t" + down + "\t" + delta);
              double fd;
              if ((i == 3 && Math.abs(b) < delta) /*|| (i == 2 && Math.abs(r) < delta)*/) {
                //there is a discontinuity in the gradient at at b == 0 r != 0, and also for puts with r = 0 hence forward difference for the test
                if (isCall) {
                  fd = (up - price) / delta;
                } else {
                  fd = (price - down) / delta;
                }
                assertEquals(i + " " + s0 + " " + r + " " + b + " " + isCall, fd, sense[i + 1], Math.abs(fd) * 1e-4);
              } else {
                fd = (up - down) / 2 / delta;
                if (!isCall && r == 0 && b > 0) {
                  assertEquals(i + " " + s0 + " " + r + " " + b + " " + isCall, fd, sense[i + 1], Math.abs(fd) * 1e-4);
                } else {
                  assertEquals(i + " " + s0 + " " + r + " " + b + " " + isCall, fd, sense[i + 1], Math.abs(fd) * 1e-5);
                }
              }
            }
          }
        }
      }
    }
  }

  @Test
  public void debugTest() {
    final BjerksundStenslandModel bs = new BjerksundStenslandModel();
    final double s0 = 90;
    final double k = 100;
    final double r = 0.0;
    final double b = 0.11;
    final double sigma = 0.35;
    final double t = 0.5;
    final boolean isCall = false;

    final double price = bs.price(s0, k, r, b, t, sigma, isCall);
    final double[] sense = bs.getPriceAdjoint(s0, k, r, b, t, sigma, isCall);
    //System.out.println(price + "\t" + sense[0]);

    //System.out.println(sense[3]);
    final double eps = 1e-4;
    final double up = bs.price(s0, k, r + eps, b, t, sigma, isCall);
    final double down = bs.price(s0, k, r - eps, b, t, sigma, isCall);
    //System.out.println(up + "\t" + down + "\t" + (up - down) / 2 / eps);
  }

  @Test
  //(enabled = false)
  public void deltaGammaTest() {
    final BjerksundStenslandModel bs = new BjerksundStenslandModel();
    final double[] s0Set = new double[] {60, 90, 100, 110, 160 };
    final double k = 100;
    final double r = 0.1;
    final double[] bSet = new double[] {-0.04, 0.0, 0.04, 0.11 };
    final double sigma = 0.35;
    final double t = 0.5;
    final boolean[] tfSet = new boolean[] {true, false };

    for (final double s0 : s0Set) {
      final double eps = 1e-5 * s0;
      for (final double b : bSet) {
        for (final boolean isCall : tfSet) {
          final double price = bs.price(s0, k, r, b, t, sigma, isCall);
          final double[] sense = bs.getPriceDeltaGamma(s0, k, r, b, t, sigma, isCall);
          assertEquals("price " + s0 + " " + b, price, sense[0], 1e-13);
          final double up = bs.price(s0 + eps, k, r, b, t, sigma, isCall);
          final double down = bs.price(s0 - eps, k, r, b, t, sigma, isCall);
          final double fd = (up - down) / 2 / eps;
          final double fd2 = (up + down - 2 * price) / eps / eps;
          assertEquals("delta " + s0 + " " + b, fd, sense[1], Math.abs(fd) * 1e-6);
          assertEquals("gamma " + s0 + " " + b, fd2, sense[2], Math.abs(fd2) * 1e-4);
        }
      }
    }
  }

  @Test
  public void phiTest() {
    final double s0 = 100;
    final double[] x2Set = new double[] {130, 150, 170 };
    final double r = 0.1;
    final double[] bSet = new double[] {-0.04, 0.0, 0.04, r };
    final double t1 = 0.5;
    final double sigma = 0.35;

    final double t = 2 * t1 / (Math.sqrt(5) - 1);
    final double[] gammaSet = new double[] {0, 1, 0.67, 1.87 };
    final double x1 = 133.0;

    for (final double x2 : x2Set) {
      for (final double b : bSet) {
        for (final double gamma : gammaSet) {

          final double[] parms = new double[] {s0, t, gamma, x1, x2, r, b, sigma };
          final int n = parms.length;

          final BjerksundStenslandModel bs = new BjerksundStenslandModel();
          final double impA = bs.getPhi(s0, t1, gamma, x1, x2, r, b, sigma);
          final double[] sense = bs.getPhiAdjoint(s0, t, gamma, x1, x2, r, b, sigma);

          // System.out.println(impA + "\t" + sense[0]);
          assertEquals(impA, sense[0], 1e-12);
          final double eps = 1e-5;
          for (int i = 0; i < 8; i++) {
            final double[] temp = Arrays.copyOf(parms, n);
            final double delta = (1 + Math.abs(parms[i])) * eps;
            temp[i] += delta;
            final double up = bs.getPhiAdjoint(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7])[0];
            temp[i] -= 2 * delta;
            final double down = bs.getPhiAdjoint(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7])[0];
            final double fd = (up - down) / 2 / delta;
            //System.out.println(fd + "\t" + sense[i + 1]);
            assertEquals(i + " " + x2 + " " + b + " " + gamma, fd, sense[i + 1], Math.abs(fd) * 2e-8);
          }
        }
      }
    }

  }

  @Test
  public void psiTest() {
    final double s0 = 100;
    final double[] kSet = new double[] {90, 100, 110 };
    final double r = 0.1;
    final double[] bSet = new double[] {-0.04, 0.04 };
    final double t = 0.5;
    final double sigma = 0.35;

    final double r2 = 0.5 * (Math.sqrt(5) - 1);
    final double t1 = r2 * t;
    final double[] gammaSet = new double[] {0, 1, 0.67, 1.87 };
    final double x1 = 133.0;
    final double x2 = 140.2;

    for (final double k : kSet) {
      for (final double b : bSet) {
        for (final double gamma : gammaSet) {

          final double[] parms = new double[] {s0, t, gamma, k, x2, x1, r, b, sigma };
          final int n = parms.length;

          final BjerksundStenslandModel bs = new BjerksundStenslandModel();
          final double impA = bs.getPsi(s0, t1, t, gamma, k, x2, x1, r, b, sigma);
          final double[] sense = bs.getPsiAdjoint(s0, t, gamma, k, x2, x1, r, b, sigma);

          assertEquals(impA, sense[0], 1e-12);
          //System.out.println(impA + "\t" + sense[0]);

          final double eps = 1e-5;
          for (int i = 0; i < n; i++) {
            final double[] temp = Arrays.copyOf(parms, n);
            final double delta = (1 + Math.abs(parms[i])) * eps;
            temp[i] += delta;
            final double up = bs.getPsiAdjoint(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8])[0];
            temp[i] -= 2 * delta;
            final double down = bs.getPsiAdjoint(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6], temp[7], temp[8])[0];
            final double fd = (up - down) / 2 / delta;
            //           System.out.println(fd + "\t" + sense[i + 1]);

            assertEquals(i + " " + k + " " + b + " " + gamma, fd, sense[i + 1], Math.abs(fd) * 1e-4); //TODO would expect better agreement than this

          }
        }
      }
    }
  }

  @Test
  public void psiDeltaTest() {
    final BjerksundStenslandModel bs = new BjerksundStenslandModel();
    final double s0 = 100;
    final double[] kSet = new double[] {90, 100, 110 };
    final double r = 0.1;
    final double[] bSet = new double[] {-0.04, 0.04 };
    final double t = 0.5;
    final double sigma = 0.35;

    final double r2 = 0.5 * (Math.sqrt(5) - 1);
    final double t1 = r2 * t;
    final double[] gammaSet = new double[] {0, 1, 0.67, 1.87 };
    final double x1 = 133.0;
    final double x2 = 140.2;

    final double eps = 1e-5 * s0;

    for (final double k : kSet) {
      for (final double b : bSet) {
        for (final double gamma : gammaSet) {

          final double psi = bs.getPsi(s0, t1, t, gamma, k, x2, x1, r, b, sigma);
          final double[] sense = bs.getPsiDelta(s0, t, gamma, k, x2, x1, r, b, sigma);
          //double psi = sense[0];
          assertEquals("psi", psi, sense[0], Math.abs(psi) * 1e-15);
          final double up = bs.getPsi(s0 + eps, t1, t, gamma, k, x2, x1, r, b, sigma);
          final double down = bs.getPsi(s0 - eps, t1, t, gamma, k, x2, x1, r, b, sigma);

          final double fd = (up - down) / 2 / eps;
          final double fd2 = (up + down - 2 * psi) / eps / eps;
          assertEquals("psi delta", fd, sense[1], Math.abs(fd) * 1e-6);
          assertEquals("psi gamma", fd2, sense[2], Math.abs(fd2) * 1e-4);
        }
      }
    }
  }

  @Test
  public void biVarNormTest() {

    final double rho = Math.sqrt(0.5 * (Math.sqrt(5) - 1));
    final double a = 1.2;
    final double b = -0.6;
    final double eps = 1e-5;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();
    final double cent = BIVARIATE_NORMAL.getCDF(new double[] {a, b, rho });
    final double[] sense = bs.bivariateNormDiv(a, b, true);
    final double aUp = BIVARIATE_NORMAL.getCDF(new double[] {a + eps, b, rho });
    final double aDown = BIVARIATE_NORMAL.getCDF(new double[] {a - eps, b, rho });
    final double bUp = BIVARIATE_NORMAL.getCDF(new double[] {a, b + eps, rho });
    final double bDown = BIVARIATE_NORMAL.getCDF(new double[] {a, b - eps, rho });
    final double aUpbUp = BIVARIATE_NORMAL.getCDF(new double[] {a + eps, b + eps, rho });
    final double aDownbDown = BIVARIATE_NORMAL.getCDF(new double[] {a - eps, b - eps, rho });
    final double aUpbDown = BIVARIATE_NORMAL.getCDF(new double[] {a + eps, b - eps, rho });
    final double aDownbUp = BIVARIATE_NORMAL.getCDF(new double[] {a - eps, b + eps, rho });

    //1st
    double fd = (aUp - aDown) / 2 / eps;
    assertEquals("dB/da", fd, sense[0], Math.abs(fd) * 1e-5);
    fd = (bUp - bDown) / 2 / eps;
    assertEquals("dB/db", fd, sense[1], Math.abs(fd) * 1e-5);
    //2nd
    fd = (aUp + aDown - 2 * cent) / eps / eps;
    assertEquals("d^2B/da^2", fd, sense[2], Math.abs(fd) * 1e-4);
    fd = (bUp + bDown - 2 * cent) / eps / eps;
    assertEquals("d^2B/db^2", fd, sense[3], Math.abs(fd) * 1e-5);
    fd = (aUpbUp + aDownbDown - aUpbDown - aDownbUp) / 4 / eps / eps;
    assertEquals("d^2B/dadb", fd, sense[4], Math.abs(fd) * 2e-4);
  }

  @Test
  public void alphaTest() {
    final double k = 123;
    final double x = 204;
    final double beta = 2.3;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();

    final double[] sense = bs.getAlphaAdjoint(k, x, beta);
    final double[] parms = new double[] {k, x, beta };
    final int n = parms.length;
    final double eps = 1e-5;
    for (int i = 0; i < n; i++) {
      final double[] temp = Arrays.copyOf(parms, n);
      temp[i] += eps;
      final double up = bs.getAlphaAdjoint(temp[0], temp[1], temp[2])[0];
      temp[i] -= 2 * eps;
      final double down = bs.getAlphaAdjoint(temp[0], temp[1], temp[2])[0];
      final double fd = (up - down) / 2 / eps;
      assertEquals(fd, sense[i + 1], Math.abs(fd) * 1e-8);
    }
  }

  @Test
  public void betaTest() {
    final double r = 0.1;
    final double b = 0.04;
    final double sigma = 0.3;
    final double sigmaSq = sigma * sigma;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();

    final double[] sense = bs.getBetaAdjoint(r, b, sigmaSq);
    final double[] parms = new double[] {r, b, sigmaSq };
    final int n = parms.length;
    final double eps = 1e-5;
    for (int i = 0; i < n; i++) {
      final double[] temp = Arrays.copyOf(parms, n);
      temp[i] += eps;
      final double up = bs.getBetaAdjoint(temp[0], temp[1], temp[2])[0];
      temp[i] -= 2 * eps;
      final double down = bs.getBetaAdjoint(temp[0], temp[1], temp[2])[0];
      final double fd = (up - down) / 2 / eps;

      assertEquals(fd, sense[i + 1], Math.abs(fd) * 1e-8);
    }
  }

  @Test
  public void lambdaTest() {
    final double[] gammaSet = new double[] {0, 1, 0.9, 2.3 };
    final double r = 0.1;
    final double[] bSet = {-0.03, 0, 0.04, r };
    final double sigma = 0.3;
    final double sigmaSq = sigma * sigma;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();

    for (final double gamma : gammaSet) {
      for (final double b : bSet) {

        final double[] sense = bs.getLambdaAdjoint(gamma, r, b, sigmaSq);
        final double[] parms = new double[] {gamma, r, b, sigmaSq };
        final int n = parms.length;
        final double eps = 1e-5;
        for (int i = 0; i < n; i++) {
          final double[] temp = Arrays.copyOf(parms, n);
          temp[i] += eps;
          final double up = bs.getLambdaAdjoint(temp[0], temp[1], temp[2], temp[3])[0];
          temp[i] -= 2 * eps;
          final double down = bs.getLambdaAdjoint(temp[0], temp[1], temp[2], temp[3])[0];
          final double fd = (up - down) / 2 / eps;
          assertEquals(fd, sense[i + 1], Math.abs(fd) * 1e-8);
        }
      }
    }
  }

  @Test
  public void kappaTest() {
    final double[] gammaSet = new double[] {0, 1, 0.9, 2.3 };
    final double[] bSet = {-0.03, 0, 0.04 };
    final double sigma = 0.3;
    final double sigmaSq = sigma * sigma;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();

    for (final double gamma : gammaSet) {
      for (final double b : bSet) {

        final double[] sense = bs.getKappaAdjoint(gamma, b, sigmaSq);
        final double[] parms = new double[] {gamma, b, sigmaSq };
        final int n = parms.length;
        final double eps = 1e-5;
        for (int i = 0; i < n; i++) {
          final double[] temp = Arrays.copyOf(parms, n);
          temp[i] += eps;
          final double up = bs.getKappaAdjoint(temp[0], temp[1], temp[2])[0];
          temp[i] -= 2 * eps;
          final double down = bs.getKappaAdjoint(temp[0], temp[1], temp[2])[0];
          final double fd = (up - down) / 2 / eps;
          assertEquals(fd, sense[i + 1], Math.abs(fd) * 2e-8);
        }
      }
    }
  }

  @Test
  public void i1Test() {
    final double k = 100.04;
    final double[] rSet = {-0.03, 0.1 };
    final double[] bb = new double[] {-0.03, 0.04 };
    final double sigma = 0.3;
    final double t = 0.3;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();

    for (final double r : rSet) {
      for (final double b : bb) {

        if (r >= b) {

          final double[] sense = bs.getI1Adjoint(k, r, b, sigma, t);
          //System.out.println(sense[0]);
          final double[] parms = new double[] {k, r, b, sigma, t };
          final int n = parms.length;
          final double eps = 1e-5;
          for (int i = 0; i < n; i++) {
            final double[] temp = Arrays.copyOf(parms, n);
            temp[i] += eps;
            final double up = bs.getI1Adjoint(temp[0], temp[1], temp[2], temp[3], temp[4])[0];
            temp[i] -= 2 * eps;
            final double down = bs.getI1Adjoint(temp[0], temp[1], temp[2], temp[3], temp[4])[0];
            final double fd = (up - down) / 2 / eps;
            //System.out.println(up + "\t" + down);
            assertEquals(i + "\t" + r + "\t" + b, fd, sense[i + 1], Math.abs(fd) * 1e-5);
          }
        }
      }
    }
  }

  @Test
  public void i2Test() {
    final double k = 100.04;
    final double r = 0.1;
    final double[] bb = new double[] {-0.03, 0.04 };
    final double sigma = 0.3;
    final double t = 1.3;

    final BjerksundStenslandModel bs = new BjerksundStenslandModel();

    for (final double b : bb) {

      final double[] sense = bs.getI2Adjoint(k, r, b, sigma, t);
      final double[] parms = new double[] {k, r, b, sigma, t };
      final int n = parms.length;
      final double eps = 1e-5;
      for (int i = 0; i < n; i++) {
        final double[] temp = Arrays.copyOf(parms, n);
        temp[i] += eps;
        final double up = bs.getI2Adjoint(temp[0], temp[1], temp[2], temp[3], temp[4])[0];
        temp[i] -= 2 * eps;
        final double down = bs.getI2Adjoint(temp[0], temp[1], temp[2], temp[3], temp[4])[0];
        final double fd = (up - down) / 2 / eps;
        assertEquals(fd, sense[i + 1], Math.abs(fd) * 1e-7);
      }
    }
  }

  @Test
  public void phiDeltaTest() {
    final BjerksundStenslandModel bs = new BjerksundStenslandModel();
    final double s0 = 100;
    final double[] x2Set = new double[] {130, 150, 170 };
    final double r = 0.1;
    final double[] bSet = new double[] {-0.04, 0.0, 0.04, r };
    final double t1 = 0.5;
    final double sigma = 0.35;

    final double t = 2 * t1 / (Math.sqrt(5) - 1);
    final double[] gammaSet = new double[] {0, 1, 0.67, 1.87 };
    final double x1 = 133.0;

    final double eps = s0 * 1e-5;

    for (final double x2 : x2Set) {
      for (final double b : bSet) {
        for (final double gamma : gammaSet) {

          final double phi = bs.getPhi(s0, t1, gamma, x1, x2, r, b, sigma);
          final double[] sense = bs.getPhiDelta(s0, t, gamma, x1, x2, r, b, sigma);
          assertEquals(phi, sense[0], Math.abs(phi) * 1e-15);
          final double up = bs.getPhi(s0 + eps, t1, gamma, x1, x2, r, b, sigma);
          final double down = bs.getPhi(s0 - eps, t1, gamma, x1, x2, r, b, sigma);
          final double fd = (up - down) / 2 / eps;
          final double fd2 = (up + down - 2 * phi) / eps / eps;
          assertEquals(fd, sense[1], Math.abs(fd) * 2e-8);
          assertEquals(fd2, sense[2], Math.abs(fd2) * 1e-5);
        }
      }
    }
  }
}
