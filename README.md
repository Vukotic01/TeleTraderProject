TeleTraderProject

Uputstvo za pokretanje

Projekat je razvijen u Java (Spring Boot) tehnologiji, uz korišćenje PostgreSQL baze podataka.

Pokretanje programa
Projekat možete testirati pomoću Postmana sa sledećim podešavanjima:

Server port: 8080

Konfiguracija baze podataka:

Port baze: 5432

Ime baze: TeleTrader (Potrebno je kreirati bazu pod tim imenom u PostgreSQL-u,ja sam koristio PostgreSQL 16)

Username: postgres

Password: super

Ukoliko želite da testirate aplikaciju sa sopstvenom PostgreSQL bazom, potrebno je da izmenite konfiguraciju u fajlu resources/application.properties:


spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/TeleTrader
spring.datasource.username=postgres
spring.datasource.password=super


Funkcionalnosti:

Sign-up

METHOD:POST

URL: http://localhost:8080/app/sign-up

Primer JSON objekta za registraciju (lozinka mora ispunjavati sledeće uslove: najmanje jedan specijalni karakter, jedno veliko slovo, jedan broj, odgovarajuća dužina):


{
  "fullName": "Veljko Doe",
  "email": "veljkoe@example.com",
  "password": "@aaaaPpassword123"
}


Sign-in

METHOD:GET

URL: http://localhost:8080/app/sign-in

U Postman-u, izaberite Authorization tab, odaberite Basic Auth i unesite registrovani email i lozinku.

Primer JSON objekta:

Email: veljkoe@example.com
Lozinka: @aaaaPpassword123

Nakon uspešne prijave, kopirajte Authorization token koji se nalazi u zaglavlju odgovora pod nazivom Authorization. Ovaj token je potreban za pristup drugim metodama.


Create order (Autorizacija potrebna)

METHOD:POST

URL: http://localhost:8080/app/order

U Postman-u, u sekciji Authorization, izaberite Bearer Token i unesite prethodno dobijeni Authorization token. Takođe, obavezno uklonite eventualni \n karakter na kraju tokena.

Primer JSON objekta za BUY:

{
  "price": 5.5,
  "amount": 23.0,
  "orderType": "BUY"
}

Primer JSON objekta za SELL:

{
  "price": 5.5,
  "amount": 23.0,
  "orderType": "SELL"
}


Top 10 Buy Orders (Autorizacija potrebna)

METHOD:GET

URL: http://localhost:8080/app/top-buy

Ova funkcionalnost vraća listu 10 najboljih Buy porudžbina sortiranih po ceni.(Opadajuce)

Autorizacija se vrši putem Bearer Token kao u prethodnoj metodi.



Top 10 Sell Orders (Autorizacija potrebna)

METHOD:GET

URL: http://localhost:8080/app/top-sell

Ova funkcionalnost vraća listu 10 najboljih Sell porudžbina sortiranih po ceni.(Rastuce)

Autorizacija se vrši putem Bearer Token kao u prethodnoj metodi.


Ukoliko imate problema sa pokretanjem ili testiranjem slobodno se obratite na email:veljkovuk4601@gmail.com

