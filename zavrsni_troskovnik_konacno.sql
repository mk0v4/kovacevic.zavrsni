drop database if exists zavrsni_troskovnik_1;
create database zavrsni_troskovnik_1 default character set utf8;

use zavrsni_troskovnik_1;

create table rad(
rad_id int primary key not null auto_increment,
grupa_radova varchar(255) not null,
kategorija_rada varchar(255) not null,
cijena decimal(18,2) not null
);

create table materijal(
materijal_id int primary key not null auto_increment,
grupa_materijal varchar(50) not null,
proizvodac varchar(50),
oznaka varchar(50) not null,
opis varchar(255),
kolicina_ambalaza decimal(6,2) not null,
jedinica_mjere_ambalaza varchar(50) not null,
cijena_ambalaza decimal(18,2) not null
);

create table analiza_rad(
opis_operacije varchar(255) not null,
broj_operacije int(2) not null,
jedinicni_normativ_vremena decimal(6,2) not null,
analiza_cijene_id int not null,
rad_id int not null,
cijena_vrijeme decimal(6,2)
);

create table analiza_materijal(
kolicina decimal(8,2) not null,
jedinica_mjere varchar(50) not null,
jedinicna_cijena_materijal decimal(6,2),
cijena_materijal decimal(6,2),
materijal_id int not null,
analiza_cijene_id int not null
);

create table analiza_cijene(
analiza_cijene_id int primary key not null auto_increment,
oznaka_norme varchar(50) not null,
grupa_norme varchar(255) not null,
opis varchar(255) not null,
jedinica_mjere varchar(10) not null,
ukupan_normativ_vremena decimal(6,2) not null,
ukupna_cijena_materijal decimal(6,2),
ukupna_cijena_rad decimal(6,2),
koeficijent_firme decimal(4,2) default 1.05,
sveukupan_iznos decimal(18,2),
stavka_troskovnik_id int not null
);

create table stavka_troskovnik(
stavka_troskovnik_id int primary key not null auto_increment,
kolicina_troskovnik decimal(8,2) not null,
dodatan_opis varchar(255),
ukupna_cijena decimal(18,2)
);



alter table analiza_rad add foreign key (analiza_cijene_id) references analiza_cijene(analiza_cijene_id);
alter table analiza_rad add foreign key (rad_id) references rad(rad_id);

alter table analiza_materijal add foreign key (materijal_id) references materijal(materijal_id);
alter table analiza_materijal add foreign key (analiza_cijene_id) references analiza_cijene(analiza_cijene_id);

alter table analiza_cijene add foreign key (stavka_troskovnik_id) references stavka_troskovnik(stavka_troskovnik_id);





insert into rad (grupa_radova, kategorija_rada, cijena) values
('Zidar','I','55.00'),
('Zidar','II','60.00'),
('Zidar','III','65.00'),
('Zidar','IV','70.00'),
('Zidar','V','75.00'),
('Zidar','VI','80.00'),
('Zidar','VII','85.00'),
('Zidar','VIII','90.00'),
('Radnik','I','50.00'),
('Radnik','II','52.50'),
('Radnik','III','55.00'),
('Radnik','IV','57.50'),
('Radnik','V','60.00'),
('Radnik','VI','62.50'),
('Radnik','VII','65.00'),
('Radnik','VIII','70.00');

insert into materijal (grupa_materijal, proizvodac, oznaka, opis, kolicina_ambalaza, jedinica_mjere_ambalaza, cijena_ambalaza) values
('Cement','NEXE','CEM II/B-M (P-S) 32,5R','Standardni mješani portland cement, visoka rana čvrstoća; Razred čvrstoće 32,5 MPa','25.00','kg','20.00'),
('Vapno','InterCAL','DL 80-30-S1','Hidratizirano vapno','25','kg','18.00'),
('Voda','Gradski vodovod','Pitka voda','Smatra se prikladnom za pripremu i ne treba se ispitivati','1.00','m3','15.00'),
('Agregat','','Granulacija 0-4 mm','Riječni pijesak','1','m3','90.00'),
('Opeka','','15x30x6,5cm','','1','kom','0.28'),
('Mort','Gradilište','Produžni mort 1:3:9 - strojno','','1','m3','304.54');

insert into stavka_troskovnik (stavka_troskovnik_id, kolicina_troskovnik, dodatan_opis, ukupna_cijena) values
('1','30.52','Početka radova nakon odobrenja nadzornog inženjera.','21886.50');

insert into analiza_cijene (oznaka_norme, grupa_norme, opis, jedinica_mjere, ukupan_normativ_vremena, ukupna_cijena_materijal,
ukupna_cijena_rad, koeficijent_firme, sveukupan_iznos, stavka_troskovnik_id) values
('GN 301-203-3.1.','Zidarski radovi','Zidanje zida punom opekom 15x30x6,5cm u produžnom mortu 1:3:9','m3','8.01','174.45','516.83','1.05','717.12','1'),
('GN 301-103-5.3.','Zidarski radovi','Strojna izrada produžnog morta 1:3:9','m3','2.03','304.54','106.58','1.00','411.12','1');

insert into analiza_materijal (kolicina, jedinica_mjere, jedinicna_cijena_materijal, cijena_materijal, materijal_id, analiza_cijene_id) values
('146.00','kg','0.80','116.80','1','2'),
('0.31','m3','324.00','100.44','2','2'),
('0.93','m3','90.00','83.70','4','2'),
('0.24','m3','15.00','3.60','3','2'),
('275','kom','0.28','77.00','5','1'),
('0.32','m3','304.54','97.45','6','1');

insert into analiza_rad (opis_operacije, broj_operacije, jedinicni_normativ_vremena, cijena_vrijeme, analiza_cijene_id, rad_id) values
('Strojno spravljanje morta','2','1.88','98.70','2','10'),
('Prijenos cementa','3','0.15','7.88','2','10'),
('Strojno spravljanje morta','1','0.60','31.5','1','10'),
('Zidanje','2','3.96','316.80','1','6'),
('Zidanje','2','1.26','66.15','1','10'),
('Prijenos opeke','3','1.30','68.25','1','10'),
('Prijenos morta','4','0.65','34.13','1','10');


