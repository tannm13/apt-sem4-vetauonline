create database BanVeTauOnline
go
use BanVeTauOnline
go

create table TaiKhoan
(
	ID int primary key identity,
	TenDangNhap varchar(10),
	MatKhau varchar(10),
	Status varchar(10)
)
go

create table Tau
(
	IDTau int primary key identity,
	TenTau varchar(50),
	SoLuongGhe int
)
go

create table Ghe
(
	IDGhe varchar(10) primary key,
	IDTau int foreign key references Tau(IDTau),
	LoaiGhe varchar(10),
)

create table GaTau
(
	IDGaTau int primary key identity,
	TenGa nvarchar(100)
)
go

create table GiaTien
(
	IDTau int foreign key references Tau(IDTau),
	LoaiGhe varchar(10),
	Tien int,
	IDTaiKhoan int foreign key references TaiKhoan(ID)
)
go

create table LichTau
(
	IDTau int foreign key references Tau(IDTau),
	IDGaDung int foreign key references GaTau(IDGaTau),
	IDSttGa int,
	GioDen int,
	PhutDen int,
	NgayThemDen int,
	KhoangCach int,
	IDTaiKhoan int foreign key references TaiKhoan(ID)
)
go

create table VeTau
(
	IDVe int primary key identity,
	IDTau int foreign key references Tau(IDTau),
	IDGhe varchar(10) foreign key references Ghe(IDGhe),
	TenKH nvarchar(100),
	CMND varchar(50),
	IDGaDi int foreign key references GaTau(IDGaTau),
	IDGaDen int foreign key references GaTau(IDGaTau),
	GioDi datetime,
	GioDen datetime,
	GiaVe int,
	Status varchar(10)
)
go