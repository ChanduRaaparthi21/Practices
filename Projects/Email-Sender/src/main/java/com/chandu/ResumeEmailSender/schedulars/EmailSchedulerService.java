package com.chandu.ResumeEmailSender.schedulars;

import com.chandu.ResumeEmailSender.model.HrDetails;
import com.chandu.ResumeEmailSender.service.EmailService;
import com.chandu.ResumeEmailSender.service.ExcelReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmailSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSchedulerService.class);

    @Autowired
    private ExcelReaderService excelReaderService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${resume.file.path}")
    private String resumePath;

    private List<HrDetails> hrDetailsList;
    private AtomicInteger currentIndex = new AtomicInteger(0);
    private boolean initialized = false;

    // Initialize HR list only once
    public void initialize() {
        if (!initialized) {
            hrDetailsList = excelReaderService.readHrDetails();
            if (hrDetailsList == null || hrDetailsList.isEmpty()) {
                logger.warn("No HR details found. Scheduler will stop.");
                shutdownApplication();
                return;
            }
            logger.info("✅ Initialized with {} HR contacts", hrDetailsList.size());
            initialized = true;
        }
    }

    /**
     * Runs every 5 seconds after the previous execution completes.
     * Sends one email each time until all HRs are processed.
     */
    @Scheduled(fixedDelayString = "${email.send.interval:5000}")
    public void sendNextEmail() {
        if (!initialized) {
            initialize();
        }

        if (hrDetailsList == null || hrDetailsList.isEmpty()) {
            logger.warn("No HR details found. Stopping scheduler.");
            shutdownApplication();
            return;
        }

        int index = currentIndex.getAndIncrement();

        if (index >= hrDetailsList.size()) {
            logger.info("✅ All emails have been sent. Shutting down...");
            shutdownApplication();
            return;
        }

        HrDetails hrDetails = hrDetailsList.get(index);
        sendEmailToHr(hrDetails);

        logger.info("📨 Progress: {}/{} emails sent", index + 1, hrDetailsList.size());
    }

    /**
     * Sends email safely with retry for transient SMTP issues.
     */
    private void sendEmailToHr(HrDetails hrDetails) {
        String hrEmail = hrDetails.getHrEmail();

        // Basic email validation
        if (hrEmail == null || hrEmail.trim().isEmpty() || !hrEmail.contains("@")) {
            logger.warn("⚠️ Skipping invalid email: {}", hrEmail);
            return;
        }

        String subject = "Application for Java Backend Developer | "
                + hrDetails.getCompanyName() + " | Chandu Raparthi";

        String body =
                "Dear " + hrDetails.getHrName() + ",\n\n" +
                        "I hope you are doing well.\n\n" +
                        "I'm Chandu Raparthi, a Java Backend Developer with 3 years of experience in building secure, scalable, and high-performance enterprise applications using Java, Spring Boot, and Spring Cloud microservices.\n\n" +
                        "Currently, I’m working at Cybrowse Digital Solutions Pvt. Ltd., Hyderabad, and I’m exploring backend opportunities at "
                        + hrDetails.getCompanyName() + ".\n\n" +
                        "My notice period is 15 days, and I’m available to join immediately after that.\n\n" +
                        "Please find my resume attached for your review.\n\n" +
                        "Best regards,\n" +
                        "Chandu Raparthi\n" +
                        "+91 9452301058\n" +
                        "raaparthichandu@gmail.com\n";

        // Try sending email with up to 3 retries
        int maxRetries = 3;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                emailService.sendEmailWithAttachment(hrEmail, subject, body, resumePath);
                logger.info("✅ Email sent to: {} ({} / {})", hrEmail, attempt, maxRetries);
                break; // success → exit retry loop
            } catch (Exception e) {
                logger.error("❌ Attempt {}/{} failed to send to {}: {}", attempt, maxRetries, hrEmail, e.getMessage());
                if (attempt == maxRetries) {
                    logger.error("🚫 Giving up on: {}", hrEmail);
                } else {
                    try {
                        TimeUnit.SECONDS.sleep(5 * attempt); // exponential backoff (5s, 10s, 15s)
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    /**
     * Gracefully shuts down Spring Boot once all emails are sent.
     */
    private void shutdownApplication() {
        logger.info("🟢 Email sending completed. Preparing to shut down application...");
        new Thread(() -> {
            try {
                Thread.sleep(5000); // allow last logs to flush
                int exitCode = SpringApplication.exit(applicationContext, () -> 0);
                System.exit(exitCode);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
