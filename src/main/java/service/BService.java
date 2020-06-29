package service;

import java.util.ArrayList;

import vo.BoardVO;
import vo.PageVO;

public interface BService {
	
	
	PageVO pageList(PageVO pvo); // pageList()
	int countUp(BoardVO vo); // countUp
	int rinsert(BoardVO vo); // Reply_insert

	ArrayList<BoardVO> selectList(); // selectList()

	BoardVO selectOne(BoardVO vo); // selectOne

	int insert(BoardVO vo); // insert

	int update(BoardVO vo); // update

	int delete(BoardVO vo); // delete

}