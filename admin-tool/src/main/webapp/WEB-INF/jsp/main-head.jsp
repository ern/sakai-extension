<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.sakaiproject.portal.util.PortalUtils" %>
<%@ page import="org.sakaiproject.portal.util.CSSUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<!-- CSS -->
<link media="all" href="<%= CSSUtils.getCssToolBaseCDN() %>" rel="stylesheet" type="text/css" />
<link media="all" href="<%= PortalUtils.getWebjarsPath() %>bootstrap/3.3.7/css/bootstrap.min.css<%= PortalUtils.getCDNQuery() %>" rel="stylesheet" type="text/css" />
<link media="all" href="<%= PortalUtils.getWebjarsPath() %>bootstrap/3.3.7/css/bootstrap-theme.min.css<%= PortalUtils.getCDNQuery() %>" rel="stylesheet" type="text/css" />
<link media="all" href="<c:url value='/css/main.css'/><%= PortalUtils.getCDNQuery() %>" rel="stylesheet" type="text/css" />