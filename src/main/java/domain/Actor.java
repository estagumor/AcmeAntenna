package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor
extends DomainEntity {
	private String name = "";
	private String surname = "";
	private String email = "";
	private String pictureUrl = "";
	private String phoneNumber = "";
	private String postalAddress = "";
	private UserAccount userAccount;

	@NotBlank
	@NotNull
	public String getName() { return name; }
	@NotBlank
	@NotNull
	public String getSurname() { return surname; }
	@NotBlank
	@NotNull
	@Email
	public String getEmail() { return email; }
	@NotNull
	@Pattern(regexp = "^$|^\\+?\\d+$")
	public String getPhoneNumber() { return phoneNumber; }
	@NotNull
	@URL
	public String getPictureUrl() { return pictureUrl; }
	@NotNull
	public String getPostalAddress() { return postalAddress; }

	@Valid
	@OneToOne(optional = false)
	public UserAccount getUserAccount()
	{
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount)
	{
		this.userAccount = userAccount;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setPictureUrl(String pictureUrl)
	{
		this.pictureUrl = pictureUrl;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void setPostalAddress(String postalAddress)
	{
		this.postalAddress = postalAddress;
	}
}