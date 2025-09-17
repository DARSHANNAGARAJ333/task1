import java.util.*;
import java.time.*;


class Book {
    private final UUID bookID;
    private final String title;
    private final String author;
    private final String genre;
    private boolean isIssued;
    private Member issuedTo;
    private LocalDate dueDate;
    private final Queue<Member> reservationQueue = new LinkedList<>();

    public Book(String title, String author, String genre) {
        this.bookID = UUID.randomUUID();
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public UUID getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public Member getIssuedTo() {
        return issuedTo;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Queue<Member> getReservationQueue() {
        return reservationQueue;
    }

    void SetdueDate(LocalDate dueDate)
    {
        this.dueDate=dueDate;
    }

    void SetisIssued(boolean isIssued)
    {
        this.isIssued=isIssued;
    }
    void SetissuedTo( Member issuedTo)
    {
        this.issuedTo=issuedTo;
    }
}

abstract class Member {
    private final UUID memberID;
    private final String name;
    private final String email;
    private final String phone;
    protected final int maxBooksAllowed;
    protected final List<Book> issuedBooks = new ArrayList<Book>();

    protected Member(String name, String email, String phone, int maxBooksAllowed) {
        this.memberID = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public UUID getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public List<Book> getIssuedBooks() {
        return Collections.unmodifiableList(issuedBooks);
    }

    boolean canIssueMore() {
        return issuedBooks.size() < maxBooksAllowed;
    }

    void receiveBook(Book b) {
        issuedBooks.add(b);
    }

    void returnBook(Book b) {
        issuedBooks.remove(b);
    }

    public abstract int getMaxAllowedDays();

    public abstract String getMemberType();

}

class StudentMember extends Member {
    private int MaxAllowedDays=14;
    private String MemberType="Student";

    StudentMember(String name, String email, String phone) {
        super(name, email, phone, 3);        
    }

    public int getMaxAllowedDays() {
        return MaxAllowedDays;
    }

    public String getMemberType() {
        return MemberType;
    }
}

class TeacherMember extends Member {
    private int MaxAllowedDays=30;
    private String MemberType="Teacher";

    TeacherMember(String name, String email, String phone) 
    {
        super(name, email, phone, 5);
        
    }
     public int getMaxAllowedDays() {
        return MaxAllowedDays;
    }

    public String getMemberType() {
        return MemberType;
    }

}

class GuestMember extends Member 
{
    private int MaxAllowedDays=7;
    private String MemberType="Guest";

    GuestMember(String name, String email, String phone) {
        super(name, email, phone, 1);
    }
    public int getMaxAllowedDays() {
        return MaxAllowedDays;
    }

    public String getMemberType() {
        return MemberType;
    }

}


class Librarian extends Member {
    
    
    private final List<Book> libraryBooks = new ArrayList<Book>();
    private final List<Member> registeredMembers = new ArrayList<Member>();

    Librarian(String name, String email, String phone) {
        super(name, email, phone, 10); 
    }

    public void addBook(Book book) {
        libraryBooks.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void removeBook(Book book) {
        if (libraryBooks.remove(book)) {
            System.out.println("Book removed: " + book.getTitle());
        } else {
            System.out.println("Book not found in library.");
        }
    }

    public void viewIssuedBooks() {
        System.out.println("Issued Books in Library:");
        boolean found = false;
        for (Book book : libraryBooks) {
            if (book.isIssued()) {
                found = true;
                System.out.println("- " + book.getTitle() + " issued to " +
                        book.getIssuedTo().getName() + " | Due: " + book.getDueDate());
            }
        }
        if (!found) {
            System.out.println("No books are currently issued.");
        }
    }

    public void overrideReturnDeadline(Book book, LocalDate newDate) {
        if (book.isIssued()) {
            book.SetdueDate(newDate);
            System.out.println("Return deadline for \"" + book.getTitle() + "\" updated to " + newDate);
        } else {
            System.out.println("Book is not issued. Cannot override deadline.");
        }
    }

    public void registerMember(Member member) {
        registeredMembers.add(member);
        System.out.println("Member registered: " + member.getName() + " (" + member.getMemberType() + ")");
    }

    public void removeMember(Member member) {
        if (registeredMembers.remove(member)) {
            System.out.println("Member removed: " + member.getName());
        } else {
            System.out.println("Member not found.");
        }
    }

    public void viewRegisteredMembers() {
        System.out.println("Registered Members:");
        for (Member m : registeredMembers) {
            System.out.println("- " + m.getName() + " (" + m.getMemberType() + ")");
        }
    }

    public boolean issueBook(Book book, Member member) {
        if (book.isIssued()) {
            System.out.println("Book \"" + book.getTitle() + "\" is already issued.");
            return false;
        }
        if (!member.canIssueMore()) {
            System.out.println(" Member " + member.getName() + " has reached book limit.");
            return false;
        }

        book.SetisIssued(true);

        book.SetissuedTo(member);
        book.SetdueDate(LocalDate.now().plusDays(member.getMaxAllowedDays()));
        member.receiveBook(book);

        System.out.println("Book \"" + book.getTitle() + "\" issued to " + member.getName() +
                " | Due date: " + book.getDueDate());
        return true;
    }

    public void returnBook(Book book, Member member) {
        if (!book.isIssued() || !book.getIssuedTo().equals(member)) {
            System.out.println("This book was not issued to " + member.getName());
            return;
        }

        member.returnBook(book);
        book.SetisIssued(false);
        book.SetissuedTo(null);
        book.SetdueDate(null);

        System.out.println( member.getName() + " returned the book \"" + book.getTitle() + "\".");

        if (!book.getReservationQueue().isEmpty()) {
            Member nextMember = book.getReservationQueue().poll();
            System.out.println(" Book \"" + book.getTitle() + "\" reserved by " + nextMember.getName() + ". Issuing now...");
            issueBook(book, nextMember);
        }
    }

    public void searchBooks(String keyword) {
    System.out.println("Searching for: " + keyword);
    boolean found = false;

    for (Book book : libraryBooks) {
        if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
            book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
            book.getGenre().toLowerCase().contains(keyword.toLowerCase())) {
            
            found = true;
            String status = book.isIssued() 
                            ? "Issued to " + book.getIssuedTo().getName() + " (Due: " + book.getDueDate() + ")"
                            : "Available";
            System.out.println("- " + book.getTitle() + " by " + book.getAuthor() + " [" + book.getGenre() + "] | " + status);
        }
    }

    if (!found) {
        System.out.println("No books found matching keyword: " + keyword);
    }
}
    public void reserveBook(Book book, Member member) {
    if (book.isIssued()) {
        if (!book.getReservationQueue().contains(member)) {
            book.getReservationQueue().add(member);
            System.out.println(member.getName() + " has reserved \"" + book.getTitle() + "\".");
        } else {
            System.out.println(member.getName() + " already reserved this book.");
        }
    } else {
        System.out.println("Book \"" + book.getTitle() + "\" is available. No need to reserve.");
    }
}

    public void viewOverdueBooks() {
    System.out.println(" Overdue Books:");
    boolean found = false;

    for (Book book : libraryBooks) {
        if (book.isIssued() && book.getDueDate().isBefore(LocalDate.now())) {
            found = true;
            Member member = book.getIssuedTo();
            System.out.println("- \"" + book.getTitle() + "\" | Issued to: " 
                + member.getName() + " (" + member.getEmail() + ") | Due: " + book.getDueDate());
        }
    }

    if (!found) {
        System.out.println("No overdue books found.");
    }
}

    public int getMaxAllowedDays() {
        return 60; 
    }

    public String getMemberType() {
        return "Librarian";
    }

    
}



 public class LibrarySystem {
    public static void main(String[] args) {
        Librarian librarian = new Librarian("Mr. Sharma", "sharma@lib.com", "98765");

        Book b1 = new Book("Java Programming", "James Gosling", "Programming");
        Book b2 = new Book("Data Structures", "Robert Lafore", "CS");

        StudentMember s1 = new StudentMember("Alice", "alice@mail.com", "12345");
        TeacherMember t1 = new TeacherMember("Bob", "bob@mail.com", "67890");

        librarian.addBook(b1);
        librarian.addBook(b2);

        librarian.registerMember(s1);
        librarian.registerMember(t1);

        librarian.issueBook(b1, s1);
        librarian.issueBook(b2, t1);

        librarian.viewIssuedBooks();

        librarian.returnBook(b1, s1);
        
        librarian.viewIssuedBooks();

        librarian.removeBook(b1);

        librarian.searchBooks("java");   
        librarian.searchBooks("data");

        librarian.reserveBook(b1, t1);

        librarian.viewOverdueBooks();




    }
}


//output
// Book added: Java Programming
// Book added: Data Structures
// Member registered: Alice (Student)
// Member registered: Bob (Teacher)
// Book "Java Programming" issued to Alice | Due date: 2025-10-01
// Book "Data Structures" issued to Bob | Due date: 2025-10-17
// Issued Books in Library:
// - Java Programming issued to Alice | Due: 2025-10-01
// - Data Structures issued to Bob | Due: 2025-10-17
// Alice returned the book "Java Programming".
// Issued Books in Library:
// - Data Structures issued to Bob | Due: 2025-10-17
// Book removed: Java Programming
// Searching for: java
// No books found matching keyword: java
// Searching for: data
// - Data Structures by Robert Lafore [CS] | Issued to Bob (Due: 2025-10-17)
// Book "Java Programming" is available. No need to reserve.
//  Overdue Books:
// No overdue books found.
// PS C:\java> 



