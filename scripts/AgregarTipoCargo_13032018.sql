ALTER TABLE adm_cargo add column crg_tipo varchar(50);
ALTER TABLE adm_cargo add column crg_editable boolean default true;
UPDATE adm_cargo SET crg_tipo = 'operativo'

