import java.util.ArrayList;

import Exceptions.StudentNotFoundException;

public class StudentDAO {
    
    private ArrayList<Student> studentList;

    StudentDAO() {
        studentList = new ArrayList<Student>();
    }

    void add(Student s) {
        studentList.add(s);
    }

    ArrayList<Student> retrieveAll() {
        return studentList;
    }

    Student retrieve(String username) throws StudentNotFoundException {
        for (Student s : studentList) {
            if (s.getUsername().equals(username)) {
                return s;
            }
        }

        throw new StudentNotFoundException();
    }

    boolean contains(String username) {
        for (Student s : studentList) {
            if (s.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    void addStudent(String username, String name, int eDollars) {
        studentList.add(new Student(username, name, eDollars));
    }

}
