package com.jasperreport.example.service;

import com.jasperreport.example.entity.Employee;
import com.jasperreport.example.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\home\\jasper";
        List<Employee> employees = employeeRepository.findAll();

        File file = ResourceUtils.getFile("classpath:jasper-example.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("createdBy", "Fadiel");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\employees.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path +"\\employees.pdf");
        }

        return "Report generated to : " + path;
    }


}
