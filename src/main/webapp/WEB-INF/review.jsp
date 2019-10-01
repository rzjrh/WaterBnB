<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WaterBnB</title>
<style>
	.col{
		display:inline-block;
	}
</style>
</head>
<body>
	<a href="/">Home</a>
	<c:if test="${loggedIn}">
	<form id="logoutForm" class="col" method="POST" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    	<input type="submit" value="Logout"/>
    </form>
	</c:if>
	<c:if test="${error != null}">
		<c:out value="${error}"></c:out>
	</c:if>
		<form action="/pools/${pool.getId()}/review" method="POST">
				<p>
					Review of <c:out value="${pool.getAddress()}"></c:out>
				</p>	
				<p>
					<input type="text" name="review"/>
				</p>
				<p>
					<select name="rating">
						<c:forEach items="${ratings}" var="starRating">
							<option value="${starRating}">${starRating}</option>
						</c:forEach>
					</select>
				</p>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Submit Review"/>
		</form>	

</body>
</html>