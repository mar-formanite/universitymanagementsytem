import java.util.Date;
import java.util.List;
import java.util.Scanner;

import courses.Course;
import dao.CourseDAO;
import dao.DepartmentDAO;
import dao.FinanceDAO;
import dao.LibraryDAO;
import dao.PersonDAO;
import dao.TranscriptDAO;
import departments.department;
import finance.FinanceAccount;
import finance.SalaryAccount;
import finance.ScholarshipAccount;
import finance.TuitionAccount;
import library.Book;
import library.BorrowRecord;
import people.AssistantProfessor;
import people.Faculty;
import people.GraduateStudent;
import people.Lecturer;
import people.Person;
import people.PhDStudent;
import people.Professor;
import people.Student;
import people.UndergraduateStudent;
import people.FacultyManager;
import people.StaffManager;
import people.StudentManager;
import courses.Transcript;

public class UniversitySystem {

    private Scanner scanner = new Scanner(System.in);
    private PersonDAO personDAO = new PersonDAO();
    private CourseDAO courseDAO = new CourseDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private LibraryDAO libraryDAO = new LibraryDAO();
    private FinanceDAO financeDAO = new FinanceDAO();
    private TranscriptDAO transcriptDAO = new TranscriptDAO();
    private StudentManager studentManager = new StudentManager();
    private FacultyManager facultyManager = new FacultyManager();
    private StaffManager staffManager = new StaffManager();

    private boolean isAdmin = false;

