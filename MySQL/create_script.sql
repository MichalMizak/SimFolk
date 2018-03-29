DROP DATABASE simfolk;
CREATE DATABASE simfolk;

USE simfolk;

CREATE TABLE `SONG`
( 
  songId	INT  NOT NULL AUTO_INCREMENT, 
  lyrics	TEXT NOT NULL,
  cleanLyrics TEXT NOT NULL,
  title		VARCHAR(250),
  songStyle VARCHAR(40),
  region	VARCHAR(40),
  source	VARCHAR(50) NOT NULL,
  PRIMARY KEY (`songId`),
  UNIQUE(`lyrics`, `cleanLyrics`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `SONG_TO_ATTRIBUTE`
(
	songId		INT NOT NULL,
    attribute	VARCHAR(40) NOT NULL,
    PRIMARY KEY (`songId`, `attribute`),
    FOREIGN KEY (`songId`)
    REFERENCES song(`songId`)
    ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM`
(
	termId			INT NOT NULL AUTO_INCREMENT,
    lyricsFragment	VARCHAR(80) NOT NULL,
	wordCount		INT NOT NULL,
    termScheme		VARCHAR(40),
    PRIMARY KEY (`termId`),
    UNIQUE (`lyricsFragment`, `termScheme`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM_TOKENIZED`
(
	termId			INT NOT NULL,				
    orderNumber		INT NOT NULL,
    word			VARCHAR(30) NOT NULL,
    PRIMARY KEY (`termId`, `orderNumber`),
    FOREIGN KEY (`termId`)
    REFERENCES term(`termId`)
    ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM_GROUP`
(
	groupId		INT NOT NULL AUTO_INCREMENT,
    termScheme		VARCHAR(40),
    termComparisonAlgorithm    VARCHAR(40) NOT NULL,
    tolerance	DOUBLE NOT NULL,
    incidenceCount	INT NOT NULL,
    PRIMARY KEY (`groupId`, `termComparisonAlgorithm`, `tolerance`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM_GROUP_TO_TERM`
(
	groupId		INT NOT NULL,
    termId		INT NOT NULL,
    PRIMARY KEY (`groupId`, `termId`),
    FOREIGN KEY (`groupId`)
    REFERENCES term_group(`groupId`)
    ON DELETE CASCADE,
    FOREIGN KEY (`termId`)
    REFERENCES term(`termId`)
    ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `WEIGHTED_TERM_GROUP`
(
	songId			INT NOT NULL,
    groupId			INT NOT NULL,
    termWeightType	VARCHAR(40) NOT NULL,
    weight			DOUBLE NOT NULL,
    PRIMARY KEY (`songId`, `groupId`), 
    FOREIGN KEY (`songId`)
    REFERENCES song(`songId`)
    ON DELETE CASCADE,
    FOREIGN KEY (`groupId`)
    REFERENCES term_group(`groupId`)
    ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `VECTOR_ALGORITHM_CONFIGURATION`
(
	configurationId				INT NOT NULL,
    termScheme					VARCHAR(40) NOT NULL,
    termDimension				INT NOT NULL,
    termWeightType				VARCHAR(40) NOT NULL,
    vectorInclusion				VARCHAR(40) NOT NULL,
    termComparisonAlgorithm		VARCHAR(40) NOT NULL,
    tolerance					VARCHAR(40) NOT NULL,
    vectorComparisonAlgorithm	VARCHAR(40) NOT NULL,
    PRIMARY KEY(`configurationId`),
    UNIQUE(`termScheme`, `termDimension`, `termWeightType`,
    `vectorInclusion`, `termComparisonAlgorithm`, `tolerance`,
    `vectorComparisonAlgorithm`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;





