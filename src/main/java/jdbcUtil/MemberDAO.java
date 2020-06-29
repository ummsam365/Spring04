package jdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;
import vo.MemberVO;

@Repository
public class MemberDAO {
	static Connection cn = DBConnection.getConnection();
	static Statement st;
	static PreparedStatement pst;
	static ResultSet rs;
	String sql;

	public ArrayList<MemberVO> selectList() {
		sql = "select * from member order by id";
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		// MemberVO vo = new MemberVO(); => 절대 안됨
		// => 모든 ROW 들이 같은 vo 주소값을 가지게 되므로 마지막 데이터만 쌓임
		try {
			st = cn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				// 결과물을 요청클래스로 전달
				do {
					MemberVO vo = new MemberVO();
					vo.setId(rs.getString(1));
					vo.setPassword(rs.getString(2));
					vo.setName(rs.getString(3));
					vo.setLev(rs.getString(4));
					vo.setBirthd(rs.getString(5));
					vo.setPoint(rs.getInt(6));
					vo.setWeight(rs.getDouble(7));
					vo.setRid(rs.getString(8));
					list.add(vo);
				} while (rs.next());
			} else {
				list = null;
			}
		} catch (Exception e) {
			System.out.println("** selectList Exception=>" + e.toString());
			list = null;
		}
		return list;
	} // selectList()
	
	public MemberVO selectOne(MemberVO vo) {
		sql = "select * from member where id=?";
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getId());
			rs = pst.executeQuery();
			if (rs.next()) {
				vo.setId(rs.getString(1));
				vo.setPassword(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setLev(rs.getString(4));
				vo.setBirthd(rs.getString(5));
				vo.setPoint(rs.getInt(6));
				vo.setWeight(rs.getDouble(7));
				vo.setRid(rs.getString(8));
			} else {
				vo = null;
			}
		} catch (Exception e) {
			System.out.println("** selectOne Exception=>" + e.toString());
			vo = null;
		}
		return vo;
	} // selectOne

	public int insert(MemberVO vo) {
		sql = "insert into member values(?,?,?,?,?,?,?,?)";
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getId());
			pst.setString(2, vo.getPassword());
			pst.setString(3, vo.getName());
			pst.setString(4, vo.getLev());
			pst.setString(5, vo.getBirthd());
			pst.setInt(6, vo.getPoint());
			pst.setDouble(7, vo.getWeight());
			pst.setString(8, vo.getRid());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** insert Exception=>" + e.toString());
			return 0;
		}
	} // insert

	public int update(MemberVO vo) {
		sql = "update member set password=?,name=?,lev=?,birthd=?,point=?,weight=?,rid=? where id=?";
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getPassword());
			pst.setString(2, vo.getName());
			pst.setString(3, vo.getLev());
			pst.setString(4, vo.getBirthd());
			pst.setInt(5, vo.getPoint());
			pst.setDouble(6, vo.getWeight());
			pst.setString(7, vo.getRid());
			pst.setString(8, vo.getId());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** update Exception=>" + e.toString());
			return 0;
		}
	} // update

	public int delete(MemberVO vo) {
		sql = "delete from member where id=?";
		try {
			pst = cn.prepareStatement(sql);
			pst.setString(1, vo.getId());
			return pst.executeUpdate();
		} catch (Exception e) {
			System.out.println("** update Exception=>" + e.toString());
			return 0;
		}
	} // delete

} // class
