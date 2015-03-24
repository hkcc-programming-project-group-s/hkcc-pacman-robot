package hkccpacmanrobot.robot.utils;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Created by beenotung on 3/3/15.
 */
public class L298NAO {
    public static GpioController gpio = GpioFactory.getInstance();
    public static final GpioPinDigitalOutput R_F = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21);
    public static final GpioPinDigitalOutput R_B = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22);
    public static final GpioPinDigitalOutput L_F = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23);
    public static final GpioPinDigitalOutput L_B = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24);
    public static boolean motorRightForward = false;
    public static boolean motorRightBackward = true;
    public static boolean motorLeftForward = true;
    public static boolean motorLeftBackward = false;

    public static void checkMotorRightForward() {
        System.out.println("checkMotorRightForward");
        if (motorRightForward)
            R_F.high();
        else
            R_F.low();
    }

    public static void checkMotorRightBackward() {
        System.out.println("checkMotorRightBackward");
        if (motorRightBackward)
            R_B.high();
        else
            R_B.low();
    }

    public static void checkMotorLeftForward() {
        System.out.println("checkMotorLeftForward");
        if (motorLeftForward)
            L_F.high();
        else
            L_F.low();
    }

    public static void checkMotorLeftBackward() {
        System.out.println("checkMotorLeftBackward");
        if (motorLeftBackward)
            L_B.high();
        else
            L_B.low();
    }

    public static void main(String[] args) {
        System.out.println("HI, PI");

        do {
            System.out.println("\n"+System.currentTimeMillis());
            motorRightForward = !motorRightForward;
            motorRightBackward = !motorRightBackward;
            motorLeftForward = !motorLeftForward;
            motorLeftBackward = !motorLeftBackward;
            checkMotorRightForward();
            checkMotorRightBackward();
            checkMotorLeftForward();
            checkMotorLeftBackward();
            try {
                System.out.println("sleep 2000");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

}
