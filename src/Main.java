import people.*;
import courses.*;
import departments.*;
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
        // Student registration and profile management
        UndergraduateStudent undergrad = new UndergraduateStudent("Alice Johnson", "S001", "alice@uni.edu", "555-1234");
        GraduateStudent grad = new GraduateStudent("Bob Smith", "S002", "bob@uni.edu", "555-5678", null);  // Fixed: Removed extra "Thesis Topic" argument
        PhDStudent phd = new PhDStudent("Charlie Brown", "S003", "charlie@uni.edu", "555-9012", null, "Dissertation Topic");
        undergrad.register(); // Abstraction: Calls overridden method
        grad.register();
        phd.register();
        // Profile management: Update grades (Encapsulation - controlled access)
        undergrad.updateGrade("CS101", "A");
        undergrad.updateGrade("MATH101", "B");
        System.out.println("Undergrad GPA: " + undergrad.getGPA()); // Controlled calculation

        // ===========================================
        // DEPARTMENT SYSTEM (Moved up to create department before faculty hiring)
        // ===========================================
        System.out.println("3. DEPARTMENT SYSTEM");
        // Department creation
        department csDept = new department("Computer Science", 100000);  // Updated: Use lowercase 'department'
        System.out.println("Department created: " + csDept.getName());  // Now works with added getName()

        // Faculty hiring and assignment (Now with valid department)
        Professor prof = new Professor("Dr. Smith", "F001", "smith@uni.edu", "555-1111", 80000);
        AssistantProfessor asstProf = new AssistantProfessor("Dr. Jones", "F002", "jones@uni.edu", "555-2222", 60000);
        Lecturer lecturer = new Lecturer("Ms. Lee", "F003", "lee@uni.edu", "555-3333", 50000);
        FacultyManager facultyMgr = new FacultyManager();
        facultyMgr.hireFaculty(prof, csDept);  // Updated: Pass csDept instead of null
        facultyMgr.hireFaculty(asstProf, csDept);  // Updated: Pass csDept
        facultyMgr.hireFaculty(lecturer, csDept);  // Updated: Pass csDept
        System.out.println("Faculty hired: " + facultyMgr.getFacultyList().size() + " members");

        // Faculty assignment to departments (Already done via hireFaculty)
        System.out.println("Faculty in CS Dept: " + csDept.getFacultyMembers().size());

        // Staff management
        Administrator admin = new Administrator("Admin User", "ST001", "admin@uni.edu", "555-4444", 45000, List.of("Manage Records"));
        Librarian librarian = new Librarian("Lib User", "ST002", "lib@uni.edu", "555-5555", 40000, List.of("Catalog Books"));
        TechnicalStaff techStaff = new TechnicalStaff("Tech User", "ST003", "tech@uni.edu", "555-6666", 42000, List.of("Maintain Systems"));
        StaffManager staffMgr = new StaffManager();
        staffMgr.hireStaff(admin);
        staffMgr.hireStaff(librarian);
        staffMgr.hireStaff(techStaff);
        System.out.println("Staff hired: " + staffMgr.getStaffList().size() + " members\n");

        // ===========================================
        // ACADEMIC OPERATIONS
        // ===========================================
        System.out.println("2. ACADEMIC OPERATIONS");
        // Course creation and management
        Course cs101 = new Course("CS101", "Intro to CS", 30, prof) {
            @Override
            public double calculateFinalGrade(Student student) { return 85.0; } // Example grade
            @Override
            public boolean checkPrerequisites(Student student) { return true; } // Simple check
            @Override
            public void generateSyllabus() { System.out.println("Syllabus for CS101 generated."); }
        };
        cs101.generateSyllabus(); // Abstraction: Calls overridden method

        // Enrollment system (add/drop courses)
        undergrad.enrollInCourse(cs101); // Enrollable interface
        grad.enrollInCourse(cs101);
        System.out.println("Students enrolled in CS101: " + cs101.getEnrolledStudents().size());
        undergrad.dropCourse(cs101); // Drop course
        System.out.println("After drop, students in CS101: " + cs101.getEnrolledStudents().size());

        // Grade management and GPA calculation
        undergrad.updateGrade("CS101", "A"); // Encapsulation: Controlled update
        undergrad.updateGrade("MATH101", "B");
        System.out.println("Undergrad GPA after updates: " + undergrad.getGPA()); // Controlled calculation

        // Prerequisite checking
        boolean canEnroll = cs101.checkPrerequisites(undergrad); // Abstraction: Calls overridden method
        System.out.println("Can undergrad enroll in CS101? " + canEnroll + "\n");

        // Course offerings per department
        csDept.addCourse(cs101); // Aggregation: Courses exist independently
        System.out.println("Courses in CS Dept: " + csDept.getOfferedCourses().size());

        // Budget management (composition)
        csDept.getBudget().spend(50000); // Composition: Budget owned by department
        System.out.println("Remaining budget in CS Dept: $" + csDept.getBudget().getRemaining() + "\n");

        // ===========================================
        // LIBRARY SYSTEM
        // ===========================================
        System.out.println("4. LIBRARY SYSTEM");
        // Book catalog
        Library library = new Library();
        Book book1 = new Book("12345", "Java OOP Guide", "Author A");
        Book book2 = new Book("67890", "Data Structures", "Author B");
        library.addBook(book1); // Aggregation: Books exist independently
        library.addBook(book2);
        System.out.println("Books in catalog: " + library.getBooks().size());

        // Borrowing system with different limits per role (Polymorphism)
        LibraryManager libMgr = new LibraryManager(library);
        undergrad.accessLibrary(); // Polymorphism: Shows limit for students
        prof.accessLibrary(); // Polymorphism: Shows limit for faculty
        libMgr.borrowBook(undergrad, "12345"); // Borrowing with limits
        libMgr.borrowBook(prof, "67890");

        // Due dates and fines calculation
        BorrowRecord record = new BorrowRecord(book1, undergrad.getId(), new Date(), new Date(System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000)); // 2 days overdue
        double fine = record.calculateFine(); // Fine calculation
        System.out.println("Fine for overdue book: $" + fine);

        // Resource sharing (aggregation)
        System.out.println("Shared resources: " + library.getBooks().size() + " books available\n");

        // ===========================================
        // FINANCIAL SYSTEM
        // ===========================================
        System.out.println("5. FINANCIAL SYSTEM");
        // Tuition fee calculation (varies by program)
        TuitionAccount tuitionAcct = new TuitionAccount("T001", 0);
        tuitionAcct.deposit(undergrad.calculateFeesOrSalary()); // Polymorphism: Fees for students
        System.out.println("Tuition deposited: $" + tuitionAcct.getBalance());

        // Salary processing for faculty/staff
        SalaryAccount salaryAcct = new SalaryAccount("S001", 0);
        salaryAcct.deposit(prof.calculateFeesOrSalary()); // Polymorphism: Salary for faculty
        salaryAcct.deposit(admin.calculateFeesOrSalary()); // Polymorphism: Salary for staff
        System.out.println("Salaries processed: $" + salaryAcct.getBalance());

        // Scholarship management
        ScholarshipAccount scholarshipAcct = new ScholarshipAccount("SC001", 10000);
        scholarshipAcct.withdraw(2000); // Manage scholarships
        System.out.println("Scholarship balance: $" + scholarshipAcct.getBalance());

        // Payment processing (Payable interface)
        undergrad.processPayment(5000); // Payable: Tuition payment
        prof.processPayment(80000); // Payable: Salary payment
        System.out.println("Payments processed via Payable interface\n");

        // ===========================================
        // FACILITY MANAGEMENT
        // ===========================================
        System.out.println("6. FACILITY MANAGEMENT");
        // Classroom allocation
        Classroom classroom = new Classroom("CR101", "Building A", 50);
        classroom.reserve(); // Reserve classroom
        System.out.println("Classroom allocated: " + classroom.getId());

        // Lab assignments (association with courses)
        Lab lab = new Lab("L101", "Building B", "Computers");
        lab.reserve(); // Reserve lab
        // Association: Lab linked to course (simulated via reservation)
        FacilityReservation labReservation = new FacilityReservation(lab, new Date(), cs101.getCourseCode());
        labReservation.confirmReservation();
        System.out.println("Lab assigned to course: " + cs101.getCourseCode());

        // Office assignments (association with faculty)
        Office office = new Office("O101", "Building C", prof); // Association: One-to-One with Faculty
        office.reserve();
        System.out.println("Office assigned to faculty: " + office.getAssignedFaculty().getName());

        // Campus resources (general facilities)
        System.out.println("Campus resources managed: Classrooms, Labs, Offices\n");

        // ===========================================
        // POLYMORPHISM DEMONSTRATION (Across Roles)
        // ===========================================
        System.out.println("7. POLYMORPHISM ACROSS ROLES");
        List<Person> universityMembers = List.of(undergrad, grad, phd, prof, asstProf, lecturer, admin, librarian, techStaff);
        for (Person person : universityMembers) {
            person.displayDashboard(); // Polymorphism: Each shows role-specific info
            person.getRole(); // Polymorphism: Returns different roles
            System.out.println("Workload: " + person.calculateWorkload()); // Polymorphism: Different calculations
        }
    }
}
