USE [BlogDb]
GO
ALTER TABLE [dbo].[Profiles] DROP CONSTRAINT [FK_Profiles_Profiles]
GO
ALTER TABLE [dbo].[Posts] DROP CONSTRAINT [FK_Post_Users]
GO
ALTER TABLE [dbo].[PostRatings] DROP CONSTRAINT [FK_PostRatings_Users]
GO
ALTER TABLE [dbo].[PostRatings] DROP CONSTRAINT [FK_PostRatings_Posts]
GO
ALTER TABLE [dbo].[CommentsRatings] DROP CONSTRAINT [FK_CommentsRatings_Users]
GO
ALTER TABLE [dbo].[CommentsRatings] DROP CONSTRAINT [FK_CommentsRatings_Comments]
GO
ALTER TABLE [dbo].[Comments] DROP CONSTRAINT [FK_Comments_Users]
GO
ALTER TABLE [dbo].[Comments] DROP CONSTRAINT [FK_Comments_Posts]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 10/30/2016 15:43:39 ******/
DROP TABLE [dbo].[Users]
GO
/****** Object:  Table [dbo].[Profiles]    Script Date: 10/30/2016 15:43:39 ******/
DROP TABLE [dbo].[Profiles]
GO
/****** Object:  Table [dbo].[Posts]    Script Date: 10/30/2016 15:43:39 ******/
DROP TABLE [dbo].[Posts]
GO
/****** Object:  Table [dbo].[PostRatings]    Script Date: 10/30/2016 15:43:39 ******/
DROP TABLE [dbo].[PostRatings]
GO
/****** Object:  Table [dbo].[CommentsRatings]    Script Date: 10/30/2016 15:43:39 ******/
DROP TABLE [dbo].[CommentsRatings]
GO
/****** Object:  Table [dbo].[Comments]    Script Date: 10/30/2016 15:43:39 ******/
DROP TABLE [dbo].[Comments]
GO
/****** Object:  UserDefinedFunction [dbo].[GetUserRating]    Script Date: 10/30/2016 15:43:39 ******/
DROP FUNCTION [dbo].[GetUserRating]
GO
/****** Object:  UserDefinedFunction [dbo].[GetPostRating]    Script Date: 10/30/2016 15:43:39 ******/
DROP FUNCTION [dbo].[GetPostRating]
GO
/****** Object:  UserDefinedFunction [dbo].[GetCommentRating]    Script Date: 10/30/2016 15:43:39 ******/
DROP FUNCTION [dbo].[GetCommentRating]
GO
USE [master]
GO
/****** Object:  Database [BlogDb]    Script Date: 10/30/2016 15:43:39 ******/
DROP DATABASE [BlogDb]
GO
/****** Object:  Database [BlogDb]    Script Date: 10/30/2016 15:43:39 ******/
CREATE DATABASE [BlogDb]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BlogDb', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\BlogDb.mdf' , SIZE = 5120KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'BlogDb_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.MSSQLSERVER\MSSQL\DATA\BlogDb_log.ldf' , SIZE = 1536KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [BlogDb] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BlogDb].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BlogDb] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BlogDb] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BlogDb] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BlogDb] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BlogDb] SET ARITHABORT OFF 
GO
ALTER DATABASE [BlogDb] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BlogDb] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BlogDb] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BlogDb] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BlogDb] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BlogDb] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BlogDb] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BlogDb] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BlogDb] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BlogDb] SET  DISABLE_BROKER 
GO
ALTER DATABASE [BlogDb] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BlogDb] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BlogDb] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BlogDb] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BlogDb] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BlogDb] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BlogDb] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BlogDb] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [BlogDb] SET  MULTI_USER 
GO
ALTER DATABASE [BlogDb] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BlogDb] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BlogDb] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BlogDb] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [BlogDb] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'BlogDb', N'ON'
GO

USE [BlogDb]
GO
/****** Object:  UserDefinedFunction [dbo].[GetCommentRating]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[GetCommentRating](@commentId int)
RETURNS int
AS
BEGIN
	DECLARE @result int;
	SET @result = 0;

	SELECT @result = SUM(CASE WHEN Value = 1 THEN 1 ELSE -1 END)
	FROM dbo.CommentsRatings
	WHERE CommentId = @commentId

	RETURN @result;

END

GO
/****** Object:  UserDefinedFunction [dbo].[GetPostRating]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[GetPostRating](@postId int)
RETURNS int
AS
BEGIN
	DECLARE @result int;
	SET @result = 0;

	SELECT @result = SUM(CASE WHEN Value = 1 THEN 1 ELSE -1 END)
	FROM dbo.PostRatings
	WHERE PostId = @postId;

	RETURN @result;

END

GO
/****** Object:  UserDefinedFunction [dbo].[GetUserRating]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[GetUserRating](@userId int)
RETURNS int
AS
BEGIN
	DECLARE @postsRating int;
	SET @postsRating = 0;

	DECLARE @commentsRating int;
	SET @commentsRating = 0;

	DECLARE @result int;
	SET @result = 0;

	SELECT @postsRating = SUM(Rating)
	FROM dbo.Posts
	WHERE dbo.Posts.UserId = @userId;

	SELECT @commentsRating = SUM(Rating)
	FROM dbo.Comments
	WHERE dbo.Comments.UserId = @userId;

	SET @result = @commentsRating + @postsRating;
	/* in future add coeficient here */

	RETURN @result;

