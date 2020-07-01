package com.itcast.thread;

/**
 * wait notify 等待通知
 * 如果遇到死锁的情况下，请使用notifyAll，这个方法是通知全部，
 * 这样可以避免线程处于等待状态，没有通知到的情况
 */
public class ThreadWaitNotify {
    public static void main(String[] args) {
        Person person = new Person();
        Input in = new Input(person);
        Output out = new Output(person);
        Thread t1 = new Thread(in);
        t1.setName("t1");
        Thread t3 = new Thread(in);
        t3.setName("t3");
        Thread t2 = new Thread(out);
        t2.setName("t2");
        Thread t4 = new Thread(out);
        t4.setName("t4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
class Person{
    private String name;
    private String age;
    private boolean redux = false;
    private boolean flag = true;
    public synchronized void setPerson(){//synchronized修饰方法，锁定的是这个方法
        while (redux){//redux==true 进入等待
            try {
                System.out.println("wait等待线程名字set："+Thread.currentThread().getName());
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(flag){
            flag = false;
            this.name = "李白";
            this.age = "23";
        }else{
            flag = true;
            this.name = "杜甫";
            this.age = "22";
        }
        redux = true;
//		this.notifyAll();//唤醒所有
        //有可能出现通知到自己的情况，这种情况下，就有可能出现死锁
        this.notify();//用于wait()的唤醒，唤醒一个，唤醒之后需要重新争抢锁，等待的时候已经放弃锁了
        System.out.println("通知线程进入就绪状态set");
    }
    public synchronized void getPerson(){
        while (!redux){//redux==false 进入等待
            try {
                System.out.println("wait等待线程名字get："+Thread.currentThread().getName());
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(name+"---------------"+age);
        redux = false;
//		this.notifyAll();//唤醒所有
        this.notify();//用于wait()的唤醒，唤醒一个，唤醒之后需要重新争抢锁，等待的时候已经放弃锁了
        System.out.println("通知线程进入就绪状态get");
    }
}
//输入
class Input implements Runnable{
    private Person person;
    Input(Person person){
        this.person = person;
    }
    public void run(){
        while (true){
            person.setPerson();
        }
    }
}
//输出
class Output implements Runnable{
    private Person person;
    Output(Person person){
        this.person = person;
    }

    public void run(){
        while (true){
            person.getPerson();
        }
    }
}
