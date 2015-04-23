package edu.hkcc.pacmanrobot.utils.lang;

/**
 * Created by beenotung on 4/22/15.
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
