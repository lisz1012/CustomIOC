package dao;

import annotation.Component;

@Component
public class StudentDaoImpl implements StudentDao {
	public void save() {
		System.out.println("Student is saved to DB");
	}
}
