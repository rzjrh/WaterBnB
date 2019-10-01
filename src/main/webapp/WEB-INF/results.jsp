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
	<a href="/">Home</a>
	<c:if test="${loggedIn}">
	<form id="logoutForm" class="col" method="POST" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    	<input type="submit" value="Logout"/>
    </form>
	</c:if>
	<h1>Find a pool!</h1>
	<form action="/search" method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="text" name="searchQuery"/><input type="submit" value="Search"/>
	</form>
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
				<c:if test="${pool.address.toLowerCase().indexOf(searchQuery.toLowerCase()) > -1}">
					<tr>
						<td>
							<a href="/pools/${pool.getId()}">
								<c:out value="${pool.getAddress()}"></c:out>
							</a>
						</td>
						<td>
							<c:out value="${pool.getSize()}"></c:out>
						</td>
						<td>
							$<c:out value="${pool.getNightlyCost()}"></c:out>
						</td>
						<td>
							<a href="/pools/${pool.getId()}/review">
								<c:out value="${pool.getAverageRating()}"></c:out> - See more
							</a>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>