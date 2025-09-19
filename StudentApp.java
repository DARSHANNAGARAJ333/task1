import java.sql.*;
import java.util.*;

class Student {
    private int id;
    private String name;
    private int age;
    private String grade;

   
    public Student(String name, int age, String grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public int getId() 
    { 
        return id; 
    }
    public String getName() 
    { 
        return name; 
    }
    public int getAge() 
    { 
        return age; 
    }
    public String getGrade() 
    { 
        return grade; 
    }

    
}

public class StudentApp {
    private static final String URL = "jdbc:mysql://localhost:4306/schooldb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addStudent(Student s) {
        String sql = "INSERT INTO students(name, age, grade) VALUES(?, ?, ?)";
        try{
            Connection conn = getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getName());
            ps.setInt(2, s.getAge());
            ps.setString(3, s.getGrade());
            ps.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showStudents() {
        String sql = "SELECT * FROM students";
        try  {
            Connection conn = getConnection(); 
            Statement stmt = conn.createStatement(); 
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) 
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String grade = rs.getString("grade");

                System.out.println("\nId:-"+id+"|"+name+"|"+age+"|"+grade);

    }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(int id, String newName, int newAge, String newGrade) {
        String sql = "UPDATE students SET name=?, age=?, grade=? WHERE id=?";
        try  {
            Connection conn = getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, newAge);
            ps.setString(3, newGrade);
            ps.setInt(4, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Student updated!");
            else System.out.println("Student not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try  {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("Student deleted!");
            else System.out.println("Student not found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Grade: ");
                    String grade = sc.nextLine();
                    addStudent(new Student(name, age, grade));
                    break;
                case 2:
                    showStudents();
                    break;
                case 3:
                    System.out.print("Enter ID to update: ");
                    int idUp = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Name: ");
                    String newName = sc.nextLine();
                    System.out.print("New Age: ");
                    int newAge = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Grade: ");
                    String newGrade = sc.nextLine();
                    updateStudent(idUp, newName, newAge, newGrade);
                    break;
                case 4:
                    System.out.print("Enter ID to delete: ");
                    int idDel = sc.nextInt();
                    deleteStudent(idDel);
                    break;
                case 0:
                    System.out.println("Bye!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

// output

// --- Student Management ---
// 1. Add Student
// 2. List Students
// 3. Update Student
// 4. Delete Student
// 0. Exit
// Choice: 1
// Name: xyz
// Age: 22
// Grade: c
// Student added successfully!

// --- Student Management ---
// 1. Add Student
// 2. List Students
// 3. Update Student
// 4. Delete Student
// 0. Exit
// Choice: 2

// Id:-1|darshan|22|A+

// Id:-3|xyz|22|c

// --- Student Management ---
// 1. Add Student
// 2. List Students
// 3. Update Student
// 4. Delete Student
// 0. Exit
// Choice: 3
// Enter ID to update: 3
// New Name: xyza
// New Age: 22
// New Grade: a
// Student updated!

// --- Student Management ---
// 1. Add Student
// 2. List Students
// 3. Update Student
// 4. Delete Student
// 0. Exit
// Choice: 2

// Id:-1|darshan|22|A+

// Id:-3|xyza|22|a

// --- Student Management ---
// 1. Add Student
// 2. List Students
// 3. Update Student
// 4. Delete Student
// 0. Exit
// Choice: 3
// Enter ID to update: 2
// New Name: www
// New Age: 2
// New Grade: a
// Student not found.

// --- Student Management ---
// 1. Add Student
// 2. List Students
// 3. Update Student
// 4. Delete Student
// 0. Exit
// Choice: 4
// Enter ID to delete: 3
// Student deleted!

// --- Student Management ---
// 1. Add Student
// 2. List Students
// 3. Update Student
// 4. Delete Student
// 0. Exit
// Choice: