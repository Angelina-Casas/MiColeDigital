USE [master]
GO
/****** Object:  Database [DBMiColeDigital]    Script Date: 11/07/2025 08:23:22 ******/
CREATE DATABASE [DBMiColeDigital]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'DBMiColeDigital_Data', FILENAME = N'c:\dzsqls\DBMiColeDigital.mdf' , SIZE = 8192KB , MAXSIZE = 30720KB , FILEGROWTH = 22528KB )
 LOG ON 
( NAME = N'DBMiColeDigital_Logs', FILENAME = N'c:\dzsqls\DBMiColeDigital.ldf' , SIZE = 8192KB , MAXSIZE = 30720KB , FILEGROWTH = 22528KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [DBMiColeDigital] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [DBMiColeDigital].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [DBMiColeDigital] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET ARITHABORT OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [DBMiColeDigital] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [DBMiColeDigital] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET  ENABLE_BROKER 
GO
ALTER DATABASE [DBMiColeDigital] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [DBMiColeDigital] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [DBMiColeDigital] SET  MULTI_USER 
GO
ALTER DATABASE [DBMiColeDigital] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [DBMiColeDigital] SET DB_CHAINING OFF 
GO
ALTER DATABASE [DBMiColeDigital] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [DBMiColeDigital] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [DBMiColeDigital] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [DBMiColeDigital] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [DBMiColeDigital] SET QUERY_STORE = ON
GO
ALTER DATABASE [DBMiColeDigital] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [DBMiColeDigital]
GO
/****** Object:  User [angiecasas_SQLLogin_1]    Script Date: 11/07/2025 08:23:24 ******/
CREATE USER [angiecasas_SQLLogin_1] FOR LOGIN [angiecasas_SQLLogin_1] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [angiecasas_SQLLogin_1]
GO
/****** Object:  Schema [angiecasas_SQLLogin_1]    Script Date: 11/07/2025 08:23:24 ******/
CREATE SCHEMA [angiecasas_SQLLogin_1]
GO
/****** Object:  Table [dbo].[CursoAula]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CursoAula](
	[idCursoAula] [int] IDENTITY(1,1) NOT NULL,
	[idCurso] [int] NOT NULL,
	[idAula] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idCursoAula] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Aula]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Aula](
	[idAula] [int] IDENTITY(1,1) NOT NULL,
	[grado] [int] NOT NULL,
	[seccion] [varchar](2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idAula] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Usuario]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Usuario](
	[idUsuario] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[correo] [varchar](100) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[idRol] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idUsuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[correo] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[AulaUsuario]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[AulaUsuario](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idUsuario] [int] NULL,
	[idAula] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Curso]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Curso](
	[idCurso] [int] IDENTITY(1,1) NOT NULL,
	[nombre] [varchar](100) NOT NULL,
	[idDocente] [int] NOT NULL,
	[idAula] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idCurso] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[VistaCursosPorEstudiante]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[VistaCursosPorEstudiante] AS
SELECT 
    u.idUsuario,
    u.nombre AS nombreEstudiante,
    a.idAula,
    a.grado,
    a.seccion,
    c.idCurso,
    c.nombre
FROM Usuario u
JOIN AulaUsuario ae ON ae.idUsuario = u.idUsuario
JOIN Aula a ON a.idAula = ae.idAula
JOIN CursoAula ca ON ca.idAula = ae.idAula
JOIN Curso c ON c.idCurso = ca.idCurso;
GO
/****** Object:  Table [dbo].[Calificacion]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Calificacion](
	[idCalificacion] [int] IDENTITY(1,1) NOT NULL,
	[idFormulario] [int] NOT NULL,
	[idEstudiante] [int] NOT NULL,
	[nota] [decimal](5, 2) NOT NULL,
	[fechaEnvio] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[idCalificacion] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Formulario]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Formulario](
	[idFor] [int] IDENTITY(1,1) NOT NULL,
	[nombreFor] [varchar](100) NOT NULL,
	[tema] [varchar](100) NOT NULL,
	[video_url] [varchar](255) NULL,
	[idCurso] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[idFor] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[PreguntaFormulario]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PreguntaFormulario](
	[idPregunta] [int] IDENTITY(1,1) NOT NULL,
	[idFormulario] [int] NOT NULL,
	[nroPregunta] [int] NOT NULL,
	[pregunta] [varchar](255) NOT NULL,
	[opcion1] [varchar](255) NULL,
	[opcion2] [varchar](255) NULL,
	[opcion3] [varchar](255) NULL,
	[opcion4] [varchar](255) NULL,
	[respuestaCorrecta] [varchar](5) NULL,
PRIMARY KEY CLUSTERED 
(
	[idPregunta] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ResultadoPractica]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ResultadoPractica](
	[idResultado] [int] IDENTITY(1,1) NOT NULL,
	[idUsuario] [int] NOT NULL,
	[idFormulario] [int] NOT NULL,
	[nota] [int] NOT NULL,
	[fechaEnvio] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[idResultado] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[idUsuario] ASC,
	[idFormulario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Rol]    Script Date: 11/07/2025 08:23:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rol](
	[idRol] [int] IDENTITY(1,1) NOT NULL,
	[nombreRol] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[idRol] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[nombreRol] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Calificacion] ADD  DEFAULT (getdate()) FOR [fechaEnvio]
GO
ALTER TABLE [dbo].[ResultadoPractica] ADD  DEFAULT (getdate()) FOR [fechaEnvio]
GO
ALTER TABLE [dbo].[AulaUsuario]  WITH CHECK ADD FOREIGN KEY([idAula])
REFERENCES [dbo].[Aula] ([idAula])
GO
ALTER TABLE [dbo].[AulaUsuario]  WITH CHECK ADD FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[Calificacion]  WITH CHECK ADD FOREIGN KEY([idEstudiante])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[Calificacion]  WITH CHECK ADD FOREIGN KEY([idFormulario])
REFERENCES [dbo].[Formulario] ([idFor])
GO
ALTER TABLE [dbo].[Curso]  WITH CHECK ADD FOREIGN KEY([idAula])
REFERENCES [dbo].[Aula] ([idAula])
GO
ALTER TABLE [dbo].[Curso]  WITH CHECK ADD FOREIGN KEY([idDocente])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[CursoAula]  WITH CHECK ADD FOREIGN KEY([idAula])
REFERENCES [dbo].[Aula] ([idAula])
GO
ALTER TABLE [dbo].[CursoAula]  WITH CHECK ADD FOREIGN KEY([idCurso])
REFERENCES [dbo].[Curso] ([idCurso])
GO
ALTER TABLE [dbo].[Formulario]  WITH CHECK ADD  CONSTRAINT [FK_Formulario_Curso] FOREIGN KEY([idCurso])
REFERENCES [dbo].[Curso] ([idCurso])
GO
ALTER TABLE [dbo].[Formulario] CHECK CONSTRAINT [FK_Formulario_Curso]
GO
ALTER TABLE [dbo].[PreguntaFormulario]  WITH CHECK ADD FOREIGN KEY([idFormulario])
REFERENCES [dbo].[Formulario] ([idFor])
GO
ALTER TABLE [dbo].[ResultadoPractica]  WITH CHECK ADD FOREIGN KEY([idFormulario])
REFERENCES [dbo].[Formulario] ([idFor])
GO
ALTER TABLE [dbo].[ResultadoPractica]  WITH CHECK ADD FOREIGN KEY([idUsuario])
REFERENCES [dbo].[Usuario] ([idUsuario])
GO
ALTER TABLE [dbo].[Usuario]  WITH CHECK ADD FOREIGN KEY([idRol])
REFERENCES [dbo].[Rol] ([idRol])
GO
USE [master]
GO
ALTER DATABASE [DBMiColeDigital] SET  READ_WRITE 
GO