    public void run() {
        System.out.println("Welcome to Smart University Management System");
        System.out.print("Enter admin password (demo: 'admin'): ");
        String password = scanner.nextLine();
        isAdmin = "admin".equals(password);

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Student Management");
            System.out.println("2. Faculty Management");
            System.out.println("3. Course Management");
            System.out.println("4. Grading Module");
            System.out.println("5. Library Module");
            System.out.println("6. Finance Module");
            System.out.println("7. Admin Module");
            System.out.println("8. Exit");
            System.out.print("Choose: ");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 1: studentMenu(); break;
                case 2: facultyMenu(); break;
                case 3: courseMenu(); break;
                case 4: gradingMenu(); break;
                case 5: libraryMenu(); break;
                case 6: financeMenu(); break;
                case 7: if (isAdmin) adminMenu(); else System.out.println("Access denied."); break;
                case 8: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void studentMenu() {
        while (true) {
            System.out.println("\nStudent Management:");
            System.out.println("1. Register Student");
            System.out.println("2. Update Student (Limited)");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student by ID");
            System.out.println("5. View All Students");
            System.out.println("6. Back");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Type (Undergrad/Grad/PhD): ");
                    String type = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();

                    Student student = null;
                    if ("Undergrad".equalsIgnoreCase(type)) {
                        student = new UndergraduateStudent(name, id, email, phone);
                    } else if ("Grad".equalsIgnoreCase(type)) {
                        System.out.print("Advisor ID (optional): ");
                        String advisorId = scanner.nextLine();
                        Professor advisor = advisorId.isBlank() ? null : (Professor) personDAO.read(advisorId);
                        student = new GraduateStudent(name, id, email, phone, advisor);
                    } else if ("PhD".equalsIgnoreCase(type)) {
                        System.out.print("Advisor ID (optional): ");
                        String advisorId = scanner.nextLine();
                        Professor advisor = advisorId.isBlank() ? null : (Professor) personDAO.read(advisorId);
                        System.out.print("Dissertation Topic: ");
                        String topic = scanner.nextLine();
                        student = new PhDStudent(name, id, email, phone, advisor, topic);
                    }

                    if (student != null) {
                        studentManager.registerStudent(student);
                    } else {
                        System.out.println("Invalid student type.");
                    }
                    break;

                case 2:
                    System.out.println("Student Update: Only limited fields can be changed via other operations (e.g., grades).");
                    System.out.println("Name, Email, Phone are immutable after registration (encapsulation).");
                    break;

                case 3:
                    System.out.print("Enter Student ID to delete: ");
                    id = scanner.nextLine();
                    studentManager.deleteStudent(id);
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    id = scanner.nextLine();
                    Student found = studentManager.findStudentById(id);
                    if (found != null) {
                        System.out.println("Found: " + found.getName() + " | " + found.getRole());
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    studentManager.displayAllStudents();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void facultyMenu() {
        while (true) {
            System.out.println("\nFaculty Management:");
            System.out.println("1. Hire Faculty");
            System.out.println("2. Update Faculty Salary");
            System.out.println("3. Delete Faculty");
            System.out.println("4. Search Faculty by ID");
            System.out.println("5. View All Faculty");
            System.out.println("6. Back");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Type (Professor/AsstProf/Lecturer): ");
                    String type = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Salary: ");
                    double salary = getDoubleInput();

                    Faculty newFaculty = null;  // ← Different name to avoid conflict
                    if ("Professor".equalsIgnoreCase(type)) {
                        newFaculty = new Professor(name, id, email, phone, salary);
                    } else if ("AsstProf".equalsIgnoreCase(type)) {
                        newFaculty = new AssistantProfessor(name, id, email, phone, salary);
                    } else if ("Lecturer".equalsIgnoreCase(type)) {
                        newFaculty = new Lecturer(name, id, email, phone, salary);
                    }

                    if (newFaculty != null) {
                        System.out.print("Department Name: ");
                        String deptName = scanner.nextLine();
                        facultyManager.hireFaculty(newFaculty, deptName);
                    } else {
                        System.out.println("Invalid faculty type.");
                    }
                    break;

                case 2:
                    System.out.print("Enter Faculty ID: ");
                    id = scanner.nextLine();
                    Person person = personDAO.read(id);
                    if (!(person instanceof Faculty)) {
                        System.out.println("Faculty not found.");
                        break;
                    }
                    Faculty facultyToUpdate = (Faculty) person;  // ← Unique name
                    System.out.print("New Salary (current: $" + facultyToUpdate.getSalary() + "): ");
                    salary = getDoubleInput();
                    if (salary > 0) {
                        facultyToUpdate.adjustSalary(salary);
                        personDAO.update(facultyToUpdate);
                        System.out.println("Salary updated to $" + salary);
                    }
                    break;

                case 3:
                    System.out.print("Enter Faculty ID to delete: ");
                    id = scanner.nextLine();
                    personDAO.delete(id);
                    System.out.println("Faculty deleted if existed.");
                    break;

                case 4:
                    System.out.print("Enter Faculty ID: ");
                    id = scanner.nextLine();
                    person = personDAO.read(id);
                    if (person instanceof Faculty) {
                        Faculty foundFaculty = (Faculty) person;
                        System.out.println("Found: " + foundFaculty.getName() + " | " + foundFaculty.getRole() + " | Salary: $" + foundFaculty.getSalary());
                    } else {
                        System.out.println("Faculty not found.");
                    }
                    break;

                case 5:
                    System.out.println("=== All Faculty Members ===");
                    List<Person> allPersons = personDAO.getAll();
                    boolean found = false;
                    for (Person pers : allPersons) {
                        if (pers instanceof Faculty) {
                            Faculty fac = (Faculty) pers;
                            System.out.println(fac.getId() + " | " + fac.getName() + " | " + fac.getRole() + " | Salary: $" + fac.getSalary());
                            found = true;
                        }
                    }
                    if (!found) System.out.println("No faculty hired yet.");
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void courseMenu() {
        while (true) {
            System.out.println("\nCourse Management:");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course (Limited)");
            System.out.println("3. Delete Course");
            System.out.println("4. Search Course by Name");
            System.out.println("5. Enroll Student");
            System.out.println("6. Drop Student");
            System.out.println("7. View Enrolled Students");
            System.out.println("8. Back");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Capacity: ");
                    int capacity = getIntInput();
                    System.out.print("Instructor ID (blank for none): ");
                    String instrId = scanner.nextLine();
                    Faculty instr = instrId.isBlank() ? null : (Faculty) personDAO.read(instrId);

                    Course course = new Course(code, name, capacity, instr) {
                        @Override public double calculateFinalGrade(Student s) { return 0; }
                        @Override public boolean checkPrerequisites(Student s) { return true; }
                        @Override public void generateSyllabus() {}
                    };
                    courseDAO.create(course);
                    System.out.println("Course added successfully.");
                    break;

                case 2:
                    System.out.println("Course Update: Only instructor can be changed via re-assignment.");
                    System.out.println("Name and capacity are immutable after creation (design choice).");
                    break;

                case 3:
                    System.out.print("Code to delete: ");
                    code = scanner.nextLine();
                    courseDAO.delete(code);
                    System.out.println("Course deleted if existed.");
                    break;

                case 4:
                    System.out.print("Name part: ");
                    name = scanner.nextLine();
                    List<Course> courses = courseDAO.searchByName(name);
                    if (courses.isEmpty()) {
                        System.out.println("No courses found.");
                    } else {
                        courses.forEach(c -> System.out.println(c.getCourseCode() + ": " + c.getCourseName() +
                                " (Instructor: " + (c.getInstructor() != null ? c.getInstructor().getName() : "None") + ")"));
                    }
                    break;

                case 5:
                    System.out.print("Student ID: ");
                    String studId = scanner.nextLine();
                    System.out.print("Course Code: ");
                    code = scanner.nextLine();
                    courseDAO.enrollStudent(studId, code);
                    break;

                case 6:
                    System.out.print("Student ID: ");
                    studId = scanner.nextLine();
                    System.out.print("Course Code: ");
                    code = scanner.nextLine();
                    courseDAO.dropStudent(studId, code);
                    break;

                case 7:
                    System.out.print("Course Code: ");
                    code = scanner.nextLine();
                    List<Student> enrolled = courseDAO.getEnrolledStudents(code);
                    if (enrolled.isEmpty()) {
                        System.out.println("No students enrolled.");
                    } else {
                        enrolled.forEach(s -> System.out.println(s.getId() + " | " + s.getName()));
                    }
                    break;

                case 8:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void gradingMenu() {
        while (true) {
            System.out.println("\nGrading Module:");
            System.out.println("1. Assign Grade");
            System.out.println("2. View Transcript");
            System.out.println("3. Calculate GPA");
            System.out.println("4. Back");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Student ID: ");
                    String studId = scanner.nextLine();
                    System.out.print("Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Grade (A/B/C/D/F): ");
                    String grade = scanner.nextLine().toUpperCase();
                    transcriptDAO.addGrade(studId, code, grade);
                    System.out.println("Grade assigned.");
                    break;
                case 2:
                    System.out.print("Student ID: ");
                    studId = scanner.nextLine();
                    Transcript trans = transcriptDAO.getTranscript(studId);
                    if (trans.getGrades().isEmpty()) {
                        System.out.println("No grades recorded.");
                    } else {
                        trans.getGrades().forEach((c, g) -> System.out.println(c + ": " + g));
                    }
                    break;
                case 3:
                    System.out.print("Student ID: ");
                    studId = scanner.nextLine();
                    double gpa = transcriptDAO.calculateGPA(studId);
                    System.out.println("GPA: " + String.format("%.2f", gpa));
                    break;
                case 4: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private void libraryMenu() {
        while (true) {
            System.out.println("\nLibrary Module:");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Search Books");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Borrow History");
            System.out.println("8. Back");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    Book newBook = new Book(isbn, title, author);
                    libraryDAO.createBook(newBook);
                    break;

                case 2:
                    System.out.print("Enter ISBN of book to update: ");
                    isbn = scanner.nextLine();
                    System.out.print("New Title (leave blank to keep current): ");
                    title = scanner.nextLine();
                    System.out.print("New Author (leave blank to keep current): ");
                    author = scanner.nextLine();

                    if (title.isBlank() && author.isBlank()) {
                        System.out.println("No changes entered. Update cancelled.");
                        break;
                    }

                    // Fetch current book to preserve fields not being updated
                    List<Book> foundBooks = libraryDAO.searchBooks(isbn); // searches by ISBN if exact
                    Book bookToUpdate = null;
                    for (Book b : foundBooks) {
                        if (b.getIsbn().equals(isbn)) {
                            bookToUpdate = b;
                            break;
                        }
                    }

                    if (bookToUpdate == null) {
                        System.out.println("Book with ISBN " + isbn + " not found.");
                        break;
                    }

                    // Apply changes only if provided
                    if (!title.isBlank()) {
                        bookToUpdate = new Book(bookToUpdate.getIsbn(), title, bookToUpdate.getAuthor());
                    }
                    if (!author.isBlank()) {
                        bookToUpdate = new Book(bookToUpdate.getIsbn(),
                                title.isBlank() ? bookToUpdate.getTitle() : title,
                                author);
                    }

                    libraryDAO.updateBook(bookToUpdate);
                    break;

                case 3:
                    System.out.print("Enter ISBN to delete: ");
                    isbn = scanner.nextLine();
                    libraryDAO.deleteBook(isbn);
                    break;

                case 4:
                    System.out.print("Enter title or author to search: ");
                    String searchTerm = scanner.nextLine();
                    if (searchTerm.isBlank()) {
                        System.out.println("Search term cannot be empty.");
                        break;
                    }
                    libraryDAO.searchBooks(searchTerm); // Already prints formatted results
                    break;

                case 5:
                    System.out.print("ISBN: ");
                    isbn = scanner.nextLine();
                    System.out.print("Borrower ID: ");
                    String borrowerId = scanner.nextLine();
                    if (isbn.isBlank() || borrowerId.isBlank()) {
                        System.out.println("ISBN and Borrower ID are required.");
                        break;
                    }
                    Date dueDate = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000); // 7 days
                    libraryDAO.borrowBook(isbn, borrowerId, dueDate);
                    break;
                case 6:
                    System.out.print("Enter ISBN to return: ");
                    isbn = scanner.nextLine();
                    if (isbn.isBlank()) {
                        System.out.println("ISBN is required.");
                        break;
                    }
                    libraryDAO.returnBook(isbn);
                    break;
                case 7:
                    System.out.print("Enter Borrower ID: ");
                    borrowerId = scanner.nextLine();
                    if (borrowerId.isBlank()) {
                        System.out.println("Borrower ID is required.");
                        break;
                    }
                    libraryDAO.getBorrowHistory(borrowerId); // Already prints formatted history
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private void financeMenu() {
        while (true) {
            System.out.println("\nFinance Module:");
            System.out.println("1. Create Account");
            System.out.println("2. Update Balance (Pay Fee / Deposit Salary)");
            System.out.println("3. Delete Account");
            System.out.println("4. View Accounts by Owner");
            System.out.println("5. Back");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Type (Tuition/Salary/Scholarship): ");
                    String type = scanner.nextLine().trim();
                    System.out.print("Account ID: ");
                    String acctId = scanner.nextLine().trim();
                    System.out.print("Initial Balance: $");
                    double bal = getDoubleInput();
                    System.out.print("Owner Person ID (Student/Faculty ID): ");
                    String ownerId = scanner.nextLine().trim();

                    if (acctId.isBlank() || ownerId.isBlank()) {
                        System.out.println("Account ID and Owner ID are required.");
                        break;
                    }

                    FinanceAccount acct = null;
                    if ("Tuition".equalsIgnoreCase(type)) {
                        acct = new TuitionAccount(acctId, bal);
                    } else if ("Salary".equalsIgnoreCase(type)) {
                        acct = new SalaryAccount(acctId, bal);
                    } else if ("Scholarship".equalsIgnoreCase(type)) {
                        acct = new ScholarshipAccount(acctId, bal);
                    } else {
                        System.out.println("Invalid account type. Use Tuition, Salary, or Scholarship.");
                        break;
                    }

                    financeDAO.create(acct, ownerId);
                    break;

                case 2:
                    System.out.print("Enter Account ID: ");
                    acctId = scanner.nextLine().trim();
                    if (acctId.isBlank()) {
                        System.out.println("Account ID required.");
                        break;
                    }

                    FinanceAccount existing = financeDAO.read(acctId);
                    if (existing == null) {
                        System.out.println("Account not found.");
                        break;
                    }

                    System.out.println("Current Balance: $" + String.format("%.2f", existing.getBalance()));
                    System.out.print("New Balance (after payment/deposit): $");
                    bal = getDoubleInput();

                    financeDAO.updateBalance(acctId, bal);
                    break;

                case 3:
                    System.out.print("Enter Account ID to delete: ");
                    acctId = scanner.nextLine().trim();
                    if (acctId.isBlank()) {
                        System.out.println("Account ID required.");
                        break;
                    }
                    financeDAO.delete(acctId);
                    break;

                case 4:
                    System.out.print("Enter Owner Person ID: ");
                    ownerId = scanner.nextLine().trim();
                    if (ownerId.isBlank()) {
                        System.out.println("Owner ID required.");
                        break;
                    }
                    financeDAO.getAccountsByOwner(ownerId); // Already prints formatted list
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Module:");
            System.out.println("1. Add Department");
            System.out.println("2. Generate Student Course Report");
            System.out.println("3. Manage Facilities (Stub)");
            System.out.println("4. Back");
            int choice = getIntInput();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Budget: ");
                    double budget = getDoubleInput();
                    department dept = new department(name, budget);
                    departmentDAO.create(dept);
                    System.out.println("Department created: " + name);
                    break;
                case 2:
                    System.out.println("Student Course Report:");
                    String sql = "SELECT p.name, c.course_name FROM Person p " +
                            "JOIN Student_Course sc ON p.id = sc.student_id " +
                            "JOIN Course c ON sc.course_code = c.course_code " +
                            "WHERE p.role LIKE '%Student%'";
                    try (java.sql.Connection conn = util.DatabaseConnection.getConnection();
                         java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
                         java.sql.ResultSet rs = pstmt.executeQuery()) {
                        boolean found = false;
                        while (rs.next()) {
                            System.out.println(rs.getString("name") + " enrolled in " + rs.getString("course_name"));
                            found = true;
                        }
                        if (!found) System.out.println("No enrollments yet.");
                    } catch (java.sql.SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Facilities management not implemented yet.");
                    break;
                case 4: return;
                default: System.out.println("Invalid.");
            }
        }
    }

    private int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid number. Retry: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private double getDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid number. Retry: ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }
}