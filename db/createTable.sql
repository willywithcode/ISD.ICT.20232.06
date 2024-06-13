-- Create aims.user table
CREATE TABLE "user" (
  "UserId" INTEGER PRIMARY KEY,
  "UserName" VARCHAR(255),
  "Password" VARCHAR(50),
  "Email" VARCHAR(255),
  "PhoneNumber" VARCHAR(50)
);

-- Create aims.cart table
CREATE TABLE cart (
  "CartId" INTEGER PRIMARY KEY,
  "UserId" UUID,
  "TotalAmount" DECIMAL(19, 2),
  CONSTRAINT "FK_cart_UserId" FOREIGN KEY ("UserId") REFERENCES "user" ("UserId") ON DELETE NO ACTION
);

-- Create aims.media table
CREATE TABLE media (
  "MediaId" INTEGER PRIMARY KEY,
  "Category" INTEGER,
  "Price" DECIMAL(19, 2),
  "Quantity" INTEGER,
  "Title" VARCHAR(255)
);

-- Create aims.dvd table
CREATE TABLE dvd (
  "DVDId" INTEGER PRIMARY KEY,
  "MediaId" INTEGER,
  "DiscType" INTEGER,
  "Director" VARCHAR(255),
  "Studio" VARCHAR(255),
  "SubTitle" VARCHAR(255),
  CONSTRAINT "FK_dvd_MediaId" FOREIGN KEY ("MediaId") REFERENCES media ("MediaId") ON DELETE NO ACTION
);

-- Create aims.book table
CREATE TABLE book (
  "BookId" INTEGER PRIMARY KEY,
  "MediaId" INTEGER,
  "Publisher" VARCHAR(255),
  "Author" VARCHAR(255),
  "Language" INTEGER,
  "BookCategory" VARCHAR(255),
  "PublisherDate" TIMESTAMP,
  "NumberOfPage" INTEGER,
  CONSTRAINT "FK_book_MediaId" FOREIGN KEY ("MediaId") REFERENCES media ("MediaId") ON DELETE NO ACTION
);

-- Create aims.mediacart table
CREATE TABLE mediacart (
  "MediaCartId" INTEGER PRIMARY KEY,
  "CartId" INTEGER,
  "MediaId" INTEGER,
  CONSTRAINT "FK_mediacart_CartId" FOREIGN KEY ("CartId") REFERENCES cart ("CartId") ON DELETE NO ACTION,
  CONSTRAINT "FK_mediacart_MediaId" FOREIGN KEY ("MediaId") REFERENCES media ("MediaId") ON DELETE NO ACTION
);

-- Create aims.deliveryinfo table
CREATE TABLE deliveryinfo (
  "DeliveryInfoId" INTEGER PRIMARY KEY,
  "Name" VARCHAR(50),
  "Address" VARCHAR(255),
  "Instructions" VARCHAR(255),
  "Province" VARCHAR(255)
);

-- Create aims.order table
CREATE TABLE "order" (
  "OrderId" INTEGER PRIMARY KEY,
  "DeliveryInfoId" INTEGER,
  "Price" DECIMAL(19, 2),
  "Quantity" INTEGER,
  "ShippingFee" DECIMAL(19, 2),
  "OrderTime" TIMESTAMP,
  CONSTRAINT "FK_order_DeliveryInfoId" FOREIGN KEY ("DeliveryInfoId") REFERENCES deliveryinfo ("DeliveryInfoId") ON DELETE NO ACTION
);

-- Create aims.mediaorder table
CREATE TABLE mediaorder (
  "MediaOrderId" INTEGER PRIMARY KEY,
  "MediaId" INTEGER,
  "OrderId" INTEGER,
  CONSTRAINT "FK_mediaorder_MediaId" FOREIGN KEY ("MediaId") REFERENCES media ("MediaId") ON DELETE NO ACTION,
  CONSTRAINT "FK_mediaorder_OrderId" FOREIGN KEY ("OrderId") REFERENCES "order" ("OrderId") ON DELETE NO ACTION
);

-- Create aims.invoice table
CREATE TABLE invoice (
  "InvoiceId" INTEGER PRIMARY KEY,
  "OrderId" INTEGER,
  "Total" DECIMAL(19, 2),
  "Content" VARCHAR(255),
  CONSTRAINT "FK_invoice_OrderId" FOREIGN KEY ("OrderId") REFERENCES "order" ("OrderId") ON DELETE NO ACTION
);

-- Create aims.creditcard table
CREATE TABLE creditcard (
  "CreditCardId" INTEGER PRIMARY KEY,
  "Name" VARCHAR(50),
  "Number" BIGINT,
  "SecurityCode" INTEGER,
  "ExpirationDate" VARCHAR(255)
);

-- Create aims.transaction table
CREATE TABLE transaction (
  "TransactionId" INTEGER PRIMARY KEY,
  "InvoiceId" INTEGER,
  "CreditCardId" INTEGER,
  "Content" VARCHAR(255),
  "Method" VARCHAR(255),
  "CreateDate" TIMESTAMP,
  CONSTRAINT "FK_transaction_CreditCardId" FOREIGN KEY ("CreditCardId") REFERENCES creditcard ("CreditCardId") ON DELETE NO ACTION,
  CONSTRAINT "FK_transaction_InvoiceId" FOREIGN KEY ("InvoiceId") REFERENCES invoice ("InvoiceId") ON DELETE NO ACTION
);
