import { RequestStatus } from "./requestStatus";

export interface IUserDeletionRequest {
  id: number;
  accepted: RequestStatus;
  deletionExplanation: string;
  userEmail: string;
  answer: string;
}
