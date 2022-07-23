use domaci
go

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
 
 