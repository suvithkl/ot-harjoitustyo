# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksella käyttäjien on mahdollista pelata perinteistä 9x9-ruudukollista sudokua. Sovellukseen on mahdollista rekisteröidä useita käyttäjänimiä. Näihin käyttäjänimiin perustuen kerätään pelituloksia, ja pelaajat voivat sitten tarkastella omia parhaita tuloksiaan.

## Käyttäjät

Sovelluksella on vain _normaaleja käyttäjiä_ eli vain yksi käyttäjärooli. Kaikkien käyttäjien on siis mahdollista käyttää kaikkia ominaisuuksia.

## Käyttöliittymä sanallisesti

Sovelluksessa on neljä näkymää. Avattaessa sovellus aukeaa kirjautumisnäkymä, jossa samassa on mahdollista luoda uusi käyttäjänimi. Kun kirjautuminen onnistuu, siirrytään aloitusvalikkonäkymään.
Siitä on mahdollista siirtyä pelinäkymään, eli itse sudokuun, tai pelitilastonäkymään. Pelinäkymästä sekä tilastonäkymästä on mahdollista siirtyä takaisin aloitusnäkymään. Kirjauduttaessa ulos siirrytään takaisin kirjautumisnäkymään.

## Toiminnallisuus

### Ennen kirjautumista

- Käyttäjä voi tehdä käyttäjänimen järjestelmään
  - Käyttäjänimen on oltava vähintään 2 merkkiä pitkä, lainausmerkkejä ja asteriskeja sisältämätön, sekä uniikki

- Käyttäjä voi kirjautua sisään järjestelmään
  - Jos syötetään olemassa oleva käyttäjänimi, kirjautuminen onnistuu
  - Jos syötetään käyttäjänimi, jota ei ole olemassa, annetaan tästä ilmoitus

### Kirjautumisen jälkeen

- Käyttäjä voi pelata sudokua
  - Valittavissa on kolme eri vaikeustasoa (helppo, normaali ja vaikea)
  - Valittaessa jonkin näistä käyttäjälle generoidaan vaikeustasoa vastaava sudokuruudukko
  - Tämän jälkeen sudoku on ratkaistavissa
  - Sen voi ratkaista valitsemalla ensin jonkin pelinäkymän numeronapeista ja sitten ruudukon ruutua klikkaamalla

- Käyttäjä voi palata kesken pelin aloitusnäkymään

- Käyttäjälle ilmoitetaan tarkistusnappia painettaessa, onko täytetty sudoku on oikein vai väärin
  - Väärän ratkaisun tapauksessa käyttäjä voi palata aloitusnäkymään tai jatkaa peliä vaihtamalla asettamiaan numeroita

- Kun sudoku on ratkaistu oikein, paikalliselle levylle tallentuu tieto pelanneesta käyttäjästä, käytetystä ajasta ja vaikeustasosta

- Käyttäjä voi tarkastella pelitulostilastoja
  - Nähtävissä ovat sekä omat, että kaikkien pelaajien kootut tulokset

- Käyttäjä voi kirjautua ulos sovelluksesta

- Käyttäjä voi sulkea sovelluksen aloitusnäkymän painikkeella

## Jatkokehitysideoita

Kurssin jälkeen järjestelmää saatetaan mahdollisesti täydentää seuraavilla ominaisuuksilla

- Pelituloksiin tallennetaan lisäksi tehtyjen virheiden määrä
- Pelityylin valinta; vapaa eli normaali ja aikahaaste (sudoku ratkaistava tietyssä ajassa)
- Keskeytettyä sudokua voi jatkaa myöhemmin
- Käyttäjänimeen liittyy myös salasana, jonka kirjautuminen vaatii
- Käyttäjänimen ja sen alla tehtyjen pelitulosten poisto
