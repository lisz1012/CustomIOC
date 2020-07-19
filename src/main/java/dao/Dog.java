package dao;

import annotation.Component;

//@Component
public class Dog implements Animal {
	@Override
	public void run() {
		System.out.println("Dog is running");
	}
}
