package dao;

import annotation.Component;

@Component
public class PersonDaoImpl implements PersonDao {
	@Override
	public void save() {
		System.out.println("Person is saved.");
	}
}
