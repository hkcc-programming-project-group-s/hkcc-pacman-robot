//on server
public class MessageManager extends Thread {

    public static final Byte RESET = 0x01;


    public void sendEndMessage(boolean win) {
    /*
        send the message to user
        if (win) send the win message to user
        else send the loss message to user
        */
    }

    public void sendSpecificMessage() {
    /*
        send the message to user
        when robot's position is not change in ??? time.
        */
    }

    // on server, send to student
    public synchronized void sendStudentTargetPosition() {
    /*
        When android send the position to sever, sever pass the message to student.
        */
    }

    //passive
    public void receiveStudentTargetPosition() {
    /*
        called from another thread
        */
    }

}