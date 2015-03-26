package hkccpacmanrobot.robot.utils;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

import static hkccpacmanrobot.utils.Maths.*;

/**
 * Created by beenotung on 3/3/15.
 */
public class L298NAO {
    public static GpioController gpio = GpioFactory.getInstance();
    public static final GpioPinDigitalOutput R_F = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21);
    public static final GpioPinDigitalOutput R_B = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22);
    public static final GpioPinDigitalOutput L_F = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
    public static final GpioPinDigitalOutput L_B = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24);

    public static void left_forward() {
        setGpioPair_FT(L_B, L_F);
    }

    public static void left_backward() {
        setGpioPair_FT(L_F, L_B);
    }

    public static void left_stop() {
        setTwoGpio(L_F, L_B, false);
    }

    public static void right_forward() {
        setGpioPair_FT(R_B, R_F);
    }

    public static void right_backward() {
        setGpioPair_FT(R_F, R_B);
    }

    public static void right_stop() {
        setTwoGpio(R_F, R_B, false);
    }


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

    public static void setGpioPair_TF(GpioPinDigitalOutput trueGpio, GpioPinDigitalOutput falseGpio) {
        trueGpio.high();
        falseGpio.low();
    }

    public static void setGpioPair_FT(GpioPinDigitalOutput falseGpio, GpioPinDigitalOutput trueGpio) {
        falseGpio.low();
        trueGpio.high();
    }

    public static void move(double direction, double distance) {
        if (distance <= 0) {
            left_stop();
            right_stop();
            return;
        }
        if (isForward(direction)) {
            left_forward();
            right_forward();
        } else if (isBackward(direction)) {
            left_backward();
            right_backward();
        } else if (isForwardRight(direction)) {
            left_backward();
            right_stop();
        } else if (isBackwardRight(direction)) {
            left_backward();
            right_stop();
        } else if (isBackwardLeft(direction)) {
            left_stop();
            right_backward();
        } else {
            left_stop();
            right_forward();
        }
    }


}
