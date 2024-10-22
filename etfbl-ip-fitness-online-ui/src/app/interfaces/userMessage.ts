export interface AddUserMessageDto{
    content: string;
    receiverId: number;
}

export interface UserMessageDetailsDto{
    id: number;
    content: string;
    isRead: boolean;
    dateTime: Date;
    senderId: number;
}

export interface InboxDto{
    senderId: number;
    username: string;
    lastMessageContent: string;
    isRead: boolean;
}

export interface InboxMessageDto{
    isMe: boolean;
    content: string;
    dateTime: Date;
}