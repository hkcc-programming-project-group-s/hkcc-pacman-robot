import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Created by beenotung on 3/3/15.
 */
public class L298NAO {
    public static GpioController gpio = GpioFactory.getInstance();
    public static final GpioPinDigitalOutput R_F = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02);
    public static final GpioPinDigitalOutput R_B = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03);

    public static void main(String[] args) {
        System.out.println("HI, PI");
        boolean motorRightForward = false;
        boolean motorRightBackward = true;
        do {
            motorRightForward = !motorRightForward;
            motorRightBackward = !motorRightBackward;
            if (motorRightForward)
                R_F.high();
            else
                R_F.low();
            if (motorRightBackward)
                R_B.high();
            else
                R_B.low();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);
    }

}
