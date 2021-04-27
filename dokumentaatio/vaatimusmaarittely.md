# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksella käyttäjien on mahdollista pelata perinteistä 9x9-ruudukollista sudokua. Sovellukseen on mahdollista rekisteröidä useita käyttäjänimiä. Näihin käyttäjänimiin perustuen kerätään pelituloksia,
ja pelaajat voivat sitten tarkastella omia parhaita tuloksiaan.

## Käyttäjät

Aluksi on vain _normaaleja käyttäjiä_ eli vain yksi käyttäjärooli. Myöhemmin mahdollisesti lisätään rooli _pääkäyttäjä_, jolla on enemmän oikeuksia, kuten käyttäjänimen poisto.

## Käyttöliittymäluonnos sanallisesti

Sovelluksessa on neljä näkymää. Avattaessa sovellus aukeaa kirjautumisnäkymä, jossa samassa on mahdollista luoda uusi käyttäjänimi. Kun kirjautuminen onnistuu, siirrytään aloitusvalikkonäkymään.
Siitä on mahdollista siirtyä pelinäkymään, eli itse sudokuun, tai pelitilastonäkymään. Pelinäkymästä sekä tilastonäkymästä on mahdollista siirtyä takaisin aloitusnäkymään. Kirjauduttaessa ulos siirrytään
takaisin kirjautumisnäkymään.

## Perusversion suunniteltu toiminnallisuus

### Ennen kirjautumista

- Käyttäjä voi tehdä käyttäjänimen järjestelmään _(tehty)_
  - Käyttäjänimen on oltava vähintään 2 merkkiä pitkä ja uniikki

- Käyttäjä voi kirjautua sisään järjestelmään _(tehty)_
  - Jos syötetään olemassa oleva käyttäjänimi, kirjautuminen onnistuu
  - Jos syötetään käyttäjänimi, jota ei ole olemassa, annetaan tästä ilmoitus

### Kirjautumisen jälkeen

- Käyttäjä voi pelata sudokua _(tehty)_
  - Käyttäjälle generoidaan näin valittaessa sudokuruudukko
  - Tämän jälkeen sudoku on ratkaistavissa
  - Sen voi ratkaista valitsemalla ruudukon ruudun kerrallaan ja kirjoittamalla numeron _(huom. jatkokehitysidean tavalla)_

- Käyttäjä voi palata kesken sudokun aloitusnäkymään _(tehty)_

- Käyttäjälle ilmoitetaan, onko täytetty sudoku on oikein vai väärin _(tehty)_
  - Väärän ratkaisun tapauksessa palataan aloitusnäkymään

- Kun sudoku on ratkaistu oikein, paikalliselle levylle tallentuu tieto pelanneesta käyttäjästä ja käytetystä ajasta

- Käyttäjä voi tarkastella pelitulostilastoja

- Käyttäjä voi kirjautua ulos sovelluksesta _(tehty)_

## Jatkokehitysideoita

Perusversion toteuttamisen jälkeen järjestelmää saatetaan mahdollisesti täydentää muun muassa seuraavilla ominaisuuksilla

- Väärin täytetyn sudokun tapauksessa on vaihtoehtoina joko palata aloitusnäkymään tai jatkaa peliä vaihtamalla numeroita
- Pelituloksiin tallennetaan peliajan lisäksi tehtyjen virheiden määrä
- Käyttäjät voivat tarkastella kaikkien pelaajien koottuja parhaita tuloksia
- Pelituloksien tallentaminen verkkoon
- Puhtaasti graafinen käyttöliittymä, numerot laitetaan ruutuihin ensin numeronappia ja sitten ruudukon ruutua klikkaamalla _(tehty)_
- Vaikeustason valinta, normaali ja vaikea (eli vähemmän numeroita alussa)
- Pelityylin valinta, vapaa eli normaali ja aikahaaste (sudoku ratkaistava tietyssä ajassa)
- Keskeytettyä sudokua voi jatkaa myöhemmin
- Käyttäjänimeen liittyy myös salasana, jonka kirjautuminen vaatii
- Käyttäjänimen ja sen alla tehtyjen pelitulosten poisto
