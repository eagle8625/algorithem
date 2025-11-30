package org.lqs.others;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class Util {

    public static void main(String[] args) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("file.txt");
            // 读取数据
            int data = fis.read();
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                try {
                    fis.close(); // 关闭资源
                } catch (IOException e) {
                    e.printStackTrace(); // 关闭时也可能抛异常！
                }
        }
    }

    public void concurrent(){
        ReentrantLock lock = new ReentrantLock();
    }

}
