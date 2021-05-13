package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class OverviewOperatorDto {

    private long id;
    private String name;
    private String loginName;
    private String email;
    private String permissions;

    public OverviewOperatorDto(long id, String name, String loginName, String email, String permissions) {
        this.id = id;
        this.name = name;
        this.loginName = loginName;
        this.email = email;
        this.permissions = permissions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
