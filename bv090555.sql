create database domaci
go

use domaci
go


CREATE TABLE [Grad]
( 
	[IDGrad]             integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Naziv]              varchar(50)  NULL ,
	[PostanskiBr]        integer  NULL 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKGrad] ON [Grad]
( 
	[IDGrad]              ASC
)
go

CREATE TABLE [Korisnik]
( 
	[IDKorisnik]         integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Ime]                varchar(20)  NULL ,
	[Prezime]            varchar(20)  NULL ,
	[Username]           varchar(20)  NULL ,
	[Pass]               varchar(20)  NULL ,
	[JMBG]               varchar(20)  NULL ,
	[BrPoslPak]          integer  NULL ,
	[IsAdmin]            char(18)  NULL 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKKorisnik] ON [Korisnik]
( 
	[IDKorisnik]          ASC
)
go

CREATE TABLE [Kurir]
( 
	[IDKorisnik]         integer  NOT NULL ,
	[BrIspPaketa]        integer  NULL ,
	[OstvarenProfit]     decimal(10,3)  NULL ,
	[Status]             integer  NULL ,
	[IDVozilo]           integer  NULL 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKKurir] ON [Kurir]
( 
	[IDKorisnik]          ASC
)
go

CREATE TABLE [Kurirrz]
( 
	[IDKorisnik]         integer  NOT NULL ,
	[regbr]              varchar(20)  NULL 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKKurirrz] ON [Kurirrz]
( 
	[IDKorisnik]          ASC
)
go

CREATE TABLE [Opstina]
( 
	[IDGrad]             integer  NULL ,
	[IDOpstina]          integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[X]                  decimal(10,3)  NULL ,
	[Y]                  decimal(10,3)  NULL ,
	[Naziv]              varchar(20)  NULL 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKOpstina] ON [Opstina]
( 
	[IDOpstina]           ASC
)
go

CREATE TABLE [Ponuda]
( 
	[IDZahtev]           integer  NOT NULL ,
	[IDKorisnik]         integer  NOT NULL ,
	[Cena]               integer  NULL ,
	[IDPonuda]           integer  NOT NULL  IDENTITY ( 1,1 ) 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKPonuda] ON [Ponuda]
( 
	[IDPonuda]            ASC
)
go

CREATE TABLE [Vozilo]
( 
	[IDVozilo]           integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[RegBr]              varchar(20)  NULL ,
	[TipGoriva]          integer  NULL ,
	[Potrosnja]          decimal(10,3)  NULL 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKVozilo] ON [Vozilo]
( 
	[IDVozilo]            ASC
)
go

CREATE TABLE [Voznja]
( 
	[IDKorisnik]         integer  NOT NULL ,
	[IDZahtev]           integer  NOT NULL ,
	[IDVoznja]           integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Gotovo]             integer  NULL 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKVoznja] ON [Voznja]
( 
	[IDVoznja]            ASC
)
go

CREATE TABLE [Zahtev]
( 
	[IDZahtev]           integer  NOT NULL  IDENTITY ( 1,1 ) ,
	[Tip]                integer  NULL ,
	[Tezina]             decimal(10,3)  NULL ,
	[OpstinaOd]          integer  NOT NULL ,
	[OpstinaDo]          integer  NOT NULL ,
	[Status]             integer  NULL ,
	[Cena]               decimal(10,3)  NULL ,
	[VrPrihvatanja]      DATETIME  NULL ,
	[IDKorisnik]         integer  NULL ,
	[Username]           varchar(50)  NULL ,
	[Duzina]             decimal(10,3)  NULL 
)
go

CREATE UNIQUE CLUSTERED INDEX [XPKZahtev] ON [Zahtev]
( 
	[IDZahtev]            ASC
)
go


ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_3] FOREIGN KEY ([IDKorisnik]) REFERENCES [Korisnik]([IDKorisnik])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Kurir]
	ADD CONSTRAINT [R_19] FOREIGN KEY ([IDVozilo]) REFERENCES [Vozilo]([IDVozilo])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Kurirrz]
	ADD CONSTRAINT [R_21] FOREIGN KEY ([IDKorisnik]) REFERENCES [Korisnik]([IDKorisnik])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go


ALTER TABLE [Opstina]
	ADD CONSTRAINT [R_2] FOREIGN KEY ([IDGrad]) REFERENCES [Grad]([IDGrad])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Ponuda]
	ADD CONSTRAINT [R_5] FOREIGN KEY ([IDZahtev]) REFERENCES [Zahtev]([IDZahtev])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Ponuda]
	ADD CONSTRAINT [R_12] FOREIGN KEY ([IDKorisnik]) REFERENCES [Kurir]([IDKorisnik])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Voznja]
	ADD CONSTRAINT [R_14] FOREIGN KEY ([IDKorisnik]) REFERENCES [Kurir]([IDKorisnik])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go

