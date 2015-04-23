package edu.hkcc.pacmanrobot.utils.lang;

/**
 * Created by beenotung on 4/22/15.
 */
public class Drawer<T> {
    final PutterLock lock = new PutterLock();
    volatile T content;

    public Drawer() {
        this(null);
    }

    public Drawer(T initObject) {
        content = initObject;
    }

    public synchronized void update(T o) {
        content = o;
        lock.put();
    }

    public synchronized T getContent() throws InterruptedException {
        lock.take();
        T result = content;
        content = null;
        return result;
    }

}
