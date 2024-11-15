package com.lmsapp.lms.controller;

import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lmsapp.lms.dto.ResponseDto;
import com.lmsapp.lms.dto.StudentInfoDto;
import com.lmsapp.lms.model.Material;
import com.lmsapp.lms.model.Response;
import com.lmsapp.lms.model.StudentInfo;
import com.lmsapp.lms.service.MaterialRepo;
import com.lmsapp.lms.service.ResponseRepo;
import com.lmsapp.lms.service.StudentInfoRepo;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	StudentInfoRepo sreRepo;
	@Autowired
	ResponseRepo resrepo;
	@Autowired
	MaterialRepo mrepo;
	
@GetMapping("/stdhome")
	public String showStudentHome(HttpSession session, HttpServletResponse response,Model model)
	{
	try {
		StudentInfo sinfo=sreRepo.findById(session.getAttribute("studentid").toString()).get();
		model.addAttribute("sinfo", sinfo);
		
		
		StudentInfoDto dto=new StudentInfoDto();
		model.addAttribute("dto", dto);
		response.setHeader("Cache-Control","no-cache,no-store,no-revalidate");
		if(session.getAttribute("studentid")!=null) {
		
		return "student/studenthome";
		}
		else {
			return "redirect:/stulogin";
		}
		
		
	} 
	
	catch (Exception ex) {
		
		return "redirect:/stulogin";
		 
	}
	
	}


@PostMapping("/stdhome")
public String uploadpic(HttpSession session,RedirectAttributes redirectAttributes,@ModelAttribute StudentInfoDto studentInfoDto)
{
	if(session.getAttribute("studentid")!=null)
	{
	try {
		  MultipartFile filedata=studentInfoDto.getProfilepic();
		   String storageFileName =new Date().getTime()+"_"+filedata.getOriginalFilename();
		   String uploadDir ="public/user/";
		Path uploadPath=Paths.get(uploadDir);
		if(!Files.exists(uploadPath));
		{
			Files.createDirectories(uploadPath);
			
		}
		try(InputStream inputStream =filedata.getInputStream())
		{
			
		Files.copy(inputStream, Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);	
			
		}
		StudentInfo std =sreRepo.findById(session.getAttribute("studentid").toString()).get();
	  std.setProfilepic(storageFileName);
	   sreRepo.save(std);
		redirectAttributes.addFlashAttribute("msg", "profile upload successfully.");
		return "redirect:/student/stdhome";
	} catch (Exception e) {
		redirectAttributes.addFlashAttribute("msg","something wrong"+e.getMessage());
		return "redirect:/student/stdhome"; 
	}
		
	}
	else
		
	{
		return "redirect:/stulogin";
		
	}

}
@GetMapping("/studymaterial")	
public String ShowStudyMaterial(HttpSession session,Model model)
{
try {
	if (session.getAttribute("studentid")!=null) {
		
		StudentInfo s=sreRepo.getById(session.getAttribute("studentid").toString());
		String program=s.getProgram();
		String branch=s.getBranch();
		String year=s.getYear();
		String materialtype="smat";
		List<Material> mlist=mrepo.getmaterial(program,branch,year,materialtype);
		model.addAttribute("mlist", mlist);
		return "student/viewstudymaterial";	
	}
	else {
		return "redirect:/stulogin";
	}	

} catch (Exception e) {
	 return "redirect:/stulogin";
}	

}
@GetMapping("/assignment")	
public String ShowAssignment(HttpSession session,Model model)
{
try {
	if (session.getAttribute("studentid")!=null) {
		StudentInfo s=sreRepo.getById(session.getAttribute("studentid").toString());
		String program=s.getProgram();
		String branch=s.getBranch();
		String year=s.getYear();
		String materialtype="assign";
		List<Material> mlist=mrepo.getmaterial(program,branch,year,materialtype);
		model.addAttribute("mlist", mlist);
		
		return "student/viewassignment";	
	}
	else {
		return "redirect:/stulogin";
	}	

} catch (Exception e) {
	 return "redirect:/stulogin";
}	

}
@GetMapping("/response")	
public String showResponse(HttpSession session,  HttpServletResponse response,Model model)
{
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
try {
	if (session.getAttribute("studentid")!=null)
	{
	ResponseDto dto = new ResponseDto();
    model.addAttribute("dto", dto);


		return "student/giveresponse";
	
	} 
	else {
		return "redirect:/stulogin";
	}	

} catch (Exception e) {
	 return "redirect:/stulogin";
}	

}
@PostMapping("/response")
public String submitResponse(HttpSession session, Model model,@ModelAttribute ResponseDto responseDto,RedirectAttributes redirectAttributes, HttpServletResponse response)
{
	try {
		if (session.getAttribute("studentid")!=null) 
		{
			
			StudentInfo std=sreRepo.getById(session.getAttribute("studentid").toString());
			model.addAttribute("studentid", session.getAttribute("userid"));
			Response res=new Response();
			
			res.setName(std.getName()); 
			res.setEnrollmentno(std.getEnrollmentno());
			res.setEmailaddress(std.getEmailaddress());
			res.setContactno(std.getContactno());
			res.setResponsetype(responseDto.getResponsetype());
			res.setSubject(responseDto.getSubject());
			res.setMessage(responseDto.getMessage());
			res.setResdate(new Date()+"");
			resrepo.save(res);
			
			return "redirect:/student/response";
			
		}
		else
		{
			return "redirect:/student/response"	;	
		}
		
	} catch (Exception e) {
		 return "redirect:/studentlogin";
	}
	

}


@GetMapping("/changepassword")	
public String ShowChangePassword(HttpSession session)
{
try {
	if (session.getAttribute("studentid")!=null) {
		return "student/changepassword";	
	}
	else {
		return "redirect:/stulogin";
	}	

} catch (Exception e) {
	 return "redirect:/stulogin";
}	

}
@GetMapping("/logout")
public String Logout(HttpSession session)
{
session.invalidate();
return "redirect:/stulogin";	
}
 
@PostMapping("/changepassword")
public String changePassword(HttpSession session, HttpServletResponse response,HttpServletRequest request,RedirectAttributes attrib)
{
try {
	
	response.setHeader("Cache-Control","no-cache,no-store,no-revalidate");
	if(session.getAttribute("studentid")!=null) {
	  StudentInfo s=sreRepo.getById(session.getAttribute("studentid").toString());
	 String oldpassword=request.getParameter("oldpassword");
	 String newpassword=request.getParameter("newpassword");
	 String confirmpassword=request.getParameter("confirmpassword");
	 if(!newpassword.equals(confirmpassword))
	 {
		 attrib.addFlashAttribute("msg", "new password and confirm password are not match");
		 return "redirect:/student/changepassword";
	 }
    if (!oldpassword.equals(s.getPassword())) {
      attrib.addFlashAttribute("msg", "old password is not matched");
      return "redirect:/student/changepassword";
        }
		 s.setPassword(newpassword);
		 sreRepo.save(s);
		 return "redirect:/student/logout";
	 }
	
	else {
		return "redirect:/stulogin";
		
		
	}
	
	
} 

catch (Exception ex) {
	
	return "redirect:/stulogin";
	 
}

}
	
}

