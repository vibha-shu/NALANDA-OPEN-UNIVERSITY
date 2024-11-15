package com.lmsapp.lms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "studentinfoes")
public class StudentInfo {
	@Id
	@Column(length = 50)
	private String enrollmentno;
	@Column(length = 58,nullable=false)
    private String name;
	@Column(length = 60,nullable=false)
    private String fname;
	@Column(length = 60,nullable=false)
    private String mname;
	@Column(length = 15,nullable=false)
    private String gender;
	@Column(length = 1000,nullable=false)
    private String address;
	@Column(length = 100,nullable=false)
    private String program;
	@Column (length = 100,nullable=false)
    private String branch;
	@Column(length = 50,nullable=false)
    private String year;
	@Column(length = 13,nullable=false)
    private String contactno;
	@Column(length = 100,nullable=false)
    private String emailaddress;
	@Column(length = 30,nullable=false)
    private String password;
	@Column(length = 60,nullable = false)
    private String regdate;
	
	@Column(length = 500,nullable=true)
	private String profilepic;
	public String getEnrollmentno() {
		return enrollmentno;
	}
	public void setEnrollmentno(String enrollmentno) {
		this.enrollmentno = enrollmentno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getProfilepic() {
		return profilepic;
	}
	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}
	
    
}
