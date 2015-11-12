<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table>
                <thead>
                    <tr>
                        <th>Class name</th>
                        <th>Grade</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody id="classListContainer">
                    <c:forEach items="${tutorForm.classList}" var="classes" varStatus="i" begin="0" >
                        <tr class="classList">    
                        <td><form:select path="classList[${i.index}].classesId" id="classesId${i.index}" value = "classList[${i.index}].classesId">
					  	<form:options items="${allClasses}" itemValue="id" itemLabel="name" />
				       	</form:select></td>
                            
                            <td><form:input path="classList[${i.index}].grade" id="grade${i.index}" /></td>
                            <td><button type="submit" name="removeClass" value="${i.index}">remove it</button></td>
                            </tr>
                    </c:forEach>
                    
                </tbody>
            </table>
            <button type="submit" name="addClass" value="true">Add New Class</button>