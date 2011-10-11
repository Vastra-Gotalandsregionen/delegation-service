insert into vgr_delegation (id, createdBy, signedBy, delegatedBy, delegatedFor, created, approved, validFrom, validTo)
 values (1,'createdBy','signedBy','delegatedBy','delegatedFor','2011-10-01 10:00:00.0','2011-10-01 11:00:00.0','2011-10-02 00:00:00.0','2011-11-03 00:00:00.0');
insert into vgr_delegation (id, createdBy, signedBy, delegatedBy, delegatedFor, created)
 values (2,'createdBy','signedBy','delegatedBy','delegatedFor', '2011-10-01 10:00:00.0');
insert into vgr_delegation (id, createdBy, signedBy, delegatedBy, delegatedFor, created, approved, validFrom, validTo)
 values (3,'createdBy','signedBy','delegatedBy','delegatedFor','2011-10-01 10:00:00.0','2011-10-02 09:00:00.0','2011-10-02 00:00:00.0','2011-11-03 00:00:00.0');
insert into vgr_delegation (id, createdBy, signedBy, delegatedBy, delegatedFor, created, approved, validFrom, validTo)
 values (4,'createdBy','signedBy','delegatedBy','delegatedFor','2011-10-01 10:00:00.0','2011-10-02 11:00:00.0','2011-10-03 00:00:00.0','2011-11-04 00:00:00.0');
insert into vgr_delegation (id, createdBy, signedBy, delegatedBy, delegatedFor, created, approved, validFrom, validTo)
 values (5,'createdBy','signedBy','delegatedBy','delegatedFor','2011-10-01 10:00:00.0','2011-10-01 11:00:00.0','2011-10-03 00:00:00.0','2011-11-04 00:00:00.0');
insert into vgr_delegation (id, createdBy, signedBy, delegatedBy, delegatedFor, created, approved, validFrom, validTo)
 values (6,'createdBy','signedBy','delegatedBy-2','delegatedFor','2011-10-01 10:00:00.0','2011-10-01 11:00:00.0','2011-10-02 00:00:00.0','2011-11-03 00:00:00.0');
-- insert into vgr_delegation (id, createdBy, signedBy, delegatedBy, delegatedFor, created, approved, validFrom, validTo)
--  values (1,'createdBy','signedBy','delegatedBy','delegatedFor',null,null,null,null);
insert into vgr_delegate_to (delegation_id, delegation_delegateto) values (1, 'apa');
insert into vgr_delegate_to (delegation_id, delegation_delegateto) values (1, 'bepa');
insert into vgr_delegate_to (delegation_id, delegation_delegateto) values (1, 'cepa');