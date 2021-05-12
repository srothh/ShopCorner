package at.ac.tuwien.sepm.groupphase.backend.endpoint.dto;

public class OverviewOperatorDto {

    private long id;
    private String name;
    private String email;
    //private AddressDto address;
    private String permissions;

    public OverviewOperatorDto(long id, String name, String email, AddressDto address, String permissions) {
        this.id = id;
        this.name = name;
        this.email = email;
        //this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }*/

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
