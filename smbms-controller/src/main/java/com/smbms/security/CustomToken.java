package com.smbms.security;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CustomToken extends UsernamePasswordToken {

    public CustomToken() {
        super();
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted during an authentication attempt, with a <tt>null</tt>
     * {@link #getHost() host} and a <tt>rememberMe</tt> default of <tt>false</tt>.
     *
     * @param username the username submitted for authentication
     * @param password the password character array submitted for authentication
     */
    public CustomToken(final String username, final char[] password) {
        this(username, password, false, null);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted during an authentication attempt, with a <tt>null</tt>
     * {@link #getHost() host} and a <tt>rememberMe</tt> default of <tt>false</tt>
     * <p/>
     * <p>
     * This is a convience constructor and maintains the password internally via a character array, i.e. <tt>password.toCharArray();</tt>. Note that storing a
     * password as a String in your code could have possible security implications as noted in the class JavaDoc.
     * </p>
     *
     * @param username the username submitted for authentication
     * @param password the password string submitted for authentication
     */
    public CustomToken(final String username, final String password) {
        this(username, password != null ? password.toCharArray() : null, false, null);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted, the inetAddress from where the attempt is occurring, and a
     * default <tt>rememberMe</tt> value of <tt>false</tt>
     *
     * @param username the username submitted for authentication
     * @param password the password string submitted for authentication
     * @param host     the host name or IP string from where the attempt is occuring
     * @since 0.2
     */
    public CustomToken(final String username, final char[] password, final String host) {
        this(username, password, false, host);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted, the inetAddress from where the attempt is occurring, and a
     * default <tt>rememberMe</tt> value of <tt>false</tt>
     * <p/>
     * <p>
     * This is a convience constructor and maintains the password internally via a character array, i.e. <tt>password.toCharArray();</tt>. Note that storing a
     * password as a String in your code could have possible security implications as noted in the class JavaDoc.
     * </p>
     *
     * @param username the username submitted for authentication
     * @param password the password string submitted for authentication
     * @param host     the host name or IP string from where the attempt is occuring
     * @since 1.0
     */
    public CustomToken(final String username, final String password, final String host) {
        this(username, password != null ? password.toCharArray() : null, false, host);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted, as well as if the user wishes their identity to be remembered
     * across sessions.
     *
     * @param username   the username submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @since 0.9
     */
    public CustomToken(final String username, final char[] password, final boolean rememberMe) {
        this(username, password, rememberMe, null);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted, if the user wishes their identity to be remembered across
     * sessions, and the inetAddress from where the attempt is ocurring.
     * <p/>
     * <p>
     * This is a convience constructor and maintains the password internally via a character array, i.e. <tt>password.toCharArray();</tt>. Note that storing a
     * password as a String in your code could have possible security implications as noted in the class JavaDoc.
     * </p>
     *
     * @param username   the username submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occuring
     * @since 1.0
     */
    public CustomToken(final String username, final String password, final boolean rememberMe, final String host) {
        this(username, password != null ? password.toCharArray() : null, rememberMe, host);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted, as well as if the user wishes their identity to be remembered
     * across sessions.
     * <p/>
     * <p>
     * This is a convience constructor and maintains the password internally via a character array, i.e. <tt>password.toCharArray();</tt>. Note that storing a
     * password as a String in your code could have possible security implications as noted in the class JavaDoc.
     * </p>
     *
     * @param username   the username submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @since 0.9
     */
    public CustomToken(final String username, final String password, final boolean rememberMe) {
        this(username, password != null ? password.toCharArray() : null, rememberMe, null);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted, if the user wishes their identity to be remembered across
     * sessions, and the inetAddress from where the attempt is ocurring.
     *
     * @param username   the username submitted for authentication
     * @param password   the password character array submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occuring
     * @since 1.0
     */
    public CustomToken(final String username, final char[] password, final boolean rememberMe, final String host) {
        super(username, password, rememberMe, host);

        /**
         * 增加手机登录验证token.
         */
    }

    private String mobile;
    private String verificatecode;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerificatecode() {
        return verificatecode;
    }

    public void setVerificatecode(String verificatecode) {
        this.verificatecode = verificatecode;
    }

    public CustomToken(final String username, final String password, final String mobile, final String verificatecode, final String host) {
        this(username, password != null ? password.toCharArray() : null, false, host);
        this.mobile = mobile;
        this.verificatecode = verificatecode;
    }

    /**
     *
     */
    public static final int Flag_Front_User = 1;
    /**
     *
     */
    public static final int Flag_Admin_User = 2;

    /**
     *
     */
    private int flagFrontOrAdmin = 0;

    /**
     *
     */
    public int getFlagFrontOrAdmin() {
        return flagFrontOrAdmin;
    }

    /**
     *
     */
    public void setFlagFrontOrAdmin(int flagFrontOrAdmin) {
        this.flagFrontOrAdmin = flagFrontOrAdmin;
    }

}
