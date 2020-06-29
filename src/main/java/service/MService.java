package service;

import java.util.ArrayList;

import vo.MemberVO;

public interface MService {

	ArrayList<MemberVO> selectList(); // selectList()

	MemberVO selectOne(MemberVO vo); // selectOne

	int insert(MemberVO vo); // insert

	int update(MemberVO vo); // update

	int delete(MemberVO vo); // delete

}