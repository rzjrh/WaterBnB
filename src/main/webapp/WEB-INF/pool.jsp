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
	<br />
	<c:choose>
		<c:when test="${pool.getHost().getId() == currentUser.getId()}">	
			<form:form action="/host/pools/${pool.getId()}" method="POST" modelAttribute="pool" >
				<div class="col">
					<p>
						<c:out value="${pool.getAddress()}"></c:out>
					</p>	
					<p>
						<form:errors path="description"/>
						<form:input path="description"/>
					</p>
				</div>	
				<div class="col">
					<p>
						Email: <c:out value="${currentUser.getEmail()}"></c:out>
					</p>
					<p>
						Name: <c:out value="${currentUser.getFullName()}"></c:out>
					</p>
					<p>
						<form:errors path="size"/>
						<form:select path="size">
							<form:options items="${sizes}"/>
						</form:select>
					</p>
					<p>
						<form:errors path="nightlyCost"/>
						<form:input path="nightlyCost" type="number" step="1"/>
					</p>
				</div>	
				<input type="submit" value="Save Changes"/>
			</form:form>	
			<div class="col">
			</div>
			<div class="col"></div>
		</c:when>
		<c:otherwise>
			<div class="col">
				<p>
					<c:out value="${pool.getAddress()}"></c:out>
				</p>
				<p>
					<c:out value="${pool.getDescription()}"></c:out>
				</p>
			</div>
			<div class="col">
				<p>
					Email: <c:out value="${pool.getHost().getEmail()}"></c:out>
				</p>
				<p>
					Name: <c:out value="${pool.getHost().getFullName()}"></c:out>
				</p>
				<p>
					Pool Size: <c:out value="${pool.getSize()}"></c:out>
				</p>
				<p>
					Cost: <c:out value="${pool.getNightlyCost()}"></c:out>
				</p>
			</div>
		</c:otherwise>
	</c:choose>
	
	<h2>Reviews: (<c:out value="${pool.getAverageRating()}"></c:out>/5)</h2>
	<div id="reviews">
		<c:forEach items="${pool.getReviews()}" var="review">
			<p>
				<c:out value="${review.getAuthor().getFullName()}"></c:out>
			</p>
			<p>
				Rating: <c:out value="${review.getRating()}"></c:out>
			</p>
			<p>
				<c:out value="${review.getReview()}"></c:out>
			</p>
		</c:forEach>
	</div>
</body>
</html>