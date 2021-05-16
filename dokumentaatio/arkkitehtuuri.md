# Arkkitehtuurikuvaus

## Rakenne

Ohjelman rakenteen muodostaa kolmen kerroksen kerrosarkkitehtuuri. Pakkausrakenne ilmenee alla olevasta kuvasta. Pakkauksessa _sudoku.ui_ on graafinen käyttöliittymä (JavaFX), pakkauksessa _sudoku.domain_ on sovelluslogiikka ja pakkaus _sudoku.dao_ huolehtii tietojen pysyväistallennuksesta.

![Pakkauskaavio](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkauskaavio.svg)

## Käyttöliittymä

Ohjelman käyttöliittymä on toteutettu luokassa [sudoku.ui.SudokuUi](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/ui/SudokuUi.java). Se sisältää neljä eri näkymää, jotka ovat kirjautumisnäkymä, valikkonäkymä, pelinäkymä ja tilastonäkymä, ja jotka jokainen ovat oma _Scene_-olionsa. Näistä näytetään aina vain yhtä ohjelman _stagen_, siis _Stage_-olion, avulla.

Käyttöliittymällä olio _sudokuService_ (luokan [sudoku.domain.SudokuService](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/SudokuService.java) ilmentymä), joka huolehtii sovelluslogiikasta. Kyseisen olion metodien kutsumisen avulla sovelluslogiikka on eriytetty käyttöliittymästä.

Kun aloitetaan uusi peli, käyttöliittymä kutsuu metodiaan [startGame](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/ui/SudokuUi.java#L104), joka saa parametrinaan valitun vaikeustason merkkijonona. Sovelluslogiikka generoi ja palauttaa tälle metodille halutunlaisen sudokuruudukon, jonka se sitten renderöi näkyviin pelinäkymään.

Kun käyttäjä päättää tarkastella pelitilastoja, käyttöliittymä kutsuu metodiaan [setStatLists](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/ui/SudokuUi.java#L152), jolle sovelluslogiikka antaa listan ratkaistuihin sudokuihin liittyvistä tiedoista. Se lajittelee tulokset omien tulosten sekä kaikkien tulosten listoihin, ja renderöi tiedot käyttäjän nähtäville tilastonäkymään. 

## Sovelluslogiikka

Alla olevasta luokkakaaviosta ilmenee ohjelman luokkarakenne.

![Luokkakaavio](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio.svg?raw=true)

Alla oleva luokka-/pakkauskaavio kuvaa eri pakkausten luokkien välisiä suhteita.

![Luokka-/pakkauskaavio](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokka-pakkauskaavio.svg)

Ohjelman datamalli koostuu luokista [User](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/User.java) ja [Game](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/Game.java), jotka ilmentävät käyttäjiä ja heidän ratkaisemiaan sudokupelejä (ks. luokkakaavio yllä).

Varsinaisesta sovelluslogiikan toiminnallisuudesta huolehtii luokka [SudokuService](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/SudokuService.java), josta tehdään vain yksi ilmentymä yhdellä ohjelman käynnistyskerralla. Sen metodien avulla käytetään käyttöliittymän toimintoja. Mahdollista on kirjautua sisään ([boolean login(String username)](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L95)), luoda uusi käyttäjä ([boolean createUser(String username)](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L125)), kirjautua ulos ([void logout()](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L115)), aloittaa uusi peli ([Grid startGame(String difficulty)](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L40)), asettaa numeroita sudokuruudukkoon ([boolean setModule(int a, int b, int number)](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L71)), tarkistaa onko ratkaistu sudoku oikein ([boolean checkGame()](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L50)) ja tarkastella pelitilastoja ([void saveGame(String time)](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L59), [User getLoggedIn()](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L108), [List<String> getSolved()](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L80)).
  
Pakkauksen _sudoku.dao_ rajapintojen [UserDao](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/dao/UserDao.java) ja [GameDao](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/dao/GameDao.java) toteuttavat luokat huolehtivat tietojen tallentamisesta. [SudokuService](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/SudokuService.java) pääsee siis niiden kautta kiinni käyttäjiin ja peleihin, kun ne sitä konstruktoitaessa alustetaan (ks. luokka-/pakkauskaavio yllä). DAO-olioiden injektoinnin sijaan sovellus on laajennettavissa eri tallennusmuodoille sopivia konstruktoreita lisäämällä, sillä näin käyttöliittymän ei tarvitse kutsua kuin yhtä sovelluslogiikan luokkaa.

### Päätoiminnallisuudet

#### Pelin aloittaminen

Painettaessa valikkonäkymän _PLAY_-nappia ohjelman kontrolli kulkeen alla olevan sekvenssikaavion mukaisesti.

![startGame-sekvenssikaavio](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/startGame-sekvenssi.png)

Käyttöliittymän tapahtumakäsittelijä kutsuu sovelluslogiikan luokan _SudokuService_ metodia _startGame_, joka aluksi luo uuden _Game_-olion, tälle annetaan parametriksi _User_-olio ja enum-tyyppinä vaikeustaso. Luodessa uusi _Game_-olio luodaan myös uusi _Grid_-olio, joka saa myös vaikeustason enum-tyyppisenä parametrikseen. _Grid_-olion luonti generoi uuden sudokuruudukon matriisin muotoon yksityisten metodien avulla. Sitten sovelluslogiikka kutsuu juuri luodun uuden pelin metodia _getGrid_, joka palauttaa juuri luodun _Grid_-olion _sudokuService_:lle, joka palauttaa sen käyttöliitymälle. Käyttöliittymä kutsuu palautetun sudokuruudukon metodia _getGrid_, joka palauttaa luodun ruudukon matriisina. Sitten käyttöliittymä generoi käyttäjälle näytettävän peliruudukon metodillaan _generateGrid_, jolle annetaan parametriksi _GridPane_-elementti ja palautettu matrsiisi. Lopuksi näkymä vaihdetaan pelinäkymään.

## Tietojen pysyväistallennus

Pakkauksen _sudoku.dao_ luokkien _DBUserDao_ ja _DBGameDao_ avulla tallennetaan tietoja SQL-tietokantaan.

### Päätoiminnallisuudet

#### Uuden käyttäjän luonti

Syötettäessä uusi uniikki käyttäjätunnus kirjautumisnäkymän uuden käyttäjän luomiseen osoitettuun kenttään ja painettaessa nappia _OK_ ohjelman kontrolli kulkee alla olevan kuvan mukaisesti.

![createUser-sekvenssikaavio](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/createUser-sekvenssi.png)

Käyttöliittymän tapahtumakäsittelijä kutsuu sovelluslogiikan _SudokuService_-luokasta löytyvää metodia _createUser_, jolle annetaan parametriksi käyttäjänimi. _userDao_ tarkistaa onko käyttäjänimeä jo olemassa, ja palauttaa tuloksen sovelluslogiikalle. Jos annettu käyttäjänimi on uniikki eli uusi käyttäjä voidaan luoda, _sudokuService_ luo uuden _User_-olion, jolle annetaan parametrina käyttäjänimi. Sovelluslogiikka tallentaa uuden käyttäjän _UserDao_:n metodilla _create_, joka saa parametrina juuri luodun _User_-olion. Jos talletus onnistuu, palautetaan totuusarvo käyttöliittymälle. Lopuksi käyttöliittymä näytettää käyttäjälle ilmoituksen uuden käyttäjän onnistuneesta luonnista ja vaihtaa näkymäksi valikkonäkymän.
