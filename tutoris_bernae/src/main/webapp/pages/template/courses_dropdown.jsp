<c:set var="studyCourseIdErrors"><form:errors path="studyCourseId"/></c:set>

<div class="control-group<c:if test="${not empty studyCourseIdErrors}">error</c:if>">
	<label class="control-label" for="field-studyCourseId">Study Course</label>	
	<div class="controls">
		<form:select path="studyCourseId" required="false">
			<form:option value="0">-- no course selected --</form:option>
			<form:options items="${studyCourseList}" itemValue="id" itemLabel="name"/>
		</form:select>
		<form:errors path="studyCourseId" cssClass="help-inline" element="span"/>
	</div>
</div>