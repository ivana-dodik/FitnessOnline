package org.unibl.etf.ip.fitnessonline.models.dto;

public record InboxDto(int senderId, String username, String lastMessageContent, boolean isRead) {
}
