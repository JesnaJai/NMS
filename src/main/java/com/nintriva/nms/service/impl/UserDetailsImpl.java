    package com.nintriva.nms.service.impl;

    import com.nintriva.nms.payload.UserDetailsDto;
    import com.nintriva.nms.repository.UserDetailsRepository;
    import com.nintriva.nms.repository.UserEntityRepository;
    import com.nintriva.nms.response.Response;
    import com.nintriva.nms.service.UserDetails;
    import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
    import org.keycloak.OAuth2Constants;
    import org.keycloak.admin.client.CreatedResponseUtil;
    import org.keycloak.admin.client.Keycloak;
    import org.keycloak.admin.client.KeycloakBuilder;
    import org.keycloak.admin.client.resource.RealmResource;
    import org.keycloak.admin.client.resource.UserResource;
    import org.keycloak.admin.client.resource.UsersResource;
    import org.keycloak.representations.idm.CredentialRepresentation;
    import org.keycloak.representations.idm.UserRepresentation;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.cache.Cache;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;

    import javax.xml.bind.ValidationException;
    import java.util.List;
    import java.util.UUID;
    @Service

    public class UserDetailsImpl implements UserDetails {

    @Autowired
        UserDetailsRepository userDetailsRepository;
    @Autowired
    UserEntityRepository userEntityRepository;
        private String authServerUrl = "http://localhost:8080/auth/";
        private String clientId = "nintriva";
        private String role = "admin";
        //Get client secret from the Keycloak admin console (in the credential tab)
        private String clientSecret = "fEsv6SxscDLbN5JhYTRWKtg2hB7JryLD";
        @Override
        public ResponseEntity<Response> addEmployee(UserDetailsDto userDetailsDto) {


            try {

                if (userDetailsRepository.existsByEmployeeCode(userDetailsDto.getEmployeeCode())) {
                    Response response1 = Response.builder().success(false).message("employee_code is already taken!").build();
                    return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
                }
                // add check for email exists in DB
                if (userDetailsRepository.existsByEmail(userDetailsDto.getEmail())) {
                    Response response2 = Response.builder().success(false).message("Email is already taken!").build();

                    return new ResponseEntity<>(response2, HttpStatus.BAD_REQUEST);
                }
                Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                        .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
                        .username("Admin").password("admin")
                        .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();

                keycloak.tokenManager().getAccessToken();
                com.nintriva.nms.entity.UserDetails ud = new com.nintriva.nms.entity.UserDetails();
                ud.setUserid(UUID.randomUUID());

                UserRepresentation user = new UserRepresentation();
                user.setEnabled(true);
                user.setUsername(userDetailsDto.getEmail());
                user.setFirstName(userDetailsDto.getFirst_name());
                user.setLastName(userDetailsDto.getLast_name());
                user.setEmail(userDetailsDto.getEmail());
//            userEntityRepository.save(user);

                RealmResource realmResource = keycloak.realm("NMS-realm");
                UsersResource usersResource = realmResource.users();

                javax.ws.rs.core.Response response = usersResource.create(user);
                userDetailsDto.setStatusCode(response.getStatus());
                userDetailsDto.setStatus(response.getStatusInfo().toString());

                ud.setEmployeeCode(userDetailsDto.getEmployeeCode());
                ud.setFirst_name(userDetailsDto.getFirst_name());
                ud.setLast_name(userDetailsDto.getLast_name());
                ud.setEmail(userDetailsDto.getEmail());
                ud.setDate_of_birth(userDetailsDto.getDate_of_birth());
                ud.setJoining_date(userDetailsDto.getJoining_date());
                ud.setManager(userDetailsDto.getManager());
                ud.setMobile(userDetailsDto.getMobile());
                ud.setEmployee_address(userDetailsDto.getEmployee_address());
                ud.setEmployment_type(userDetailsDto.getEmployment_type());
                ud.setWork_status(userDetailsDto.getWork_status());
                ud.setSalary(userDetailsDto.getSalary());
                ud.setProbation_status(userDetailsDto.getProbation_status());
                ud.setProbation_period(userDetailsDto.getProbation_period());
                ud.setGender(userDetailsDto.getGender());
                ud.setDesignation(userDetailsDto.getDesignation());
                ud.setPAN_number(userDetailsDto.getPAN_number());
                ud.setDaily_work_hour(userDetailsDto.getDaily_work_hour());
                ud.setWeekly_work_hour(userDetailsDto.getWeekly_work_hour());
                ud.setAadhar_number(userDetailsDto.getAadhar_number());
                ud.setDepartment(userDetailsDto.getDepartment());

                userDetailsRepository.save(ud);
                Response response3 = Response.builder().success(true).message("User created!").build();

                return new ResponseEntity<>(response3, HttpStatus.OK);

            } catch (Exception e) {

                Response response4 = Response.builder().success(false).message("Incomplete Field").build();
                return new ResponseEntity<>(response4, HttpStatus.OK);
            }

        }
    }
