package com.lmsapp.lms.dto;

import org.springframework.web.multipart.MultipartFile;

public class StudentInfoDto {
	
private String enrollmentno;
private String name;
private String fname;
private String mname;
private String gender;
private String address;
private String program;
private String branch;
private String year;
private String contactus;
private String emailadddress;
private String password;
private String regdate;
private MultipartFile profilepic;
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
public String getContactus() {
	return contactus;
}
public void setContactus(String contactus) {
	this.contactus = contactus;
}
public String getEmailadddress() {
	return emailadddress;
}
public void setEmailadddress(String emailadddress) {
	this.emailadddress = emailadddress;
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
public MultipartFile getProfilepic() {
	return profilepic;
}
public void setProfilepic(MultipartFile profilepic) {
	this.profilepic = profilepic;
}




}
