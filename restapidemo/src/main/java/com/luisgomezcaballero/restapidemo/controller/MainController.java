package com.luisgomezcaballero.restapidemo.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.luisgomezcaballero.restapidemo.model.MyEntity;
import com.luisgomezcaballero.restapidemo.service.MyService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class MainController {

	@Autowired
	MyService myService;

	@ApiOperation(value = "List all entities (JSON)", nickname = "readAllEntitiesJson")
	@RequestMapping(value = "/myentity/json", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<MyEntity>> readAllEntitiesJson() {
		return new ResponseEntity<List<MyEntity>>(myService.readAll(), HttpStatus.OK);
	}

	@ApiOperation(value = "List all entities (XML)", nickname = "readAllEntitiesXml")
	@RequestMapping(value = "/myentity/xml", method = RequestMethod.GET, produces = "application/xml")
	public ResponseEntity<List<MyEntity>> readAllEntitiesXml() {
		return new ResponseEntity<List<MyEntity>>(myService.readAll(), HttpStatus.OK);
	}

	@ApiOperation(value = "List all entities (CSV)", nickname = "readAllEntitiesCsv")
	@RequestMapping(value = "/myentity/csv", method = RequestMethod.GET, produces = "text/csv")
	public void readAllEntitiesCsv(HttpServletResponse response) throws IOException {
		response.setContentType("text/csv");
		String reportName = "CSV_Report_Name.csv";
		response.setHeader("Content-disposition", "attachment;filename=" + reportName);

		List<MyEntity> rows = myService.readAll();

		Iterator<MyEntity> iter = rows.iterator();
		while (iter.hasNext()) {
			MyEntity outputString = (MyEntity) iter.next();
			response.getOutputStream().print(outputString.getMyLong() + ";" + outputString.getMyString() + "\n");
		}
		response.getOutputStream().flush();
	}

	@ApiOperation(value = "Read entity (JSON)", nickname = "readEntityByIdJson")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Entity identifier", required = true, dataType = "long", paramType = "query") })
	@RequestMapping(value = "/myentity/json/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<MyEntity> readEntityByIdJson(@PathVariable("id") long id) {
		MyEntity myEntityResult = myService.readById(id);
		return new ResponseEntity<MyEntity>(myEntityResult, HttpStatus.OK);
	}

	@ApiOperation(value = "Read entity (XML)", nickname = "readEntityByIdXml")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Entity identifier", required = true, dataType = "long", paramType = "query") })
	@RequestMapping(value = "/myentity/xml/{id}", method = RequestMethod.GET, produces = "application/xml")
	public ResponseEntity<MyEntity> readEntityByIdXml(@PathVariable("id") long id) {
		MyEntity myEntityResult = myService.readById(id);
		return new ResponseEntity<MyEntity>(myEntityResult, HttpStatus.OK);
	}

	@ApiOperation(value = "Read entity (CSV)", nickname = "readEntityByIdCsv")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Entity identifier", required = true, dataType = "long", paramType = "query") })
	@RequestMapping(value = "/myentity/csv/{id}", method = RequestMethod.GET, produces = "text/csv")
	public void readEntityByIdCsv(HttpServletResponse response, @PathVariable("id") long id) throws IOException {
		response.setContentType("text/csv");
		String reportName = "CSV_Report_Name.csv";
		response.setHeader("Content-disposition", "attachment;filename=" + reportName);

		MyEntity myEntityResult = myService.readById(id);

		response.getOutputStream().print(myEntityResult.getMyLong() + ";" + myEntityResult.getMyString() + "\n");
		response.getOutputStream().flush();
	}

	@ApiOperation(value = "Create entity", nickname = "createEntity")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "myEntity", value = "Entity", required = true, dataType = "MyEntity", paramType = "body") })
	@RequestMapping(value = "/myentity", method = RequestMethod.POST)
	public ResponseEntity<MyEntity> createEntity(@RequestBody MyEntity myEntity,
			@AuthenticationPrincipal final UserDetails userDetails) {
		myService.create(myEntity);
		return new ResponseEntity<MyEntity>(myEntity, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update entity", nickname = "updateEntity")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Entity identifier", required = true, dataType = "long", paramType = "query"),
			@ApiImplicitParam(name = "myEntity", value = "Entity", required = true, dataType = "MyEntity", paramType = "body") })
	@RequestMapping(value = "/myentity/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MyEntity> updateEntity(@PathVariable("id") long id, @RequestBody MyEntity myEntity) {
		myService.update(id, myEntity);
		return new ResponseEntity<MyEntity>(myEntity, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete entity", nickname = "deleteEntityById")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "Entity identifier", required = true, dataType = "long", paramType = "query") })
	@RequestMapping(value = "/myentity/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<MyEntity> deleteEntityById(@PathVariable("id") long id) {
		myService.deleteById(id);
		return new ResponseEntity<MyEntity>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Delete all entities", nickname = "deleteAllEntities")
	@RequestMapping(value = "/myentity", method = RequestMethod.DELETE)
	public ResponseEntity<MyEntity> deleteAllEntities() {
		myService.deleteAll();
		return new ResponseEntity<MyEntity>(HttpStatus.NO_CONTENT);

	}
}
