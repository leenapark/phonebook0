package com.javaex.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhoneController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 많이 사용하는 코드 정리
		PhoneDao phoneDao = new PhoneDao(); // dao 메모리 생성
		
		// post&get 한글 깨짐 현상 방지
		request.setCharacterEncoding("UTF-8");
		
		// 컨트롤러 테스트
		// System.out.println("controller");

		// 파라미터 action 값을 읽는다
		String action = request.getParameter("action");
		// 액션값이 잘 들어오는지 확인
		System.out.println("=====action======");
		System.out.println(action);

		// action = ? --> ? 에 해당하는 일을 한다

		// action list --> list 출력
		if ("wform".equals(action)) {
			System.out.println("등록 폼 처리");

			WebUtil.forword(request, response, "./WEB-INF/writeForm.jsp");

		} else if ("insert".equals(action)) {
			System.out.println("전화번호 저장");

			// 파라미터 3개 값 받은 거 꺼내기
			String name = request.getParameter("name"); // action은 이미 실행되어 있음으로 다시 적용하지 않음
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			// personVo로 묶음
			PersonVo personVo = new PersonVo(name, hp, company);

			// new dao
			phoneDao.phoneInsert(personVo); // 저장
			// dao.phoneInsert(personVo)

			// 리다이렉트로 주소로 응답해줌
			// response.sendRedirect("/phonebook2/pbc?action=list");
			WebUtil.redirect(request, response, "/phonebook2/pbc");		//static 으로 올려둔 포워드 메소드를 사용해준다

		} else if ("delete".equals(action)) {
			System.out.println("전화번호 삭제");

			int personId = Integer.parseInt(request.getParameter("id")); // 몇번째 걸 삭제할 건지 아이디를 가져와야함
			System.out.println(personId); // 가져온 숫자가 잘 들어오는지 확인

			
			phoneDao.getDelete(personId);

			// 삭제 후 리다이렉트 주소로 응답해주기
			WebUtil.redirect(request, response, "/phonebook2/pbc?action=list");
		} else if ("updateForm".equals(action)) {

			int personId = Integer.parseInt(request.getParameter("id"));
			System.out.println(personId);

			System.out.println("등록 폼 처리");

			
			// List<PersonVo> personList = phoneDao.getList();
			PersonVo personVo = phoneDao.getPerson(personId);

			System.out.println(personVo.toString());

			// 데이터 전달
			// request.setAttribute("별명", 실제 데이터);
			request.setAttribute("pList", personVo);
			
			WebUtil.forword(request, response, "./WEB-INF/updateForm.jsp");

		} else if ("update".equals(action)) {
			System.out.println("전화번호 수정");

			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			int personId = Integer.parseInt(request.getParameter("id"));

			PersonVo personVo = new PersonVo(personId, name, hp, company);
			
			// 업데이트 된 정보 확인용
			System.out.println(personVo.toString());

			phoneDao.getUpdate(personVo);

			// 수정 후 리스트 화면으로 돌아가기 - 리다이렉트
			WebUtil.redirect(request, response, "/phonebook2/pbc?action=list");

		} else {
			System.out.println("리스트 처리");
			
			List<PersonVo> personList = phoneDao.getList();
		
			
			// 데이터 전달
			// request.setAttribute("별명", 실제 데이터);
			request.setAttribute("pList", personList);

			// html --> 복잡 --> jsp에서 만들어주는 게 편하다
			// jsp에 포워드 시킴
			// String path = "./WEB-INF/list.jsp"; // path-jsp 위치를 알려줌
			WebUtil.forword(request, response, "./WEB-INF/list.jsp");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 위 코드를 같이 쓰고 싶으면 이 기본 생성 코드를 사용 (post 방식)
		doGet(request, response);
	}

}
