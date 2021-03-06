DROP DATABASE simfolk;
CREATE DATABASE simfolk;

USE simfolk;

CREATE TABLE `SONG`
(
    songId	BIGINT  NOT NULL AUTO_INCREMENT,
    lyrics	TEXT NOT NULL,
    cleanLyrics TEXT NOT NULL,
    title		VARCHAR(400),
    songStyle VARCHAR(400),
    region	VARCHAR(400),
    source	VARCHAR(400) NOT NULL,
    PRIMARY KEY (`songId`),
    UNIQUE(`lyrics`(767))
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `SONG_TO_ATTRIBUTE`
(
    songId		BIGINT NOT NULL,
    attribute	VARCHAR(40) NOT NULL,
    PRIMARY KEY (`songId`, `attribute`),
    FOREIGN KEY (`songId`)
    REFERENCES SONG(`songId`)
        ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM`
(
    termId			BIGINT NOT NULL AUTO_INCREMENT,
    lyricsFragment	VARCHAR(800) NOT NULL,
    wordCount		INT NOT NULL,
    termScheme		VARCHAR(40),
    PRIMARY KEY (`termId`),
    UNIQUE (`lyricsFragment`, `termScheme`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM_TOKENIZED`
(
    termId			BIGINT NOT NULL,
    orderNumber		INT NOT NULL,
    word			VARCHAR(300) NOT NULL,
    PRIMARY KEY (`termId`, `orderNumber`),
    FOREIGN KEY (`termId`)
    REFERENCES TERM(`termId`)
        ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM_GROUP`
(
    groupId		BIGINT NOT NULL AUTO_INCREMENT,
    termScheme		VARCHAR(40),
    termComparisonAlgorithm    VARCHAR(40) NOT NULL,
    tolerance	DOUBLE NOT NULL,
    incidenceCount	INT NOT NULL,
    PRIMARY KEY (`groupId`, `termComparisonAlgorithm`, `tolerance`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM_GROUP_TO_TERM`
(
    groupId		BIGINT NOT NULL,
    termId		BIGINT NOT NULL,
    PRIMARY KEY (`groupId`, `termId`),
    FOREIGN KEY (`groupId`)
    REFERENCES TERM_GROUP(`groupId`)
        ON DELETE CASCADE,
    FOREIGN KEY (`termId`)
    REFERENCES TERM(`termId`)
        ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `TERM_WEIGHT_TYPE`
(
    termWeightTypeId	INT NOT NULL AUTO_INCREMENT,
    isTFIDF				TINYINT(1) NOT NULL,
    TF					VARCHAR(40),
    IDF					VARCHAR(40),
    nonTFIDFTermWeight	VARCHAR(40),
    PRIMARY KEY (`termWeightTypeId`),
    UNIQUE(`isTFIDF`, `TF`, `IDF`, `nonTFIDFTermWeight`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO term_weight_type VALUES (1, 1, 'TF_NAIVE', 'NONE', 'NONE');


CREATE TABLE `WEIGHTED_TERM_GROUP`
(
    songId				BIGINT NOT NULL,
    groupId				BIGINT NOT NULL,
    termWeightTypeId	INT NOT NULL,
    weight				DOUBLE NOT NULL,
    PRIMARY KEY (`songId`, `groupId`, `termWeightTypeId`),
    FOREIGN KEY (`songId`)
    REFERENCES SONG(`songId`)
        ON DELETE CASCADE,
    FOREIGN KEY (`groupId`)
    REFERENCES TERM_GROUP(`groupId`)
        ON DELETE CASCADE,
    FOREIGN KEY (`termWeightTypeId`)
    REFERENCES TERM_WEIGHT_TYPE(`termWeightTypeId`)
        ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `VECTOR_ALGORITHM_CONFIGURATION`
(
    configurationId				BIGINT NOT NULL,
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

CREATE TABLE `VECTOR_ALGORITHM_RESULT`
(
    resultId		BIGINT NOT NULL AUTO_INCREMENT,
    songId			BIGINT NOT NULL,
    configurationId	BIGINT NOT NULL,
    PRIMARY KEY (`resultId`, `songId`, `configurationId`),
    FOREIGN KEY (`songId`)
    REFERENCES SONG(`songId`)
        ON DELETE CASCADE,
    FOREIGN KEY (`configurationId`)
    REFERENCES VECTOR_ALGORITHM_CONFIGURATION(`configurationId`)
        ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `RESULT_TO_SONG`
(
    songId 		BIGINT NOT NULL,
    resultId	BIGINT NOT NULL,
    similarity	DOUBLE NOT NULL,
    UNIQUE (`songId`, `resultId`),
    FOREIGN KEY (`songId`)
    REFERENCES SONG(`songId`)
        ON DELETE CASCADE,
    FOREIGN KEY (`resultId`)
    REFERENCES VECTOR_ALGORITHM_RESULT(`resultId`)
        ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- select of grouped lyricFragments
CREATE VIEW `tt` as
    select t1.lyricsFragment, wtg1.groupId from TERM t1 join TERM_GROUP_TO_TERM tg1 on t1.termId = tg1.termId JOIN WEIGHTED_TERM_GROUP wtg1 on wtg1.groupId = tg1.groupId group by t1.lyricsFragment order by tg1.groupId

SELECT * from tt
WHERE groupId IN ( SELECT groupId FROM tt GROUP BY groupId HAVING COUNT(groupId) > 1)