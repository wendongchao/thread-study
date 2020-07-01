package com.itcast.thread;

/**
 * 使用synchronized进行加锁
 * synchronized的锁对象是bank类对象，那么当线程获取锁时，
 * 那么就是对整个bank对象进行上锁，其中包括其中的属性及属性值
 */
public class Bank {
    // 假设一个账户有1000块钱
    static double money = 1000;
    // 柜台Counter取钱的方法
    public void Counter(double money) {
        Bank.money -= money;
        System.out.println("柜台取钱" + money + "元，还剩" + Bank.money + "元！");
    }
    // ATM取钱的方法
    public void ATM(double money) {
        Bank.money -= money;
        System.out.println("ATM取钱" + money + "元，还剩" + Bank.money + "元！");
    }
}
//当银行卡多余100时，PersonA就取出钱
//线程1，继承Thread类并重写run方法
class PersonA extends Thread{
    Bank bank;
    public PersonA(Bank bank){
        this.bank = bank;
    }
    @Override
    public  void run(){
        while (Bank.money>=100){
            synchronized (bank) {//添加锁对象
                bank.Counter(100);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
//当银行卡多余200时，PersonB就取出钱
class PersonB extends Thread{
    Bank bank;

    public PersonB(Bank bank){
        this.bank = bank;
    }
    @Override
    public  void run(){
        while (Bank.money>=200){
            synchronized (bank) {//添加锁对象
                bank.ATM(200);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Test02{
    public static void main(String[] args) {
        Bank bank = new Bank();
        PersonA a = new PersonA(bank);
        PersonB b = new PersonB(bank);
        a.start();
        b.start();
    }
}