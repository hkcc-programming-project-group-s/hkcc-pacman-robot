package studentrobot.code;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

import static studentrobot.code.Maths.*;

/**
 * Created by beenotung on 3/3/15.
 */
public class L298NAO {
    public static GpioController gpio = GpioFactory.getInstance();
    public static final GpioPinDigitalOutput R_F = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21);
    public static final GpioPinDigitalOutput R_B = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22);
    public static final GpioPinDigitalOutput L_B = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
    public static final GpioPinDigitalOutput L_F = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24);

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


    public static void setGpioPair_FT(GpioPinDigitalOutput falseGpio, GpioPinDigitalOutput trueGpio) {
        if (falseGpio.isHigh()) falseGpio.low();
        if (trueGpio.isLow()) trueGpio.high();
    }

    public static void both_stop() {
        left_stop();
        right_stop();
    }

    public static void move(double direction, double distance) {
        //System.out.printf("motor move: \t%.2f,\t%.2f\n", direction, distance);
        if (distance <= 0) {
            both_stop();
            return;
        }
        if (isInRange(direction, B_L) || isInRange(direction, B) || isInRange(direction, R)) left_backward();
        else if (isInRange(direction, F_R) || isInRange(direction, B_R)) left_stop();
        else left_forward();
        if (isInRange(direction, L) || isInRange(direction, B) || isInRange(direction, B_R)) right_backward();
        else if (isInRange(direction, F_L) || isInRange(direction, B_L)) right_stop();
        else right_forward();
    }

}
