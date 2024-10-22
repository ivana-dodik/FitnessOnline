package org.unibl.etf.ip.fitnessonline.models.dto;

import java.sql.Timestamp;

public record InboxMessageDto(boolean isMe, String content, Timestamp dateTime) {
}
