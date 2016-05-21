# STEAM Dataminer

Ez az alkalmazás a Steam Piac (Steam Market) elemeit vizsgálja.

### Használt technológiák:
---
  [Maven] - A projekt összeállításáért felelős felelős eszköz.

### Telepítés
---
Letöltjük a projektet a Git segítségével:

```sh
git clone https://github.com/orotib/steam-dataminer.git
```

A telepítés a Maven segítségével történik. A projekt főkönyvtárában állva:

```sh
mvn clean install
```

Majd az elkészült futtatható állományt futtatjuk:

```sh
java -jar target\steam-market-dataminer-1.0-jar-with-dependencies.jar
```

### Szoftveres követelmények
---
  Apache Maven 3.3.9

  [Oracle JDK 8]

### Használati útmutató
---
Regisztrálni kell egy Account-ot az alábbi oldalon: [CurrencyLayer]

Kötelező valuták:

    USD
    GBP
    EUR
    CHF
    RUB
    BRL
    JPY
    SEK
    IDR
    MYR
    PHP
    SHD
    THB
    KRW
    TRY
    MXN
    CAD
    NZD
    CNY
    INR
    CLP
    SGD
    COP
    ZAR
    HKD
    TWD
    SAR
    AED

#### Linux
Bemásoljuk a letöltő linket a következő fájlba:

```sh
/home/.steamdataminer/currency-download-link
```

A vizsgálandó linkeket az alábbi fájlnak kell tartalmaznia:

```sh
/home/.steamdataminer/links
```

Az alábbi formátumban:

```sh
szám;steam link
```

---
#### Windows
Bemásoljuk a letöltő linket a következő fájlba:

```sh
C:\Users\<Felhasználói név>\AppData\Roaming\Steam Data Miner\currency-download-link
```

A vizsgálandó linkeket az alábbi fájlnak kell tartalmaznia:

```sh
C:\Users\<Felhasználói név>\AppData\Roaming\Steam Data Miner\links
```

Az alábbi formátumban:

```sh
szám;steam link
```

### Felhasznált függőségek
---
    - [Commons IO]
    - [Google Gson]
    - [Jsoup]
    - [SLF4J]
    - [Logback]
    - [Commons-lang 3]
    - [Junit]

### Verzió
---
1.0

### License
----
[GNU GPLv3](http://www.gnu.org/licenses/gpl-3.0.html)

   [Maven]: <https://maven.apache.org/>
   [Oracle JDK 8]: <http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>
   [git-repo-url]: <https://github.com/orotib/steam-dataminer.git>
   [CurrencyLayer]: <https://currencylayer.com>
   [Commons IO]: <https://commons.apache.org/proper/commons-io/>
   [Google Gson]: <https://github.com/google/gson>
   [Jsoup]: <https://jsoup.org/>
   [SLF4J]: <http://www.slf4j.org/>
   [Logback]: <http://logback.qos.ch/>
   [Commons-lang 3]: <https://commons.apache.org/proper/commons-lang/>
   [Junit]: <http://junit.org/junit4/>