ALTER TABLE [Voznja]
	ADD CONSTRAINT [R_16] FOREIGN KEY ([IDZahtev]) REFERENCES [Zahtev]([IDZahtev])
		ON DELETE NO ACTION
		ON UPDATE CASCADE
go


ALTER TABLE [Zahtev]
	ADD CONSTRAINT [R_9] FOREIGN KEY ([OpstinaOd]) REFERENCES [Opstina]([IDOpstina])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Zahtev]
	ADD CONSTRAINT [R_11] FOREIGN KEY ([OpstinaDo]) REFERENCES [Opstina]([IDOpstina])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

ALTER TABLE [Zahtev]
	ADD CONSTRAINT [R_18] FOREIGN KEY ([IDKorisnik]) REFERENCES [Kurir]([IDKorisnik])
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
go

-------------------------------------------------------


delete Ponuda
delete zahtev
delete kurir
delete korisnik
delete opstina
delete grad
delete Vozilo
delete Kurirrz
delete Voznja




go

set identity_insert grad on
INSERT INTO [Grad](
			idgrad,
           [Naziv]
           ,[PostanskiBr])
     VALUES
		   (1,'Beograd',11000),
		   (2,'Nis',18101),
		   (3,'Pariz',75000),
		   (4,'Palo Alto',94020),
		   (5,'Petrovgrad','14255'),
		   (6,'Uzice','12255')
GO
set identity_insert grad off



set identity_insert Opstina on
INSERT INTO [dbo].[Opstina]
           (idopstina,
		   [IDGrad]
           ,[X]
           ,[Y]
           ,[Naziv])
     VALUES

			(1,1,15,14,'Borca'),
			(2,1,52,45,'Dorcol'),
			(3,1,133,144,'Bulbulder'),
			(4,1,241,124,'Surcin'),
			(5,1,235,634,'Karaburma')
GO
set identity_insert Opstina off


set identity_insert Korisnik on
INSERT INTO [dbo].[Korisnik]
			(IDKorisnik,
            [Ime]
           ,[Prezime]
           ,[Username]
           ,[Pass]
           ,[JMBG]
           ,[BrPoslPak]
           ,[IsAdmin])
     VALUES 
			(1,'aco','pejovic','asd','asd','103346543',0,1),
			(2,'ziko','pejovic','asd1','asd1','103112243',5,0),
			(3,'djoko','pejovic','asd2','asd2','103124343',10,0),
			(4,'bobo','pejovic','asd3','asd3','103312432',0,0),
			(5,'neso','pejovic','asd4','asd4','103342341',0,0)
GO
set identity_insert Korisnik off


set identity_insert Vozilo on
INSERT INTO [dbo].[Vozilo]
           (IDVozilo,
		   [RegBr]
           ,[TipGoriva]
           ,[Potrosnja])
     VALUES

			(1,'bg544ds',1,7),
			(2,'ns523eo',3,12),
			(3,'bg541ds',2,9),
			(4,'bg542ds',2,4),
			(5,'bg543ds',3,6)
GO
set identity_insert Vozilo off


INSERT INTO [dbo].[Kurir]
           ([IDKorisnik]
           ,[BrIspPaketa]
           ,[OstvarenProfit]
           ,[Status]
		   ,[IDVozilo]
		   )
     VALUES
			(4,12,5000,0,1),
			(5,23,7500,1,2)
GO

set identity_insert Zahtev on
INSERT INTO Zahtev
        (	IDZahtev,
			[Tip]
           ,[Tezina]
           ,[OpstinaOd]
           ,[OpstinaDo]
           ,[Status]
           ,[Cena]
           ,[VrPrihvatanja]
           ,[IDKorisnik])
     VALUES
	  (1,1,22.52,2,4,0,500.85,GETDATE()-17,4),
	  (2,1,22.5,1,3,0,500.85,GETDATE()-15,5),
	  (3,2,62.5,5,2,0,500.85,null,null)
set identity_insert Zahtev off
go



INSERT INTO [dbo].[Ponuda]
           ([IDZahtev]
           ,[IDKorisnik]
		   ,[Cena])
     VALUES
			(1,4,10),
			(1,5,5),
			(2,5,12)
       
GO

