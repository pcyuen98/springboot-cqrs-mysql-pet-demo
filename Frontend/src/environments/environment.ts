// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,

  keycloakURL: "http://localhost:8080",
  realm: "keycloak",
  clientID: "angularfront",
  redirect: "http://localhost:4200",

  spring_boot_test_url: "http://localhost:7090/demo/test/v1",
  spring_boot_url:  "http://localhost:7090/demo/keycloak/v1",
  
};