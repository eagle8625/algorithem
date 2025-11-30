package org.lqs.others;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MultipleThread {
    private ReentrantLock lock = new ReentrantLock();

    public void interrupt() {
        lock.lock();
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            boolean tryLocked = lock.tryLock(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Condition condition = lock.newCondition();
    }


    /**
     * 使用reentrantLock实现一个阻塞队列
     * @param <T>
     */
    class BlockingQueue<T>{
        private ReentrantLock lock = new ReentrantLock();
        private Condition notFull = lock.newCondition();
        private Condition notEmpty = lock.newCondition();
        private Integer capacity;
        private List<T> list = new ArrayList<>();

        public BlockingQueue(Integer capacity){
            this.capacity = capacity;
        }

        public void put(T t) throws InterruptedException{
            lock.lock();
            try {
                //这里不能用if，会导致虚假唤醒 spurious wakeup
                while (list.size() >= capacity){
                    notFull.await();
                }
                list.add(t);
                notEmpty.signalAll();
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }

        public T take() throws InterruptedException{
            lock.lock();
            try {
                while (list.isEmpty()){
                    notEmpty.await();
                }
                return list.get(0);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
            return null;
        }
    }
}
