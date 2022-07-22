package sn.seye.gestionmatricule.mefpai.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String PROVISEUR = "ROLE_PROVISEUR";
    public static final String DIRECTEUR = "ROLE_DIRECTEUR";
    public static final String BFPA = "ROLE_BFPA";
    public static final String CHEF_TRAVAUX = "ROLE_CHEF_TRAVAUX";
    public static final String COMPTABLE_MATIERE = "ROLE_COMPTABLE_MATIERE";
    public static final String APPRENANT = "ROLE_APPRENANT";

    private AuthoritiesConstants() {}
}
