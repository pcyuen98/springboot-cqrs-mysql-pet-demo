
export class Action {
  label?: string;
  color?: string;
  handler: () => void;
  isButtonErrorType?: boolean;
  isClearButtonErrorType?: boolean;
}