package edu.hkcc.pacmanrobot.utils.lang;

/**
 * Created by beenotung on 4/22/15.
 */
public class PutterSemaphore {

    private volatile boolean avaliable;

    public PutterSemaphore(boolean initStatus) {
        this.avaliable = initStatus;
    }

    public synchronized void take() throws InterruptedException {
        while (!this.avaliable) wait();
        this.avaliable = false;
        this.notify();
    }


    public synchronized void release() throws InterruptedException {
        this.avaliable = true;
        this.notify();
    }
}
