package selab.desktop.resource_management.cloudresourceManagement.domain.vo;

import lombok.Data;

@Data
public class UserVo {

    private String username;
    private String password;
    private String name;
    private Integer userStatus;
    private String email;
}
