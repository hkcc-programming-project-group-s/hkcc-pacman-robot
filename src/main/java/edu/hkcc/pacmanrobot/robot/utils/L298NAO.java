package edu.hkcc.pacmanrobot.robot.utils;

import com.pi4j.io.gpio.*;
import com.pi4j.wiringpi.SoftPwm;
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
    public static int PWM_MAX_RANGE = 100;

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

    public static void move_pwm(Point2D point2D) {
        move_pwm(point2D._1(), (int) Math.round(point2D._2() * 100));
    }

    public static void setPwm(GpioPinDigitalOutput enablePin, GpioPinPwmOutput forwardPin, GpioPinPwmOutput backwardPin, int pwm) {
        if (pwm == 0) {
            if (enablePin.isHigh())
                enablePin.low();
            backwardPin.setPwm(pwm);
            forwardPin.setPwm(pwm);
        } else {
            if (enablePin.isLow())
                enablePin.high();
            if (pwm > 0) {
                backwardPin.setPwm(0);
                forwardPin.setPwm(pwm);
            } else {
                forwardPin.setPwm(0);
                backwardPin.setPwm(-pwm);
            }
        }
    }

    /**
     * @param direction (radian)
     *                  0 to PI*2
     * @param pwm
     *                  0 to 1
     */
    public static void move_pwm(double direction, double pwm) {
        if (pwm == 0) {
            both_stop();
            return;
        }
        int l, r;
        if (isInRange(direction, F_R)) {
            l = 1;
            r = 0;
        } else if (isInRange(direction, R)) {
            l = 1;
            r = -1;
        } else if (isInRange(direction, B_R)) {
            l = -1;
            r = 0;
        } else if (isInRange(direction, B)) {
            l = r = -1;
        } else if (isInRange(direction, B_L)) {
            l = 0;
            r = -1;
        } else if (isInRange(direction, L)) {
            l = -1;
            r = 1;

        } else if (isInRange(direction, F_L)) {
            l = 0;
            r = 1;
        } else //if (Maths.isInRange(direction,F))
        {
            l = r = 1;
        }
        l *= (pwm * PWM_MAX_RANGE);
        r *= (pwm * PWM_MAX_RANGE);
        left_pwm(l);
        right_pwm(l);
    }

    /**
     * @param pwm direction -100 to 100
     *            -100 = full speed backward
     *            100 = full sped forward
     *            0 = stop
     */
    public static void right_pwm(int pwm) {
        setPwm(E_R, R_F, R_B, pwm);
    }

    /**
     * @param pwm direction -100 to 100
     *            -100 = full speed backward
     *            100 = full sped forward
     *            0 = stop
     */
    public static void left_pwm(int pwm) {
        setPwm(E_L, L_F, L_B, pwm);
    }

    public static void both_stop() {
        left_off();
        right_off();
    }

    public static boolean ready=false;
    public static void setup(){
        SoftPwm.softPwmCreate()
        public static final GpioPinPwmOutput R_F = gpio.provisionPwmOutputPin(RaspiPin.GPIO_21);
        public static final GpioPinPwmOutput R_B = gpio.provisionPwmOutputPin(RaspiPin.GPIO_22);
        public static final GpioPinPwmOutput L_B = gpio.provisionPwmOutputPin(RaspiPin.GPIO_23);
        public static final GpioPinPwmOutput L_F = gpio.provisionPwmOutputPin(RaspiPin.GPIO_24);
        ready=true;
    }
}