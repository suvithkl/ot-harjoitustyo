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

Alla kuvataan kahta sovelluksen varsinaisiin toiminnallisuuksiin liittyvää päätoiminnallisuutta sekvenssikaavioiden avulla. 

#### Pelin aloittaminen

Painettaessa valikkonäkymän _PLAY_-nappia ohjelman kontrolli kulkeen alla olevan sekvenssikaavion mukaisesti.

![startGame-sekvenssikaavio](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/startGame-sekvenssi.png)

Käyttöliittymän tapahtumakäsittelijä kutsuu sovelluslogiikan luokan [SudokuService](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/SudokuService.java) metodia [startGame](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L40), joka saa parametrinaan vaikeustason String-tyyppisenä, ja joka aluksi muuntaa tämän vaikeustason [Difficulty](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/Difficulty.java)-enumiksi. Sitten se luo uuden [Game](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/Game.java)-olion, tälle annetaan parametriksi _User_-olio ja enumiksi muunnettu vaikeustaso. Luodessa uusi _Game_-olio luodaan myös uusi [Grid](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/Grid.java)-olio, joka saa myös vaikeustason enum-tyyppisenä parametrikseen. _Grid_-olion selvittää montako sudokuruudukon ruutua sen on jätettävä tyhjäksi _Difficulty_-enumin avulla. Tämän jälkeen se generoi uuden sudokuruudukon matriisin muotoon luokkien [GridCreator](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/GridCreator.java) ja [GridSolver](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/GridSolver.java) metodien avulla. Sitten sovelluslogiikka kutsuu juuri luodun ja sille palautetun uuden pelin metodia _getGrid_, joka palauttaa juuri luodun _Grid_-olion _sudokuService_:lle, joka palauttaa sen käyttöliitymälle. Käyttöliittymä kutsuu palautetun sudokuruudukon metodia _getGrid_, joka palauttaa luodun ruudukon matriisina. Sitten käyttöliittymä generoi käyttäjälle näytettävän peliruudukon metodillaan _generateGrid_, jolle annetaan parametriksi palautettu matrsiisi. Lopuksi näkymä vaihdetaan pelinäkymään.

#### Pelin tarkistaminen

Painettaessa pelinäkymän _CHECK_-nappia ohjelman kontrolli kulkee alla olevan sekvenssikaavion mukaisesti.

![chekGame-sekvenssikaavio](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/checkGame-skevenssi.png)

