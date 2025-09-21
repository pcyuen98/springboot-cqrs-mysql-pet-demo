// models/question.model.ts
export class QuestionAIPayload {
  question: string;
  answer: string;
  isValidApplicationSuggestion: boolean | null;
  isHarmful: boolean | null;
  harmfulType: string;
}
