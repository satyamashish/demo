
Create table egov_department( 
  id bigint NOT NULL,
  name varchar(64) NOT NULL,
  code varchar(10) NOT NULL,
  active boolean NOT NULL,
    createdby varchar(50),
		createddate timestamp without time zone,
		lastmodifiedby varchar(50),
		lastmodifieddate timestamp without time zone,
		tenantId varchar(250),
		version bigint
);
alter table egov_department add constraint pk_egov_department primary key (id);
create sequence seq_egov_department;
