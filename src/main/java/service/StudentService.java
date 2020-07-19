package service;

import annotation.Inject;
import dao.Animal;
import dao.PersonDao;
import dao.PersonDaoImpl;
import dao.StudentDao;

public class StudentService {
	@Inject
	private StudentDao studentDao;

	@Inject
	private PersonDao personDao; // 用子类PersonDaoImpl类型也不会出错 ^_^

	@Inject
	private Animal animal;

	public void save() {
		personDao.save();
		studentDao.save();
	}

	public void run() {
		animal.run();
	}
}
