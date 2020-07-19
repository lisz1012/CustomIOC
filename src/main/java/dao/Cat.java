package dao;

import annotation.Component;

@Component
public class Cat implements Animal {
	@Override
	public void run() {
		System.out.println("Cat is running");
	}
}
