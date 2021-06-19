package space.laimengchao.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import space.laimengchao.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${mail.fromMail.addr}")
    private String fromMailAddress;

    @Async
    @Override
    public void sendHtmlMail(String[] to, String[] bcc, String[] cc, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            // true表示需要创建一个multipart message
            MimeMessageHelper mimeMailMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMailMessageHelper.setFrom(fromMailAddress);
            mimeMailMessageHelper.setTo(to);
            if (bcc != null && bcc.length > 0) {
                mimeMailMessageHelper.setBcc(bcc);
            }
            if (cc != null && cc.length > 0) {
                mimeMailMessageHelper.setCc(cc);
            }
            mimeMailMessageHelper.setSubject(subject);
            mimeMailMessageHelper.setText(content);
            mailSender.send(mimeMessage);
            log.info("邮件发送成功");
        } catch (MessagingException e) {
            log.error(ExceptionUtil.getMessage(e));
        }
    }

}
