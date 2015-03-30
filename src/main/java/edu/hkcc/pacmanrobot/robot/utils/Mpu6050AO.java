package edu.hkcc.pacmanrobot.robot.utils;//reference python demo http://blog.bitify.co.uk/2013/11/reading-data-from-mpu-6050-on-raspberry.html

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import edu.hkcc.pacmanrobot.utils.Maths.Point3D;

import java.io.IOException;

import static edu.hkcc.pacmanrobot.utils.Maths.length;

public class Mpu6050AO extends Thread {
    public static final int power_mgmt_1 = 0x6b;
    public static final int power_mgmt_2 = 0x6c;
    private static final long ONE_SECOND = 1000000000L;
    public static double bg_z = 0;
    private static long TIME0;
    private static I2CBus bus;
    private static I2CDevice mpu6050;
    public double accel_xout_scaled, accel_yout_scaled, accel_zout_scaled;
    public double gyro_xout, gyro_yout, gyro_zout;
    public Point3D acceleration,angularAcceleration,rotation,displacement;
    private Timer gyroTimer = new Timer();
    private Timer accelTimer = new Timer();
    private double bufferDouble;



    public static double getXRotation(double x, double y, double z) {
        double rad = Math.atan2(x, length(y, z));
        return -Math.toDegrees(rad);
    }

    public static double getYRotation(double x, double y, double z) {
        double rad = Math.atan2(y, length(x, z));
        return -Math.toDegrees(rad);
    }

    public static byte readByte(int addr) throws IOException {
        return (byte) mpu6050.read(addr);
    }

    public void setup() throws IOException {
        bus = I2CFactory.getInstance(I2CBus.BUS_1);
        mpu6050 = bus.getDevice(0x68);
        mpu6050.write(power_mgmt_1, (byte) 0x00);
        long last_time = 0;
        long now;
        TIME0 = System.nanoTime();
        long TIME1;
        while (((TIME1 = System.nanoTime()) - TIME0) < 2147483647) {
            writeMpu6050((byte) 0x01);
            bg_z += 1.0 * readWord2C(0x47);
            writeMpu6050((byte) 0xff);
        }
        bg_z /= 1.0 * TIME1 - TIME0;
        System.out.println("bg_z=" + bg_z);
    }
//
//    private String printGyro() {
//        String msg = "\tgyro_xout: " + gyro_xout + "    \tscaled: " + String.format("%.2f    \taccum_scaled%8.2f", gyro_xout / 131d, real(r_x) / 131d);
//        msg += "\n\tgyro_yout: " + gyro_yout + "    \tscaled: " + String.format("%.2f    \taccum_scaled%8.2f", gyro_yout / 131d, r_y / 131d);
//        msg += "\n\tgyro_zout: " + gyro_zout + "    \tscaled: " + String.format("%.2f    \taccum_scaled%8.2f", gyro_zout / 131d, r_z / 131d);
//        //System.out.println(msg);
//        return msg;
//    }
//
//    private String printAccel() {
//        String msg = "\taccel_xout: " + accel_xout + "    \tscaled: " + String.format("%.2f", accel_xout_scaled);
//        msg += "\n\taccel_yout: " + accel_yout + "    \tscaled: " + String.format("%.2f", accel_yout_scaled);
//        msg += "\n\taccel_zout: " + accel_zout + "    \tscaled: " + String.format("%.2f", accel_zout_scaled);
//        //System.out.println(msg);
//        return msg;
//    }
//
//    private String printRotation() {
//        String msg = "\tx rotation: " + String.format("%.2f", getXRotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled));
//        msg += "\n\ty rotation: " + String.format("%.2f", getYRotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled));
//        //System.out.println(msg);
//        return msg;
//    }

    public void loop() throws IOException {
        //String msg = "";
        writeMpu6050((byte) 0x01);
        readGyro();
        readAccel();
        writeMpu6050((byte) 0xff);
        //msg += printGyro();
        //msg += "\n" + bg_z + "\n";
        //msg += "\n\n" + mpu6050AO.printAccel();
        //msg += "\n\n" + mpu6050AO.printRotation();
        //clearConsole();
        //for (int i = 0; i < 35; i++)            msg = "\n" + msg;
        //System.out.print(msg);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private double real(double shiftedValue, long time1) {
        return shiftedValue - bg_z * (time1 - TIME0);
    }

    public void readAccel() throws IOException {
        accel_xout = readWord2C(0x3b);
        accel_yout = readWord2C(0x3d);
        accel_zout = readWord2C(0x3f);
        accelTimer.getDelta();
        if(accelTimer.last!=0){

        }
        accel_xout_scaled = accel_xout / 16384.0;
        accel_yout_scaled = accel_yout / 16384.0;
        accel_zout_scaled = accel_zout / 16384.0;
    }

    public void readGyro() throws IOException {
        gyro_xout = readWord2C(0x43);
        gyro_yout = readWord2C(0x45);
        gyro_zout = readWord2C(0x47);
        gyroTimer.getDelta();
        if (gyroTimer.last != 0) {
            bufferDouble = 1.0 * gyroTimer.delta * ONE_SECOND;
            r_x += gyro_xout / bufferDouble;
            r_y += gyro_yout / bufferDouble;
            r_z += gyro_zout / bufferDouble;
        }
    }

    public void writeMpu6050(byte data) throws IOException {
        mpu6050.write(0x0d, data);
    }

    public short readWord(int addr) throws IOException {
        byte hight = readByte(addr);
        byte low = readByte(addr + 1);
        return (short) ((hight << 8) + low);
    }

    public int readWord2C(int addr) throws IOException {
        short value = readWord(addr);
        if (value >= 0x8000)
            return -((65535 - value) + 1);
        else return value;
    }

    @Override
    public void run() {
        System.out.println("Mpu6050AO start");
        try {
            setup();
            while (true) {
                loop();
            }
        } catch (IOException e) {
        }
        System.out.println("Mpu6050AO end");

    }

    private class Timer {
        public long last=0, now=0, delta;

        public long getDelta() {
            last=now;
            delta = (now = System.nanoTime()) - last;
            return delta;
        }
    }
}