package com.umg.voxel.chequealo.service;

import net.sf.jasperreports.engine.*;
import com.umg.voxel.chequealo.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.apache.commons.collections4.map.HashedMap;
import com.umg.voxel.chequealo.utils.DelayEmployeeReport;
import com.umg.voxel.chequealo.utils.GeneralMarkingReport;
import com.umg.voxel.chequealo.repository.DelayRepository;
import com.umg.voxel.chequealo.utils.EmployeeMarkingReport;
import com.umg.voxel.chequealo.repository.MarkingRepository;
import com.umg.voxel.chequealo.utils.DepartmentMarkingReport;
import com.umg.voxel.chequealo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.umg.voxel.chequealo.repository.JobPositionRepository;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.util.Map;
import java.util.List;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.io.FileNotFoundException;

@Service
public class ReportsService {

    @Autowired
    private MarkingRepository markingRepository;

    @Autowired
    private DelayRepository delayRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * @param reportFormat the reportFormat
     * @return string
     * @throws FileNotFoundException
     * @throws JRException
     */
    public String generateMarkingsReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "reports/general-employee-marking/";
        String finalPath = "";
        ArrayList<GeneralMarkingReport> markingsToReport = new ArrayList<>();
        List<Marking> markings = markingRepository.findAll();

        for (Marking marking : markings) {
            List<Delay> delays = delayRepository.findAllByMarking(marking);

            GeneralMarkingReport markRep = new GeneralMarkingReport();
            markRep.setMarkingId(marking.getId());
            markRep.setUsername(marking.getEmployee().getUser().getUsername());
            markRep.setDepartment(marking.getEmployee().getJobPosition().getDepartment().getName());
            markRep.setJobPosition(marking.getEmployee().getJobPosition().getName());
            markRep.setEntryAt((Timestamp) marking.getEntryAt());
            markRep.setDepartureAt((Timestamp) marking.getDepartureAt());

            for (Delay delay : delays) {
                if (delay.getType().equals(Delay.TYPE_DELAY)) {
                    markRep.setDelayAt((Timestamp) delay.getCreatedAt());
                } else {
                    markRep.setAdvanceAt((Timestamp) delay.getCreatedAt());
                }
            }

            markingsToReport.add(markRep);
        }

        // Load file and compile it
        File file = ResourceUtils.getFile("classpath:general_marking.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(markingsToReport);
        Map<String, Object> parameters = new HashedMap<>();
        parameters.put("createdBy", "Voxel");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            finalPath = path + "general_marking.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, finalPath);
        } else {
            finalPath = path + "general_marking.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, finalPath);
        }

