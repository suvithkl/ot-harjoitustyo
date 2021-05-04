# Ohjelmistotekniikka, harjoitustyö

## Sudoku

Sovelluksella käyttäjien on mahdollista pelata perinteistä 9x9-ruudukollista sudokua. Sovellukseen on mahdollista rekisteröidä useita käyttäjänimiä. Näihin käyttäjänimiin perustuen kerätään pelituloksia,
ja pelaajat voivat sitten tarkastella omia parhaita tuloksiaan.

Sillä JetBrainsin [linja](https://intellij-support.jetbrains.com/hc/en-us/articles/206544839) projektintekohetkellä on, että kaikkia .idea-kansion tiedostoja _ei_ poisteta versionhallinnasta, projektin
juureen on jätetty joitakin .idea-kansion tiedostoja [suositusten](https://github.com/github/gitignore/blob/master/Global/JetBrains.gitignore) mukaisesti (auto-import ei käytössä).

## Dokumentaatio

[Käyttöohje](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/suvithkl/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/suvithkl/ot-harjoitustyo/releases/tag/viikko6)

## Komentorivikomennot

Komennot on kirjoitettu muodossa, jossa ne voidaan suorittaa hakemistossa _Sudoku_ (eli esim. Viikon 5 releasen polussa _/ot-harjoitustyo-viikko5/Sudoku_).

### Suorittaminen

Projekti voidaan suorittaa komennolla
```
mvn compile exec:java -Dexec.mainClass=sudoku.ui.Main
```
Jos ohjelmaa yritetään suorittaa virtuaalityöpöydän avulla ja yllä oleva komento aiheuttaa SQL-virheen, on repositorio kloonattava hakemistoon _/tmp_ (ei siis kotihakemistoon, virtuaalityöpöydän verkkolevy ei ole osoittautunut tarpeeksi nopeaksi tietokannalle).

### Testaus

Testit voidaan suorittaa komennolla
```
mvn test
```

Testikattavuusraportti voidaan luoda komennolla
```
mvn jacoco:report
```
sekä komennolla
```
mvn test jacoco:report
```
ja avaamalla selaimella tiedosto _target/site/jacoco/index.html_ sitä voidaan lukea.

### Suoritettavan jarin generointi

Suoritettava jar-tiedosto voidaan luoda komennolla
```
mvn package
```
ja se löytyy hakemistosta _target_ nimellä _Sudoku-1.0-SNAPSHOT.jar_ ja mahdollisesti nimellä _Sudoku-1.0-SNAPSHOT-shaded.jar_.

Se voidaan suorittaa komennolla
```
java -jar target/Sudoku-1.0-SNAPSHOT.jar
```

### JavaDoc

JavaDoc voidaan luoda komennolla
```
mvn javadoc:javadoc
```
ja avaamalla selaimella tiedosto _target/site/apidocs/index.thml_ sitä voidaan lukea.

### Checkstyle

Tiedostossa [checkstyle.xml](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/checkstyle.xml) määrittellyt tarkastukset voidaan suorittaa komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
ja avaamalla selaimella tiedosto _target/site/checkstyle.html_ mahdollisia virheilmoituksia voidaan lukea.
