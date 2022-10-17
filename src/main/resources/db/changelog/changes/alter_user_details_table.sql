--create sequence id;

--alter table nms_user_details add column id default nextval('id_sequence'::regclass) ;
--ALTER TABLE nms_user_details ALTER COLUMN id SET DEFAULT nextval('id_seq');