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
