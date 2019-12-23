package com.avi.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeTest {

    private static Employee[] arrayOfEmps = {
            new Employee(1, "Rahul", 100000.0),
            new Employee(2, "Vinayak", 200000.0),
            new Employee(3, "Jhon", 300000.0),
    };

    private static List<Employee> empList = Arrays.asList(arrayOfEmps);

    private static EmployeeRepository employeeRepository = new EmployeeRepository(empList);

    @Test
    public void whenBuildStreamFromElements() {
        Stream.Builder<Employee> employeeBuilder = Stream.builder();
        employeeBuilder.accept(arrayOfEmps[0]);
        employeeBuilder.accept(arrayOfEmps[1]);
        employeeBuilder.accept(arrayOfEmps[2]);

        Stream<Employee> employeeStream = employeeBuilder.build();
        assert (employeeStream instanceof Stream<?>);
    }

    @Test
    public void whenIncrementSalaryForEachEmployee() {
        List<Employee> employeeList = Arrays.asList(arrayOfEmps);
        employeeList.forEach(e -> e.salaryIncrement(10.0));
    }

    @Test
    public void whenMapIdToEmployees() {
        Integer[] empIds = {1, 2, 3};
        List<Employee> employees = Stream.of(empIds)
                .map(employeeRepository::findById)
                .collect(Collectors.toList());
    }

    @Test
    public void whenCollectStreamToList() {
        List<Employee> employeeList = empList.stream().collect(Collectors.toList());
    }

    @Test
    public void whenFilterEmployees() {
        Integer[] empIds = {1, 2, 3, 4};
        List<Employee> employeeList = Stream.of(empIds)
                .map(employeeRepository::findById)
                .filter(e -> e != null)
                .filter(e -> e.getSalary() > 200000)
                .collect(Collectors.toList());
    }

    @Test
    public void whenFindFirst() {
        Integer[] empIds = {1, 2, 3, 4};

        Employee employee = Stream.of(empIds)
                .map(employeeRepository::findById)
                .filter(e -> e != null)
                .filter(e -> e.getSalary() > 100000)
                .findFirst()
                .orElse(null);
    }

    @Test
    public void whenStreamToArray() {
        Employee[] employees = empList.stream().toArray(Employee[]::new);
    }

    @Test
    public void whenFlatMapEmployee() {
        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Rahul", "Jhon"),
                Arrays.asList("Vinayak", "Smith"),
                Arrays.asList("Amit", "Vaibhav"));
        List<String> list = namesNested.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Test
    public void whenIncrementSalaryByPeek() {
        empList.stream()
                .peek(e -> e.salaryIncrement(10.0))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    @Test
    public void whenInfiniteStream() {
        Stream<Integer> infiniteStream = Stream.iterate(2, i -> i * 2);
        List<Integer> integerList = infiniteStream.skip(3)
                .limit(5)
                .collect(Collectors.toList());
    }

    @Test
    public void whenSortStream() {
        List<Employee> employeeList = empList.stream()
                .sorted(Comparator.comparing(Employee::getName)).collect(Collectors.toList());
    }

    @Test
    public void WhenFindMinId() {
        Employee employee = empList.stream()
                .min((e1, e2) -> e1.getId() - e2.getId())
                .orElseThrow(NoSuchElementException::new);
    }

    @Test
    public void whenFindMaxSalary() {
        Employee employee = empList.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElseThrow(NoSuchElementException::new);
    }

    @Test
    public void whenApplyDistinct() {
        List<Integer> intList = Arrays.asList(2, 5, 3, 2, 4, 3);
        List<Integer> distinct = intList.stream().distinct().collect(Collectors.toList());
    }

    @Test
    public void whenApplyMatch_thenReturnBoolean() {
        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);

        boolean allEven = intList.stream().allMatch(i -> i % 2 == 0);
        boolean oneEven = intList.stream().anyMatch(i -> i % 2 == 0);
        boolean noneMultipleOfThree = intList.stream().noneMatch(i -> i % 3 == 0);
    }

    @Test
    public void whenFindMaxOnIntStream() {
        Integer latestEmpId = empList.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElseThrow(NoSuchElementException::new);
    }

    @Test
    public void whenApplySumOnIntStream_thenGetSum() {
        Double avgSal = empList.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElseThrow(NoSuchElementException::new);
    }


}
