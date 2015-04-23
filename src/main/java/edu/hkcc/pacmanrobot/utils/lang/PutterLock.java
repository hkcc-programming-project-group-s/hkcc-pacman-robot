package edu.hkcc.pacmanrobot.utils.lang;

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

    public synchronized void take() throws InterruptedException {
        while (!this.avaliable) wait();
        this.avaliable = false;
        this.notify();
    }


    public synchronized void put() {
        this.avaliable = true;
        this.notify();
    }
}
