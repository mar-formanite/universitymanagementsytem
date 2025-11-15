package people;

import java.util.List;
import java.util.ArrayList;

public class StaffManager {
    private List<Staff> staffList;

    public StaffManager() {
        this.staffList = new ArrayList<>();
    }

    public void hireStaff(Staff staff) {
        staffList.add(staff);
    }

    public List<Staff> getStaffList() {
        return staffList;
    }
}