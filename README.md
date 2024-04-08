# Tietokoneen varaosien kauppa 

## Verkkosoveluksen ominaisuudet.

* Helppokäyttöinen verkkokaupan sovellus
* Tervetulosivu (sisäänpääsy joko myynti- tai admin sivulle)
* Myyntisivu tuotteiden tilaukseen (nimi, määrä, hinta, loppusumma, kori, tilaajan tiedot)
* Admin sivu (sisäänkirjautuminen, tuotteiden lisääminen, lompakko)
* Yhteinen tietokanta

## Tavoite

Projektin tavoitteena on luoda varastonhallintatyökalu pienelle kivijalka-tietokonekaupalle. Sovellus tulee
yrityksen sisäiseen käyttöön ja suuntautuu myynnin ja varastonhallinnan tueksi, joten sen on tarkoitus olla
graafisesti yksinkertainen ja suoraviivainen. Sidosryhmä koostuu myyntihenkilöistä, varastomiehistä,
esimiehestä ja ylläpitäjästä. Jokaiselle käyttäjäryhmälle luodaan heille suunnatut käyttöliittymät, eivätkä he
pääse muiden käyttäjien näkymään paitsi järjestelmän ylläpitäjä. Sovellus koostuu myyntisivusta,
varastosivusta, tilaussivusta ja taloussivusta. Myyntisivulla myyntihenkilö voi luoda asiakkaan tilauksen
saatavilla olevista osista ja tietokonepaketeista. Varastosivulla varastohenkilöstö voi hallita varastoa ja
lisätä tai poistaa sieltä tuotteita. Tilaussivulla kaikki työntekijät näkevät parhaillaan olevat tilaukset ja
taloussivulla esimies voi tarkkailla yrityksen taloustilannetta.

## Työvälineet 

Verkkosovellus on arkkitehtuuriltaan RESTfull:in mukainen, jossa on BackEnd-puoli toteutetaan Javalla Maven- ja Spring sovelluskehyksillä 
Apache NetBeans -ohjelmointiympäristössä ja FrontEnd-puoli tehdään JavaScriptilla käyttäen React, Redux, Node.js 
Visual Studion Code -ohjelmointiympäristössä. Muun muuassa ohjelmoinnissa käytetään MySQL relaatioteitokantaa, sekä Bootstrapia, CSS:ää  
Aputyökaluina on käytetty Jenkins-palvelua ja Githubia.
