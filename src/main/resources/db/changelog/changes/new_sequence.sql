--liquibase formatted sql
--changeset A-10232:new_sequence

CREATE SEQUENCE id_seq INCREMENT BY 1 START WITH 1 OWNED BY nms_user_details.id;

ALTER TABLE nms_user_details ALTER COLUMN id SET DEFAULT nextval('id_seq');