END

GO
/****** Object:  Table [dbo].[Comments]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comments](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[UserId] [int] NOT NULL,
	[Text] [nvarchar](255) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[Rating]  AS ([dbo].[GetCommentRating]([Id])),
	[PostId] [int] NOT NULL,
 CONSTRAINT [PK_Comments] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[CommentsRatings]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CommentsRatings](
	[CommentId] [int] NOT NULL,
	[UserId] [int] NOT NULL,
	[Value] [bit] NOT NULL,
 CONSTRAINT [PK_CommentsRatings] PRIMARY KEY CLUSTERED 
(
	[CommentId] ASC,
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[PostRatings]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[PostRatings](
	[PostId] [int] NOT NULL,
	[UserId] [int] NOT NULL,
	[Value] [bit] NOT NULL,
 CONSTRAINT [PK_PostRatings] PRIMARY KEY CLUSTERED 
(
	[PostId] ASC,
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Posts]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Posts](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](20) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[UserId] [int] NOT NULL,
	[Text] [nvarchar](max) NOT NULL,
	[Rating]  AS ([dbo].[GetPostRating]([Id])),
 CONSTRAINT [PK_Post] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Profiles]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Profiles](
	[UserId] [int] NOT NULL,
	[Name] [nvarchar](20) NULL,
	[Surname] [nvarchar](20) NULL,
	[BirthDate] [datetime] NULL,
	[Sex] [bit] NULL,
	[Country] [nvarchar](20) NULL,
	[City] [nvarchar](20) NULL,
	[Address] [nvarchar](50) NULL,
	[Email] [nvarchar](20) NULL,
	[Rating]  AS ([dbo].[GetUserRating]([UserId])),
 CONSTRAINT [PK_Profiles] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Users]    Script Date: 10/30/2016 15:43:39 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[Login] [nvarchar](20) NOT NULL,
	[Password] [nvarchar](255) NOT NULL,
	[Seed] [nchar](255) NOT NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[Comments]  WITH CHECK ADD  CONSTRAINT [FK_Comments_Posts] FOREIGN KEY([PostId])
REFERENCES [dbo].[Posts] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Comments] CHECK CONSTRAINT [FK_Comments_Posts]
GO
ALTER TABLE [dbo].[Comments]  WITH CHECK ADD  CONSTRAINT [FK_Comments_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Comments] CHECK CONSTRAINT [FK_Comments_Users]
GO
ALTER TABLE [dbo].[CommentsRatings]  WITH CHECK ADD  CONSTRAINT [FK_CommentsRatings_Comments] FOREIGN KEY([CommentId])
REFERENCES [dbo].[Comments] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[CommentsRatings] CHECK CONSTRAINT [FK_CommentsRatings_Comments]
GO
ALTER TABLE [dbo].[CommentsRatings]  WITH CHECK ADD  CONSTRAINT [FK_CommentsRatings_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[CommentsRatings] CHECK CONSTRAINT [FK_CommentsRatings_Users]
GO
ALTER TABLE [dbo].[PostRatings]  WITH CHECK ADD  CONSTRAINT [FK_PostRatings_Posts] FOREIGN KEY([PostId])
REFERENCES [dbo].[Posts] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[PostRatings] CHECK CONSTRAINT [FK_PostRatings_Posts]
GO
ALTER TABLE [dbo].[PostRatings]  WITH CHECK ADD  CONSTRAINT [FK_PostRatings_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[PostRatings] CHECK CONSTRAINT [FK_PostRatings_Users]
GO
ALTER TABLE [dbo].[Posts]  WITH CHECK ADD  CONSTRAINT [FK_Post_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([Id])
GO
ALTER TABLE [dbo].[Posts] CHECK CONSTRAINT [FK_Post_Users]
GO
ALTER TABLE [dbo].[Profiles]  WITH CHECK ADD  CONSTRAINT [FK_Profiles_Profiles] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([Id])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Profiles] CHECK CONSTRAINT [FK_Profiles_Profiles]
GO
USE [master]
GO
ALTER DATABASE [BlogDb] SET  READ_WRITE 
GO
