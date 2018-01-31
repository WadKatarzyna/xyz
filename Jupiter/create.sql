


drop table if exists dienstplan;
drop table if exists angestellter;
drop table if exists weiterbildung;
drop table if exists admin;
drop table if exists unterkategorie;
drop table if exists bestellteArtikel;
drop table if exists artikelkategorie;
drop table if exists kursbesuche;
drop table if exists warenkorb;
drop table if exists warenkorbArtikel;
drop table if exists kategorie;
drop table if exists artikel;
drop table if exists bestellung;
drop table if exists account;
drop table if exists person;
drop table if exists kategorie_unterkategorie;

-- Normalisierungstabellen
drop table if exists rolle;
drop table if exists hersteller;
drop table if exists hobby;
drop table if exists abteilung;

-- Entscheidungtabelle .. SQL oder NoSQL
drop table if exists work_with;


create table hersteller(
	hersteller_id integer primary key auto_increment not null,
	hersteller_name varchar(30) not null
);

create table rolle(
	rolle_id integer primary key auto_increment not null,
	rolle_name varchar(30) not  null	
);

create table hobby(
	hobby_id integer primary key auto_increment not null,
	hobby_name varchar(30) not null
);

create table abteilung(
	abteilung_id integer primary key auto_increment not null,
	abteilung_name varchar(30) not null
);

create table person(
	person_personalnr integer primary key auto_increment not null, 
	person_vorname varchar(30) not null,
	person_nachname varchar(30) not null,
	person_geschlecht varchar(30) not null,
	person_geburtsdatum date not null,
	person_email varchar(30) not null,
	person_rolle_id integer not null,

	constraint fk_person_rolle foreign key(person_rolle_id) references rolle(rolle_id) on delete cascade
);

create table account(
	account_id integer primary key auto_increment not null,
	account_username varchar(30) unique not null,
	account_erstelldatum timestamp not null,
	account_passwort varchar (30) not null,
	account_person_personalnr integer not null, 


	constraint fk_account_person foreign key(account_person_personalnr) references person(person_personalnr) on delete cascade

);

create table bestellung (
	bestellung_id integer primary key auto_increment not null, 
	bestellung_bestellzeitpunkt timestamp not null,
	bestellung_lieferdatum date not null,
	bestellung_summe numeric not null, 
	bestellung_account_id integer not null, 

	constraint fk_bestellung_account foreign key(bestellung_account_id) references account(account_id) on delete cascade
);

create table artikel(
	artikel_id integer primary key not null auto_increment, 
	artikel_preis numeric not null, 
	artikel_beschreibung varchar(500) not null, 
	artikel_rest_menge integer not null, 
	artikel_hersteller_id integer not null, 

	constraint fk_artikel_hersteller foreign key(artikel_hersteller_id) references hersteller(hersteller_id) on delete cascade

);

create table bestellteArtikel(
	bestellteArtikel_bestellung_id integer not null , 
	bestellteArtikel_artikel_id integer not null,
	bestellteArtikel_menge integer not null, 
	bestellteArtikel_preis numeric not null, 
	
	primary key (bestellteArtikel_bestellung_id, bestellteArtikel_artikel_id),
	constraint fk_bestellteArtikel_bestellung foreign key(bestellteArtikel_bestellung_id) references bestellung(bestellung_id) on delete cascade, 
	constraint fk_bestellteArtikel_artikel foreign key(bestellteArtikel_artikel_id) references artikel(artikel_id) on delete cascade
);

create table kategorie(
	kategorie_id integer primary key not null auto_increment, 
	kategorie_name varchar(30) unique not null,
	kategorie_bezeichnung varchar(150) not null
	
);

create table unterkategorie(
	unterkategorie_id integer primary key not null auto_increment, 
	unterkategorie_name varchar(30) not null,
	unterkategorie_bezeichnung varchar(150) not null 
	
);

create table kategorie_unterkategorie(
	kategorie_unterkategorie_artikel_id integer not null,
	kategorie_unterkategorie_kategorie_id integer not null,
	kategorie_unterkategorie_unterkategorie_id integer not null,
	
	primary key(kategorie_unterkategorie_artikel_id, kategorie_unterkategorie_kategorie_id, kategorie_unterkategorie_unterkategorie_id),
	constraint fk_kategorie_unterkategorie_artikel foreign key(kategorie_unterkategorie_artikel_id) references artikel(artikel_id) on delete cascade,
	constraint fk_kategorie_unterkategorie_kategorie foreign key(kategorie_unterkategorie_kategorie_id) references kategorie(kategorie_id) on delete cascade,
	constraint fk_kategorie_unterkategorie_unterkategorie foreign key(kategorie_unterkategorie_unterkategorie_id) references unterkategorie(unterkategorie_id) on delete cascade
);

