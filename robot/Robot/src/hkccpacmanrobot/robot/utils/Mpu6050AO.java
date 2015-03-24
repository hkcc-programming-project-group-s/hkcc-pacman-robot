//reference python demo http://blog.bitify.co.uk/2013/11/reading-data-from-mpu-6050-on-raspberry.html

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

public class Mpu6050AO {

    public static final int power_mgmt_1 = 0x6b;
    public static final int power_mgmt_2 = 0x6c;
    private static I2CBus bus;
    private static I2CDevice mpu6050;
    public double accel_xout_scaled, accel_yout_scaled, accel_zout_scaled;
    public int gyro_xout, gyro_yout, gyro_zout;
    public int accel_xout, accel_yout, accel_zout;

    public final static void goto11() {
        System.out.print((char) 27 + "[1;1H");
    }

    public final static void clearConsole() {
        System.out.print((char) 27 + "[2J");
        goto11();
    }

    public static void main(String[] args) {
        System.out.println("Hello, PI");
        try {
            Mpu6050AO mpu6050AO = new Mpu6050AO();
            bus = I2CFactory.getInstance(I2CBus.BUS_1);
            mpu6050 = bus.getDevice(0x68);
            mpu6050AO.writeMpu6050((byte) 0xff);
            mpu6050.write(power_mgmt_1, (byte) 0x00);
            while (true) {
                String msg="";
                mpu6050AO.readGyro();
                mpu6050AO.readAccel();
                msg+=mpu6050AO.printGyro();
                msg+="\n\n"+mpu6050AO.printAccel();
                msg+="\n\n"+mpu6050AO.printRotation();
                clearConsole();
                for(int i=0;i<35;i++)
                    msg="\n"+msg;
                System.out.print(msg);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
        }
        System.out.println("Bye, PI");
    }

    public static double dist(double a, double b) {
        return Math.sqrt((a * a) + (b * b));
    }

    public static double getYRotation(double x, double y, double z) {
        double rad = Math.atan2(x, dist(y, z));
        return -Math.toDegrees(rad);
    }

    public static double getXRotation(double x, double y, double z) {
        double rad = Math.atan2(y, dist(x, z));
        return -Math.toDegrees(rad);
    }

    private String printGyro() {
        String msg = "\tgyro_xout: " + gyro_xout + "    \tscaled: " + String.format("%.2f", (gyro_xout / 131d));
        msg += "\n\tgyro_yout: " + gyro_yout + "    \tscaled: " + String.format("%.2f", (gyro_yout / 131d));
        msg += "\n\tgyro_zout: " + gyro_zout + "    \tscaled: " + String.format("%.2f", (gyro_zout / 131d));
        //System.out.println(msg);
        return msg;
    }

    private String printAccel() {
        String msg = "\taccel_xout: " + accel_xout + "    \tscaled: " + String.format("%.2f", accel_xout_scaled);
        msg += "\n\taccel_yout: " + accel_yout + "    \tscaled: " + String.format("%.2f", accel_yout_scaled);
        msg += "\n\taccel_zout: " + accel_zout + "    \tscaled: " + String.format("%.2f", accel_zout_scaled);
        //System.out.println(msg);
        return msg;
    }

    private String printRotation() {
        String msg = "\tx rotation: " + String.format("%.2f", getXRotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled));
        msg += "\n\ty rotation: " + String.format("%.2f", getYRotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled));
        //System.out.println(msg);
        return msg;
    }

    public void readAccel() throws IOException {
        accel_xout = readWord2C(0x3b);
        accel_yout = readWord2C(0x3d);
        accel_zout = readWord2C(0x3f);
        accel_xout_scaled = accel_xout / 16384.0;
        accel_yout_scaled = accel_yout / 16384.0;
        accel_zout_scaled = accel_zout / 16384.0;
    }

    public void readGyro() throws IOException {
        gyro_xout = readWord2C(0x43);
        gyro_yout = readWord2C(0x45);
        gyro_zout = readWord2C(0x47);
    }

    public void writeMpu6050(byte data) throws IOException {
        mpu6050.write(0x0d, data);
    }

    public byte readByte(int addr) throws IOException {
        return (byte) mpu6050.read(addr);
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

}
