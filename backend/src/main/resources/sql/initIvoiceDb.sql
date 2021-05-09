INSERT INTO invoice(id,amount, date) VALUES (-1,200, '2011-01-22');
INSERT INTO invoice(id,amount, date) VALUES (-2,250.10, '2011-04-22');
INSERT INTO invoice(id,amount, date) VALUES (-3,200.30, '2011-03-22');
INSERT INTO invoice(id,amount, date) VALUES (-4,15.99, '2011-02-22');

INSERT INTO product(id) VALUES (-4);
INSERT INTO product(id) VALUES (-3);
INSERT INTO product(id) VALUES (-2);
INSERT INTO product(id) VALUES (-1);

INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-4,-1,3);
INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-3,-2,4);
INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-2,-3,2);
INSERT INTO invoice_item(invoice_id,product_id,number_of_items) VALUES (-1,-4,3);

