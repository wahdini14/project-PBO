package wahdini.getajobcopy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import wahdini.getajobcopy.model.JobApplication;
import wahdini.getajobcopy.model.User;
import wahdini.getajobcopy.service.UserService;
import wahdini.getajobcopy.repository.JobApplicationRepository;
import java.security.Principal;
import java.util.List;

@Controller
public class JobApplicationController {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/pekerjaansaya")
public String pekerjaanSaya(Model model, HttpSession session) {

    User user = (User) session.getAttribute("loggedInUser");

    List<JobApplication> applied = jobApplicationRepository.findByUserAndStatus(user, "APPLIED");
    List<JobApplication> accepted = jobApplicationRepository.findByUserAndStatus(user, "ACCEPTED");
    List<JobApplication> finished = jobApplicationRepository.findByUserAndStatus(user, "FINISHED");

    model.addAttribute("appliedJobs", applied);
    model.addAttribute("acceptedJobs", accepted);
    model.addAttribute("finishedJobs", finished);

    return "pekerjaansaya";
}
}