Käyttöliittymän tapahtumakäsittelijä kutsuu sovelluslogiikan luokan _SudokuService_ metodia [checkGame](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L50), joka edelleen kutsuu ratkaistavana olevan pelin, siis _Game_-olion, metodia, joka tarkistaa onko peli ratkaistu oikein. Kyseinen metodi kutsuu edelleen kyseiseen peliin liittyvän _Grid_-olion vastaavaa metodia, joka ohjaa tarkistusvastuun [GridSolver](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/GridSolver.java)-luokan metodille [solved](https://github.com/suvithkl/ot-harjoitustyo/blob/fe609c281e2dec282f36fd8abcbd3d9adf87a6db/Sudoku/src/main/java/sudoku/domain/GridSolver.java#L16). Tämä metodi saa parametrinaan sudokuruudukon matriisina, ja tarkistaa luokan yksityisten metodien avulla onko sudokuruudukko ratkaistu oikein. Jos näin on, totuusarvo _true_ palautetaan takaisin aina käyttöliittymälle saakka, joka sitten tallentaa ratkaistuun sudokuun liittyvän pelituloksen. Lopuksi näkymä vaihdetaan valikkonäkymään.

## Tietojen pysyväistallennus

Pakkauksen _sudoku.dao_ luokkien [DBUserDao](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/dao/DBUserDao.java) ja [DBGameDao](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/dao/DBGameDao.java) avulla tallennetaan tietoja SQL-tietokantaan.

Kyseiset luokat on toteutettu DAO-suunnittelumallin mukaisesti. Siis sovelluslogiikka ei suoraan kutsu niitä ja sovelluksen tallennustapaa on mahdollista muuttaa, on vain tehtävä uudet toteutukset rajapinnoille [UserDao](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/dao/UserDao.java) ja [GameDao](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/dao/GameDao.java), sekä edellä mainitusti toinen konstruktori [SudokuService](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/SudokuService.java)-luokalle.

### Tietokanta

Käyttäjät ja pelitulokset tallennetaan samaan SQL-tietokantaan, mutta omiin tauluihinsa. Tietokannan ja taulujen nimet ovat määritelty, ja uudelleen määriteltävissä, ohjelman konfiguraatiotiedoston [config.properties](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/config.properties) avulla.

Käyttäjistä tallennetaan ainoastaan käyttäjänimi.

Ratkaistuista sudokupeleistä tallennetaan peliaika sarakkeeseen _time_, vaikeustaso sarakkeeseen _difficulty_, sekä sudokun ratkaisseen pelaajan käyttäjänimi sarakkeeseen _name_. Sarakkeet ovat taulussa vastaavassa järjestyksessä.

Itse tietokantaa käsitellään suoraan ainoastaan avustajaluokka [DatabaseHelper](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/dao/DatabaseHelper.java):n kautta, joka injektoidaan kummallekin DAO-rajapinnan toteuttavalle luokalle [SudokuService](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/SudokuService.java):n toimesta. Näin tietokannan käsittely on saatu pelkistettyä DAO-luokissa yksinkertaisiksi metodikutsuiksi.

### Päätoiminnallisuudet

Alla kuvataan yhtä tietojen tallentamiseen liittyvää päätoiminnallisuutta sekvenssikaavion avulla. Pelituloksen tallentaminen ([saveGame](https://github.com/suvithkl/ot-harjoitustyo/blob/bc399e1c08d08333d2d40fd7b9aa0cfed01697ed/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L59)) noudattelee käytännössä samaa logiikkaa poissulkien alussa tehtävä käyttäjänimen uniikkiuden tarkistus.

#### Uuden käyttäjän luonti

Syötettäessä uusi uniikki käyttäjätunnus kirjautumisnäkymän uuden käyttäjän luomiseen osoitettuun kenttään ja painettaessa nappia _OK_ ohjelman kontrolli kulkee alla olevan kuvan mukaisesti.

![createUser-sekvenssikaavio](https://github.com/suvithkl/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/createUser-sekvenssi.png)

Käyttöliittymän tapahtumakäsittelijä kutsuu sovelluslogiikan [SudokuService](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/SudokuService.java)-luokasta löytyvää metodia [createUser](https://github.com/suvithkl/ot-harjoitustyo/blob/49c6775605e062466c1fead71f7e4f6542d47946/Sudoku/src/main/java/sudoku/domain/SudokuService.java#L125), jolle annetaan parametriksi käyttäjänimi. _userDao_ tarkistaa onko käyttäjänimeä jo olemassa, ja palauttaa tuloksen sovelluslogiikalle. Jos annettu käyttäjänimi on uniikki eli uusi käyttäjä voidaan luoda, _sudokuService_ luo uuden _User_-olion, jolle annetaan parametrina käyttäjänimi, joka palautetaan sovelluslogiikalle. Sovelluslogiikka tallentaa uuden käyttäjän _userDao_:n metodilla _create_, joka saa parametrina juuri luodun [User](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/domain/User.java)-olion. Jos talletus onnistuu, palautetaan totuusarvo käyttöliittymälle. Lopuksi käyttöliittymä näytettää käyttäjälle ilmoituksen uuden käyttäjän onnistuneesta luonnista ja vaihtaa näkymäksi valikkonäkymän.

## Ohjelman rakenteen heikkoudet

### Käyttöliittymä

Graafinen käyttöliittymä rakennetaan kokonaan pakkauksen _sudoku.ui_ luokassa [SudokuUi](https://github.com/suvithkl/ot-harjoitustyo/blob/master/Sudoku/src/main/java/sudoku/ui/SudokuUi.java). Vaikkakin luokka on pilkottu pienehköihin osiin, eri näkymien varsinaisten graafisten komponenttien luomista lukuunottamatta (näkymät ovat kuitenkin silti omissa metodeissaan), olisi näitä metodeja voinut jakaa useampaan luokkaan. Nyt luokka on turhan pitkä. Lisäksi näkymien luomista olisi varmastikin vielä saanut pilkottua metodeihin.

Myös FMXL:n käyttöönotto voisi selkeyttää käyttöliittymää, sillä siinä on melko useita muuttuvia osia.

### Sovelluslogiikka

_Game_ ja _Grid_ -luokkien erottelusta tuli loppujen lopuksi ehkä hieman turha, sillä _GridCreator_ ja _GridSolver_ -luokkien mukaantulo lyhensikin alunperin pitkää _Grid_-luokkaa huomattavasti. Nyt niiden yhdistämistä voisi mahdollisesti harkita.

Lisäksi sudokuruudukon satunnaistamista voisi totta kai aina lisätä. _GridCreator_-luokan metodista _canErase_ saisi varmastikin vielä luontevamman, ja siinä voisi olla lisänä, että vaikeammissa vaikeustasoissa yksi tyhjä rivi tai kolumni olisi hyväksyttävä pelaajalle annettavassa sudokuruudukossa.
