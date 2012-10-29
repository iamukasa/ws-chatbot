package de.vlad.supportcenter.chatbot;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import de.vlad.supportcenter.model.ChatEntry;
import de.vlad.supportcenter.model.ChatSession;
import de.vlad.supportcenter.model.ChatUserPermission;
import de.vlad.supportcenter.model.SupportAgent;
import de.vlad.supportcenter.model.enums.ChatStatus;
import de.vlad.supportcenter.model.enums.EntryType;
import de.vlad.supportcenter.model.enums.SupportRole;
import de.vlad.supportcenter.model.manager.ChatEntryManager;
import de.vlad.supportcenter.model.manager.ChatManager;
import de.vlad.supportcenter.model.manager.enums.SystemNotification;

@Configurable
public class ChatBotEntryPoint {
	
	private ChatEntryManager chatEntryManager;
	private SupportAgent botAgent;
	private ChatUserPermission botChatPermission;
	private ChatBotAI chatbotAI;
	
	/** The chat manager. */
	@Autowired
	private transient ChatManager chatManager;
	
	public ChatBotEntryPoint(ChatEntryManager chatEntryManager) {
		this.chatEntryManager = chatEntryManager;
		this.botAgent = SupportAgent.findAllSupportAgents().get(0);
		System.out.println("Bot Agent inited:" + botAgent.toString());
		this.chatbotAI = new ChatBotAI();
	}
	
	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		botChatPermission = assignSessionToBot(chatEntryManager.getChatSession());
	}
	
	/**
	 * Called when the chat user (real person) has posted a new message. Here is the entry point for the analysis of the user message and the preparation of the bot answer.
	 * @param userMessage The message which the real user has posted
	 */
	public void userPostedMessageEventListener(String userMessage) {
		String answer = chatbotAI.generateBotAnswer(userMessage);
		postChatbotMessage(answer);
	}
	
	/**
	 * Posts the prepared message from the bot to the chat.
	 * @param botMessage The message which the bot should post.
	 */
	private void postChatbotMessage(String botMessage) {
		
		ChatEntry entry = new ChatEntry();
		Date curTime = new Date();
		entry.setComment(botMessage);
		entry.setUserPermission(botChatPermission);
		chatManager.updateSessionTime(chatEntryManager.getChatSession().getId());
		entry.setTimeCreated(curTime);
		entry.setType(EntryType.COMMENT);
		entry.persist();
	}
	
	/**
	 * Assign session to current agent.
	 *
	 * @param session the session
	 * @return true, if successful
	 */
	@Transactional
	public ChatUserPermission assignSessionToBot(ChatSession session) {
		SupportAgent existingAssignee = chatEntryManager.getSupportAgent();
		if(existingAssignee == null) {
			Date curTime = new Date();
			ChatUserPermission permission = new ChatUserPermission();
			permission.setActive(true);
			permission.setChatUser(botAgent);
			permission.setSession(session);
			permission.setChatRole(SupportRole.SUPPORT_AGENT);
			permission.setTimeAssigned(curTime);
			permission.persist();
			
			ChatEntry systemEntry = new ChatEntry();
			systemEntry.setComment(SystemNotification.AGENT_ASSIGN.toString());
			systemEntry.setTimeCreated(curTime);
			systemEntry.setType(EntryType.SYSTEM_NOTIFICATION);
			systemEntry.setUserPermission(permission);
			systemEntry.persist();
			
			
			chatManager.updateSessionStatus(session.getId(), ChatStatus.IN_PROGRESS);
			
			return permission;
		}
		
		return chatEntryManager.getPermissionManager().getSupportAgentPermission();
	}

}
