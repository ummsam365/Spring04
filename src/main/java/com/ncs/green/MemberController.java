package com.ncs.green;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import service.MService;
import vo.MemberVO;

@Controller
public class MemberController {
	@Autowired
	MService service;
	// MService service=new MServiceImpl();
	
	@RequestMapping(value = "/loginf")
	public ModelAndView loginf(ModelAndView mv) {
		mv.setViewName("login/loginForm");
		return mv;
	} // loginf

	@RequestMapping(value = "/joinf")
	public ModelAndView joinf(ModelAndView mv) {
		mv.setViewName("member/joinForm");
		return mv;
	}// joinf
	
	// Id Dup Check
	@RequestMapping(value = "/idDupCheck")
	public ModelAndView idDupCheck(ModelAndView mv, MemberVO vo) {
		// client 로 부터 전달된 id 의 존재 여부 확인 : selectOne()
		// => notNull (존재하면) : 사용불가
		// => Null (존재하지 않으면) : 사용가능 (먼저 입력한 ID 보관 ) 
		mv.addObject("ID",vo.getId());
		vo=service.selectOne(vo);
		if (vo!=null)  // 사용불가
			 mv.addObject("idUse","F");
		else mv.addObject("idUse","T");
		
		mv.setViewName("member/idDupCheck");
		return mv;
	}// idDupCheck	
	

	@RequestMapping(value = "/mlist")
	public ModelAndView mlist(ModelAndView mv) {
		ArrayList<MemberVO> list = service.selectList();
		if (list != null) {
			mv.addObject("Banana", list); // scope 이 request 와 동일
		} else {
			mv.addObject("message", "~~ 검색된 자료가 1건도 없습니다. ~~");
		}
		mv.setViewName("member/memberList");
		return mv;
	} // mlist

	// request.getParameter값 VO에 담기 => 자동화됨
	// => vo를 매핑 메서드의 매개변수로 선언하면 자동으로 대입됨
	// => 단, form 의 input Tag의 name과 vo 의 변수명이 동일한 경우만 자동 대입됨.
	@RequestMapping(value = "/login")
	public ModelAndView login(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		String password = vo.getPassword();
		mv.setViewName("login/loginForm");

		vo = service.selectOne(vo);
		if (vo != null) { // id 존재
			if (vo.getPassword().equals(password)) {
				// 로그인 성공 -> login 정보 보관 (id, name을 session에) -> loginSuccess
				request.getSession().setAttribute("logID", vo.getId());
				request.getSession().setAttribute("logName", vo.getName());
				mv.setViewName("login/loginOn");
			} else {
				// Password 오류 -> 재로그인
				mv.addObject("message", " Password 오류 !! ~~ 다시 하세요 ~~");
			}
		} else { // ID 오류 -> 재로그인
			mv.addObject("message", " ID 오류 !! 다시 하세요 ~~");
		}
		return mv;
	} // login

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
		request.getSession().invalidate();
		mv.setViewName("redirect:home"); // url 요청명 home
		// mv.setViewName("home"); // home.jsp 로 forward
		return mv;
	}// logout
		// "redirect:home"
		// => viewRevolver 활용하면서, 확장자 안붙이고, redirect 함

	@RequestMapping(value = "/mdetail")
	public ModelAndView mdetail(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		// 1) login 여부 확인
		String id = "";
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("logID") != null) {
			id = (String) session.getAttribute("logID");
		} else {
			// login 하도록 유도 후에 메서드 return 으로 종료
			mv.addObject("message", "~~ 로그인 후에 하세요 ~~");
			mv.setViewName("login/loginForm");
			return mv;
		}
		vo.setId(id);
		vo = service.selectOne(vo);
		mv.addObject("myInfo", vo);

		// 4) 결과 ( Detail or Update 인지 )
		// => request.getParameter("code") 가 U 인지 확인
		mv.setViewName("member/memberDetail");
		if ("U".equals(request.getParameter("code"))) {
			// 내정보 수정화면으로
			mv.setViewName("member/updateForm");
		} else if ("E".equals(request.getParameter("code"))) { // 내정보 수정에서 오류 상황
			mv.addObject("message", "~~ 내정보 수정 오류  !!! 다시 하세요 ~~");
		}
		return mv;
		
	}// mdetail

	@RequestMapping(value = "/join")
	public ModelAndView join(ModelAndView mv, MemberVO vo) {

		mv.setViewName("member/doFinish");
		if (service.insert(vo) > 0) {
			// Join 성공
			mv.addObject("joinID", vo.getId());
			mv.addObject("fCode", "JS");
		} else {
			// Join 실패
			mv.addObject("fCode", "JF");
		}
		return mv;
	}// join

	@RequestMapping(value = "/mupdate")
	public ModelAndView update(HttpServletRequest request, ModelAndView mv, MemberVO vo) {
		if (service.update(vo) > 0) {
			// 회원수정 성공 -> memberList 출력
			// session 의 Attribute logName 도 변경
			request.getSession().setAttribute("loginName", vo.getName());
			mv.setViewName("redirect:mlist");
		} else {
			// 회원수정 실패 -> 내정보 보기 화면으로
			mv.setViewName("redirect:mdetail?code=E");
		} // if
		return mv;
	}// mupdate

	@RequestMapping(value = "/mdelete")
	public ModelAndView mdelete(HttpServletRequest request, ModelAndView mv, MemberVO vo) {

		// 1) login 여부 확인
		String id = "";
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("logID") != null) {
			id = (String) session.getAttribute("logID");
		} else {
			// login 하도록 유도 후에 메서드 return 으로 종료
			mv.addObject("message", "~~ 로그인 후에 하세요 ~~");
			mv.setViewName("login/loginForm");
			return mv;
		}
		// 2) Login OK -> delete
		vo.setId(id);
		mv.setViewName("member/doFinish");
		mv.addObject("deleteID", id);

		if (service.delete(vo) > 0) {
			// Delete 성공
			mv.addObject("fCode", "DS");
			session.invalidate(); // 삭제 되면 session 도 삭제해야함.
		} else {
			// Delete 실패
			mv.addObject("fCode", "DF");
		}
		return mv;

	}// mdelete

} // class
