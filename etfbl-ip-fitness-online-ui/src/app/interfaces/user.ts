export interface RegisterUserDto {
  firstName: string;
  lastName: string;
  city: string;
  username: string;
  password: string;
  email: string;
  avatarUrl: string;
  captchaResponse: string;
  answer: number;
}

export interface UserEntity {
  id: number;
  firstName: string;
  lastName: string;
  city: string;
  username: string;
  password: string;
  email: string;
  activated: boolean;
  deleted: boolean;
  avatarUrl: string | null;
  activationToken: string | null; 
}

export interface EditUserDto {
  firstName: string;
  lastName: string;
  city: string;
  email: string;
}

export interface EditUserPasswordDto {
  currentPassword: string;
  newPassword: string;
}

export interface InstructorInfoDto {
  firstName: string;
  lastName: string;
  username: string;
}

export interface UserDto{
  userId: number;
  username: string;
}