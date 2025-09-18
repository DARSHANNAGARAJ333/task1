import java.util.*;
import java.io.*;


class Student{
    private int id;
    private String name;
    private int age;
    private String grade;
    private String address;

    Student(int id, String name, int age, String grade, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.address = address;
    }

    public int getId()
    { 
        return id; 
    }
    public void setId(int id) 
    { 
        this.id = id; 
    }

    public String getName() 
    { 
        return name; 
    }
    public void setName(String name) 
    { 
        this.name = name; 
    }

    public int getAge() 
    { 
        return age; 
    }
    public void setAge(int age) 
    { 
        this.age = age; 
    }

    public String getGrade() 
    { 
        return grade; 
    }
    public void setGrade(String grade) 
    { 
        this.grade = grade; 
    }

    public String getAddress() 
    { 
        return address; 
    }
    public void setAddress(String address) 
    { 
        this.address = address; 
    }

    
    public String toString() {
        return String.format(id+"|"+name+"|"+age+"|"+grade+"|"+address);
    }

    public boolean equals(Object o) {
        if (this == o)
        {
            //System.out.println("Yes bot are equal");
            return true;
        }
        //System.out.println("Both are not equal");
        return false;
    }

    public int hashCode() {
        return Objects.hash(id);
    }

    
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);
    }
}

class StudentManager {
    private ArrayList<Student> students=new ArrayList<Student>();;
    private HashMap<Integer, Student> studentMap= new HashMap<Integer, Student>();;
    private TreeSet<Student> sortedStudents= new TreeSet<>(Comparator.comparing(Student::getId)); 

    

    public void addStudent(Student student) {
        if (!studentMap.containsKey(student.getId())) {
            students.add(student);
            studentMap.put(student.getId(), student);
            sortedStudents.add(student);
        } else {
            System.out.println("Student with ID " + student.getId() + " already exists.");
        }
    }

    public void removeStudent(int id) {
        Student s = studentMap.get(id);
        if (s != null) {
            students.remove(s);
            studentMap.remove(id);
            sortedStudents.remove(s);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public void updateStudent(int id, String name, int age, String grade, String address) {
        Student s = studentMap.get(id);
        if (s != null) {
            s.setName(name);
            s.setAge(age);
            s.setGrade(grade);
            s.setAddress(address);
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public Student searchStudent(int id) {
        return studentMap.get(id);
    }

    public void displayAllStudents() {
        if (sortedStudents.isEmpty()) {
            System.out.println("No students found.");
        } else {
            sortedStudents.forEach(System.out::println);//<-
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student s : students) {
                writer.println(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getGrade() + "," + s.getAddress());
            }
            System.out.println("Data saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    String grade = parts[3];
                    String address = parts[4];
                    addStudent(new Student(id, name, age, grade, address));
                }
            }
            System.out.println("Data loaded from " + filename);
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}


public class studentmain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();

        String filename = "students.txt";
        manager.loadFromFile(filename);

        while (true) {
          
                System.out.println("\n===== Student Management System =====");
                System.out.println("1. Add a new student");
                System.out.println("2. Remove a student by ID");
                System.out.println("3. Update student details by ID");
                System.out.println("4. Search for a student by ID");
                System.out.println("5. Display all students (sorted)");
                System.out.println("6. Exit and save data");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Grade: ");
                        String grade = sc.nextLine();
                        System.out.print("Enter Address: ");
                        String address = sc.nextLine();
                        manager.addStudent(new Student(id, name, age, grade, address));
                        break;

                    case 2:
                        System.out.print("Enter ID to remove: ");
                        int removeId = sc.nextInt();
                        manager.removeStudent(removeId);
                        break;

                    case 3:
                        System.out.print("Enter ID to update: ");
                        int updateId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter New Age: ");
                        int newAge = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter New Grade: ");
                        String newGrade = sc.nextLine();
                        System.out.print("Enter New Address: ");
                        String newAddress = sc.nextLine();
                        manager.updateStudent(updateId, newName, newAge, newGrade, newAddress);
                        break;

                    case 4:
                        System.out.print("Enter ID to search: ");
                        int searchId = sc.nextInt();
                        Student s = manager.searchStudent(searchId);
                        if (s != null) {
                            System.out.println("Found: " + s);
                        } else {
                            System.out.println("Student not found.");
                        }
                        break;

                    case 5:
                        manager.displayAllStudents();
                        break;

                    case 6:
                        manager.saveToFile(filename);
                        System.out.println("Exiting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            
        }
    }
}


// output
// ===== Student Management System =====
// 1. Add a new student
// 2. Remove a student by ID
// 3. Update student details by ID
// 4. Search for a student by ID
// 5. Display all students (sorted)
// 6. Exit and save data
// Enter choice: 1
// Enter ID: 101
// Enter Name: darshan
// Enter Age: 23
// Enter Grade: A
// Enter Address: banglore

// ===== Student Management System =====
// 1. Add a new student
// 2. Remove a student by ID
// 3. Update student details by ID
// 4. Search for a student by ID
// 5. Display all students (sorted)
// 6. Exit and save data
// Enter choice: 1
// Enter ID: 102
// Enter Name: xyz
// Enter Age: 22
// Enter Grade: B
// Enter Address: mysuru

// ===== Student Management System =====
// 1. Add a new student
// 2. Remove a student by ID
// 3. Update student details by ID
// 4. Search for a student by ID
// 5. Display all students (sorted)
// 6. Exit and save data
// Enter choice: 5
// 101|darshan|23|A|banglore
// 102|xyz|22|B|mysuru

// ===== Student Management System =====
// 1. Add a new student
// 2. Remove a student by ID
// 3. Update student details by ID
// 4. Search for a student by ID
// 5. Display all students (sorted)
// 6. Exit and save data
// Enter choice: 2
// Enter ID to remove: 102
// Both are not equal
// Yes bot are equal
// Student removed successfully.
// 2. Remove a student by ID
// 3. Update student details by ID
// 4. Search for a student by ID
// 5. Display all students (sorted)
// 6. Exit and save data
// Enter choice: 3
// Enter ID to update: 101
// Enter New Name: darshan_n
// Enter New Age: 22
// Enter New Grade: A+
// Enter New Address: USA
// Student updated successfully.

// ===== Student Management System =====
// 1. Add a new student
// 2. Remove a student by ID
// 3. Update student details by ID
// 4. Search for a student by ID
// 5. Display all students (sorted)
// 6. Exit and save data
// Enter choice: 5
// 101|darshan_n|22|A+|USA

// ===== Student Management System =====
// 1. Add a new student
// 2. Remove a student by ID
// 3. Update student details by ID
// 4. Search for a student by ID
// 5. Display all students (sorted)
// 6. Exit and save data
// Enter choice: 4
// Enter ID to search: 101
// Found: 101|darshan_n|22|A+|USA

// ===== Student Management System =====
// 1. Add a new student
// 2. Remove a student by ID
// 3. Update student details by ID
// 4. Search for a student by ID
// 5. Display all students (sorted)
// 6. Exit and save data
// Enter choice: 6
// Data saved to students.dat
// Exiting...
// PS C:\java> 