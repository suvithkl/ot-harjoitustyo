# Ohjelmistotekniikka, harjoitustyö

## Sudoku

Sovelluksella käyttäjien on mahdollista pelata perinteistä 9x9-ruudukollista sudokua. Sovellukseen on mahdollista rekisteröidä useita käyttäjänimiä. Näihin käyttäjänimiin perustuen kerätään pelituloksia,
ja pelaajat voivat sitten tarkastella omia parhaita tuloksiaan.

Projekti voidaan suorittaa kansiosta Sudoku komennolla
```
mvn compile exec:java -Dexec.mainClass=sudoku.ui.Main
```
Sillä JetBrainsin [linja](https://intellij-support.jetbrains.com/hc/en-us/articles/206544839) projektintekohetkellä on, että kaikkia .idea-kansion tiedostoja _ei_ poisteta versionhallinnasta, projektin
juureen on jätetty joitakin .idea-kansion tiedostoja [suositusten](https://github.com/github/gitignore/blob/master/Global/JetBrains.gitignore) mukaisesti (auto-import ei käytössä).

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)
