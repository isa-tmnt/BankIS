package bis.project.security;

public class PasswordDTO {
	
	private String oldPassword;
	private String newPassword;
	private String repeatedPassword;
	
	public PasswordDTO() {}

	public PasswordDTO(String oldPassword, String newPassword, String repeatedPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.repeatedPassword = repeatedPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
}
