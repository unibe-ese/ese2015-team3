<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<table>
                <thead>
                    <tr>
                        <th>Class name</th>
                        <th>Grade</th>
                    </tr>
                </thead>
                <tbody id="classListContainer">
      				
      				<c:if test="${not empty classListError}">${classListError}</c:if>
                    <c:forEach items="${tutorForm.classList}" var="classes" varStatus="i" begin="0" >
                        <tr class="classList">    
                        <td><form:select path="classList[${i.index}].classes" id="classesId${i.index}" value="${tutorForm.classList[i.index].classes.id}">
					  	<form:options items="${allClasses}" itemLabel="name" itemValue="id"/>
				       	</form:select></td>
                            <td><form:select path="classList[${i.index}].grade" id="grade${i.index}" value="${tutorForm.classList[i.index].grade}">
					  		<form:option value = "4"> 4.00 </form:option>
					  		<form:option value = "4.50"> 4.50 </form:option>
					  		<form:option value = "5"> 5.00 </form:option>
					  		<form:option value = "5.50"> 5.50 </form:option>
					  		<form:option value = "6.00"> 6.00 </form:option>
					  		</form:select>
					  		</td>
                            <td><button type="submit" name="removeClass" value="${i.index}">remove it</button></td>
                         </tr>
                    </c:forEach>
                </tbody>
            </table>
           
            <button type="submit" name="addClass" value="true">Add New Class</button><br>
            <form:errors path="classList" cssClass="help-inline" element="span"/>