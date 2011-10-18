package se.vgregion.delegation.domain;

/**
 * @author <a href="mailto:david.rosell@redpill-linpro.com">David Rosell</a>
 */
public enum DelegationStatus {

    /** New delegation - not yet approved */
    PENDING,

    /** Signed delegation - currently active (only one per VerksamhetsChef and OrganizationUnit) */
    ACTIVE,

    /** Signed delegation - has at some time been active */
    SUPERSEDED,

    /** Invalid Delegation - never signed and has never been active */
    REJECTED;
}
