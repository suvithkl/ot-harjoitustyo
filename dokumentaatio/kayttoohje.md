# Käyttöohje

Lataa tiedosto [Sudoku.jar](https://github.com/suvithkl/ot-harjoitustyo/releases/tag/loppupalautus).

Vaihtoehtoisesti lataa ja pura [palautusrepositorion lähdekoodi](https://github.com/suvithkl/ot-harjoitustyo/releases/tag/loppupalautus).

## Konfigurointi

Sovellus edellyttää, että jar-tiedoston kanssa samassa hakemistossa on tietokannan, sekä sen taulujen nimet määrittelevä konfiguraatiotiedosto [config.properties](https://github.com/suvithkl/ot-harjoitustyo/releases/tag/loppupalautus). Lataa myös se samaan hakemistoon, tai vaihtoehtoisesti määrittele se itse seuraavassa muodossa
```
sudokuDB=sudokuData.db
userTable=User
gameTable=Game
```
Ladattaessa lähdekoodi konfiguraatiotiedosto tulee automaattisesti mukana.

## Sovelluksen käynnistäminen

Käynnistä sovellus komennolla
```
java -jar Sudoku.jar
```

Ladattaessa lähdekoodi käynnistä sovellus kansiosta _Sudoku_ komennolla
```
mvn compile exec:java -Dexec.mainClass=sudoku.ui.Main
```

## Sisäänkirjautuminen

Sovellus käynnistyy kirjautumisäkymään.

Kirjaudu sisään kirjoittamalla olemassa oleva käyttäjänimi syötekenttään _Sign in:_ ja painamalla sen alapuolella olevaa nappia _OK_.

Sisäänkirjautumisen jälkeen siirrytään valikkonäkymään.

## Käyttäjän luominen

Kirjautumisnäkymässä voi myös luoda uuden käyttäjän.

Luo uusi käyttäjä syöttämällä uusi käyttäjänimi syötekenttään _Create new user:_ ja painamalla sen alapuolella olevaa nappia _OK_.

Sovellus ei hyväksy uusia käyttäjänimiä, jotka eivät ole uniikkeja, pituudeltaan 2-32 merkkiä sekä lainausmerkkejä ja asteriskeja sisältämättömiä.

Käyttäjän onnistuneen luomisen jälkeen siirrytään valikkonäkymään.

## Uuden pelin aloittaminen

Aloita uusi sudokupeli painamalla valikkonäkymän _New game_ otsikon alla olevista napeista haluamaasti vaikeustasoa vastaavaa nappia.

Vaikeustasovaihtoehtoina ovat helppo (nappi _EASY_), keskivaikea (nappi _NORMAL_) ja vaikea (nappi _HARD_).

Vaikeustason valinnan jälkeen siirrytään pelinäkymään.

## Sudokun pelaaminen

Täytä sudokuruudukkoa painamalla ensin haluamaasi ruutua ja sen jälkeen alareunan numeronapeista haluamaasi numeroa.

Näet sudokun ratkaisemiseen käyttäneesi sekunnit ja minuutit numeronappien alapuolella.

Tarkista onko täyttämäsi sudoku ratkaistu oikein painamalla nappia _CHECK_.

Jos sudoku on ratkaistu oikein, pelin tiedot tallennetaan pelitilastoihin ja siirrytään takaisin valikkonäkymään.

Jos sudoku on ratkaistu väärin, voit valita siirrytäänkö takaisin valikkonäkymään, vai jatketaanko pelaamista.

Lopeta keskeneräinen peli ja palaa takaisin valikkonäkymään painamalla nappia _MENU_.

## Pelitilastojen tarkastelu

Tarkastele pelitilastoja painamalla valikkonäkymän nappia _STATS_.

Ratkaistuista sudokuista näytetään ratkaisseen pelaajan käyttäjänimi, käytetty aika sekä pelin vaikeustaso.

Vasemmassa listassa näkyvät omat pelituloksesi, oikeassa kaikkien pelaajien kootut pelitulokset.

Palaa takaisin valikkonäkymään painamalla nappia _MENU_.

## Uloskirjautuminen

Kirjaudu ulos painamalla nappia _LOG OUT_.

Uloskirjautumisen jälkeen siirrytään kirjautumisnäkymään.

## Sovelluksen sulkeminen

Sulje sovellus kirjautumisnäkymässä painamalla nappia _EXIT_.

Vaihtoehtoisesti sulje sovellus sulkemalla ikkuna eli painamalla ikkunan yläkulman ruksia.
