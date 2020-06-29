<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Login Test **</title>
<link rel="stylesheet" type="text/css" href="resources/jqLib/myStyle.css">
<script src="resources/jqLib/jquery-3.2.1.min.js"></script>
<script src="resources/jqLib/memberCheck.js"></script>
<script>
// ** Login시 id, password 입력자료의 무결성 점검
// => jQuery 의 focusout , submit
// => 1) 전역변수정의  
//    2) 각 InputTag의 focusout 이벤트핸들러
//    3) submit 처리

// 1) 전역변수정의
	var iCheck=false;
	var pCheck=false;
// 2) 각 InputTag의 focusout 이벤트핸들러	
$(function() {
	$('#id').focus();
	$('#id').focusout(function() {
	 	iCheck=idCheck();
		//if () iCheck=true;
		//else iCheck=false;
	}); // id_focusout
	
	$('#password').focusout(function() {
		pCheck=pwCheck();
	}); // password_focusout
}); // ready

//3) submit 처리
function inCheck() {
	// input Tag의 value에  default값을 준경우 focusout 없이 submit 누르는 경우 
	if (iCheck==false) {iCheck=idCheck() };
	if (pCheck==false) {pCheck=pwCheck() };
	
	if (iCheck==true && pCheck==true) {
		  return true;
	}else return false;
	
} //inCheck 

</script>

</head>
<body>
<h2>** LoginForm Spring_inCheck **</h2>
<form action="login" method=post>
<h2>
<table>
<tr><td>I      D : </td>
    <td><input type="text" name="id" id="id" value="banana"><br> 
    	<span id="iMessage" class="eMessage"></span></td>
</tr>
<tr><td>Password : </td>
    <td><input type="password" name="password" id="password" value="1234!"><br>
    <span id="pMessage" class="eMessage"></span></td>
</tr>
<tr><td></td>
    <td><input type="submit" value="Login" onclick="return inCheck()"> &nbsp; 
        <input type="reset" value="Reset"><br>
    </td>
</tr>
</table></h2></form>
<hr>
<a href="home"> [Home]</a>
<hr>
<c:if test="${message != null}">
 => ${message}
</c:if>
</body>
</html>