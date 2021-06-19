package space.laimengchao.service;

public interface MailService {

    void sendHtmlMail(String [] to,String [] bcc,String [] cc, String subject, String content);

}
