package com.nintriva.nms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "nms_user_details")

public class UserDetails {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type="org.hibernate.type.PostgresUUIDType")
    private UUID userid;
    @NotEmpty(message = "first_name cannot be null")
    private String first_name;
    @NotEmpty(message = "last_name cannot be null")
    private String last_name;
    @NotEmpty(message = "Employee code cannot be null")
    private String employeeCode;
    @NotEmpty
    private String email;
    @NotEmpty(message = "Department cannot be null")
    private String department;
    @Id
    @Nullable private int id;
    @Nullable private String mobile;
    @Nullable private Date date_of_birth;
    @Nullable private Date joining_date;
    @Nullable private String manager ;
    @Nullable private String employee_address ;
    @Nullable private String employment_type ;
    @Nullable private String work_status ;
    @Nullable private int salary;
    @Nullable private String probation_status ;
    @Nullable private String probation_period ;
    @Nullable private String gender;
    @Nullable private String designation;
    @Nullable private String PAN_number;
    @Nullable private String daily_work_hour;
    @Nullable private String weekly_work_hour;
    @Nullable private int aadhar_number;


}
