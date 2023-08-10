# TaskTracker

## Informations

### Tehnologies used
- [Node.js - v18.17.0](https://nodejs.org/en/)
- [Angular CLI - v16.1.6](https://angular.io/)
- [npm - v9.6.7](https://www.npmjs.com/)
- [WSL 2](https://learn.microsoft.com/en-us/windows/wsl/install)
- [~~Fake Json Server~~](https://www.npmjs.com/package/json-server)
- [Java - v20](https://www.oracle.com/java/technologies/javase/jdk20-archive-downloads.html)
- [SpringBoot - v3.1.2](https://spring.io/projects/spring-boot)
- [MongoDB](https://www.mongodb.com/)

## Backend

### Start the fake backend server (DEPRECATED)
To start the backend server run the following command in the `Fake JSON Server` directory:
```
npm run server
```
__*This is deprecated because we are using a real backend server now*__.

## Frontend

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

### Open the frontend in a browser
To open the frontend in a browser, navigate to [http://localhost:4200/](http://localhost:4200/) in your browser.