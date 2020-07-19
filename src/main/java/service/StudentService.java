package service;

import annotation.Inject;
import dao.StudentDao;

public class StudentService {
	@Inject
	private StudentDao studentDao;

	public void save() {
		studentDao.save();
	}
}
