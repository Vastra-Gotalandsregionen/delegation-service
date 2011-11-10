insert into vgr_uppdrag (id, vardEnhet, signToken, approvedBy, approvedOn)
 values (-1, 'hsaVE1', 'UUID-apa', 'hsaApa', '2011-11-09 10:00:00.0');
insert into vgr_uppdrag (id, vardEnhet, signToken, approvedBy, approvedOn)
 values (-2, 'hsaVE1', 'UUID-apa', 'hsaApa', '2011-11-09 11:00:00.0');
insert into vgr_uppdrag (id, vardEnhet, signToken, approvedBy, approvedOn)
 values (-3, 'hsaVE1', 'UUID-apa', 'hsaApa', '2011-11-10 10:00:00.0');
insert into vgr_uppdrag (id, vardEnhet) values (-4, 'hsaVE1');

insert into vgr_uppdrag_action (id, changeType, hsaIdentity, hsaCommissionMember, createdBy, created,
uppdragVardEnhet_id)
 values (-1, 'ADD_MEMBER', 'hsaS1', 'hsaP1;;', 'hsaApa', '2011-11-09 09:30:00.0', -1);
insert into vgr_uppdrag_action (id, changeType, hsaIdentity, hsaCommissionMember, createdBy, created,
uppdragVardEnhet_id)
 values (-2, 'ADD_MEMBER', 'hsaS1', 'hsaP2;;', 'hsaApa', '2011-11-09 09:30:00.0', -1);

insert into vgr_uppdrag_action (id, changeType, hsaIdentity, hsaCommissionMember, createdBy, created,
uppdragVardEnhet_id)
 values (-3, 'DELETE_MEMBER', 'hsaS1', 'hsaP2;;', 'hsaApa', '2011-11-09 09:30:00.0', -2);

insert into vgr_uppdrag_action (id, changeType, hsaIdentity, hsaCommissionMember, createdBy, created,
uppdragVardEnhet_id)
 values (-4, 'UPDATE_MEMBER', 'hsaS1', 'hsaP2;;2011-12-09 00:00:00.0', 'hsaApa', '2011-11-09 09:30:00.0', -3);

insert into vgr_uppdrag_action (id, changeType, hsaIdentity, hsaCommissionPurpose, hsaCommissionRight,
cn, description, createdBy, created, uppdragVardEnhet_id)
 values (-5, 'CREATE_COMMISSION', 'hsaS2', 'Vård och behandling', 'Läsa;alla;SJF', 'S2 Namn', 'S2 beskr.',
 'hsaApa', '2011-11-09 09:30:00.0', -4);