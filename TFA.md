# Weryfikacja dwuskładnikowa

### Spis treści
- [Charakterystyka problemu](#charakterystyka-problemu)
- [Wykorzystane narzędzia](#wykorzystane-narzedzia)
- [Opis implementacji](#opis-implementacji)
- [Flow](#flow)

## Charakterystyka problemu

Weryfikacja dwuskładnikowa z wykorzystaniem aplikacji takich
jak Google Authenticator polega na wykorzystaniu algorytmów
hashujących do wygenerowania sekretu użytkownika, dodaniu konta
do aplikacji mobilnej za pomocą specjalnego kodu QR, a następnie
weryfikowaniu niezerowych, najczęściej 6-cio cyfrowych kodów, 
odświeżanych co określony interwał czasowy.

Implementacja rozwiązania wymaga bibliotek do:

- generowania kodów QR
- konwersji sekretnych kluczy na hex
- TOTP (Time-based one-time password)

## Wykorzystane narzędzia

Do implementacji MFA w aplikacji wykorzystano bibliotekę

> samstevens.totp:totp:1.7.1 (https://mvnrepository.com/artifact/dev.samstevens.totp/totp/1.7.1)

Opakowuje ona wszystkie wymienione wyżej narzędzia w dodatkową warstwę
abstrakcji, natomiast nie jest to najświeższe rozwiązanie w kontekście 
wykorzystania nowych wersji Javy / Spring Boota.

Repo oraz dokumentacja: https://github.com/samdjstevens/java-totp

## Opis implementacji

Do już istniejącego modułu security, zawierającego podstawową
konfigurację, obsługę JWT oraz uwierzytelniania przez zewnętrznych
providerów (w tym przypadku Google), został dodany `TwoFactorAuthenticationService`
z zestawem metod do generowania kodu QR w oparciu o określone parametry
oraz walidacji 6-cio cyfrowego kodu wygenerowanego w aplikacji mobilnej.

Odpowiednie metody do weryfikacji kodów i MFA znajdują się w `AuthController`
oraz `AuthenticationService`.

## Flow

>Nowe konto

 1. Użytkownik przechodzi do mockowego formularza rejestracji i decyduje, czy od razu chce włączyć weryfikację dwuskładnikową.

> Jeśli TFA włączone

2. Wyświetla się kod QR, użytkownik musi go zeskanować w aplikacji i wprowadzić kod weryfikacyjny, aby przejść dalej.
3. Użytkownik musi wprowadzać kod przy każdym ponownym logowaniu do aplikacji. Może wyłączyć TFA w ustawieniach konta.

> Jeśli TFA wyłączone

2. Następuje przekierowanie do aplikacji.
3. Użytkownik może logować się do aplikacji bez podawania kodu. Może włączyć TFA w ustawieniach konta.

---------------

> Włączanie TFA w ustawieniach

1. Użytkownik zaznacza "weryfikację dwuskładnikową" w ustawieniach konta.
2. Wyświetla się kod QR, użytkownik musi go zeskanować w aplikacji i wprowadzić. Dopiero po poprawnej weryfikacji sekret zostaje przypisany do konta i TFA zostaje faktycznie włączone.
3. Każdorazowe wyłączenie i ponowne włączenie TFA skutkuje koniecznością ponownego skanowania QR. Poprzednio zapisany kod nie działa, ponieważ zmienia się sekretny klucz.



