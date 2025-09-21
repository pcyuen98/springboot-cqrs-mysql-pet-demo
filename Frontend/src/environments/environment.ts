// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,

  keycloakURL: "https://belisty.com:8443",
  realm: "keycloak",
  clientID: "angularfrontlocal",
  redirect: "http://localhost:4200",

  spring_boot_test_url: "http://localhost:7090/demo/test/v1",
  spring_boot_url:  "http://localhost:7090/demo/keycloak/v1",
  
};