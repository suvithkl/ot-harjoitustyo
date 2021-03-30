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

- Käyttäjä voi luoda käyttäjänimen järjestelmään
  - Käyttäjänimen on oltava vähintään 2 merkkiä pitkä ja uniikki

- Käyttäjä voi kirjautua järjestelmään
  - Jos syötetään olemassa oleva käyttäjänimi, kirjautuminen onnistuu
  - Jos syötetään käyttäjänimi, jota ei ole olemassa, annetaan tästä ilmoitus

### Kirjautumisen jälkeen

- Käyttäjä voi pelata sudokua
  - Käyttäjälle generoidaan näin valittaessa sudoku
  - Tämän jälkeen sen voi ratkaista valitsemalla ruudukon ruudun kerrallaan ja kirjoittamalla numeron

- Käyttäjä voi palata kesken sudokun aloitusnäkymään

- Käyttäjälle ilmoitetaan, onko kokonaan täytetty sudoku on oikein vai väärin
  - Väärän ratkaisun tapauksessa palataan aloitusnäkymään

- Kun sudoku on ratkaistu oikein, paikalliselle levylle tallentuu tieto pelanneesta käyttäjästä ja käytetystä ajasta

- Käyttäjä voi tarkastella pelitulostilastoja

- Käyttäjä voi kirjautua ulos järjestelmästä

## Jatkokehitysideoita

Perusversion toteuttamisen jälkeen järjestelmää saatetaan mahdollisesti täydentää muun muassa seuraavilla ominaisuuksilla

- Väärin täytetyn sudokun tapauksessa on vaihtoehtoina joko palata aloitusnäkymään tai jatkaa peliä vaihtamalla numeroita
- Pelituloksiin tallennetaan peliajan lisäksi tehtyjen virheiden määrä
- Käyttäjät voivat tarkastella kaikkien pelaajien koottuja parhaita tuloksia
- Pelituloksien tallentaminen verkkoon
- Puhtaasti graafinen käyttöliittymä, numerot laitetaan ruutuihin ensin numeronappia ja sitten ruudukon ruutua klikkaamalla
- Vaikeustason valinta, normaali ja vaikea (eli vähemmän numeroita alussa)
- Pelityylin valinta, vapaa eli normaali ja aikahaaste (sudoku ratkaistava tietyssä ajassa)
- Keskeytettyä sudokua voi jatkaa myöhemmin
- Jos sudoku ratkaistaan väärin, tämä ilmoitus näytetään vain kerran pelin aikana
- Käyttäjänimeen liittyy myös salasana, jonka kirjautuminen vaatii
- Käyttäjänimen ja siihen liittyvien pelitulosten poisto
