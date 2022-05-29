export interface IBusinessOwnerRegistrationRequest {
  id: number;
  accepted: boolean;
  registrationExplanation: string;
  declineReason: string;
  userEmail: string;
}
