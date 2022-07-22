<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.localisation}" scope="session"/>
<fmt:setBundle basename="localisation.localisedtext"/>


<div class="footer-margin">
    <footer class="fixed-bottom footer">
        <div class="footer-contact">
            <a href="#"><i class="fab fa-vk"></i></a>
            <a href="#"><i class="fab fa-github"></i></a>
            <a href="#"><i class="fab fa-linkedin-in"></i></a>
        </div>
        <div class="row footer-copyright justify-content-center">
            <p class="text-muted">© Copyright 2022 by Zlata Migas. All Rights Reserved.</p>
        </div>
    </footer>
</div>


