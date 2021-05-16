# Testausdokumentti

Sovellus on testattu JUnitin automatisoitujen testien avulla, sekä manuaalisten järjestelmätason testin avulla.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Erityisesti pakkauksen _sudoku.domain_ luokkia testataan integraatiotestein. Sovelluslogiikan varsinaisista toiminnallisuuksista huolehtivan _SudokuService_-luokan testit on jaettu kahteen eri testiluokkaan _SudokuServiceUserTest_ ja _SudokuServiceGameTest_, jotta käyttöliittymän toiminnallisuudet saataisiin kattavasti testattua integraatiotestien avulla.

Integraatiotestien lisäksi erityisesti "kirjaimellisemmin" olioita edustavissa luokissa, siis _User_, _Game_ ja _Grid_ -luokissa, on hyödynnetty myös jonkin verran yksikkötestausta, sillä ne sisältävät tätä vaativia metodeja.

### Tietojen pysyväistallennus

Kaikki pakkauksen _sudoku.dao_ luokat on testattu hyödyntämällä väliaikaista tietokantaa, joka poistetaan, kun testit on suoritettu. Tämän tilapäisen tietokannan avulla kaikille pakkauksen luokille on saatu tehtyä integraatiotestejä. Myös yksikkötestauksen periaatteita on hyödynnetty.

### Testauskattavuus

Poislukien käyttäliittymä testauksen rivikattavuus on 98% ja sen haaraumakattavuus on 95%.

![testauskattavuus](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/testauskattavuus.png)

Testaus puuttuu joiltakin sudokuruudukon muodostamisen ja ratkaisemisen yksityiskohdilta, tilanteelta jossa tietokanta lukkiutuu, sekä tietokannan kahdelta virheellisen toiminnan varotoimenpiteeltä, sillä näitä ei saatu tuotettua.

## Järjestelmätestaus

Järjestelmätestaus on manuaalisesti toteutettua.

### Asennus ja konfigurointi

Sovellus on asennettu ja testattu kuten [käyttöohje](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) ohjaa. Testausta suoritettiin sekä Linux- että Windows-ympäristöissä. Käynnistyshakemistossa oli _config.properties_-tiedosto, kuten käyttöohje edellyttää.

Sovellusta on testattu niin, että tietokantaa ei ollut aluksi olemassa, sekä niin, että tietokanta oli jo luotuna.

### Toiminnallisuudet

Niin [vaatimusmäärittelyn](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md) kuin käyttöohjeenkin toiminnallisuudet ja vaiheet on testattu. Myös virheellisiä syötteitä on testattu, sekä sudokuruudukon alunperin sisältämien numeroiden vaihtamista (mikä ei ole sallittua) on yritetty.

## Sovelluksen laatuongelmat

Virtuaalityöpöytää käyttäessä sovellus saattaa lukkiutua, jos sitä ei ladata hakemistoon _~/_ tai hakemistoon _/tmp_. Tällöin virheilmoitus tulostuu ainoastaan terminaaliin. Lisäksi jos tietokantaan ei ole luku- tai kirjoitusoikeuksia, virheilmoitus tulostuu vain terminaaliin.
