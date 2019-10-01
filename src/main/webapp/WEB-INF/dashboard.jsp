<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WaterBnB</title>
</head>
<body>
	<c:if test="${loggedIn}">
	<form id="logoutForm" class="col" method="POST" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    	<input type="submit" value="Logout"/>
    </form>
	</c:if>
	<h1>Your Current Listings</h1>
	<table>
		<thead>
			<tr>
				<td>Address</td>
				<td>Pool size</td>
				<td>Cost per night</td>
				<td>Details</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pools}" var="pool">
				<c:if test="${pool.getHost().getId() == currentUser.getId()}">
					<tr>
						<td>
							<c:out value="${pool.getAddress()}"></c:out>
						</td>
						<td>
							<c:out value="${pool.getSize()}"></c:out>
						</td>
						<td>
							$<c:out value="${pool.getNightlyCost()}"></c:out>
						</td>
						<td>
							<a href="/host/pools/${pool.getId()}">
								<c:out value="${pool.getAverageRating()}"></c:out> - Edit
							</a>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
	
	<form:form action="/host/dashboard" method="POST" modelAttribute="newPool">
		<form:label path="address">Address</form:label>
		<form:errors path="address"></form:errors>
		<form:input path="address"/>
		<br />
		<form:label path="description">Description</form:label>
		<form:errors path="description"></form:errors>
		<form:input path="description"/>	
		<br />
		<form:label path="nightlyCost">Cost</form:label>
		<form:errors path="nightlyCost"></form:errors>
		<form:input path="nightlyCost" type="number" step="1"/>
		<br />
		<form:label path="size">Size</form:label>
		<form:errors path="size"></form:errors>
		<form:select path="size">
			<form:options items="${sizes}"/>
		</form:select>	
		<input type="submit" value="Add Listing"/>
	</form:form>
</body>
</html>