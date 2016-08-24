<%@ include file="/WEB-INF/views/includes.jsp"%>

<div class="container">
	<div class="row">
		<ul>
			<li><a href="${pageContext.request.contextPath}/notifications">
					<span class="glyphicon glyphicon-envelope white"></span>&nbsp;<span
					class="badge">${notificationMessageCount}</span>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/other-link">
					<span class="glyphicon glyphicon-log-out"></span>&nbsp;<span
					class="badge">Log Out</span>
			</a></li>
			<li><a href="${pageContext.request.contextPath}/other-link">
					<span class="glyphicon glyphicon-user"></span>&nbsp;<span
					class="badge">User</span>
			</a></li>
		</ul>
	</div>
</div>