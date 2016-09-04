/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.math.BigDecimal;



/**
 * Clase para resolver la encuación cuadrática
 * @author extmchlfchacon
 */
public class Ecuaciones {
   


    private double a = 0;
    private double b = 0;
    private double c = 0;
    double real = 0;
    double imag = 0;
    private double x;
    private double x1;
    private double x2;

    /**
     * Calcular la fórmula de la ecuación de segundo grado
     */
    public void raizcuadratica() {
        BigDecimal raiz = new BigDecimal(Math.pow(b, 2.0) - 4 * a * c);
              if (raiz.compareTo(BigDecimal.ZERO)==-1) {
            real = -b / (2 * a);
            imag = Math.sqrt(Math.abs(Math.pow(b, 2.0) - 4 * a * c)) / (2 * a);


        } else {
            setX1((-b + Math.sqrt(Math.pow(b, 2.0) - 4 * a * c)) / (2 * a));
            setX2((-b - Math.sqrt(Math.pow(b, 2.0) - 4 * a * c)) / (2 * a));


        }
       

    }

    @Override
    public String toString() {
        if (imag != 0) {
            return String.valueOf(real) + " + " + String.valueOf(imag) + "i";
        } else {
            return "";
        }
    }

    /**
     * @return the a
     */
    public double getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(double a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public double getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(double b) {
        this.b = b;
    }

    /**
     * @return the c
     */
    public double getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(double c) {
        this.c = c;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the x1
     */
    public double getX1() {
        return x1;
    }

    /**
     * @param x1 the x1 to set
     */
    public void setX1(double x1) {
        this.x1 = x1;
    }

    /**
     * @return the x2
     */
    public double getX2() {
        return x2;
    }

    /**
     * @param x2 the x2 to set
     */
    public void setX2(double x2) {
        this.x2 = x2;
    }

   

   





  










}
