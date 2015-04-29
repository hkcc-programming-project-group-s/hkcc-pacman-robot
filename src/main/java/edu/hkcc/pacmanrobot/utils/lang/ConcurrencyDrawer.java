package edu.hkcc.pacmanrobot.utils.lang;

import java.util.concurrent.Semaphore;

/**
 * Created by beenotung on 4/22/15.
 * need more testing
 */
public class ConcurrencyDrawer<T> {
    //private final PutterLock lock = new PutterLock();
    private volatile T content;
    private Semaphore semaphore;

    public ConcurrencyDrawer() {
        this(null);
    }

    public ConcurrencyDrawer(T initObject) {
        content = initObject;
        semaphore = new Semaphore(1);
    }

    /*public T update(T newObject) {
        synchronized (this) {
            Debug.getInstance().printMessage("\n\n\n\n\n\n\n\nnull check: content= " + content);
            Debug.getInstance().printMessage("\n\n\n\n\n\n\n\nPutterLock update: " + content.toString());
            T oldObject = content;
            content = newObject;
            lock.putKey();
            return oldObject;
        }
    }*/
    public T update(T newContent) {
        T oldContent = null;
        try {
            semaphore.acquire();
            oldContent = content;
            content = newContent;
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return oldContent;
    }

    public T waitGetContent() throws InterruptedException {
        T oldContent = null;
        do {
            try {
                semaphore.acquire();
                oldContent = content;
                content = null;
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (oldContent == null)
                Thread.sleep(1);
        } while (oldContent == null);
        return oldContent;
    }

    /*public T tryGetContent() {
        synchronized (this) {
            lock.takeKey();
            T result = content;
            content = null;
            return result;
        }
    }*/

    public T tryGetContent() {
        T oldContent = null;
        try {
            semaphore.acquire();
            oldContent = content;
            content = null;
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return oldContent;
    }

    /*public void clear() {
        synchronized (this) {
            lock.takeKey();
            content = null;
        }
    }*/

    public void clear() {
        try {
            semaphore.acquire();
            content = null;
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
