INSERT INTO invoice(id,amount, date) VALUES (-1,200, '2011-01-22');
INSERT INTO invoice(id,amount, date) VALUES (-2,250.10, '2011-04-22');
INSERT INTO invoice(id,amount, date) VALUES (-3,200.30, '2011-03-22');
INSERT INTO invoice(id,amount, date) VALUES (-4,15.99, '2011-02-22');

INSERT INTO tax_rate(id,percentage) VALUES (-3,10);
INSERT INTO tax_rate(id,percentage) VALUES (-2,20);
INSERT INTO tax_rate(id,percentage) VALUES (-1,30);

INSERT INTO product(id,name,price,tax_rate) VALUES (-4,'apple',15,-1);
INSERT INTO product(id,name,price,tax_rate) VALUES (-3,'cherry',16,-2);
INSERT INTO product(id,name,price,tax_rate) VALUES (-2,'pineapple',17,-3);
INSERT INTO product(id,name,price,tax_rate) VALUES (-1,'pear',18,-1);

INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-4,-1,3);
INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-3,-2,4);
INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-2,-3,2);
INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-1,-4,3);
INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-1,-3,1);


