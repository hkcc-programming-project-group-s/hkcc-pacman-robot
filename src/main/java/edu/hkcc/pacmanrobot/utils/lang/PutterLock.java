package edu.hkcc.pacmanrobot.utils.lang;

import edu.hkcc.pacmanrobot.debug.Debug;

/**
 * Created by beenotung on 4/22/15.
 */
public class PutterLock {

    private volatile boolean avaliable;

    public PutterLock(boolean initStatus) {
        this.avaliable = initStatus;
    }

    public PutterLock() {
        this(false);
    }

    public boolean isAvaliable() {
        return avaliable;
    }

    public synchronized void waitAndTakeKey() throws InterruptedException {
        Debug.getInstance().printMessage(Thread.currentThread().getName() + " waitKey (PutterLock)\n\n\n\n\n\n\n\n\n");
        while (!this.avaliable) {
            //Debug.getInstance().printMessage(Thread.currentThread().getName() + " self block (PutterLock)");
            //wait();
            Thread.sleep(1);
        }
        Debug.getInstance().printMessage(Thread.currentThread().getName() + " takeKey (PutterLock)\n\n\n\n\n\n\n\n\n");
        this.avaliable = false;
        this.notify();
    }


    public synchronized void putKey() {
        Debug.getInstance().printMessage(Thread.currentThread().getName() + " putKey (PutterLock)\n\n\n\n\n\n\n\n\n");
        this.avaliable = true;
        this.notify();
    }

    public synchronized void takeKey() {
        this.avaliable = false;
        this.notify();
    }
}
