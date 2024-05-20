Database is initialized automatically by Hibernate with mocked data bootstrapped straight into it with help of CommandLineRunner.  

**Test user**

>username: user <br />
> password: password

JWT token check only a secret key and username, also since it was not meant to be valid, it has expiration time of around one day.