package vo;

// ** 공통모듈 구현 1.
// VO (Value Object, DTO: Data Transfer Object) 
// => member 테이블의 구조
// => 자료를 주고 받는 통로 역할

public class MemberVO {
	private String id;
	private String password;
	private String name;
	private String lev;
	private String birthd;
	private int point;
	private double weight;
	private String rid;
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getLev() { return lev; }
	public void setLev(String lev) { this.lev = lev; }
	public String getBirthd() { return birthd; }
	public void setBirthd(String birthd) { this.birthd = birthd; }
	public int getPoint() { return point; }
	public void setPoint(int point) { this.point = point; }
	public double getWeight() { return weight; }
	public void setWeight(double weight) { this.weight = weight; }
	public String getRid() { return rid; }
	public void setRid(String rid) { this.rid = rid; }
	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", password=" + password + ", name=" + name + ", lev=" + lev + ", birthd="
				+ birthd + ", point=" + point + ", weight=" + weight + ", rid=" + rid + "]";
	}
} // MemberVO
