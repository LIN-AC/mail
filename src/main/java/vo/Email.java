package vo;

import javax.activation.DataSource;
import javax.mail.Address;
import java.util.List;

/**
 * 邮件信息类
 *
 * @author 高焕杰
 **/
public class Email {

	private long id; // 邮件id
	private String date; // 创建时间
	private String from; // 发件人账户
	private int readState; // 邮件是否已读
	private String subject; // 邮件主题
	private String draftTo; // 草稿箱收件人账户
	private String htmlContent; // HTML邮件内容
	private List<Address> to; // 收件人账户
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public int getReadState() {
		return readState;
	}
	public void setReadState(int readState) {
		this.readState = readState;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDraftTo() {
		return draftTo;
	}
	public void setDraftTo(String draftTo) {
		this.draftTo = draftTo;
	}
	public String getHtmlContent() {
		return htmlContent;
	}
	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	public List<Address> getTo() {
		return to;
	}
	public void setTo(List<Address> to) {
		this.to = to;
	}
	public List<DataSource> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<DataSource> attachments) {
		this.attachments = attachments;
	}
	@Override
	public String toString() {
		return "Email{" + "from='" + from + '\'' + ", subject='" + subject + '\'' + ", htmlContent='" + htmlContent
				+ '\'' + ", to=" + to + '}';
	}
}
