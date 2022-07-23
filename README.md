# Transport_Paketa_MSSQL
MSSQL
JDBC
JAVA
ERWIN MODELER

Opis sistema

Potrebno je napraviti sistem za transport paketa. U sistemu se vodi evidencija o gradovima (naziv,
poštanski broj), opštinama (naziv, grad, x koordinata (u km), y koordinata (u km)). U sistemu postoje
korisnici, za koje se pamti ime, prezime, korisničko ime, šifra, jmbg, broj poslatih paketa. Neki korisnici
su administratori. Korisnici mogu postati kuriri tako što šalju zahtev administratoru koji pored informacija
o korisniku sadrži i informacije o vozilu koji bi želeo da vozi. Administrator može da prihvati ili odbije
zahtev (zahtevi se ne čuvaju trajno u bazi). Za vozilo se pamte registracioni broj, tip goriva (0 - “plin”, 1 -
“dizel”, 2 - “benzin”) i potrošnja (litri po kilometru). Nakon odobrenja zahteva, korisnik postaje kurir (u
svakom trenutku kola može da vozi samo jedan kurir i kurir ne može da vozi vise automobila). Prethodnu
funkcionalnost potrebno je uraditi kao storu proceduru. Za svakog kurira se u sistemu pamti njegovo
vozilo, broj isporučenih paketa, ostvaren profit i status (0 - “ne vozi”, 1 - “vozi”).
Prevoz se obavlja tako što korisnik postavlja zahtev za prevozom tako što bira opštinu u kojoj se
preuzima paket, opštinu u koju se dostavlja paket, tip paketa (0 - “pismo”, 1 - “standardno”, 2 -
“lomljivo”) i težinu paketa (decimalni broj) . Zatim kuriri koji su u statusu “ne vozi” daju svoje ponude za
postavljen zahtev. Ponude sadrže procenat cene isporuke (na primer: 10 označava 10% cene isporuke koja
se računa po formuli CenaJedneIsporuke ), informacije o kuriru i informacije o paketu. Korisnik koji je
postavio zahtev za prevozom bira jednu od ponuda. Nakon izbora ponude, sve ponude za taj zahtev se
brišu (okidač ili okidači sa prefiksom TR_TransportOffer_ ). Za svaki paket se čuvaju informacije o
kuriru, statusu isporuke (0 - “kreiran”, 1 - “zahtev prihvaćen”, 2 - “ pokupljen”, 3 - “isporučen”), cena i
vreme prihvatanja zahteva. Vožnja se sastoji od svi odobrenih ponuda u trenutku početka vožnje. Pre
početka vožnje, kurir određuje koje pakete isporučuje i zatim ih prevozi u FCFS redosledu. Po završetku
vožnje, kurir računa ostvaren profit. Profit za jednu vožnju računa se kao razlika sume cena svih isporuka
paketa i potrošnje goriva prilikom te vožnje (cena goriva: “plin” - 15/litar, “benzin” - 36/litar, “dizel” -
32/litar).

CenaJedneIsporuke= (OSNOVNA_CENA[i] + (TEŽINSKI_FAKTOR[i] * weight) * CENA_PO_KG[i] ) * euklidska_distanca

                  Pismo Standardno Lomljivo                  
  Početna cena      10        25       75
  Težinski faktor    0         1        2
  Cena po KG         /        100      300

Poželjno je da onde gde je to moguće referencijalni integriteti budu: ON UPDATE CASCADE,
ON DELETE NO ACTION. Iz tog razloga, metoda koja briše određeni red iz tabele ne treba da briše i
ostale redove drugih tabela koje referenciraju red za brisanje. Ostale redove treba prvo eksplicitno obrisati
drugim metodama (ukoliko ta metoda ne postoji koristiti referencijalni integritet ON DELETE
CASCADE).

Svaka kolona Id koja je primarni ključ i nije strani ključ treba da bude IDENTITY kolona.
Koristiti tip DECIMAL(10,3) za brojeve sa zarezom. Podrazumevana maksimalna dužina svih
tekstualnih kolona u tabelama je 100 karaktera, osim ukoliko za neku kolonu nije drugačije navedeno.
