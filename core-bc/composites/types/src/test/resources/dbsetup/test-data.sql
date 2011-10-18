-- delegateBy, delegateFor
insert into vgr_delegation (id, status, signToken, delegatedBy, created, approvedOn, revokedOn)
 values (-1,'SUPERSEDED','signToken','delegatedBy','2011-09-01 10:00:00.0','2011-09-01 11:00:00.0','2011-10-01 11:00:00.0');
insert into vgr_delegation (id, status, signToken, delegatedBy, created, approvedOn)
 values (-2,'ACTIVE','signToken','delegatedBy','2011-10-01 10:00:00.0','2011-10-01 11:00:00.0');
insert into vgr_delegation (id, status, signToken, delegatedBy, created)
 values (-3,'IN_PROGRESS','','delegatedBy','2011-10-01 12:00:00.0');
insert into vgr_delegation (id, status, signToken, delegatedBy, created)
 values (-4,'REJECTED','','delegatedBy','2011-10-01 11:30:00.0');
-- delegateBy, delegateFor2
insert into vgr_delegation (id, status, signToken, delegatedBy, created)
 values (-5,'IN_PROGRESS','','delegatedBy','2011-10-01 12:00:00.0');
-- delegateBy2, delegateFor
insert into vgr_delegation (id, status, signToken, delegatedBy, created, approvedOn, revokedOn)
 values (-6,'SUPERSEDED','signToken','delegatedBy2','2011-09-01 10:00:00.0','2011-09-01 11:00:00.0','2011-10-01 11:00:00.0');
insert into vgr_delegation (id, status, signToken, delegatedBy, created, approvedOn)
 values (-7,'ACTIVE','signToken','delegatedBy2','2011-10-01 10:00:00.0','2011-10-01 11:00:00.0');
-- delegateBy3, delegateFor3
insert into vgr_delegation (id, status, signToken, delegatedBy, created)
 values (-8,'REJECTED','','delegatedBy3','2011-10-01 12:00:00.0');
-- delegateBy4, delegateFor3
insert into vgr_delegation (id, status, signToken, delegatedBy, created)
 values (-9,'IN_PROGRESS','','delegatedBy4','2011-10-01 12:00:00.0');

-- delegationBy, delegateFor - SUPERSEDED
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom)
 values (-1,-1,'delegatedFor','apa','2011-09-01 11:00:00.0');
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom, validTo)
 values (-2,-1,'delegatedFor','bepa','2011-09-01 11:00:00.0','2011-09-15 11:00:00.0');
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom)
 values (-3,-1,'delegatedFor','cepa','2011-09-15 11:00:00.0');
-- delegationBy, delegateFor - ACTIVE
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom)
 values (-4,-2,'delegatedFor','apa','2011-10-01 11:00:00.0');
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom)
 values (-5,-2,'delegatedFor','cepa','2011-10-01 11:00:00.0');
-- delegationBy, delegateFor - IN_PROGRESS
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom, validTo)
 values (-6,-3,'delegatedFor','bpa','2011-11-05 11:00:00.0','2011-11-15 11:00:00.0');
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto)
 values (-7,-3,'delegatedFor','cepa');
-- delegationBy, delegateFor - REJECTED
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto)
 values (-8,-4,'delegatedFor','cepa');
-- delegationBy, delegateFor2 - IN_PROGRESS
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom)
 values (-9,-5,'delegatedFor2','cepa','2011-11-01 12:00:00.0');
-- delegationBy2, delegateFor - SUPERSEDED
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom)
 values (-10,-6,'delegatedFor','depa','2011-09-01 11:00:00.0');
-- delegationBy2, delegateFor - ACTIVE
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom)
 values (-11,-7,'delegatedFor','apa','2011-09-10 11:00:00.0');
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom)
 values (-12,-7,'delegatedFor','depa','2011-09-10 11:00:00.0');
-- delegationBy3, delegateFor3 - REJECTED
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto)
 values (-13,-8,'delegatedFor3','apa');
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto)
 values (-14,-8,'delegatedFor3','bepa');
insert into vgr_delegation_to (id, delegationby_id, delegatedFor, delegateto, validFrom, validTo)
 values (-15,-8,'delegatedFor3','cepa','2011-11-05 11:00:00.0','2011-11-15 11:00:00.0');
