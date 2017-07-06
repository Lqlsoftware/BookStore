package com.lqlsoftware.ATGUIGU.entity;

/**
 * 用户 用户
 * @author robinlu
 */
public class User {

	// 用户id
	private Long user_id;

	// 用户名
	private String user_name;

	// 登录名
	private String login_name;

	// 密码
	private String password;

	// 银行账户id
	private Long account_id;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}
}