package com.projectapi.Project_NIC.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Data
@Builder
@Document(collection = "client")
public class Client implements UserDetails {

    @Id
    @JsonIgnore
    private String client_id;
    @JsonIgnore
    private String client_secret;
    private Date created_on;
    private Date expiry_on;

    @Field("mobile_no")
    private long mobileNo;

    @Field("email_id")
    private String emailId;
    private String name;
    private String gender;
    private String dob;
    private String address;

    private Role role;

    @JsonIgnore
    public String getUsername() {
        return client_id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.client_id = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @JsonIgnore
    public String getPassword() {
        return client_secret;

    }

    public void setPassword(String password) {
        this.client_secret = password;
    }

}

