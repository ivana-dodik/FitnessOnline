export interface Comment {
  username: string;
  content: string;
  dateTime: Date;
}

export interface AddCommentDto {
  content: string;
}
