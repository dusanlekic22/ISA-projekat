import { RequestStatus } from "./requestStatus";

export interface IBusinessOwnerRegistrationRequest {
  id: number;
  accepted: RequestStatus;
  registrationExplanation: string;
  declineReason: string;
  userEmail: string;
}