create table artikelkategorie(
	artikelkategorie_artikel_id integer not null,
	artikelkategorie_kategorie_id integer not null,
	artikelkategorie_unterkategorie_id integer not null,
	
	primary key(artikelkategorie_artikel_id, artikelkategorie_kategorie_id, artikelkategorie_unterkategorie_id),
	constraint fk_artikelkategorie_artikel foreign key(artikelkategorie_artikel_id) references artikel(artikel_id) on delete cascade,
	constraint fk_artikelkategorie_kategorie foreign key(artikelkategorie_kategorie_id) references kategorie(kategorie_id) on delete cascade,
	constraint fk_artikelkategorie_unterkategorie foreign key(artikelkategorie_unterkategorie_id) references unterkategorie(unterkategorie_id) on delete cascade
);

create table admin(
	person_personalnr integer primary key not null,
	admin_fachgebiet varchar(30) not null,
	admin_hobby_id integer not null,

	constraint fk_admin_person foreign key(person_personalnr) references person(person_personalnr) on delete cascade,
	constraint fk_admin_hobby foreign key(admin_hobby_id) references hobby(hobby_id) on delete cascade
);


create table angestellter(
	person_personalnr integer primary key not null,
	angestellter_abteilung_id integer not null,
	angestellter_eintrittsdatum date not null,

	constraint fk_angestellter_person foreign key(person_personalnr) references person(person_personalnr) on delete cascade,
	constraint fk_angestellter_abteilung foreign key(angestellter_abteilung_id) references abteilung(abteilung_id) on delete cascade
);

create table dienstplan(
	dienstplan_id integer not null auto_increment,
	dienstplan_angestellten_id integer not null,
	dienstplan_jahr integer not null,
	dienstplan_woche integer not null,
	dienstplan_tag integer not null,
	dienstplan_beginn varchar(10) not null,
	dienstplan_ende varchar(10) not null,

	primary key(dienstplan_id, dienstplan_angestellten_id),
	constraint fk_dienstplan_angestellten foreign key(dienstplan_angestellten_id) references angestellter(person_personalnr) on delete cascade
);

create table weiterbildung(
	weiterbildung_id integer primary key not null auto_increment,
	weiterbildung_kursname varchar(30) not null,
	weiterbildung_max_anzahl integer not null

);

create table kursbesuche(
	kursbesuche_angestellter_id integer not null,
	kursbesuche_weiterbildung_id integer not null,
	kursbesuche_admin_id integer not null,
	kursbesuche_ort varchar(30) not null,
	kursbesuche_zeit varchar(30) not null,
	
	primary key(kursbesuche_angestellter_id, kursbesuche_weiterbildung_id),
	constraint fk_kursbesuche_angestellter foreign key(kursbesuche_angestellter_id) references angestellter(person_personalnr) on delete cascade,
	constraint fk_kursbesuche_weiterbildung foreign key(kursbesuche_weiterbildung_id) references weiterbildung(weiterbildung_id) on delete cascade,
	constraint fk_kursbesuche_admin foreign key(kursbesuche_admin_id) references admin(person_personalnr) on delete cascade
);

create table warenkorb(
	warenkorb_id integer primary key not null auto_increment,
	warenkorb_erstelldatum date not null,
	warenkorb_account_id integer not null,

	constraint fk_warenkorb_account foreign key(warenkorb_account_id) references account(account_id) on delete cascade
);

create table warenkorbArtikel(
	warenkorbArtikel_id integer not null,
	warenkorbArtikel_artikel_id integer not null,
	warenkorbArtikel_menge integer not null,
	warenkorbArtikel_summe numeric not null,

	primary key(warenkorbArtikel_id, warenkorbArtikel_artikel_id),
	constraint fk_warenkorbArtikel foreign key(warenkorbArtikel_id) references warenkorb(warenkorb_id) on delete cascade,
	constraint fk_warenkorbArtikel_artikel foreign key(warenkorbArtikel_artikel_id) references artikel(artikel_id) on delete cascade

);

create table work_with(
	id integer primary key not null,
	SQL_ smallint(1) not null
);

















