package cn.com.ddhj.model;

/**
 * 
 * 类: TUser <br>
 * 描述: 注册用户表 <br>
 * 作者: zhy<br>
 * 时间: 2016年10月2日 上午9:35:22
 */
public class TUser extends BaseModel {

	/**
	 * 用户编码
	 */
	private String userCode;

	/**
	 * 用户手机号
	 */
	private String phone;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户头像
	 */
	private String headPic;

	/**
	 * 用户电子邮箱
	 */
	private String eMail;

	/**
	 * 是否已登录，0 已登录 1 已登出
	 */
	private Integer isLogin;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

}