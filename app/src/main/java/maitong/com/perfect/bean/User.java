package maitong.com.perfect.bean;
/**
 * 登录用户
 * @author zheng
 *
 */
public class User {
	public User() {
	}
	private int id;
	/**
	 * 用户名称
	 */
	private String username;
	/**
	 * 登录密码
	 */
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + "]";
	}


}
