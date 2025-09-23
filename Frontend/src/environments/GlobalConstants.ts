import { environment } from "./environment";

export class GlobalConstants {

  // keycloak related info
  public static keycloakURL: string = environment.keycloakURL
  public static realm: string = environment.realm
  public static clientID: string = environment.clientID
  public static redirect: string = environment.redirect

  // AI Gemini URL
  public static miniURL: string = "https://belisty.com/mini";

  // Spring BOOT BE URL
  public static spring_boot_test_url: string = environment.spring_boot_test_url
  public static spring_boot_url: string = environment.spring_boot_url

  public static query_url: string = environment.query_url
  public static command_url: string = environment.command_url

  public static globalBEError: any;
  public static globalBESuccess: any;
}