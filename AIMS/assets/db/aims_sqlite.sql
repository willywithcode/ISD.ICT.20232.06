
PRAGMA
foreign_keys = OFF;

-- Schema: aims
ATTACH
"aims.db" AS "aims";
BEGIN;
CREATE TABLE "aims"."Media"
(
    "id"       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "type"     VARCHAR(45) NOT NULL,
    "category" VARCHAR(45) NOT NULL,
    "price"    INTEGER     NOT NULL,
    "quantity" INTEGER     NOT NULL,
    "title"    VARCHAR(45) NOT NULL,
    "value"    INTEGER     NOT NULL,
    "imageUrl" VARCHAR(45) NOT NULL
);
CREATE TABLE "aims"."CD"
(
    "id"           INTEGER PRIMARY KEY NOT NULL,
    "artist"       VARCHAR(45)         NOT NULL,
    "recordLabel"  VARCHAR(45)         NOT NULL,
    "musicType"    VARCHAR(45)         NOT NULL,
    "releasedDate" DATE,
    CONSTRAINT "fk_cd_media"
        FOREIGN KEY ("id")
            REFERENCES "Media" ("id")
);
CREATE TABLE "aims"."Book"
(
    "id"           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "author"       VARCHAR(45) NOT NULL,
    "coverType"    VARCHAR(45) NOT NULL,
    "publisher"    VARCHAR(45) NOT NULL,
    "publishDate"  DATE    NOT NULL,
    "numOfPages"   INTEGER     NOT NULL,
    "language"     VARCHAR(45) NOT NULL,
    "bookCategory" VARCHAR(45) NOT NULL,
    CONSTRAINT "fk_book_media"
        FOREIGN KEY ("id")
            REFERENCES "Media" ("id")
);

CREATE TABLE "aims"."DVD"
(
    "id"           INTEGER PRIMARY KEY NOT NULL,
    "discType"     VARCHAR(45)         NOT NULL,
    "director"     VARCHAR(45)         NOT NULL,
    "runtime"      INTEGER             NOT NULL,
    "studio"       VARCHAR(45)         NOT NULL,
    "subtitle"     VARCHAR(45)         NOT NULL,
    "releasedDate" DATE,
    "filmType"     VARCHAR(45)         NOT NULL,
    CONSTRAINT "fk_dvd_media"
        FOREIGN KEY ("id")
            REFERENCES "Media" ("id")
);
CREATE TABLE "aims"."Order"
(
    "id"           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "name"        VARCHAR(45) NOT NULL,
    "province"        VARCHAR(45) NOT NULL,
    "address"      VARCHAR(45) NOT NULL,
    "phone"        VARCHAR(45) NOT NULL,
    "shipping_fee" INTEGER     NOT NULL
);
CREATE TABLE "aims"."Shipping"
(
    "id"           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "shipType"        INTEGER NOT NULL,
    "deliveryInstruction"        VARCHAR(255) NOT NULL,
    "shipmentDetail"      VARCHAR(255) NOT NULL,
    "deliveryTime"        VARCHAR(255) NOT NULL,
    "orderID" INTEGER     NOT NULL,
    CONSTRAINT "fk_ordermedia_order"
        FOREIGN KEY ("orderID")
            REFERENCES "Order" ("id")
);
CREATE TABLE "aims"."OrderMedia"
(
    "mediaID"  INTEGER NOT NULL,
    "orderID"  INTEGER NOT NULL,
    "price"    INTEGER NOT NULL,
    "quantity" INTEGER NOT NULL,
    PRIMARY KEY ("mediaID", "orderID"),
    CONSTRAINT "fk_ordermedia_media"
        FOREIGN KEY ("mediaID")
            REFERENCES "Media" ("id"),
    CONSTRAINT "fk_ordermedia_order"
        FOREIGN KEY ("orderID")
            REFERENCES "Order" ("id")
);
CREATE INDEX "aims"."OrderMedia.fk_ordermedia_order_idx" ON "OrderMedia" ("orderID");
CREATE TABLE "aims"."Transaction"
(
    "id"       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    "orderID"  INTEGER     NOT NULL,
    "createAt" DATETIME    NOT NULL,
    "content"  VARCHAR(45) NOT NULL,
    CONSTRAINT "fk_transaction_order"
        FOREIGN KEY ("orderID")
            REFERENCES "Order" ("id")
);
CREATE INDEX "aims"."Transaction.fk_transaction_order_idx" ON PaymentTransaction ("orderID");
COMMIT;
