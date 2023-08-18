# TaskTracker

## Informations

### Tehnologies used
- [Node.js - v18.17.0](https://nodejs.org/en/)
- [Angular CLI - v16.1.6](https://angular.io/)
- [npm - v9.6.7](https://www.npmjs.com/)
- [WSL 2](https://learn.microsoft.com/en-us/windows/wsl/install)
- [Java - v20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html)
- [SpringBoot - v3.1.2](https://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com/)

### Start the frontend server
To start the frontend server run the following command in the `Frontend` directory:
```
ng run serve
```

If you run the frontend server using `WSL`, run the following command instead:
> If you use the previous command, the frontend will not update when you make changes to the code, until you restart the server.
```
ng serve --poll=2000
```
>In the previous command, the service worker is disabled. If you want to work the notifications reminder feature, you need to use this command instead:
```
ng serve --configuration=production
```
> Using this command will enable the service worker, but you will not be able to see the changes you make to the code until you restart the server.

### Config pulic and private key
Generate a public and private key using the following website: https://web-push-codelab.glitch.me/.
Using the keys generated, modify both backend and frontend.
```
Backend: com.litamihai.service.ReminderService.java
Frontend: src\app\components\footer\footer.component.ts
```
This keys are needed for the reminder feature.

### Open the frontend in a browser
To open the frontend in a browser, navigate to [http://localhost:4200/](http://localhost:4200/) in your browser.