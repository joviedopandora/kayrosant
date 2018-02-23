select * from vnt_cliente where cln_nombres like '%TITAN%';

SELECT * FROM vnt_detallecliente WHERE cln_id = 91

ALTER TABLE vnt_detallecliente add column dcln_estado boolean default true;
