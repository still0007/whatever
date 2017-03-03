package net.nemo.whatever.converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import net.nemo.whatever.util.DateUtil;
import net.nemo.whatever.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.nemo.whatever.entity.Attachment;
import net.nemo.whatever.entity.Chat;
import net.nemo.whatever.entity.ChatMessageType;
import net.nemo.whatever.entity.Message;
import net.nemo.whatever.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class MailMessageConverter {
	
	public final static String CHAT_DATE_PATTERN = "(\\d{4}-\\d{1,2}-\\d{1,2})";
	public final static String SENDER_TIME_PATTERN = "([a-zA-Z0-9\u4e00-\u9fa5]+) (\\d+:\\d+)";
	public final static String IMAGE_MSG_PATTERN = "图片(\\d+)（可在附件中查看）";
	public final static String LINK_MSG_PATTERN = "\\[(.*) : (http[s]?://.*)]";
	public final static String GROUP_CHAT_TITLE_PATTERN = "微信群\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"的聊天记录";
	public final static String DIALOG_CHAT_TITLE_PATTERN = "\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"和\"([a-zA-Z0-9\u4e00-\u9fa5]+)\"的聊天记录";

	@Value("${app.assets.path}")
	private String fileStorePath;

	public Chat fromMailMessage(javax.mail.Message message) {
		Chat chat = new Chat();
		try {
			chat.setGroupChat(isGroupChat(message.getSubject()));
			chat.setChatOwner(getMessageOwner(message.getSubject()));
			chat.setReceiver(getMessageReceiver(message.getSubject(), (InternetAddress) message.getFrom()[0]));

			List<Message> messages = new ArrayList<Message>();
			List<Attachment> attachments = new ArrayList<Attachment>();

			Part part = (Part) message;
			if (part.isMimeType("multipart/*")) {
				Multipart multipart = (Multipart) part.getContent();
				int counts = multipart.getCount();
				for (int j = 0; j < counts; j++) {
					BodyPart bodyPart = multipart.getBodyPart(j);
					if (bodyPart.isMimeType("multipart/*")) {
						saveAttachments(attachments, bodyPart);
					} else {
						messages.addAll(parseMessageBody(bodyPart.getContent()));
					}
				}
			}
			chat.setAttachments(attachments);
			
			for(Message msg : messages){
				if(ChatMessageType.IMAGE == msg.getType()){
					for(Attachment att : attachments){
						if(att.getFileName().endsWith(msg.getContent())){
							msg.setContent(att.getFileName());
							break;
						}
					}
				}
			}
			chat.setMessages(messages);
			
			message.setFlag(Flags.Flag.DELETED, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return chat;
	}

	private List<Message> parseMessageBody(Object content) {
		List<Message> messages = new ArrayList<Message>();

		String[] lines = content.toString().split(System.getProperty("line.separator"));
		String sender = null, date = null, time = null;
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].replaceAll("\\r", "");
			if (!"".equals(line)) {
				List<String> matches = null;

				// Date of the chat
				if (null != (matches = StringUtil.findFirstMatch(CHAT_DATE_PATTERN, line))) {
					date = matches.get(0);
				}
				// Name:Time of the message
				else if (null != (matches = StringUtil.findFirstMatch(SENDER_TIME_PATTERN, line))) {
					sender = matches.get(0);
					time = matches.get(1);
				}
				// Image content of the message
				else if (null != (matches = StringUtil.findFirstMatch(IMAGE_MSG_PATTERN, line))) {
					Message chatMessage = new Message();
					chatMessage.setContent(String.format("__%s.png", matches.get(0)));
					chatMessage.setType(ChatMessageType.IMAGE);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date + " " + time));

					messages.add(chatMessage);

					time = null;
					sender = null;
				}
				// Link content of the message
				else if (null != (matches = StringUtil.findFirstMatch(LINK_MSG_PATTERN, line))) {
					Message chatMessage = new Message();
					chatMessage.setContent(getLinkPreviewSegement(line));
					chatMessage.setType(ChatMessageType.LINK);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date + " " + time));

					messages.add(chatMessage);

					time = null;
					sender = null;
				}
				// Text content of the message
				else {
					if (time == null && sender == null)
						continue;

					Message chatMessage = new Message();
					chatMessage.setContent(line);
					chatMessage.setType(ChatMessageType.TEXT);
					chatMessage.setSender(sender);
					chatMessage.setTime(DateUtil.parseDate(date + " " + time));

					messages.add(chatMessage);

					time = null;
					sender = null;
				}
			}
		}

		return messages;
	}

	private void saveAttachments(List<Attachment> attachements, Part part) throws Exception {
		String fileName = "";
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mPart = mp.getBodyPart(i);
				String disposition = mPart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					fileName = mPart.getFileName();
					if (fileName.toLowerCase().indexOf("gb2312") != -1) {
						fileName = MimeUtility.decodeText(fileName);
					}
					String timestampedName = getTimestampedName(fileName);
					Attachment attachment = new Attachment(timestampedName, String.format("%s%s%s", DateUtil.formatDate(new Date(), "yyyyMMdd"), System.getProperty("file.separator"), timestampedName));
					attachements.add(attachment);
					saveFile(attachment, mPart.getInputStream());
				} else if (mPart.isMimeType("multipart/*")) {
					saveAttachments(attachements, mPart);
				} else {
					fileName = mPart.getFileName();
					if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
						fileName = MimeUtility.decodeText(fileName);
						String timestampedName = getTimestampedName(fileName);
						Attachment attachment = new Attachment(timestampedName, String.format("%s%s%s", DateUtil.formatDate(new Date(), "yyyyMMdd"), System.getProperty("file.separator"), timestampedName));
						attachements.add(attachment);
						saveFile(attachment, mPart.getInputStream());
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachments(attachements, (Part) part.getContent());
		}
	}

	private String getTimestampedName(String fileName) {
		Date now = new Date();
		return String.format("%d_%s", now.getTime(), fileName);
	}

	private void saveFile(Attachment attachment, InputStream stream) throws Exception{
		File dir = new File(this.fileStorePath + System.getProperty("file.separator") + DateUtil.formatDate(new Date(), "yyyyMMdd"));
		if(!dir.exists())
			dir.mkdir();
		
		File file = new File(dir.getAbsolutePath() + System.getProperty("file.separator") + attachment.getFileName());
		try {
            FileCopyUtils.copy(stream, new FileOutputStream(file));
			System.out.println("File saved to : " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("文件保存失败!");
		}
	}

	private boolean isGroupChat(String messageSubject) {
		List<String> matches = StringUtil.findFirstMatch(GROUP_CHAT_TITLE_PATTERN, messageSubject);
		return matches != null;
	}

	private String getMessageOwner(String messageSubject) {
		List<String> groupMatches = StringUtil.findFirstMatch(GROUP_CHAT_TITLE_PATTERN, messageSubject);
		List<String> dialogMatches = StringUtil.findFirstMatch(DIALOG_CHAT_TITLE_PATTERN, messageSubject);
		return groupMatches == null ? dialogMatches.get(0) : groupMatches.get(0);
	}

	private User getMessageReceiver(String messageSubject, InternetAddress from) {
		List<String> dialogMatches = StringUtil.findFirstMatch(DIALOG_CHAT_TITLE_PATTERN, messageSubject);
		String name = dialogMatches != null ? dialogMatches.get(1) : "";
		String email = from.getAddress().toString();
		return new User(name, email);
	}
	
	private String getLinkPreviewSegement(String line){
		String[] aa = line.replace("[", "").replace("]", "").split(" : ");
		String imgSrc = getLinkImage(aa[1]);
		
		String template = "<a target=\"_BLANK\" class=\"fragment\" href=\"%s\">"
				+ "<img src =\"%s\" class=\"thumbnail\"/>"
				+ "<span class=\"desc\">%s</span>"
				+ "<p class=\"text\"></p></a>";
		
		return String.format(template, aa[1], imgSrc, aa[0]);
	}
	
	private String getLinkImage(String url) {
		Document doc;
		try {
			doc = Jsoup.connect(url).get();

			Elements links = doc.select("img");
			for (Element link : links) {
				String src = link.attr("data-src") == null ? link.attr("src") : link.attr("data-src");
				if (src != null && !"".equals(src.trim())) {
					return src;
				}
			}
			return "/static/images/link.jpg";
		} catch (IOException e) {
			return "/static/images/link.jpg";
		}
	}
}
