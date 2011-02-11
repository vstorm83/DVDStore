select * from MovieCatgory
insert into MovieCatgory select LoaiPhim as MovieCatgoryName,0 as Version from BanDia.dbo.tblLoaiPhim 
select * from Director
insert into Director select DaoDien as DirectorName,0 as Version from BanDia.dbo.tblDaoDien 
select * from Actor
insert into Actor select DienVien as ActorName,0 as Version from BanDia.dbo.tblDienVien 
select * from Movie
select * from BanDia.dbo.tblPhim
insert into Movie select TenPhim as MovieName,
AnhMinhHoa as MoviePicName,
MoTa as MovieDescription,
5 as MovieCatgoryId,
9 as MovieDirectorId,
GiaXuat as MoviePrice,
GiamGia as MovieSaleOff,
getDate() as DateCreated,
0 as Version from BanDia.dbo.tblPhim 