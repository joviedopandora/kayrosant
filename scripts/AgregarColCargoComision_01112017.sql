--select * from adm_cargo 



--select * from adm_crgxcol 
alter table adm_cargo add column crg_aplica_comision boolean default false;
insert into adm_cargo(crg_id, crg_nombre, crg_desc, crg_aplica_comision)
values('SYS41', 'Ejecutivo de cuenta','Encargado de las vantas, aplica comisión', true);

--select max(substr(crg_id,4, length(crg_id))) from adm_cargo