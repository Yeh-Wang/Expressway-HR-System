package org.yeh.wang.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftScheduler {

    // 定义班次的枚举
    enum Shift {
        MORNING, AFTERNOON, NIGHT
    }

    // 员工类
    static class Employee {
        String name;
        int hoursAvailable;

        public Employee(String name, int hoursAvailable) {
            this.name = name;
            this.hoursAvailable = hoursAvailable;
        }
    }

    // 排班方法
    public static Map<Employee, List<Shift>> scheduleShifts(List<Employee> employees, int shiftsPerDay, int daysPerWeek) {
        Map<Employee, List<Shift>> schedule = new HashMap<>();
        // 初始化排班表
        for (Employee employee : employees) {
            schedule.put(employee, new ArrayList<>());
        }

        // 分配班次
        for (int day = 0; day < daysPerWeek; day++) {
            for (int shiftIndex = 0; shiftIndex < shiftsPerDay; shiftIndex++) {
                Shift shift = Shift.values()[shiftIndex % 3]; // 获取当前班次
                for (Employee employee : employees) {
                    // 检查员工是否还有可用工作时长
                    if (employee.hoursAvailable > 0) {
                        // 分配班次
                        schedule.get(employee).add(shift);
                        // 减少可用工作时长
                        employee.hoursAvailable -= 8; // 假设每个班次是8小时
                    }
                }
            }
        }
        return schedule;
    }

    // 主方法，用于演示排班
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Alice", 40));
        employees.add(new Employee("Bob", 40));
        employees.add(new Employee("Charlie", 40));
        employees.add(new Employee("David", 40));

        Map<Employee, List<Shift>> schedule = scheduleShifts(employees, 3, 7);

        // 打印排班表
        for (Map.Entry<Employee, List<Shift>> entry : schedule.entrySet()) {
            System.out.println(entry.getKey().name + "'s schedule: " + entry.getValue().toString());
        }
    }
}
