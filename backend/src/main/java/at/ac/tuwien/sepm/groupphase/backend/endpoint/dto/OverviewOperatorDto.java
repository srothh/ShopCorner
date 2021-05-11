package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class OverviewOperatorDto {

    private long id;
    private String name;
    private String mail;
    private AddressDto address;
    private String permissionType;

    public OverviewOperatorDto(long id, String name, String mail, AddressDto address, String permissionType) {
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

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }
}