INSERT INTO [dbo].[Voznja]
           ([IDZahtev]
           ,[IDKorisnik]
		   ,[gotovo])
		   
     VALUES
			(1,4,0),
			(2,4,0),
			(3,4,0)
       
GO
          


select * from grad 
select * from Opstina
select * from Korisnik 
select* from kurir
select* from Ponuda
select* from vozilo
select* from zahtev
select* from Kurirrz
select* from Voznja





-------------------------------------------------------
use domaci
go


create procedure novi_kurir 
@username varchar(50),
@ou INT OUTPUT 
as
begin
SET NOCOUNT ON 
declare @idkor int,@regbr varchar(50),@idv int,@br int
set @ou=1

select @br=count(*) from korisnik where username= @username

if @br=0 begin set @ou=-1 
return
end
Select  @idkor=idkorisnik from korisnik where username= @username 


select @br=count(*) from kurirrz where idkorisnik=@idkor
if @br=0 
begin 
	set @ou=-1 
	return
end
Select @regbr=regbr from kurirrz where idkorisnik=@idkor


select @br=count(*) from vozilo where regbr=@regbr 
if @br=0 
begin 
	set @ou=-1 
	return
end
Select @idv=idvozilo from vozilo where regbr=@regbr 




if not exists (Select * from kurir where idkorisnik=@idkor or idvozilo=@idv)
begin
insert into Kurir(idkorisnik, idvozilo,BrIspPaketa,ostvarenprofit,status) values (@idkor,@idv,0,0,0)
end

delete from kurirrz where IDKorisnik=@idkor


end

go


-----------------------------------------------------



create trigger opstinebrisi on
grad
instead of delete
as
begin
declare @id int,@kur cursor;



set @kur=cursor for
select idgrad from deleted

open @kur 

fetch @kur
into @id

while @@FETCH_STATUS=0
begin

delete from opstina where IDGrad=@id
delete from grad where IDGrad=@id

fetch @kur
into @id

end
close @kur


end
 
 
 
 go
 -------------------------------------------------------
 
 
 create trigger zahtevibrisi on
opstina
instead of delete
as
begin
declare @id int,@kur cursor;



set @kur=cursor for
select idopstina from deleted

open @kur 

fetch @kur
into @id

while @@FETCH_STATUS=0
begin

delete from zahtev where OpstinaOd=@id or OpstinaDo=@id
delete from opstina where IDOpstina=@id

fetch @kur
into @id

end
close @kur


end
 go
 
 ----------------------------------------------
 
 create trigger kurirbrisi on
korisnik
instead of delete
as
begin
declare @id int,@kur cursor;



set @kur=cursor for
select idkorisnik from deleted

open @kur 

fetch @kur
into @id

while @@FETCH_STATUS=0
begin

delete from kurir where idkorisnik=@id
delete from kurirrz where idkorisnik=@id
delete from korisnik where idkorisnik=@id

fetch @kur
into @id

end
close @kur


end
 go
 ---------------------------------------
create trigger zakurirabrisi on Kurir
instead of delete
as
begin
declare @id int,@kur cursor;



set @kur=cursor for
select idkorisnik from deleted

open @kur 

fetch @kur
into @id

while @@FETCH_STATUS=0
begin


delete from Ponuda where idkorisnik=@id
delete from zahtev where idkorisnik=@id
delete from voznja where idkorisnik=@id
delete from kurirrz where idkorisnik=@id

delete from kurir where idkorisnik=@id




fetch @kur
into @id

end
close @kur


end
 
go

----------------------------------------------


create trigger ponudebrisi on
zahtev
instead of delete
as
begin
declare @id int,@kur cursor;



set @kur=cursor for
select idzahtev from deleted

open @kur 

fetch @kur
into @id

while @@FETCH_STATUS=0
begin

delete from ponuda where IDZahtev=@id
delete from voznja where IDZahtev=@id
delete from Zahtev where IDZahtev=@id

fetch @kur
into @id

end
close @kur


end
go


----------------------------------------------


create TRIGGER TR_TransportOffer_
ON zahtev
AFTER UPDATE 
as
SET NOCOUNT ON 
declare @idz int, @kur cursor

set @kur=cursor for
select I.IDZahtev FROM inserted I JOIN deleted D ON I.IDZahtev = D.IDZahtev AND I.Status <> D.Status AND I.Status=1

open @kur

fetch from @kur
into @idz

IF UPDATE(status)
BEGIN

while @@FETCH_STATUS=0
begin

delete from Ponuda where idzahtev=@idz

fetch from @kur
into @idz

end

close @kur

end

---------------------------------

