package wahdini.getajobcopy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import wahdini.getajobcopy.model.JobApplication;
import wahdini.getajobcopy.model.User;
import wahdini.getajobcopy.repository.JobApplicationRepository;
import wahdini.getajobcopy.repository.JobRepository;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class JobApplicationController {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/pekerjaansaya")
    public String pekerjaanSaya(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            System.out.println("⚠️ USER SESSION NULL — redirect login");
            return "redirect:/login";
        }

        System.out.println("LOGIN USER ID = " + user.getId());

        List<JobApplication> dilamar = jobApplicationRepository.findByUserAndStatus(user, "APPLIED");
        List<JobApplication> diterima = jobApplicationRepository.findByUserAndStatus(user, "ACCEPTED");
        List<JobApplication> selesai = jobApplicationRepository.findByUserAndStatus(user, "FINISHED");

        System.out.println("Jumlah Dilamar = " + dilamar.size());
        System.out.println("Jumlah Diterima = " + diterima.size());
        System.out.println("Jumlah Selesai = " + selesai.size());

        model.addAttribute("dilamarList", dilamar);
        model.addAttribute("diterimaList", diterima);
        model.addAttribute("selesaiList", selesai);

        return "pekerjaansaya";
    }

    @PostMapping("/pekerjaansaya/{appId}/finish")
    public String markFinished(@PathVariable Long appId, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        JobApplication app = jobApplicationRepository.findById(appId).orElse(null);
        if (app == null) return "redirect:/pekerjaansaya";

        // pastikan aplikasi milik user yang sedang login
        if (!app.getUser().getId().equals(user.getId())) return "redirect:/pekerjaansaya";

        app.setStatus("FINISHED");
        jobApplicationRepository.save(app);

        // juga tandai job sebagai FINISHED
        if (app.getJob() != null) {
            var job = app.getJob();
            job.setStatus("FINISHED");
            jobRepository.save(job);
        }

        return "redirect:/pekerjaansaya";
    }

}
