package edu.hkcc.pacmanrobot.utils.lang;

import edu.hkcc.pacmanrobot.debug.Debug;

/**
 * Created by beenotung on 4/22/15.
 * need more testing
 */
public class ConcurrencyDrawer<T> {
    private final PutterLock lock = new PutterLock();
    private volatile T content;

    public ConcurrencyDrawer() {
        this(null);
    }

    public ConcurrencyDrawer(T initObject) {
        content = initObject;
    }

    public synchronized T update(T newObject) {
        //Debug.getInstance().printMessage("PutterLock update: "+content.toString());
        T oldObject = content;
        content = newObject;
        lock.putKey();
        return oldObject;
    }

    public synchronized T getContent() throws InterruptedException {
        lock.waitAndTakeKey();
        T result = content;
        content = null;
        return result;
    }

    public synchronized T tryGetContent() {
        lock.takeKey();
        T result = content;
        content = null;
        return result;
    }

    public synchronized void clear() {
        lock.takeKey();
        content = null;
    }
}
