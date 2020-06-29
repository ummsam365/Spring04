package service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jdbcUtil.MemberDAO;
import vo.MemberVO;

//Service 클래스
//=> Controller 와  DAO 사이에서 
//요청받은 역활을 수행하는 클래스
//즉, dao 를 실행하는 클래스

//** interface 자동완성 
//=> Shift + Alt + T

@Service
public class MServiceImpl implements MService {
	@Autowired
	MemberDAO dao;
	
	@Override
	public ArrayList<MemberVO> selectList() {
		return dao.selectList() ;
	} // selectList()
	
	@Override
	public MemberVO selectOne(MemberVO vo) {
		return dao.selectOne(vo);
	} // selectOne
	
	@Override
	public int insert(MemberVO vo) {  
		return dao.insert(vo);  // 처리된 row 갯수 return	 
	} // insert

	@Override
	public int update(MemberVO vo) { // row(vo) 전달 받아 수정
		return dao.update(vo);  // 처리된 row 갯수 return
	} // update

	@Override
	public int delete(MemberVO vo) {
		return dao.delete(vo);  // 처리된 row 갯수 return
	} // delete

} // class
