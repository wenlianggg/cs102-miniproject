import java.util.ArrayList;

import Exceptions.StudentNotFoundException;

public class StudentDAO {
    
    ArrayList<Student> studentList;

    StudentDAO() {
        studentList = new ArrayList<Student>();
        studentList.add(new Student("raini", "Rainie Yang", 20));
        studentList.add(new Student("hyun", "Hyun Bin", 30));
        studentList.add(new Student("aaron", "Aaron Yang", 40));
        studentList.add(new Student("simi", "Shiela Sim", 50));
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

    void addStudent(String username, String name, int eDollars) {
        studentList.add(new Student(username, name, eDollars));
    }

}
