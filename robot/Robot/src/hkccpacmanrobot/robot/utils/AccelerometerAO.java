package hkccpacmanrobot.robot.utils;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;

public class AccelerometerAO {

   /**
    * Trying to implement the I2C connection 
    * implemented on ADXL345 Adafruit code
    */
   
   
   private I2CDevice device;
   
   

   /*
    * Adress uit Arduino code
    * 
    * 
    */
   /*
    *  REGISTERS
    -----------------------------------------------------------------------
    #define ADXL345_REG_DEVID               (0x00)    // Device ID
    #define ADXL345_REG_THRESH_TAP          (0x1D)    // Tap threshold
    #define ADXL345_REG_OFSX                (0x1E)    // X-axis offset
    #define ADXL345_REG_OFSY                (0x1F)    // Y-axis offset
    #define ADXL345_REG_OFSZ                (0x20)    // Z-axis offset
    #define ADXL345_REG_DUR                 (0x21)    // Tap duration
    #define ADXL345_REG_LATENT              (0x22)    // Tap latency
    #define ADXL345_REG_WINDOW              (0x23)    // Tap window
    #define ADXL345_REG_THRESH_ACT          (0x24)    // Activity threshold
    #define ADXL345_REG_THRESH_INACT        (0x25)    // Inactivity threshold
    #define ADXL345_REG_TIME_INACT          (0x26)    // Inactivity time
    #define ADXL345_REG_ACT_INACT_CTL       (0x27)    // Axis enable control for activity and inactivity detection
    #define ADXL345_REG_THRESH_FF           (0x28)    // Free-fall threshold
    #define ADXL345_REG_TIME_FF             (0x29)    // Free-fall time
    #define ADXL345_REG_TAP_AXES            (0x2A)    // Axis control for single/double tap
    #define ADXL345_REG_ACT_TAP_STATUS      (0x2B)    // Source for single/double tap
    #define ADXL345_REG_BW_RATE             (0x2C)    // Data rate and power mode control
    #define ADXL345_REG_POWER_CTL           (0x2D)    // Power-saving features control
    #define ADXL345_REG_INT_ENABLE          (0x2E)    // Interrupt enable control
    #define ADXL345_REG_INT_MAP             (0x2F)    // Interrupt mapping control
    #define ADXL345_REG_INT_SOURCE          (0x30)    // Source of interrupts
    #define ADXL345_REG_DATA_FORMAT         (0x31)    // Data format control
    #define ADXL345_REG_DATAX0              (0x32)    // X-axis data 0
    #define ADXL345_REG_DATAX1              (0x33)    // X-axis data 1
    #define ADXL345_REG_DATAY0              (0x34)    // Y-axis data 0
    #define ADXL345_REG_DATAY1              (0x35)    // Y-axis data 1
    #define ADXL345_REG_DATAZ0              (0x36)    // Z-axis data 0
    #define ADXL345_REG_DATAZ1              (0x37)    // Z-axis data 1
    #define ADXL345_REG_FIFO_CTL            (0x38)    // FIFO control
    #define ADXL345_REG_FIFO_STATUS         (0x39)    // FIFO status
    */
   
   private static final byte ADRESS = 0x53;
   
   private static final byte ADXL345_REG_POWER_CTL = 0x2D;
   
   private static final byte ADXL345_REG_DATAX0 = 0x32; //X-axis data 0
   private static final byte ADXL345_REG_DATAY0 = 0x34; //Y-axis data 0
   private static final byte ADXL345_REG_DATAZ0 = 0x36; //Z-axis data 0
   
   private static final double ADXL345_MG2G_MULTIPLIER = 0.004; // 4mg per lsb
   private static final float SENSOR_GRAVITY_STANDARD = 9.80665F;
   
   private static final int ADXL345_REG_OFSY   =  0x1F;
   
   public Accel(I2CBus bus) throws IOException{
      System.out.println("Starting Accel");
      device = bus.getDevice(ADRESS);
      device.write(ADXL345_REG_POWER_CTL, (byte)0x08);
   }
   public double y_displacement=0;
   public double x_displacement=0;
   public double z_displacement=0;
   public void readAxis() throws IOException{
	  byte[] buff = new byte[2];
      //device.write(ADXL345_REG_DATAY0); //send data to register to read	  
	  //read y      
      device.read(ADXL345_REG_DATAY0,buff,0,2);
      int response = (asInt(buff[0]) | ((int)buff[1])<< 8);      
	  y_displacement+=(double)response * ADXL345_MG2G_MULTIPLIER * SENSOR_GRAVITY_STANDARD;
	  //read x	  
      device.read(ADXL345_REG_DATAX0,buff,0,2);
      int response = (asInt(buff[0]) | ((int)buff[1])<< 8);      
	  x_displacement+=(double)response * ADXL345_MG2G_MULTIPLIER * SENSOR_GRAVITY_STANDARD;
	  //read z	  
      device.read(ADXL345_REG_DATAZ0,buff,0,2);
      int response = (asInt(buff[0]) | ((int)buff[1])<< 8);      
	  z_displacement+=(double)response * ADXL345_MG2G_MULTIPLIER * SENSOR_GRAVITY_STANDARD;
      System.out.println("x:"+x_displacement+"\t y:"+y_displacement+"\t z:"+z_displacement);
   }
   public int asInt(byte b){
      int i = b;
      if(i<0){
         i = i +256;
      }
      return i;
   }
   
}
