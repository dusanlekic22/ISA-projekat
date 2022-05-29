export interface IUserDeletionRequest {
  id: number;
  accepted: boolean;
  deletionExplanation: string;
  userEmail: string;
  answer: string;
}
