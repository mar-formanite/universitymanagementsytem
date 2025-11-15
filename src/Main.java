import people.*;
import courses.*;
import departments.department;
import facility.*;
import finance.*;
import library.*;
import researchproject.*;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Smart University Management System Demonstration ===\n");

        // ===========================================
        // PERSON MANAGEMENT
        // ===========================================
        System.out.println("1. PERSON MANAGEMENT");
        UndergraduateStudent undergrad = new UndergraduateStudent("Alice Johnson", "S001", "alice@uni.edu", "555-1234");
        GraduateStudent grad = new GraduateStudent("Bob Smith", "S002", "bob@uni.edu", "555-5678", null);
        PhDStudent phd = new PhDStudent("Charlie Brown", "S003", "charlie@uni.edu", "555-9012", null, "Dissertation Topic");
        undergrad.register();
        grad.register();
        phd.register();
        undergrad.updateGrade("CS101", "A");
        undergrad.updateGrade("MATH101", "B");
        System.out.println("Undergrad GPA: " + undergrad.getGPA());

        department csDept = new department("Computer Science", 100000);
        System.out.println("Department created: " + csDept.getName());

        Professor prof = new Professor("Dr. Smith", "F001", "smith@uni.edu", "555-1111", 80000);
        AssistantProfessor asstProf = new AssistantProfessor("Dr. Jones", "F002", "jones@uni.edu", "555-2222", 60000);
        Lecturer lecturer = new Lecturer("Ms. Lee", "F003", "lee@uni.edu", "555-3333", 50000);
        FacultyManager facultyMgr = new FacultyManager();
        facultyMgr.hireFaculty(prof, csDept);
        facultyMgr.hireFaculty(asstProf, csDept);
        facultyMgr.hireFaculty(lecturer, csDept);
        System.out.println("Faculty hired: " + facultyMgr.getFacultyList().size() + " members");

        Administrator admin = new Administrator("Admin User", "ST001", "admin@uni.edu", "555-4444", 45000, List.of("Manage Records"));
        Librarian librarian = new Librarian("Lib User", "ST002", "lib@uni.edu", "555-5555", 40000, List.of("Catalog Books"));
        TechnicalStaff techStaff = new TechnicalStaff("Tech User", "ST003", "tech@uni.edu", "555-6666", 42000, List.of("Maintain Systems"));
        StaffManager staffMgr = new StaffManager();
        staffMgr.hireStaff(admin);
        staffMgr.hireStaff(librarian);
        staffMgr.hireStaff(techStaff);
        System.out.println("Staff hired: " + staffMgr.getStaffList().size() + " members\n");

        // ===========================================
        // TEST SCENARIOS
        // ===========================================
        System.out.println("=== TEST SCENARIOS ===\n");

        // Test Scenario 1: Student enrolling in multiple courses
        System.out.println("Test 1: Student Enrolling in Multiple Courses");
        Course cs101 = new Course("CS101", "Intro to CS", 30, prof) {
            @Override public double calculateFinalGrade(Student student) { return 85.0; }
            @Override public boolean checkPrerequisites(Student student) { return true; }
            @Override public void generateSyllabus() { System.out.println("Syllabus for CS101 generated."); }
        };
        Course math101 = new Course("MATH101", "Calculus", 25, asstProf) {
            @Override public double calculateFinalGrade(Student student) { return 90.0; }
            @Override public boolean checkPrerequisites(Student student) { return true; }
            @Override public void generateSyllabus() { System.out.println("Syllabus for MATH101 generated."); }
        };
        undergrad.enrollInCourse(cs101); // Enrollable interface
        undergrad.enrollInCourse(math101);
        System.out.println("Enrolled students in CS101: " + cs101.getEnrolledStudents().size() + "\n");

        // Test Scenario 2: Professor teaching and grading
        System.out.println("Test 2: Professor Teaching and Grading");
        prof.teach(cs101); // Teachable interface
        prof.assignGrades(cs101); // Assigns grades
        System.out.println("Professor taught and graded CS101. Final grade for undergrad: " + cs101.calculateFinalGrade(undergrad) + "\n");

        // Test Scenario 3: Library book borrowing
        System.out.println("Test 3: Library Book Borrowing");
        Library library = new Library();
        Book book1 = new Book("12345", "Java OOP Guide", "Author A");
        library.addBook(book1);
        LibraryManager libMgr = new LibraryManager(library);
        undergrad.accessLibrary(); // Polymorphism: Shows student limit
        libMgr.borrowBook(undergrad, "12345"); // Borrowing
        BorrowRecord record = new BorrowRecord(book1, undergrad.getId(), new Date(), new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)); // Due in 7 days
        System.out.println("Book borrowed. Fine if overdue: $" + record.calculateFine() + "\n");

        // Test Scenario 4: Department managing faculty
        System.out.println("Test 4: Department Managing Faculty");
        csDept.addFaculty(prof); // Association
        csDept.addCourse(cs101); // Aggregation
        System.out.println("CS Dept faculty: " + csDept.getFacultyMembers().size() + ", Courses: " + csDept.getOfferedCourses().size());
        csDept.getBudget().spend(50000); // Composition
        System.out.println("Budget remaining: $" + csDept.getBudget().getRemaining() + "\n");

        // Test Scenario 5: Financial transactions
        System.out.println("Test 5: Financial Transactions");
        TuitionAccount tuitionAcct = new TuitionAccount("T001", 0);
        undergrad.processPayment(5000); // Payable interface
        tuitionAcct.deposit(undergrad.calculateFeesOrSalary()); // Polymorphism
        SalaryAccount salaryAcct = new SalaryAccount("S001", 0);
        prof.processPayment(80000); // Payable interface
        salaryAcct.deposit(prof.calculateFeesOrSalary()); // Polymorphism
        System.out.println("Tuition balance: $" + tuitionAcct.getBalance() + ", Salary balance: $" + salaryAcct.getBalance() + "\n");

        // Test Scenario 6: Schedule generation
        System.out.println("Test 6: Schedule Generation");
        undergrad.viewSchedule(); // Enrollable interface: Shows enrolled courses
        System.out.println("Schedule generated for undergrad.\n");

        // ===========================================
        // POLYMORPHISM DEMONSTRATION
        // ===========================================
        System.out.println("7. POLYMORPHISM ACROSS ROLES");
        List<Person> universityMembers = List.of(undergrad, grad, phd, prof, asstProf, lecturer, admin, librarian, techStaff);
        for (Person person : universityMembers) {
            person.displayDashboard();
            System.out.println("Workload: " + person.calculateWorkload());
        }
    }
}
