package com.tienthanh.employeeservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tienthanh.employeeservice.command.data.Employee;
import com.tienthanh.employeeservice.command.data.EmployeeRepository;

@Component
public class EmployeeEventHandler {
	@Autowired
	private EmployeeRepository employeeRepository;

	@EventHandler
	public void on(EmployeeCreatedEvent event) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(event, employee);
		employeeRepository.save(employee);
	}

	@EventHandler
	public void on(EmployeeUpdatedEvent event) {
		Employee employee = employeeRepository.getById(event.getEmployeeId());
		employee.setFirstName(event.getFirstName());
		employee.setLastName(event.getLastName());
		employee.setKin(event.getKin());
		employee.setIsDisciplined(event.getIsDisciplined());
		employeeRepository.save(employee);
	}

	@EventHandler
	public void on(EmployeeDeletedEvent event) {
		try {
			employeeRepository.deleteById(event.getEmployeeId());
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

	}

}
