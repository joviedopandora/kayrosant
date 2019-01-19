ALTER TABLE vnt_registroventa
  ADD COLUMN emp_id integer;
ALTER TABLE vnt_registroventa
  ADD FOREIGN KEY (emp_id) REFERENCES adm_empresa (emp_id) ON UPDATE RESTRICT ON DELETE RESTRICT;
