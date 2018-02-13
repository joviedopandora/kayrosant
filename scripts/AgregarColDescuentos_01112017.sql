--select * from eval_descuento 
alter table eval_descuento add column descuenta_multiplicable boolean default true

--select descuento_desc, 'UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = '|| descuento_id || ';' from eval_descuento


UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 3;

UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 8;
UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 9;
UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 11;
UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 13;
UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 14;
UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 17;


UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 6;
UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 7;


UPDATE eval_descuento SET descuenta_multiplicable = false WHERE descuento_id = 16;
