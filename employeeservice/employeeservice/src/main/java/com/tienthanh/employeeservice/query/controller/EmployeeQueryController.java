package com.tienthanh.employeeservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienthanh.employeeservice.query.model.EmployeeReponseModel;
import com.tienthanh.employeeservice.query.queries.GetAllEmployeeQuery;
import com.tienthanh.employeeservice.query.queries.GetEmployeeQuery;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeQueryController {
	@Autowired
	private QueryGateway queryGateway;

	@GetMapping("/{employeeId}")
	public EmployeeReponseModel getEmployeeDetail(@PathVariable String employeeId) {
		System.out.println("CHECKING POINT EMPLOYEE ID "+employeeId);
		GetEmployeeQuery getEmployeesQuery = new GetEmployeeQuery();
		getEmployeesQuery.setEmployeeId(employeeId);

		EmployeeReponseModel employeeReponseModel = queryGateway
				.query(getEmployeesQuery, ResponseTypes.instanceOf(EmployeeReponseModel.class)).join();

		return employeeReponseModel;
	}

	@GetMapping
	public List<EmployeeReponseModel> getAllEmployee() {
		System.out.println("Checkpoint 1");
		List<EmployeeReponseModel> list = queryGateway
				.query(new GetAllEmployeeQuery(), ResponseTypes.multipleInstancesOf(EmployeeReponseModel.class)).join();
		return list;
	}
}
