package edu.hkcc.pacmanrobot.robot.utils; /**
 * Class to monitor distance measured by an HC-SR04 distance sensor on a 
 * Raspberry Pi.
 *
 * The main method assumes the trig pin is connected to the pin # 7 and the echo
 * pin is connected to pin # 11.  Output of the program are comma separated lines
 * where the first value is the number of milliseconds since unix epoch, and the
 * second value is the measured distance in centimeters.
 */

import com.pi4j.io.gpio.*;

import static java.lang.System.nanoTime;
import static java.time.Instant.now;

/**
 * UltrasonicAO class to monitor distance measured by sensor
 *
 * @reference Rutger Claes <rutger.claes@cs.kuleuven.be>
 */
public class SRF05AO {

    private final static float DURATION_RATIO = 340.29f / (2 * 10000);  // speed of sound in m/s

    private final static int TRIG_DURATION_IN_MICROS = 20; // min trigger duration of 10 micro s
    private final static int ECHO_DELAY_TIMEOUT_IN_MILLIS = 600; // max interval between trigger and echo of 50 milli s
    private final static int ECHO_DURATION_TIMEOUT_IN_MILLIS = 60; // max echo duration of 30 milli s

    private final static int WAIT_CYCLE_DURATION_IN_MILLIS = 60; // wait 60 milli s  (between each distance measure)

    private final static GpioController gpio = GpioFactory.getInstance();

    private final GpioPinDigitalOutput trigPin;
    private final GpioPinDigitalInput echoPin;

    private SRF05AO(Pin trigPin, Pin echoPin) {
        this.trigPin = gpio.provisionDigitalOutputPin(trigPin);
        this.echoPin = gpio.provisionDigitalInputPin(echoPin);
        this.trigPin.low();
    }

    public static void main(String[] args) {
        Pin trigPin = RaspiPin.GPIO_14; // PI4J custom numbering (pin 7)
        Pin echoPin = RaspiPin.GPIO_15; // PI4J custom numbering (pin 11)
        SRF05AO monitor = new SRF05AO(trigPin, echoPin);

        while (true) {
            try {
                System.out.println("\nstart measure: " + now());
                System.out.printf("%1$d,%2$.3f%n", System.currentTimeMillis(), monitor.measureDistance());
            } catch (TimeoutException e) {
                System.err.println(e);
            }

            try {
                Thread.sleep(WAIT_CYCLE_DURATION_IN_MILLIS);
            } catch (InterruptedException ex) {
                System.err.println("Interrupt during trigger");
            }
        }
    }

    /*
     * This method returns the distance measured by the sensor in cm
     *
     * @throws TimeoutException if a timeout occurs
     */
    public float measureDistance() throws TimeoutException {
        this.triggerSensor();
        this.waitForSignal();
        long duration = this.measureSignal();

        return duration * DURATION_RATIO;
    }

    /**
     * Put a high on the trig pin for TRIG_DURATION_IN_MICROS
     */
    private void triggerSensor() {
        try {
            this.trigPin.high();
            Thread.sleep(0, TRIG_DURATION_IN_MICROS * 1000);
            this.trigPin.low();
        } catch (InterruptedException ex) {
            System.err.println("Interrupt during trigger");
        }
        //trigPin.pulse(TRIG_DURATION_IN_MICROS*1000, PinState.HIGH);
    }

    /**
     * Wait for a high on the echo pin
     *
     * @throws SRF05AO.TimeoutException if no high appears in time
     */
    private void waitForSignal() throws TimeoutException {
        long start = nanoTime();
        long end = start + ECHO_DELAY_TIMEOUT_IN_MILLIS * 1000 * 1000;
        while (this.echoPin.isLow() && nanoTime() < end) {
        }
        end = nanoTime();

        if ((end - start) > ECHO_DELAY_TIMEOUT_IN_MILLIS * 1000 * 1000) {
            //if (this.echoPin.isLow()) {
            throw new TimeoutException("Timeout waiting for signal start: " + (end - start));
        }
    }

    /**
     * @return the duration of the signal in micro seconds
     * @throws SRF05AO.TimeoutException if no low appears in time
     */
    private long measureSignal() throws TimeoutException {
        long start = nanoTime();
        long end = start + ECHO_DURATION_TIMEOUT_IN_MILLIS * 1000 * 1000;
        while (this.echoPin.isHigh() && nanoTime() < end) {
        }
        end = nanoTime();

        //if (this.echoPin.isHigh()) {
        if ((end - start) > ECHO_DURATION_TIMEOUT_IN_MILLIS * 1000 * 1000) {
            throw new TimeoutException("Timeout waiting for signal end: " + (end - start));
        }

        return (long) Math.ceil((end - start) / 1000.0);  // Return micro seconds
    }

    /**
     * Exception thrown when timeout occurs
     */
    private static class TimeoutException extends Exception {

        private final String reason;

        public TimeoutException(String reason) {
            this.reason = reason;
        }

        @Override
        public String toString() {
            return this.reason;
        }
    }
}
