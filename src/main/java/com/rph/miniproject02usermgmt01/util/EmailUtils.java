package com.rph.miniproject02usermgmt01.util;


import com.rph.miniproject02usermgmt01.constants.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body)  {
        boolean isSent = false;
        try {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setText(body, true);
        mailSender.send(mimeMessageHelper.getMimeMessage());
        isSent = true;
        } catch (Exception e) {
            logger.error(AppConstants.EXCEPTION_OCCURRED  + e.getMessage(), e);
        }
        return isSent;
    }
}
