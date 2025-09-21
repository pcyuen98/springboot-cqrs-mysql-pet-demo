import { FeedbackResponse } from "./feedback-response";
import { Learning } from "./learning";
import { User } from "./user";

export class Feedback {
    id: number;
    feedbackType: number;
    msg: string | undefined;
    creationDate: string | undefined;
    userDTO: User;
    learning: Learning;
    feedbackResponse: FeedbackResponse;
  }