        return finalPath;
    }

    /**
     * @param type         the type
     * @param reportFormat the reportFormat
     * @return string
     * @throws FileNotFoundException
     * @throws JRException
     */
    public String generateDelaysReport(String type, String reportFormat) throws FileNotFoundException, JRException {
        String path = "reports/employees-" + type + "/";
        String finalPath = "";
        ArrayList<DelayEmployeeReport> markingsToReport = new ArrayList<>();
        List<Delay> delays = delayRepository.findAllByType(type);
        ArrayList<Employee> employees = new ArrayList<>();

        for (Delay delay : delays) {
            Marking marking = delay.getMarking();
            Employee employee = marking.getEmployee();

            if (employees.contains(employee)) {
                int index = employees.indexOf(employee);
                employee = employees.get(index);
                employee.quantity++;
                employees.set(index, employee);
            } else {
                employee.quantity++;
                employees.add(employee);
            }
        }

        for (Employee employee : employees) {
            Cuser user = employee.getUser();
            DelayEmployeeReport markRep = new DelayEmployeeReport();
            markRep.setCuserId(user.getId());
            markRep.setFullName(employee.getFirstName() + " " + employee.getLastName());
            markRep.setUsername(user.getUsername());
            markRep.setDepartment(employee.getJobPosition().getDepartment().getName());
            markRep.setJobPosition(employee.getJobPosition().getName());
            markRep.setQuantity(employee.quantity);

            markingsToReport.add(markRep);
        }

        // Load file and compile it
        File file = ResourceUtils.getFile("classpath:" + type + "_employees.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(markingsToReport);
        Map<String, Object> parameters = new HashedMap<>();
        parameters.put("createdBy", "Voxel");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            finalPath = path + type + "_employees_marking.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, finalPath);
        } else {
            finalPath = path + type + "_employees_marking.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, finalPath);
        }

        return finalPath;
    }

    /**
     * @param department   the department
     * @param reportFormat the reportFormat
     * @return string
     * @throws FileNotFoundException
     * @throws JRException
     */
    public String generateDepartmentReport(Department department, String reportFormat) throws FileNotFoundException, JRException {
        String path = "reports/department-employee-marking/";
        String finalPath = "";
        ArrayList<DepartmentMarkingReport> markingsToReport = new ArrayList<>();
        ArrayList<Employee> employees = new ArrayList<>();
        List<JobPosition> jobPositions = jobPositionRepository.findAllByDepartment(department);

        for (JobPosition jobPosition : jobPositions) {
            List<Employee> employeesWithJob = employeeRepository.findAllByJobPosition(jobPosition);

            employees.addAll(employeesWithJob);
        }

        for (Employee employee : employees) {
            List<Marking> markings = markingRepository.findAllByEmployee(employee);

            for (Marking marking : markings) {
                List<Delay> delays = delayRepository.findAllByMarking(marking);

                DepartmentMarkingReport markRep = new DepartmentMarkingReport();
                markRep.setMarkingId(marking.getId());
                markRep.setUsername(employee.getUser().getUsername());
                markRep.setJobPosition(employee.getJobPosition().getName());
                markRep.setEntryAt((Timestamp) marking.getEntryAt());
                markRep.setDepartureAt((Timestamp) marking.getDepartureAt());

                for (Delay delay : delays) {
                    if (delay.getType().equals(Delay.TYPE_DELAY)) {
                        markRep.setDelayAt((Timestamp) delay.getCreatedAt());
                    } else {
                        markRep.setAdvanceAt((Timestamp) delay.getCreatedAt());
                    }
                }

                markingsToReport.add(markRep);
            }
        }

        // Load file and compile it
        File file = ResourceUtils.getFile("classpath:department_marking.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(markingsToReport);
        Map<String, Object> parameters = new HashedMap<>();
        parameters.put("createdBy", "Voxel");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            finalPath = path + department.getName().replace(" ", "_") + "_employee_marking.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, finalPath);
        } else {
            finalPath = path + department.getName().replace(" ", "_") + "_employee_marking.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, finalPath);
        }

        return finalPath;
    }

    /**
     * @param employee     the employee
     * @param reportFormat the reportFormat
     * @return string
     * @throws FileNotFoundException
     * @throws JRException
     */
    public String generateEmployeeReport(Employee employee, String reportFormat) throws FileNotFoundException, JRException {
        ArrayList<EmployeeMarkingReport> markingsToReport = new ArrayList<>();
        List<Marking> markings = markingRepository.findAllByEmployee(employee);
        String path = "reports/single-employee-marking/";
        String finalPath = "";

        for (Marking marking : markings) {
            List<Delay> delays = delayRepository.findAllByMarking(marking);

            EmployeeMarkingReport markRep = new EmployeeMarkingReport();
            markRep.setMarkingId(marking.getId());
            markRep.setEntryAt((Timestamp) marking.getEntryAt());
            markRep.setDepartureAt((Timestamp) marking.getDepartureAt());

            for (Delay delay : delays) {
                if (delay.getType().equals(Delay.TYPE_DELAY)) {
                    markRep.setDelayAt((Timestamp) delay.getCreatedAt());
                } else {
                    markRep.setAdvanceAt((Timestamp) delay.getCreatedAt());
                }
            }

            markingsToReport.add(markRep);
        }

        // Load file and compile it
        File file = ResourceUtils.getFile("classpath:employee_marking.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(markingsToReport);
        Map<String, Object> parameters = new HashedMap<>();
        parameters.put("createdBy", "Voxel");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            finalPath = path + employee.getUser().getUsername() + "_employee_marking.html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, finalPath);
        } else {
            finalPath = path + employee.getUser().getUsername() + "_employee_marking.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, finalPath);
        }

        return finalPath;
    }
}
