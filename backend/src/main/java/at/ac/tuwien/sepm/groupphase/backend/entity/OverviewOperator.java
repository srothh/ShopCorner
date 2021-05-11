package at.ac.tuwien.sepm.groupphase.backend.entity;

public class OverviewOperator {

    public enum PermissionType {
        admin,
        employee
    }

    private long id;
    private String name;
    private String mail;
    private Address address;
    private PermissionType permissionType;

    public OverviewOperator(long id, String name, String mail, Address address, PermissionType permissionType) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.address = address;
        this.permissionType = permissionType;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PermissionType getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }
}
