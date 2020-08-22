package vo;

import java.util.List;

import javax.activation.DataSource;

public class Draft {

	private long id; // 邮件id
	private String date; // 创建时间
	private String subject; // 邮件主题
	private String to; // 草稿箱收件人账户
	private String htmlContent; // HTML邮件内容
	private List<DataSource> attachments; // 邮件附件
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public List<DataSource> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<DataSource> attachments) {
		this.attachments = attachments;
	}
	@Override
	public String toString() {
		return "Draft [id=" + id + ", date=" + date + ", subject=" + subject + ", to=" + to + ", htmlContent=" + htmlContent + ", attachments=" + attachments + "]";
	}
}