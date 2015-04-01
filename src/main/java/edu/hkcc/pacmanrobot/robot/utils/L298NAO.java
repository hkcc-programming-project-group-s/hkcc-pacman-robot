package edu.hkcc.pacmanrobot.robot.utils;

import com.pi4j.io.gpio.*;
import edu.hkcc.pacmanrobot.utils.maths.Point2D;


import static edu.hkcc.pacmanrobot.utils.Maths.*;

/**
 * Created by beenotung on 3/3/15.
 */
public class L298NAO {
    public static GpioController gpio = GpioFactory.getInstance();
    public static final GpioPinPwmOutput R_F = gpio.provisionPwmOutputPin(RaspiPin.GPIO_21);
    public static final GpioPinPwmOutput R_B = gpio.provisionPwmOutputPin(RaspiPin.GPIO_22);
    public static final GpioPinPwmOutput L_B = gpio.provisionPwmOutputPin(RaspiPin.GPIO_23);
    public static final GpioPinPwmOutput L_F = gpio.provisionPwmOutputPin(RaspiPin.GPIO_24);
    public static final GpioPinDigitalOutput E_R = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04);
    public static final GpioPinDigitalOutput E_L = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05);

    public static void enableMotor(GpioPinDigitalOutput pin, boolean on) {
        if ((on) && pin.isLow()) pin.high();
        else if ((!on) && pin.isHigh()) pin.low();
    }

    public static void left_on() {
        enableMotor(E_L, true);
    }

    public static void left_off() {
        enableMotor(E_L, false);
    }

    public static void right_on() {
        enableMotor(E_R, true);
    }

    public static void right_off() {
        enableMotor(E_R, false);
    }



    @Deprecated
    public static void setGpio(GpioPinDigitalOutput gpio, boolean isHigh) {
        if (isHigh)
            gpio.high();
        else
            gpio.low();
    }

    public static void setTwoGpio(GpioPinDigitalOutput gpio1, GpioPinDigitalOutput gpio2, boolean isHigh) {
        if (isHigh) {
            gpio1.high();
            gpio2.high();
        } else {
            gpio1.low();
            gpio2.low();
        }
    }


    public static void setGpioPair_FT(GpioPinDigitalOutput falseGpio, GpioPinDigitalOutput trueGpio) {
        if (falseGpio.isHigh()) falseGpio.low();
        if (trueGpio.isLow()) trueGpio.high();
    }



    public static void move(Point2D point2D) {
        move(point2D._1(), point2D._2());
    }

    public static void move_pwm(Point2D point2D) {
        move_pwm(point2D._1(), point2D._2());
    }

    public static void move_pwm(double direction, double power) {
        if (power <= 0) {
            both_stop();
            return;
        }
        double l, r;
        if (isInRange(direction, F_R)) {
            l=1;
            r=0;
        } else if (isInRange(direction, R)) {
            l=1;
            r=-1;
        } else if (isInRange(direction, B_R)) {
            l=-1;
            r=0;
        } else if (isInRange(direction, B)) {
            l=r=-1;
        } else if (isInRange(direction, B_L)) {
            l=0;
            r=-1;
        } else if (isInRange(direction, L)) {
            l=-1;
            r=1;

        } else if (isInRange(direction, F_L)) {
            l=0;
            r=1;
        } else //if (Maths.isInRange(direction,F))
        {
            l=r=1;
        }
        l*=power;
        r*=power;
        if(l>0)L_F.setPwm((int) Math.round(l*255));
        if(r>0)R_F.setPwm((int) Math.round(l*255));
    }



}