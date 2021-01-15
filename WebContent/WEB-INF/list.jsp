<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.javaex.vo.PersonVo" %>
<%
	//PhoneController 에서 personList를 포워드 시킴
	List<PersonVo> personList = (List<PersonVo>)request.getAttribute("pList");	//어트리뷰트는 오브젝트로 담겨있기 때문에 형변환을 꼭 해줘야함 ex --> (List<PersonList>)로 형변환 해줌
	System.out.println("=====list.jsp======");
	System.out.println(personList);
	
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

		<%for (int i=0; i<personList.size(); i++) { %>
			<table border="1">
				<tr>
					<td>이름(name)</td>
					<td><%=personList.get(i).getName() %></td>
				</tr>
				<tr>
					<td>핸드폰(hp)</td>
					<td><%=personList.get(i).getHp() %></td>
				</tr>
				<tr>
					<td>회사(company)</td>
					<td><%=personList.get(i).getCompany() %></td>
				</tr>
				<tr>
				
					<td><a href="/phonebook2/pbc?action=updateForm&id=<%=personList.get(i).getPersonId() %>"> 수정 </a></td>
					<td><a href="/phonebook2/pbc?action=delete&id=<%=personList.get(i).getPersonId() %>"><input type="hidden" name="action" value="delete" >삭제</a></td>
					
				</tr>
			</table>
			<br>
		<%} %>

		
	<a href="/phonebook2/pbc?action=wform">추가 번호 등록</a>
</body>
</html>