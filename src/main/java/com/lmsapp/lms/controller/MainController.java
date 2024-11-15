package com.lmsapp.lms.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lmsapp.lms.api.SmsSender;
import com.lmsapp.lms.dto.AdminLoginDto;
import com.lmsapp.lms.dto.EnquiryDto;
import com.lmsapp.lms.dto.StudentInfoDto;
import com.lmsapp.lms.model.AdminLogin;
import com.lmsapp.lms.model.Enquiry;
import com.lmsapp.lms.model.StudentInfo;
import com.lmsapp.lms.service.AdminLoginRepo;
import com.lmsapp.lms.service.EnquiryRepo;
import com.lmsapp.lms.service.StudentInfoRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;


@Controller
public class MainController {
	
	@Autowired
	EnquiryRepo erepo;
	
	@Autowired
	StudentInfoRepo stdRepo;
	@Autowired
	AdminLoginRepo adrepo;
@GetMapping("/home")
	public String showIndex()
	{
	return "index";		
	}
@GetMapping("/aboutus")
public String showaboutus()
{
return "aboutus";	
	
}

@GetMapping("/stulogin")
public String showstulogin(Model model)
{
	StudentInfoDto dto=new StudentInfoDto();
	model.addAttribute("dto", dto);
	return "stulogin";	

}
@PostMapping("/stulogin")

public String validateStudent(@ModelAttribute StudentInfoDto dto,HttpSession session, RedirectAttributes attrib)
{
 
	try {
		
	StudentInfo s=stdRepo.getById(dto.getEnrollmentno());
	
		if (s.getPassword().equals(dto.getPassword())) {
			//attrib.addFlashAttribute("msg", "Valid User");
			session.setAttribute("studentid", s.getEnrollmentno());
			return "redirect:/student/stdhome";
		}
		else {
			attrib.addFlashAttribute("msg", "Invalid User");
		}
		
		return "redirect:/stulogin";
	} catch (EntityNotFoundException ex) {
	
		attrib.addFlashAttribute("msg","Student does not exist");
		return "redirect:/stulogin";
	}
	
	
	
}


@GetMapping("/studentlogin")
public String showstudentlogin(Model model)
{
	StudentInfoDto dto =new StudentInfoDto();
	model.addAttribute("dto", dto);
return "studentlogin";	
}
@PostMapping("/studentlogin")
public String Registration(@ModelAttribute StudentInfoDto studentInfoDto,RedirectAttributes redirectAttributes)
{
try {
	
	StudentInfo std = new StudentInfo();
	std.setEnrollmentno(studentInfoDto.getEnrollmentno());
	std.setName(studentInfoDto.getName());
	std.setFname(studentInfoDto.getFname());
	std.setMname(studentInfoDto.getMname());
	std.setGender(studentInfoDto.getGender());
	std.setAddress(studentInfoDto.getAddress());
	std.setProgram(studentInfoDto.getProgram());
	std.setBranch(studentInfoDto.getBranch());
	std.setYear(studentInfoDto.getYear());
	std.setContactno(studentInfoDto.getContactus());
	std.setEmailaddress(studentInfoDto.getEmailadddress());
	std.setPassword(studentInfoDto.getPassword());
	std.setRegdate(new Date()+"");
	stdRepo.save(std);
	redirectAttributes.addFlashAttribute("message", "registration successfull");
	
	
	return "redirect:/studentlogin";
} catch (Exception e) {
	 redirectAttributes.addFlashAttribute("message", "something went wrong"+e.getMessage());
	 return "redirect:/studentlogin";
}
}





@GetMapping("/contactus")
public String showcontactus(Model model)
{
	EnquiryDto dto=new EnquiryDto();
	model.addAttribute("dto", dto);
return "contactus";	
	
}
@PostMapping("/contactus")
public String submitEnquiry(@ModelAttribute EnquiryDto enquiryDto,BindingResult result,RedirectAttributes redirectAttributes)
{
try {
	
Enquiry eq =new Enquiry();
eq.setName(enquiryDto.getName());
eq.setGender(enquiryDto.getGender());
eq.setContactno(enquiryDto.getContactno());
eq.setEmailaddress(enquiryDto.getEmailaddress());
eq.setEnquirytext(enquiryDto.getEnquirytext());
eq.setPosteddate(enquiryDto.getPosteddate());
eq.setPosteddate(new Date()+"");
erepo.save(eq);
SmsSender ss=new SmsSender();
ss.sendSms(enquiryDto.getContactno());
redirectAttributes.addFlashAttribute("message", "Form Submitted Successfully" );
return "redirect:/contactus";
}
catch(Exception e)
{
	redirectAttributes.addFlashAttribute("message", "Somthing went wrong");
return "redirect:/contactus";	
}

}


@GetMapping("/adminlogin")
public String ShowAdminLogin(Model model)
{
	AdminLoginDto dto=new AdminLoginDto();
	model.addAttribute("dto", dto);
	
return "adminlogin";	
}
@PostMapping("/adminlogin")
public String Adminlogin(@ModelAttribute AdminLoginDto adminLoginDto,HttpSession session,RedirectAttributes redirectAttributes)
{

try {
	AdminLogin adminLogin=adrepo.getById(adminLoginDto.getUserid());
	if(adminLogin.getPassword().equals(adminLoginDto.getPassword()))
	{
		//redirectAttributes.addFlashAttribute("msg", "valid user");
		session.setAttribute("adminid",  adminLoginDto.getUserid());
		
		return "redirect:/admin/adhome";
		
	}
	else {
		redirectAttributes.addFlashAttribute("msg", "invalid user");
		return "redirect:/adminlogin";
	}
	 
} catch (Exception e) {
	redirectAttributes.addFlashAttribute("msg", "user does not exist");
	return "redirect:/adminlogin";
	
}	
	
}
    
	
}
 