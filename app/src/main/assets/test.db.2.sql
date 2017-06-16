ALTER TABLE product RENAME TO product_orig;

CREATE TABLE product ( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER );

create table imsi(
    _id integer primary key,
     price integer
  );

INSERT INTO imsi VALUES (1, 900);
INSERT INTO imsi VALUES (2, 2000);
INSERT INTO imsi VALUES (3, 1000);
INSERT INTO imsi VALUES (4, 1500);
INSERT INTO imsi VALUES (5, 32000);
INSERT INTO imsi VALUES (6, 190000000);
INSERT INTO imsi VALUES (7, 1900);
INSERT INTO imsi VALUES (8, 8900000);
INSERT INTO imsi VALUES (9, 25000);

INSERT INTO product(_id, name, price)
    SELECT _id, name, price
    FROM product_orig join imsi using (_id);

drop table product_orig;
drop table imsi;